import { useEffect } from 'react';
import { useHistory } from 'react-router-dom';

export default function Logout(props) {
  const { logOut } = props

  useEffect(() => {
    logOut();
  }, []);

  return (
    <>
    </>
);
}