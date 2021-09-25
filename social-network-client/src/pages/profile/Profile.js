import './profile.css';
import Topbar from '../../components/topbar/Topbar';
import { Add, Chat, Edit, Person } from '@mui/icons-material';
import { Button } from '@mui/material';
import Post from '../../components/post/Post';
import { getUserById } from '../../Api';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { STORAGE } from '../../Constants';
import EditProfileDialog from '../../components/editProfileDialog/EditProfileDialog';

export default function Profile() {
  const { id } = useParams();
  const [user, setUser] = useState(null);
  const [editProfileDialogOpen, setEditProfileDialogOpen] = useState(false);

  useEffect(() => {
    getUserById(id).then(resp => setUser(resp.data))
    .catch(err => window.location = '/notFound')
  }, []);

  function userProp(prop) {
    return user ? user[prop] : '';
  }

  function calculateAge(birthday) {
    if (!birthday)
      return '';
    return (new Date(Date.now() - new Date(birthday).getTime()).getUTCFullYear() - 1970) + ' years';
  }

  function getUserName() {
    if (!userProp('name') && !userProp('surname'))
      return userProp('login');
    if (!userProp('name'))
      return userProp('surname');
    if (!userProp('surname'))
      return userProp('name')
    return userProp('name') + ' ' + userProp('surname');
  }

  return (
    <>
      <Topbar/>
      <div className="profile">
        <div className="profileTop">
          <div className="profileTopLeft">
            <div className="profilePhotoAndUsername">
              <Person className="profilePhoto"/>
              <span className="profileUsername">{getUserName()}</span>
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
            <span className="profileInfoValue">16</span>
          </div>
          <div className="profileInfoItem">
            <span className="profileInfoKey">Users followed:</span>
            <span className="profileInfoValue">34</span>
          </div>
          {localStorage.getItem(STORAGE.userId) != id && (<>
          <Button variant="contained" startIcon={<Add/>} className="profileButtonFollow">Follow</Button>
          <Button variant="contained" startIcon={<Chat/>} className="profileButtonChat">Text</Button>
          </>)}
          {localStorage.getItem(STORAGE.userId) == id && (<>
          <Button variant="contained"
                  startIcon={<Edit/>}
                  onClick={() => setEditProfileDialogOpen(true)}
                  className="profileButtonEdit">Edit profile</Button>
          </>)}
        </div>
      </div>
      <Post/>
      <Post/>
      <Post/>
      {user && (<EditProfileDialog
        open={editProfileDialogOpen}
        onClose={() => setEditProfileDialogOpen(false)}
        user={user}
      />)}
    </>
  );
}