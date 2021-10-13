import './login.css';
import { Button } from '@mui/material';
import { MESSAGES, STORAGE } from '../../Constants';
import { Link, useHistory } from 'react-router-dom';
import { useState } from 'react';
import { login } from '../../Api';
import { ToastContainer } from 'react-toastify';
import { toastError, toastErrorWithDelay } from '../../Common';

export default function Login() {
  let history = useHistory();
  const [loginInput, setLoginInput] = useState('');
  const [passInput, setPassInput] = useState('');

  const handleLogin = () => {
    login({login: loginInput, password: passInput})
    .then(resp => {
      localStorage.setItem(STORAGE.token, resp.data.accessToken);
      localStorage.setItem(STORAGE.tokenExpiresAt, Date.now() + resp.data.expiresIn * 1000);
      localStorage.setItem(STORAGE.user, JSON.stringify(resp.data.user));
      history.push('/');
    })
    .catch(() => toastErrorWithDelay(MESSAGES.loginError, 5000))
  };

  return (
    <div className="login">
      <ToastContainer/>
      <div className="loginBox">
        <div className="loginBoxWrapper">
          <h3>Sign in</h3>
          <input className="loginInput" placeholder="login"
                 value={loginInput} onInput={e => setLoginInput(e.target.value)}/>
          <input className="passwordInput" placeholder="password" type="password"
                 value={passInput} onInput={e => setPassInput(e.target.value)}/>
          <Button variant="contained"
                  onClick={handleLogin}
                  className="submitButton">
            Submit
          </Button>
          <Button variant="contained"
                  component={Link}
                  to="/register"
                  style={{ color: '#FFFFFF' }}
                  className="registerButton">
            Register new account
          </Button>
        </div>
      </div>
    </div>
  );
}