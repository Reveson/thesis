import './message.css';
import { Person } from '@mui/icons-material';
import { getCurrentUser } from '../../Common';

export default function Message(props) {
  const {message} = props;
  const own = getCurrentUser().id == message.authorId


  return (
    <div className={own ? 'message own' : 'message'}>
      <div className='messageTop'>
        <Person/>
        <p className='messageText'>{message.content}</p>
      </div>
      <div className='messageBottom'>
        {new Date(message.timestamp).toLocaleString()}
      </div>
    </div>
  )
}