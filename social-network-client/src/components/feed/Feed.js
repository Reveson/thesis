import Share from '../share/Share';
import Post from '../post/Post';
import { useEffect, useState } from 'react';
import { getFeedsBySubscriber, getUsersByIds } from '../../Api';
import { toastError } from '../../Common';
import { MESSAGES } from '../../Constants';

export default function Feed(props) {
  const { getCurrentUser } = props;
  const [feeds, setFeeds] = useState([]);
  const loggedUserId = getCurrentUser().id;

  function addNewFeed(feed) {
    setFeeds(feeds.concat(feed)); //TODO username
  }


  useEffect(() => {

    getFeedsBySubscriber(loggedUserId).then(resp => {
      let feedsResp = resp.data;
      getUsersByIds({ userIds: [...new Set(feedsResp.map(feed => feed.userId))] })
      .then(usersResp => {
        let userIdToUser = new Map(usersResp.data.map(user => [user.id, user]));
        feedsResp.forEach(feed => feed.user = userIdToUser.get(feed.userId));
        setFeeds(feedsResp);
      }).catch(() => {
        toastError(MESSAGES.partialRequestError);
        setFeeds([]);
      });
    }).catch(() => {
      toastError(MESSAGES.partialRequestError);
      setFeeds([]);
    });

  }, []);


  return (
    <div className="feed">
      <Share addNewFeed={addNewFeed} getCurrentUser={getCurrentUser}/>
      {feeds.sort((a, b) => (b.timestamp - a.timestamp) || 0)
      .map(post => (<Post key={post.timestamp} post={post} user={post.user} getCurrentUser={getCurrentUser}/>))}
    </div>
  );
}