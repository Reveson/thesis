import './conversation.css';
import { Person } from '@mui/icons-material';
import { CircularProgress } from '@mui/material';

export default function Conversation(props) {
  const { user, onSelected } = props;

  function getUsername() {
    if (!user)
      return '';

    let username = '';

    username += user.name ? user.name + ' ' : '';
    username += user.surname ? user.surname : '';

    if (username)
      return username;
    return user.login
  }

  return (
    <div className="conversation" onClick={() => onSelected(user)}>
      {getUsername() == '' && (<CircularProgress/>)}
      {getUsername() != '' && (<>
        <Person/>
        <span className="conversationName">{getUsername()}</span>
      </>)}
    </div>
  );
}