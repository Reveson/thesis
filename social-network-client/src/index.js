import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'axios';
import axios from 'axios';
import { MESSAGES, URLS } from './Constants';

axios.defaults.baseURL = URLS.backendUrl;
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
      throw new Error(MESSAGES.http401)
  }
});

ReactDOM.render(
  <React.StrictMode>
    <App/>
  </React.StrictMode>,
  document.getElementById('root'),
);