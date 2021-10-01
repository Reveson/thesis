import './profile.css';
import Topbar from '../../components/topbar/Topbar';
import { Add, Chat, Edit, Person, Remove } from '@mui/icons-material';
import { Button } from '@mui/material';
import Post from '../../components/post/Post';
import {
  followUser,
  getFeedsByAuthor,
  getNumberOfFollowers,
  getNumberOfUsersFollowed,
  getUserById,
  isUserFollowed,
  unfollowUser,
} from '../../Api';
import { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { STORAGE } from '../../Constants';
import EditProfileDialog from '../../components/editProfileDialog/EditProfileDialog';
import { getUsername } from '../../Common';

export default function Profile() {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [editProfileDialogOpen, setEditProfileDialogOpen] = useState(false);
  const [followedUsers, setFollowedUsers] = useState(0);
  const [followingUsers, setFollowingUsers] = useState(0);
  const [isFollowed, setIsFollowed] = useState(null);
  const [feeds, setFeeds] = useState([]);
  const loggedUserId = localStorage.getItem(STORAGE.userId);

  useEffect(() => {
    getUserById(id).then(resp => setUser(resp.data))
    .catch(err => window.location = '/notFound');

    getNumberOfFollowers(id).then(resp => setFollowingUsers(resp.data));
    getNumberOfUsersFollowed(id).then(resp => setFollowedUsers(resp.data));
    isUserFollowed(localStorage.getItem(STORAGE.userId), id).then(resp => setIsFollowed(resp.data));

    getFeedsByAuthor(id).then(resp => setFeeds(resp.data))

  }, []);

  function userProp(prop) {
    return user ? user[prop] : '';
  }

  function calculateAge(birthday) {
    if (!birthday)
      return '';
    return (new Date(Date.now() - new Date(birthday).getTime()).getUTCFullYear() - 1970) + ' years';
  }

  function onFollowClick() {
    const changeFollow =
      isFollowed ? unfollowUser : followUser;

    changeFollow().then(() => {
      setFollowingUsers(followingUsers + (isFollowed ? -1 : 1));
      setIsFollowed(!isFollowed);
    });
  }

  return (
    <>
      <Topbar/>
      <div className="profile">
        <div className="profileTop">
          <div className="profileTopLeft">
            <div className="profilePhotoAndUsername">
              <Person className="profilePhoto"/>
              <span className="profileUsername">{getUsername(user)}</span>
            </div>
            <div className="profileLogin">
              <span>{user ? '@' + user['login'] : ''}</span>
            </div>
          </div>
          <div className="profileTopRight">
            <div className="profileInfoItem">
              <span className="profileInfoKey">city:</span>
              <span className="profileInfoValue">{userProp('city')}</span>
            </div>
            <div className="profileInfoItem">
              <span className="profileInfoKey">age:</span>
              <span className="profileInfoValue">{calculateAge(userProp('birthDate'))}</span>
            </div>
            <div className="profileInfoItem">
              <span className="profileInfoKey">birthday:</span>
              <span className="profileInfoValue">{userProp('birthDate')}</span>
            </div>
          </div>
        </div>
        <div className="profileBottom">
          <div className="profileInfoItem">
            <span className="profileInfoKey">Users following:</span>
            <span className="profileInfoValue">{followingUsers}</span>
          </div>
          <div className="profileInfoItem">
            <span className="profileInfoKey">Users followed:</span>
            <span className="profileInfoValue">{followedUsers}</span>
          </div>
          <Button variant="contained"
                  startIcon={isFollowed ? <Remove/> : <Add/>}
                  disabled={isFollowed === null}
                  sx={{ display: (loggedUserId != id ? '' : 'none') }}
                  onClick={onFollowClick}
                  className="profileButtonFollow">
            {isFollowed ? 'Unfollow' : 'Follow'}
          </Button>
          <Link to={{ pathname: '/chat', recipient: user}} >
            <Button variant="contained"
                    startIcon={<Chat/>}
                    sx={{ display: (loggedUserId != id ? '' : 'none') }}
                    className="profileButtonChat">
              Text
            </Button>
          </Link>
          <Button variant="contained"
                  startIcon={<Edit/>}
                  onClick={() => setEditProfileDialogOpen(true)}
                  sx={{ display: (loggedUserId == id ? '' : 'none') }}
                  className="profileButtonEdit">
            Edit profile
          </Button>
        </div>
      </div>
      {feeds.sort((a, b) => (b.timestamp - a.timestamp) || 0)
      .map(post => (<Post key={post.timestamp} post={post} user={user}/>))}
      {user && (<EditProfileDialog
        open={editProfileDialogOpen}
        onClose={() => setEditProfileDialogOpen(false)}
        user={user}
      />)}
    </>
  );
}