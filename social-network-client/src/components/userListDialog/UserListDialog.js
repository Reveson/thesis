import './userListDialog.css';
import { Dialog, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import UserListElement from '../userListElement/userListElement';
import { useHistory } from 'react-router-dom';

export default function UserListDialog(props) {
  let history = useHistory();
  const { open, onClose, users } = props;

  function redirectToUserPage(id) {
    history.push('/user/' + id);
    onClose();
  }

  return (
    <div className="userListDialog">
      <Dialog open={open}
              onClose={onClose}>
        <DialogTitle>Followed list</DialogTitle>
        <DialogContent>
          <DialogContentText component={'span'}>
            {
              users.map(user => (
                <UserListElement
                  key={user.id}
                  user={user}
                  redirectToUserPage={() => redirectToUserPage(user.id)}
                />),
              )
            }
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  );
}