import Share from '../share/Share';
import Post from '../post/Post';
import { useEffect, useState } from 'react';
import { STORAGE } from '../../Constants';
import { getFeedsBySubscriber, getUsersByIds } from '../../Api';

export default function Feed() {
  const [feeds, setFeeds] = useState([]);
  const loggedUserId = localStorage.getItem(STORAGE.userId);

  function addNewFeed(feed) {
    setFeeds(feeds.concat(feed)); //TODO username
  }


  useEffect(() => {

    getFeedsBySubscriber(loggedUserId).then(resp => {
      let feedsResp = resp.data;
      getUsersByIds({userIds: [...new Set(feedsResp.map(feed => feed.userId))] }).then(usersResp => {
        let userIdToUser = new Map(usersResp.data.map(user => [user.id, user]));
        feedsResp.forEach(feed => feed.user = userIdToUser.get(feed.userId));
        setFeeds(feedsResp);
      })
    })
  }, []);


  return (
    <div className='feed'>
      <Share addNewFeed={addNewFeed}/>
      {feeds.sort((a, b) => (b.timestamp - a.timestamp) || 0)
      .map(post => (<Post key={post.timestamp} post={post} user={post.user}/>))}
    </div>
  )
}