import './conversation.css';
import { Person } from '@mui/icons-material';
import { CircularProgress } from '@mui/material';
import { getUsername } from '../../Common';

export default function Conversation(props) {
  const { user, onSelected } = props;

  return (
    <div className="conversation" onClick={() => onSelected(user)}>
      {!getUsername(user) && (<CircularProgress/>)}
      {getUsername(user) && (<>
        <Person/>
        <span className="conversationName">{getUsername(user)}</span>
      </>)}
    </div>
  );
}