import './bottomBar.css';
import { ExitToApp } from '@mui/icons-material';
import { useHistory } from 'react-router-dom';
import { STORAGE } from '../../Constants';

export default function BottomBar() {
  let history = useHistory();

  function logout() {
    localStorage.setItem(STORAGE.token, '');
    localStorage.setItem(STORAGE.tokenExpiresAt, 0)
    history.push('/login');
  }

  return (
    <div className="bottomBar" onClick={logout}>
      <span className="logOutText">Log out</span>
      <ExitToApp/>
    </div>
  )
}