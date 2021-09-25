import axios from 'axios';
import { STORAGE } from './Constants';

export function login(loginRequest) {
  delete axios.defaults.headers.common.Authorization;
  const promise = axios.post('user-service/login', loginRequest);
  axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(STORAGE.token);
  return promise;
}

export function getUserById(userId) {
  return axios.get('user-service/user/' + userId);
}

export function editUser(editRequest, userId) {
  return axios.put('user-service/user/' + userId, editRequest)
}