import './topbar.css';
import { AccountCircle, Chat, Person } from '@mui/icons-material';
import TopbarDropdownItem from '../topbarDropdownItem/TopbarDropdownItem';
import NotificationList from '../notificationList/NotificationList';
import UserListDialog from '../userListDialog/UserListDialog';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getNotifications, getNumberOfNotifications, getNumberOfUnreadMessages, getUsersByIds, getUsersIdsFollowed } from '../../Api';
import { toastError } from '../../Common';
import SearchBar from '../searchbar/SearchBar';
import { MESSAGES } from '../../Constants';

export default function Topbar(props) {
  const { getCurrentUser } = props;
  const [usersDialogOpen, setUsersDialogOpen] = useState(false);
  const [usersFollowed, setUsersFollowed] = useState([]);
  const [unreadNotifications, setUnreadNotifications] = useState(0);
  const [notificationsList, setNotificationsList] = useState([]);
  const [unreadMessages, setUnreadMessages] = useState(0);
  const currentUser = getCurrentUser();

  useEffect(() => {
    getNumberOfNotifications(currentUser.id).then(resp => setUnreadNotifications(resp.data)).catch(() => setUnreadNotifications(0));
    getNumberOfUnreadMessages(currentUser.id).then(resp => setUnreadMessages(resp.data)).catch(() => setUnreadMessages(0));
  }, []);

  const handleClickOpenUsers = () => {
    getUsersIdsFollowed(currentUser.id).then(resp => {
      let userIds = resp.data;
      setUsersFollowed(userIds.map(userId => {
        return { id: userId };
      }));
      getUsersByIds({ userIds: userIds }).then(usersResp => {
        setUsersFollowed(usersResp.data);
        setUsersDialogOpen(true);
      }).catch(() => {
        setUsersFollowed([]);
        toastError(MESSAGES.requestError);
      });
    }).catch(() => {
      setUsersFollowed([]);
      toastError(MESSAGES.requestError);
    });
  };

  const handleCloseUsers = () => {
    setUsersDialogOpen(false);
  };

  const handleClickOpenNotifications = () => {
    getNotifications(currentUser.id)
    .then(resp => setNotificationsList(resp.data))
    .catch(() => {
      setNotificationsList([]);
      toastError(MESSAGES.requestError);
    })
  };

  return (
    <>
      <div className="topbar">
        <Link to="/" className="topbarLogo">Social Network App</Link>
        <SearchBar className="searchBar"/>
        <div className="topbarIcons">
          <div className="topbarIconGroup">
            <div className="topbarIconItem" onClick={handleClickOpenUsers}>
              <Person/>
            </div>
          </div>
          <div className="topbarIconGroup">
            <Link to="/chat" className="topbarIconItem">
              <Chat/>
              <span className={'topbarIconBadge' + (unreadMessages === 0 ? ' hidden' : '')}>
                {unreadMessages}
              </span>
            </Link>
          </div>
          <div className="topbarIconGroup">
            <div className="topbarIconItem">
              <TopbarDropdownItem loadNotifications={handleClickOpenNotifications}>
                {notificationsList && notificationsList.length > 0 && <NotificationList notifications={notificationsList}/>}
              </TopbarDropdownItem>
              <span className={'topbarIconBadge' + (unreadNotifications === 0 ? ' hidden' : '')}>
                {unreadNotifications}
              </span>
            </div>
          </div>
          <Link to={'/user/' + currentUser.id} className="topbarIconItem">
            <span className="helloUser">
              {currentUser?.login ? 'Hello, ' + currentUser.login : ''}
            </span>
            <AccountCircle/>
          </Link>
        </div>
      </div>
      <UserListDialog
        open={usersDialogOpen}
        onClose={handleCloseUsers}
        users={usersFollowed}
      />
      <ToastContainer/>
    </>
  );
}