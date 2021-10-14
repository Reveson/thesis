import './app.css';
import Home from './pages/home/Home';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import Chat from './pages/chat/Chat';
import Profile from './pages/profile/Profile';
import { createGlobalStyle } from 'styled-components';
import { COLORS, STORAGE } from './Constants';
import Login from './pages/login/Login';
import NotFound from './pages/notFound/NotFound';
import Register from './pages/register/Register';
import { clearToken, useInterval } from './Common';
import { useState } from 'react';
import axios from 'axios';
import Logout from './pages/logout/Logout';
import { keepAlive } from './Api';

const GlobalStyles = createGlobalStyle`
  html {
    --color-text: ${COLORS.text};
    --color-background: ${COLORS.background};
    --color-text-hover: ${COLORS.textHover};
  }
`;

function App() {
  const [currentUser, setCurrentUser] = useState(getCurrentUser());
  const [token, setToken] = useState(localStorage.getItem(STORAGE.token));
  const [tokenExp, setTokenExp] = useState(localStorage.getItem(STORAGE.tokenExpiresAt));
  axios.defaults.headers.common['Authorization'] = 'Bearer ' + token;

  function getCurrentUser() {
    try {
      return JSON.parse(localStorage.getItem(STORAGE.user));
    } catch (e) {
      return null;
    }
  }

  useInterval(() => {
    if (isLoggedIn())
      keepAlive(currentUser.id)
      .then(() => {})
      .catch(() => {});
  }, 5 * 60 * 1000);

  function isLoggedIn() {
    return currentUser
      && tokenExp
      && tokenExp > Date.now();
  }

  function logOut() {
    if (isLoggedIn()) {
      clearToken();
      setToken(localStorage.getItem(STORAGE.token));
      setTokenExp(localStorage.getItem(STORAGE.tokenExpiresAt));
    }
    window.location = "/login";
  }

  function renderIfLoggedIn(component) {
    return () => isLoggedIn() ? component : (<Redirect to="/login"/>)
  }
  function renderIfNotLoggedIn(component) {
    return () => !isLoggedIn() ? component : (<Redirect to="/"/>)
  }

  return (
    <>
      <GlobalStyles/>
      <Router>
        <Switch>
          <Route exact path="/" render={renderIfLoggedIn((<Home getCurrentUser={() => currentUser}/>))} />
          <Route path="/chat" render={renderIfLoggedIn((<Chat getCurrentUser={() => currentUser}/>))} />
          <Route path="/user/:id" render={renderIfLoggedIn((<Profile getCurrentUser={() => currentUser}/>))} />
          <Route path="/login" render={renderIfNotLoggedIn((<Login setCurrentUser={setCurrentUser} setToken={setToken} setTokenExp={setTokenExp}/>))} />
          <Route path="/register" render={renderIfNotLoggedIn((<Register/>))} />
          <Route path="/logout" render={() => (<Logout logOut={logOut} />)} />
          <Route path="/notFound" component={NotFound} />
          <Route path="*" render={() => (<Redirect to="/notFound"/>)} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
