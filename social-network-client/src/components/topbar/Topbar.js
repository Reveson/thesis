import './topbar.css';
import { Chat, Person, Search } from '@mui/icons-material';
import TopbarDropdownItem from '../topbarDropdownItem/TopbarDropdownItem';
import NotificationList from '../notificationList/NotificationList';
import UserListDialog from '../userListDialog/UserListDialog';
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { STORAGE } from '../../Constants';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getUsersByIds, getUsersIdsFollowed } from '../../Api';

export default function Topbar() {
  const [usersDialogOpen, setUsersDialogOpen] = useState(false);
  const [usersFollowed, setUsersFollowed] = useState([]);
  const userLogin = localStorage.getItem(STORAGE.userLogin);
  const userId = localStorage.getItem(STORAGE.userId);

  const handleClickOpenUsers = () => {
    getUsersIdsFollowed(userId).then(resp => {
      let userIds = resp.data;
      setUsersFollowed(userIds.map(userId => {
        return { id: userId }
      }));
      getUsersByIds({ userIds: userIds }).then(usersResp => {
        setUsersFollowed(usersResp.data);
      });
    });
    setUsersDialogOpen(true);
  };

  const handleCloseUsers = () => {
    setUsersDialogOpen(false);
  };

  return (
    <>
      <div className="topbar">
        <Link to="/" className="topbarLogo">Social Network App</Link>
        <div className="searchBar">
          <Search/>
          <input placeholder="Find a user" className="searchInput"/>
        </div>
        <div className="topbarIcons">
          <div className="topbarIconGroup">
            <div className="topbarIconItem" onClick={handleClickOpenUsers}>
              <Person/>
            </div>
          </div>
          <div className="topbarIconGroup">
            <Link to='/chat' className="topbarIconItem">
              <Chat/>
              <span className="topbarIconBadge">1</span>
            </Link>
          </div>
          <div className="topbarIconGroup">
            <div className="topbarIconItem">
              <TopbarDropdownItem>
                <NotificationList/>
                <NotificationList/>
                <NotificationList/>
              </TopbarDropdownItem>
              <span className="topbarIconBadge">1</span>
            </div>
          </div>
          <span>
            {userLogin ? 'Hello, ' + userLogin : ''}
          </span>
        </div>
      </div>
      <UserListDialog
        open={usersDialogOpen}
        onClose={handleCloseUsers}
        users = {usersFollowed}
      />
      <ToastContainer/>
    </>
  );
}