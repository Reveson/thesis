import './chat.css';
import Topbar from '../../components/topbar/Topbar';
import Conversation from '../../components/conversation/Conversation';
import Message from '../../components/message/Message';
import { Send } from '@mui/icons-material';
import { Button } from '@mui/material';
import { useEffect, useState } from 'react';
import { getChatUserIds, getMessages, getUsersByIds, sendMessage } from '../../Api';
import { useLocation } from 'react-router-dom';
import { toastError, useInterval } from '../../Common';
import BottomBar from '../../components/bottombar/BottomBar';
import { MESSAGES } from '../../Constants';

export default function Chat(props) {
  const { getCurrentUser } = props;
  const [messages, setMessages] = useState([]);
  const [chatUsers, setChatUsers] = useState([]);
  const [textMessage, setTextMessage] = useState('');
  const [selectedChatUser, setSelectedChatUser] = useState(null);
  const initialRecipient = useLocation().recipient;
  const currentUserId = getCurrentUser().id;

  useEffect(() => {
    getChatUserIds(currentUserId)
    .then(resp => {
      setChatUsers(resp.data.map(idElem => {
        return { id: idElem };
      }));
      getUsersByIds({ userIds: resp.data }).then(resp => {
        setChatUsers(resp.data);
      }).catch(() => {
        setChatUsers([]);
        toastError(MESSAGES.messagesRequestError);
      });
    }).catch(() => {
      setChatUsers([]);
      toastError(MESSAGES.messagesRequestError);
    });
  }, []);

  const selectChatUser = (user) => {
    setSelectedChatUser(user);
    getMessages(currentUserId, user.id)
    .then(resp => setMessages(resp.data))
    .catch(() => {
      setMessages([]);
      toastError(MESSAGES.messagesRequestError);
    });
  };

  useInterval(() => { //TODO incremental
    if (selectedChatUser)
      getMessages(currentUserId, selectedChatUser.id)
      .then(resp => {
        const data = resp.data;
        setMessages(data);
      }).catch(() => {});
  }, 3000);

  const send = () => {
    if (textMessage === '')
      return;

    sendMessage(currentUserId, { recipientId: selectedChatUser.id, content: textMessage })
    .then(resp => {
      const newMessage = resp.data;
      setMessages(messages.concat(newMessage));
    })
    .catch(() => toastError(MESSAGES.requestError));
    setTextMessage('');
  };

  if (initialRecipient && !selectedChatUser)
    selectChatUser(initialRecipient);
  return (
    <>
      <Topbar getCurrentUser={getCurrentUser}/>
      <div className="chat">
        <div className="chatConversations">
          <div className="chatConversationsWrapper">
            {chatUsers.map(user => (<Conversation key={user.id} user={user} onSelected={selectChatUser}/>))}
          </div>
        </div>
        <div className="chatBox">
          <div className={'noMessageInfo' + (selectedChatUser != null ? ' hidden' : '')}>
            Select user to open conversation.
          </div>
          <div className={'chatBoxWrapper' + (selectedChatUser == null ? ' hidden' : '')}>
            <div className="chatBoxTop">
              <div className="chatBoxTopWrapper">
                {messages.sort((a, b) => (b.timestamp - a.timestamp) || 0)
                .reverse().map(message => (<Message key={message.timestamp} message={message}/>))}
              </div>
            </div>
            <div className="chatBoxBottom">
              <textarea placeholder="Write a message..."
                        value={textMessage}
                        onInput={e => setTextMessage(e.target.value)}
                        className="chatMessageInput"/>
              <Button variant="contained"
                      startIcon={<Send/>}
                      onClick={send}
                      sx={{ color: '#798a96', backgroundColor: '#1f2b36' }}

                      className="chatSubmitButton">
                Send
              </Button>
            </div>
          </div>
        </div>
      </div>
      <BottomBar/>
    </>
  );
}