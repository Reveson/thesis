import './chat.css';
import Topbar from '../../components/topbar/Topbar';
import Conversation from '../../components/conversation/Conversation';
import Message from '../../components/message/Message';
import { Send } from '@mui/icons-material';
import { Button } from '@mui/material';

export default function Chat() {
  return (
    <div>
      <Topbar/>
      <div className="chat">
        <div className="chatConversations">
          <div className="chatConversationsWrapper">
            <Conversation/>
            <Conversation/>
          </div>
        </div>
        <div className="chatBox">
          <div className="chatBoxWrapper">
            <div className="chatBoxTop">
              <Message own={true}/>
              <Message/>
              <Message own={true}/>
              <Message/>
            </div>
            <div className="chatBoxBottom">
              <textarea placeholder="Write a message..." className="chatMessageInput"/>
              <Button variant="contained"
                      startIcon={<Send/>}
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