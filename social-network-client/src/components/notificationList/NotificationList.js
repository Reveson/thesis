import './notificationList.css';
import { Person } from '@mui/icons-material';
import Message from '../message/Message';

export default function NotificationList(props) {
  const { notifications } = props;


  function NotificationItem(props) {
    const { notification } = props;

    return (
      <div className="notificationItem">
        <span className={'notificationContent' + (notification.isRead ? '' : ' unread')}>{notification.content}</span>
      </div>
    );
  }

  return (
    <div className="notificationList">
      {
        notifications
        .map(n => (<NotificationItem key={n.timestamp} notification={n}/>))
      }
    </div>
  );
}