import './post.css';
import { Comment, Person, ThumbUp } from '@mui/icons-material';
import { getCurrentUser, getUsername } from '../../Common';
import CommentListDialog from '../commentListDialog/CommentListDialog';
import { getComments, getUsersByIds } from '../../Api';
import { useState } from 'react';

export default function Post(props) {
  const { post, user } = props;
  const [commentsDialogOpen, setCommentsDialogOpen] = useState(false);
  const [comments, setComments] = useState([]);

  const handleClickOpenComments = () => {
    getComments(post.id).then(resp => {
      let commentsResp = resp.data;
      getUsersByIds({userIds: [...new Set(commentsResp.map(comm => comm.userId))] }).then(usersResp => {
        const userIdToUser = new Map(usersResp.data.map(user => [user.id, user]));
        commentsResp.forEach(comm => comm.user = userIdToUser.get(comm.userId));
        console.log(commentsResp);
        setComments(commentsResp);
      })
    });
    setCommentsDialogOpen(true);
  };

  const handleCloseComments = () => {
    setCommentsDialogOpen(false);
  };

  function addNewComment(comment) {
    comment.user = getCurrentUser();
    setComments(comments.concat(comment));
  }

  return (
    <>
      <div className="post">
        <div className="postWrapper">
          <div className="postTop">
            <Person/>
            <span className="postUsername">{getUsername(user)}</span>
            <span className="postDate">{new Date(post.timestamp).toLocaleString()}</span>
          </div>

          <div className="postCenter">
            <div className="postText">{post.content}</div>
          </div>
          <div className="postBottom">
            <div className="postLikes">
              <ThumbUp/>
              <span className="postLikeCounter">112 likes</span>
            </div>
            <div className="postComments">
              <Comment/>
              <span className="postCommentCounter" onClick={handleClickOpenComments}>See comments</span>
            </div>
          </div>
        </div>
      </div>
      <CommentListDialog
        open={commentsDialogOpen}
        onClose={handleCloseComments}
        comments={comments}
        feedId={post.id}
        pushbackCommentToList={addNewComment}
      />
    </>
  );
}