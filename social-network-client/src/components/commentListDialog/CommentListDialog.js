import './commentListDialog.css';
import { Button, Dialog, DialogContent, DialogContentText, DialogTitle, TextareaAutosize } from '@mui/material';
import Comment from '../comment/Comment';
import { Send } from '@mui/icons-material';
import { useState } from 'react';
import { toastError, toastSuccess, toastWarn } from '../../Common';
import { addComment } from '../../Api';
import { MESSAGES } from '../../Constants';

export default function CommentListDialog(props) {
  const { open, onClose, comments, feedId, pushbackCommentToList } = props;
  const [commentMessage, setCommentMessage] = useState('');


  function send() {
    if (!commentMessage || commentMessage.length < 5) {
      toastWarn('Your comment is too short.');
      return;
    }

    let content = commentMessage;
    setCommentMessage('');
    addComment(feedId, content)
    .then(resp => {
      pushbackCommentToList(resp.data);
      toastSuccess('Comment has been published!');
    })
    .catch(() => toastError(MESSAGES.requestError));

  }

  return (
    <div className="commentListDialog">
      <Dialog open={open}
              onClose={onClose}>
        <DialogTitle>Comments</DialogTitle>
        <DialogContent>
          <DialogContentText component={'span'}>
            <div className="comments">
              <div className="commentsWrapper">
                {
                  comments.sort((a, b) => (a.timestamp - b.timestamp) || 0)
                  .map(comment => (<Comment key={comment.timestamp} comment={comment}/>))
                }
              </div>
            </div>
            <hr/>
            <div className="addComment">
              <TextareaAutosize
                value={commentMessage}
                onInput={e => setCommentMessage(e.target.value)}
                placeholder="write a new comment"
                className="shareInput"
                style={{ resize: 'none' }}
                minRows={1}
              />
              <div className="sendButtonDiv">
                <Button variant="contained"
                        endIcon={<Send/>}
                        size="small"
                        style={{ transform: 'scale(0.8)', backgroundColor: 'lightgray', color: 'black' }}
                        onClick={send}
                        className="Button">
                  Add a comment
                </Button>
              </div>
            </div>
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  );
}