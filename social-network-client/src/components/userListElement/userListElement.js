import './userListElement.css';
import { Add, Chat, Person } from '@mui/icons-material';
import { Link } from 'react-router-dom';

export default function UserListElement() {
  return (
    <div className="userListElement">
      <Link to='/user/:id' className="usernameWithPhoto">
        <Person/>
        <span className="username">John Smith</span>
      </Link>
      <Add/>
      <Chat/>
    </div>
  );
}