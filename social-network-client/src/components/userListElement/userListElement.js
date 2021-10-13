import './userListElement.css';
import { Chat, Person } from '@mui/icons-material';
import { Link } from 'react-router-dom';
import { getUsername } from '../../Common';

export default function UserListElement(props) {
  const { user, redirectToUserPage } = props;


  return (
    <div className="userListElement">
      <a onClick={redirectToUserPage} className="usernameWithPhoto">
        <Person/>
        <span className="username">{getUsername(user)}</span>
      </a>
      <Link to={{ pathname: '/chat', recipient: user }}>
        <Chat className="chatButton"/>
      </Link>
    </div>
  );
}