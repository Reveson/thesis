import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'axios';
import axios from 'axios';
import { STORAGE, URLS } from './Constants';
import { toast } from 'react-toastify';
import { toastWarn } from './Common';

axios.defaults.baseURL = URLS.backendUrl;
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(STORAGE.token);
axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.interceptors.response.use(undefined, error => {
  if (!error.response) {
    throw new Error(error)
  }
  const { status } = error.response;
  if (status === 500) {
    throw new Error(error)
  }

  if (status === 401) {
    if (window.location.pathname !== '/login')
      window.location = '/login';
    else
      toastWarn("Wrong username or password.");
  }
});

ReactDOM.render(
  <React.StrictMode>
    <App/>
  </React.StrictMode>,
  document.getElementById('root'),
);