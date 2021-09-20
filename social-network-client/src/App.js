import './app.css';
import Home from './pages/home/Home';
import {
  BrowserRouter as Router,
  Switch,
  Route,
} from 'react-router-dom';
import Chat from './pages/chat/Chat';
import Profile from './pages/profile/Profile';
import { createGlobalStyle } from 'styled-components';
import { COLORS } from './Constants';

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
          <Route path="/user">
            <Profile/>
          </Route>
        </Switch>
      </Router>
    </>
  );
}

export default App;
