import './login.css';
import { Button } from '@mui/material';
import { STORAGE, URLS } from '../../Constants';
import { useHistory } from 'react-router-dom';
import { useState } from 'react';
import { login } from '../../Api';

export default function Login() {
  let history = useHistory();
  const [loginInput, setLoginInput] = useState('');
  const [passInput, setPassInput] = useState('');

  const handleLogin = () => {
    login({login: loginInput, password: passInput})
    .then(resp => {
      localStorage.setItem(STORAGE.token, resp.access_token);
      history.push('/');
    })
    .catch(error => {
      console.log(error);
    });
  };

  return (
    <div className="login">
      <div className="loginBox">
        <div className="loginBoxWrapper">
          <h3>Sign in</h3>
          <input className="loginInput" placeholder="login"
                 value={loginInput} onInput={e => setLoginInput(e.target.value)}/>
          <input className="passwordInput" placeholder="password"
                 value={passInput} onInput={e => setPassInput(e.target.value)}/>
          <Button variant="contained"
                  onClick={handleLogin}
                  className="submitButton">
            Submit
          </Button>
          <Button variant="contained"
                  onClick={() => window.location.href = URLS.registrationForm}
                  className="registerButton">
            Register new account
          </Button>
        </div>
      </div>
    </div>
  );
}