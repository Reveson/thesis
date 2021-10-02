import './app.css';
import Home from './pages/home/Home';
import { BrowserRouter as Router, Redirect, Route, Switch } from 'react-router-dom';
import Chat from './pages/chat/Chat';
import Profile from './pages/profile/Profile';
import { createGlobalStyle } from 'styled-components';
import { COLORS } from './Constants';
import Login from './pages/login/Login';
import NotFound from './pages/notFound/NotFound';
import Register from './pages/register/Register';
import { isLoggedIn } from './Common';

const GlobalStyles = createGlobalStyle`
  html {
    --color-text: ${COLORS.text};
    --color-background: ${COLORS.background};
    --color-text-hover: ${COLORS.textHover};
  }
`;

function App() {
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
          <Route exact path="/" render={renderIfLoggedIn((<Home/>))} />
          <Route path="/chat" render={renderIfLoggedIn((<Chat/>))} />
          <Route path="/user/:id" render={renderIfLoggedIn((<Profile/>))} />
          <Route path="/login" render={renderIfNotLoggedIn((<Login/>))} />
          <Route path="/register" render={renderIfNotLoggedIn((<Register/>))} />
          <Route path="/notFound" component={NotFound} />
          <Route path="*" render={() => (<Redirect to="/notFound"/>)} />
        </Switch>
      </Router>
    </>
  );
}

export default App;
