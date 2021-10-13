import Topbar from '../../components/topbar/Topbar';
import Feed from '../../components/feed/Feed';
import BottomBar from '../../components/bottombar/BottomBar';

export default function Home(props) {
  const { getCurrentUser } = props;

  return (
    <>
      <Topbar getCurrentUser={getCurrentUser}/>
      <Feed getCurrentUser={getCurrentUser}/>
      <BottomBar/>
    </>
  );
}