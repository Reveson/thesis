import './share.css';
import { AddPhotoAlternate, Send } from '@mui/icons-material';
import { Button, TextareaAutosize } from '@mui/material';

export default function Share() {
  return (
    <div className="share">
      <div className="shareWrapper">
        <TextareaAutosize
          placeholder="write a new post"
          className="shareInput"
          style={{resize: 'none'}}
          minRows={1}
        />
        <hr/>
        <div className="shareBottom">
          <Button variant="contained"
                  startIcon={<AddPhotoAlternate/>}
                  disabled={true}
                  sx={{ color: '#798a96', backgroundColor: '#1f2b36' }}
                  className="Button">
            Add a photo
          </Button>
          <Button variant="contained"
                  endIcon={<Send/>}
                  sx={{ color: '#798a96', backgroundColor: '#1f2b36' }}
                  className="Button">
            Share
          </Button>
        </div>
      </div>
    </div>
  );
}