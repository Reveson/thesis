import './editProfileDialog.css';
import { Button, Dialog, DialogContent, DialogTitle, Input } from '@mui/material';
import { Save } from '@mui/icons-material';
import { useState } from 'react';
import { editUser } from '../../Api';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { getCurrentUser, toastError } from '../../Common';
import { MESSAGES } from '../../Constants';


export default function EditProfileDialog(props) {
  const { open, onClose, user, setUser } = props;
  const [nameInput, setNameInput] = useState(user.name ? user.name : '');
  const [surnameInput, setSurnameInput] = useState(user.surname ? user.surname : '');
  const [cityInput, setCityInput] = useState(user.city ? user.city : '');
  const [birthDateInput, setBirthDateInput] = useState(user.birthDate ? user.birthDate : '');
  const currentUser = getCurrentUser();

  const sendEditUser = () => {
    editUser(
      {name : nameInput, surname : surnameInput, city: cityInput, birthDate : birthDateInput},
      currentUser.id)
    .then(resp => {
      setUser(resp.data)
      onClose();
    }).catch(() => toastError(MESSAGES.requestError))

  }

  return (
    <>
      <ToastContainer/>
      <Dialog open={open}
              onClose={onClose}>
        <DialogTitle>Edit profile</DialogTitle>
        <DialogContent>
          <div className="editGroup">
            <div className="key">name:</div>
            <Input className="input" value={nameInput} onInput={e => setNameInput(e.target.value)}/>
          </div>
          <div className="editGroup">
            <div className="key">surname:</div>
            <Input className="input" value={surnameInput} onInput={e => setSurnameInput(e.target.value)}/>
          </div>
          <div className="editGroup">
            <div className="key">city:</div>
            <Input className="input" value={cityInput} onInput={e => setCityInput(e.target.value)}/>
          </div>
          <div className="editGroup">
            <div className="key">birthday:</div>
            <Input className="input" value={birthDateInput} onInput={e => setBirthDateInput(e.target.value)}/>
          </div>
          <div className="saveWrapper">
            <Button
              variant="contained"
              startIcon={<Save/>}
              onClick={sendEditUser}
            >Save</Button>
          </div>
        </DialogContent>
      </Dialog>
    </ >
  );
}