import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'axios';
import axios from 'axios';
import { STORAGE, URLS } from './Constants';
import { toast } from 'react-toastify';

axios.defaults.baseURL = URLS.backendUrl;
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(STORAGE.token);
axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.interceptors.response.use(undefined, error => {
  if (!error.response) {
    toast.error("There has been network error while communicating with backend service.");
    return;
  }

  const {status} = error.response;

  if (status === 401)
    window.location = "/login";
  else
    toast.error("Got error with " + status + " code."); //TODO development only

});

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);