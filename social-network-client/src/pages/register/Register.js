import './register.css';

import { Button } from '@mui/material';
import { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import { registerAccount } from '../../Api';
import 'react-toastify/dist/ReactToastify.css';
import { REGISTER } from '../../Constants';
import { toastSuccess, toastWarn } from '../../Common';

export default function Register() {
  let history = useHistory();

  const [usernameInput, setUsernameInput] = useState('');
  const [firstNameInput, setFirstName] = useState('');
  const [lastNameInput, setLastName] = useState('');
  const [passInput, setPassInput] = useState('');
  const [passRepeatInput, setPassRepeatInput] = useState('');

  function handleRegister() {
    if (!usernameInput || usernameInput.length < REGISTER.minUsernameLength)
      toastWarn('Username must have at least ' + REGISTER.minUsernameLength + ' characters.');
    else if (!passInput || passInput.length < REGISTER.minPasswordLength)
      toastWarn('Password must have at least ' + REGISTER.minPasswordLength + ' characters.');
    else if (passInput !== passRepeatInput)
      toastWarn('Passwords don\'t match.');
    else {
      registerAccount({
        username: usernameInput,
        firstName: firstNameInput,
        lastName: lastNameInput,
        password: passInput,
      }).then(() => {
        let redirectTime = 5000;
        toastSuccess("Account has been created. You will be redirected to login page in " + redirectTime/1000 + " seconds.", redirectTime);
        setTimeout(() => history.push('/login'), redirectTime);
      });
    }

  }

  return (
    <div className="register">
      <ToastContainer/>
      <div className="registerBox">
        <div className="registerBoxWrapper">
          <h3>Register new account</h3>
          <input className="input" placeholder="username"
                 value={usernameInput} onInput={e => setUsernameInput(e.target.value)}/>
          <input className="input" placeholder="first name"
                 value={firstNameInput} onInput={e => setFirstName(e.target.value)}/>
          <input className="input" placeholder="last name"
                 value={lastNameInput} onInput={e => setLastName(e.target.value)}/>
          <input type="password" className="input" placeholder="password"
                 value={passInput} onInput={e => setPassInput(e.target.value)}/>
          <input type="password" className="input" placeholder="password"
                 value={passRepeatInput} onInput={e => setPassRepeatInput(e.target.value)}/>
          <Button variant="contained"
                  onClick={handleRegister}
                  className="submitButton">
            Submit
          </Button>
          <Link
            to="/login"
            className="backLink">
            Back to login
          </Link>
        </div>
      </div>
    </div>
  );
}