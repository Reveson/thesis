import './userListElement.css';
import { Add, Chat, Person } from '@mui/icons-material';
import { Link } from 'react-router-dom';
import { STORAGE } from '../../Constants';
import { getUsername } from '../../Common';

export default function UserListElement(props) {
  const { user } = props;


  return (
    <div className="userListElement">
      <a onClick={() => window.location.href = '/user/' + user.id} className="usernameWithPhoto">
        <Person/>
        <span className="username">{getUsername(user)}</span>
      </a>
      <Link to={{ pathname: '/chat', recipient: user }}>
        <Chat className="chatButton"/>
      </Link>
    </div>
  );
}