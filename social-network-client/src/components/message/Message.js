import './message.css'
import { Person } from '@mui/icons-material';
import { STORAGE } from '../../Constants';

export default function Message(props) {
  const {message} = props;
  const own = localStorage.getItem(STORAGE.userId) == message.authorId


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