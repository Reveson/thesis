import './post.css';
import { Comment, Delete, Person, ThumbUp } from '@mui/icons-material';
import { getUsername, toastError, toastSuccess } from '../../Common';
import CommentListDialog from '../commentListDialog/CommentListDialog';
import { addReaction, deleteFeed, getComments, getCommentsCount, getReactions, getUsersByIds, removeReaction } from '../../Api';
import { useEffect, useState } from 'react';
import { MESSAGES } from '../../Constants';

export default function Post(props) {
  const { post, user, getCurrentUser } = props;
  const [commentsDialogOpen, setCommentsDialogOpen] = useState(false);
  const [comments, setComments] = useState([]);
  const [commentsCount, setCommentsCount] = useState(0)
  const [reactions, setReactions] = useState({count: 0, alreadyReacted: undefined}) //TODO typo

  const handleClickOpenComments = () => {
    getComments(post.id).then(resp => {
      let commentsResp = resp.data;
      getUsersByIds({userIds: [...new Set(commentsResp.map(comm => comm.userId))] }).then(usersResp => {
        const userIdToUser = new Map(usersResp.data.map(user => [user.id, user]));
        commentsResp.forEach(comm => comm.user = userIdToUser.get(comm.userId));
        setComments(commentsResp);
        setCommentsDialogOpen(true);
      }).catch(() => toastError(MESSAGES.requestError));
    }).catch(() => toastError(MESSAGES.requestError));
  };

  const handleCloseComments = () => {
    setCommentsDialogOpen(false);
  };

  function addNewComment(comment) {
    comment.user = getCurrentUser();
    setComments(comments.concat(comment));
  }

  function handleNewReaction() {
    if (reactions.alreadyReacted === undefined || getCurrentUser().blocked)
      return;

    if (reactions.alreadyReacted) {
      removeReaction(post.id, getCurrentUser().id)
      setReactions({ count: reactions.count - 1, alreadyReacted: !reactions.alreadyReacted })
    } else {
      addReaction(post.id, getCurrentUser().id)
      setReactions({ count: reactions.count + 1, alreadyReacted: !reactions.alreadyReacted })
    }
  }

  function handleDeletePost() {
    if (window.confirm("Are you sure you want to delete this post?")) {
      deleteFeed(user.id, post.id)
      .then(() => toastSuccess("The post had been deleted.\nPlease reload the page.", 3000));
    }
  }

  function decodeTags(content) {
    return content.replaceAll(/@\(id={([0-9]+)};login={([a-zA-Z0-9]+)}\)/g, ' <a href=\'/user/$1\'>@$2</a>')
  }

  useEffect(() => {
    getReactions(post.id, getCurrentUser().id)
    .then(resp => setReactions(resp.data))
    .catch(() => toastError(MESSAGES.partialRequestError));
  }, [])

  useEffect(() => {
    getCommentsCount(post.id)
    .then(resp => setCommentsCount(resp.data))
    .catch(() => toastError(MESSAGES.partialRequestError));
  }, [comments])

  return (
    <>
      <div className="post">
        <div className="postWrapper">
          <div className="postTop">
            <Person/>
            <span className="postUsername">{getUsername(user)}</span>
            <span className="postDate">{new Date(post.timestamp).toLocaleString()}</span>
            {getCurrentUser().admin && <div className="deletePost" onClick={handleDeletePost}><Delete/></div>}
          </div>

          <div className="postCenter">
            <div className="postText" dangerouslySetInnerHTML={{ __html: decodeTags(post.content) }} />
          </div>
          <div className="postBottom">
            <div className="postLikes" onClick={handleNewReaction}>
              <ThumbUp/>
              <span className="postLikeCounter">{reactions.count + (reactions.count === 1 ? " like" : " likes")}</span>
            </div>
            <div className="postComments" onClick={handleClickOpenComments}>
              <Comment/>
              <span className="postCommentCounter">{commentsCount + (commentsCount === 1 ? " comment" : " comments")}</span>
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
        getCurrentUser={getCurrentUser}
      />
    </>
  );
}