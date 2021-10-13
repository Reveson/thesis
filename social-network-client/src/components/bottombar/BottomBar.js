import './bottomBar.css';
import { ExitToApp } from '@mui/icons-material';
import { useHistory } from 'react-router-dom';
import { STORAGE } from '../../Constants';
import { clearToken } from '../../Common';

export default function BottomBar() {
  let history = useHistory();

  function logout() {
    history.push('/logout');
  }

  return (
    <div className="bottomBar" onClick={logout}>
      <span className="logOutText">Log out</span>
      <ExitToApp/>
    </div>
  )
}