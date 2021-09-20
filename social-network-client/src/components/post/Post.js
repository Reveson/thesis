import './post.css'
import { Comment, Person, ThumbUp } from '@mui/icons-material';

export default function Post() {
  return (
    <div className='post'>
      <div className='postWrapper'>
        <div className='postTop'>
          <Person/>
          <span className='postUsername'>John Smith</span>
          <span className='postDate'>5 min ago</span>
        </div>

        <div className='postCenter'>
          <div className='postText'>Hello there! Its my post!</div>
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