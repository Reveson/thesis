import './app.css';
import Home from './pages/home/Home';
import { BrowserRouter as Router, Route, Switch, Redirect } from 'react-router-dom';
import Chat from './pages/chat/Chat';
import Profile from './pages/profile/Profile';
import { createGlobalStyle } from 'styled-components';
import { COLORS } from './Constants';
import Login from './pages/login/Login';
import NotFound from './pages/notFound/NotFound';

const GlobalStyles = createGlobalStyle`
  html {
    --color-text: ${COLORS.text};
    --color-background: ${COLORS.background};
    --color-text-hover: ${COLORS.textHover};
  }
`;

function App() {
  return (
    <>
      <GlobalStyles/>
      <Router>
        <Switch>
          <Route exact path="/">
            <Home/>
          </Route>
          <Route path="/chat">
            <Chat/>
          </Route>
          <Route path="/user/:id">
            <Profile/>
          </Route>
          <Route path="/login">
            <Login/>
          </Route>
          <Route path="/notFound">
            <NotFound/>
          </Route>
          <Route path="*">
            <Redirect to="/notFound" />
          </Route>
        </Switch>
      </Router>
    </>
  );
}

export default App;
