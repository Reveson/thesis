import './profile.css';
import Topbar from '../../components/topbar/Topbar';
import { Add, Chat, Person } from '@mui/icons-material';
import { Button } from '@mui/material';
import Post from '../../components/post/Post';

export default function Profile() {
  return (
    <>
      <Topbar/>
      <div className="profile">
        <div className="profileTop">
          <div className="profileTopLeft">
            <div className="profilePhotoAndUsername">
              <Person className='profilePhoto'/>
              <span className="profileUsername">John Smith</span>
            </div>
            <div className="profileLogin">
              <span>@johny</span>
            </div>
          </div>
          <div className="profileTopRight">
            <div className="profileInfoItem">
              <span className="profileInfoKey">city:</span>
              <span className="profileInfoValue">Warsaw</span>
            </div>
            <div className="profileInfoItem">
              <span className="profileInfoKey">age:</span>
              <span className="profileInfoValue">34 years</span>
            </div>
            <div className="profileInfoItem">
              <span className="profileInfoKey">birthday:</span>
              <span className="profileInfoValue">01 january</span>
            </div>
          </div>
        </div>
        <div className="profileBottom">
          <div className="profileInfoItem">
            <span className="profileInfoKey">Users following:</span>
            <span className="profileInfoValue">16</span>
          </div>
          <div className="profileInfoItem">
            <span className="profileInfoKey">Users followed:</span>
            <span className="profileInfoValue">34</span>
          </div>
          <Button variant='contained' startIcon={<Add/>} className='profileButtonFollow'>Follow</Button>
          <Button variant='contained' startIcon={<Chat/>} className='profileButtonChat'>Text</Button>
        </div>
      </div>
      <Post/>
      <Post/>
      <Post/>
    </>
  );
}