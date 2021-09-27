import axios from 'axios';
import { STORAGE } from './Constants';

//user service
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

export function getUsersByIds(userIdsRequest) {
  return axios.post('user-service/user/many', userIdsRequest)
}

//message service
export function getChatUserIds(userId) {
  return axios.get('message-service/message/' + userId + '/chats')
}

export function getMessages(userId, recipientId) {
  return axios.get('message-service/message/' + userId + '/get/' + recipientId)
}

export function sendMessage(userId, newMessageRequest) {
  return axios.post('message-service/message/' + userId + '/new', newMessageRequest);
}