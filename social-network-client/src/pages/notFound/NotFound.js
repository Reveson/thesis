import './notFound.css';
import { Button } from '@mui/material';
import { Home } from '@mui/icons-material';
import { COLORS } from '../../Constants';
import { Link } from 'react-router-dom';


export default function NotFound() {

  return (
    <div className="notFound">
      <div className="notFoundWrapper">
        <div className="top404">404</div>
        <div className="topNotFound">Not Found</div>
        <div className="bottomText">Sorry, I didn't find what you were looking for.</div>
          <Button
            variant="contained"
            endIcon={<Home/>}
            sx={{ color: COLORS.background, backgroundColor: COLORS.text }}
            component={Link} to={"/"}
            className="homeButton">
            Home
          </Button>
      </div>
    </div>
  );
}