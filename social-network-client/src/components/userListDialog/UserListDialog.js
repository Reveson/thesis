import './userListDialog.css';
import { Dialog, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import UserListElement from '../userListElement/userListElement';

export default function UserListDialog(props) {
  const { open, onClose, users } = props;

  return (
    <div className="userListDialog">
      <Dialog open={open}
              onClose={onClose}>
        <DialogTitle>Followed list</DialogTitle>
        <DialogContent>
          <DialogContentText component={'span'}>
            {
              users.map(user => (<UserListElement key={user.id} user={user}/>))
            }
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  );
}