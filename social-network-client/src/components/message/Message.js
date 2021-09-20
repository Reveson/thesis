import './message.css'
import { Person } from '@mui/icons-material';

export default function Message({own}) {
  return (
    <div className={own ? 'message own' : 'message'}>
      <div className='messageTop'>
        <Person/>
        <p className='messageText'>Hello, how are you?</p>
      </div>
      <div className='messageBottom'>
        1 hour ago
      </div>
    </div>
  )
}