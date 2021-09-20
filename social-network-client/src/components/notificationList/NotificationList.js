import './notificationList.css';
import { Person } from '@mui/icons-material';

export default function NotificationList() {
  function NotificationItem() {
    return (
      <div className="notificationItem">
        <Person/>
        <span className="notificationUserName">John Smith</span>
      </div>
    );
  }

  return (
    <div className="notificationList">
      <NotificationItem/>
      <NotificationItem/>
      <NotificationItem/>
    </div>
  );
}