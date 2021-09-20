import './conversation.css'
import { Person } from '@mui/icons-material';

export default function Conversation() {
  return (
    <div className='conversation'>
      <Person/>
      <span className='conversationName'>John Smith</span>
    </div>
  )
}