import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'axios'
import axios from 'axios';
import { STORAGE, URLS } from './Constants';

axios.defaults.baseURL = URLS.backendUrl;
axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(STORAGE.token);
axios.defaults.headers.common['Content-Type'] = 'application/json';

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);