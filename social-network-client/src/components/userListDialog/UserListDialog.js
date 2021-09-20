import './userListDialog.css';
import { Dialog, DialogContent, DialogContentText, DialogTitle } from '@mui/material';
import UserListElement from '../userListElement/userListElement';

export default function UserListDialog(props) {
  const { open, onClose } = props;

  return (
    <div className="userListDialog">
      <Dialog open={open}
              onClose={onClose}>
        <DialogTitle>Friends list</DialogTitle>
        <DialogContent>
          <DialogContentText>
            <UserListElement/>
            <UserListElement/>
            <UserListElement/>
            <UserListElement/>
            <UserListElement/>
            <UserListElement/>
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </div>
  );
}