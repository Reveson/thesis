import './topbarDropdownItem.css';
import { Notifications } from '@mui/icons-material';
import { useState } from 'react';
import OutsideClickHandler from 'react-outside-click-handler/esm/OutsideClickHandler';

export default function TopbarDropdownItem(props) {
  const [open, setOpen] = useState(false);
  const { loadNotifications } = props;

  function openList() {
    loadNotifications();
    setOpen(true);
  }

  return (
    <>
      <Notifications className="topbarItemButton" onClick={openList}/>
      <OutsideClickHandler onOutsideClick={() => setOpen(false)}>
        {open && props.children}
      </OutsideClickHandler>
    </>
  );

}