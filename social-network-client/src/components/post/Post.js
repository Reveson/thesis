import './post.css'
import { Comment, Person, ThumbUp } from '@mui/icons-material';
import { getUsername } from '../../Common';

export default function Post(props) {
  const {post, user} = props;


  return (
    <div className='post'>
      <div className='postWrapper'>
        <div className='postTop'>
          <Person/>
          <span className='postUsername'>{getUsername(user)}</span>
          <span className='postDate'>{new Date(post.timestamp).toLocaleString()}</span>
        </div>

        <div className='postCenter'>
          <div className='postText'>{post.content}</div>
        </div>
        <div className='postBottom'>
          <div className='postLikes'>
            <ThumbUp/>
            <span className='postLikeCounter'>112 likes</span>
          </div>
          <div className='postComments'>
            <Comment/>
            <span className='postCommentCounter'>38 comments</span>
          </div>
        </div>
      </div>
    </div>
  )
}