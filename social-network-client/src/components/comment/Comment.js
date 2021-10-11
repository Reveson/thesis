import './comment.css';
import { Person } from '@mui/icons-material';
import { getUsername } from '../../Common';

export default function Comment(props) {
  const { comment} = props

  return (
    <div className='comment'>
      <div className='commentWrapper'>
        <div className='leftSide'>
          <Person/>
        </div>
        <div className='rightSide'>
          <span className='username'>{getUsername(comment.user)}</span>
          <span className='content'>{comment.content}</span>
          <span className='commentDate'>{new Date(comment.timestamp).toLocaleString()}</span>
        </div>
      </div>
    </div>
  );
}