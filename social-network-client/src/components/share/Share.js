import './share.css';
import { AddPhotoAlternate, Send } from '@mui/icons-material';
import { Button, TextareaAutosize } from '@mui/material';
import { useState } from 'react';
import { createNewFeed, getUsersByIds, getUsersByLogins, sendNotification } from '../../Api';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { toastError } from '../../Common';
import { MESSAGES } from '../../Constants';

export default function Share(props) {
  const { addNewFeed, getCurrentUser } = props;
  const [postMessage, setPostMessage] = useState('');
  const loggedUserId = getCurrentUser().id;

  function send() {
    if (!postMessage || postMessage.length < 10) {
      toast.warn('You need to specify at least 10 characters.', {
        position: 'top-center',
        hideProgressBar: true,
        autoClose: 3000,
        pauseOnHover: false,
      });
      return;
    }

    let enrichedPostWithTagsPromise = enrichPostWithTags(postMessage);
    enrichedPostWithTagsPromise.then(content => {
      setPostMessage('');
      createNewFeed({ authorId: loggedUserId, content: content })
      .then(resp => {
        let newFeed = resp.data;
        newFeed.user = getCurrentUser();
        addNewFeed(newFeed);
        toast.success('Feed has been published!', {
          position: 'top-center',
          hideProgressBar: true,
          autoClose: 3000,
          pauseOnHover: false,
        });
      }).catch(() => toastError(MESSAGES.requestError));
    });

  }

  async function enrichPostWithTags(content) {
    let foundTags = content.match(/ @([a-zA-Z0-9]+)/g);
    if (!foundTags)
      return content;
    let taggedUsernames = foundTags.map(tag => tag.substr(2));
    const usersResp = await getUsersByLogins({ userLogins: [...new Set(taggedUsernames)] })
    .catch(() => {
      toastError(MESSAGES.requestError);
    });
    let resContent = content;
    usersResp.data.forEach(taggedUser => {
      resContent = resContent.replaceAll(' @'+taggedUser.login, '@(id={' + taggedUser.id + '};login={' + taggedUser.login + '})');
    });
    usersResp.data.forEach(taggedUser => sendNotification(taggedUser.id, 'User ' + getCurrentUser().login + ' has tagged you in his post.'))
    return resContent;
  }

  return (
    <div className="share">
      <div className="shareWrapper">
        <TextareaAutosize
          value={postMessage}
          onInput={e => setPostMessage(e.target.value)}
          placeholder="write a new post"
          className="shareInput"
          style={{ resize: 'none' }}
          minRows={1}
        />
        <hr/>
        <div className="shareBottom">
          <Button variant="contained"
                  startIcon={<AddPhotoAlternate/>}
                  disabled={true}
                  sx={{ color: '#798a96', backgroundColor: '#1f2b36' }}
                  className="Button">
            Add a photo
          </Button>
          <Button variant="contained"
                  endIcon={<Send/>}
                  onClick={send}
                  disabled={getCurrentUser().blocked}
                  sx={{ color: '#798a96', backgroundColor: '#1f2b36' }}
                  className="Button">
            Share
          </Button>
        </div>
      </div>
    </div>
  );
}