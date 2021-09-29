import './chat.css';
import Topbar from '../../components/topbar/Topbar';
import Conversation from '../../components/conversation/Conversation';
import Message from '../../components/message/Message';
import { Send } from '@mui/icons-material';
import { Button } from '@mui/material';
import { useEffect, useState} from 'react';
import { getChatUserIds, getMessages, getUsersByIds, sendMessage } from '../../Api';
import { STORAGE } from '../../Constants';
import { useLocation } from 'react-router-dom'

export default function Chat() {
  const [messages, setMessages] = useState([]);
  const [chatUsers, setChatUsers] = useState([]);
  const [textMessage, setTextMessage] = useState('');
  const [selectedChatUser, setSelectedChatUser] = useState(null);
  const initialRecipient = useLocation().recipient;

  useEffect(() => {
    getChatUserIds(localStorage.getItem(STORAGE.userId))
    .then(resp => {
      setChatUsers(resp.data.map(idElem => {
        return { id: idElem };
      }));
      getUsersByIds({ userIds: resp.data }).then(resp => {
        setChatUsers(resp.data);
      });
    })
  }, []);

  const selectChatUser = (user) => {
    setSelectedChatUser(user);
    getMessages(localStorage.getItem(STORAGE.userId), user.id)
    .then(resp => setMessages(resp.data))
  };

  const send = () => {
    if (textMessage === '')
      return;

    sendMessage(localStorage.getItem(STORAGE.userId),
      {recipientId: selectedChatUser.id, content: textMessage})
    .then(resp => setMessages(messages.concat(resp.data)))
    setTextMessage('');
  }

  if (initialRecipient && !selectedChatUser)
    selectChatUser(initialRecipient);
  return (
    <div>
      <Topbar/>
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
    </div>
  );
}