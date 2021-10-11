import axios from 'axios';
import { STORAGE } from './Constants';
import { getCurrentUser } from './Common';

//user service
export function login(loginRequest) {
  delete axios.defaults.headers.common.Authorization;
  const promise = axios.post('user-service/login', loginRequest);
  axios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem(STORAGE.token);
  return promise;
}

export function registerAccount(registerRequest) {
  delete axios.defaults.headers.common.Authorization;
  return axios.post('user-service/register', registerRequest);
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

//feed service
export function getNumberOfFollowers(userId) {
  return axios.get('feed-service/feed/followers/' + userId);
}

export function getNumberOfUsersFollowed(userId) {
  return axios.get('feed-service/feed/followed/' + userId);
}

export function getUsersIdsFollowed(userId) {
  return axios.get('feed-service/feed/followed/' + userId + '/list');
}

export function isUserFollowed(userFollowing, userFollowed) {
  return axios.get('feed-service/feed/isFollowed/' + userFollowing + '?followedId=' + userFollowed);
}

export function followUser(userFollowing, userFollowed) {
  return axios.post('feed-service/feed/follow', {userId: userFollowing, followedId: userFollowed});
}

export function unfollowUser(userFollowing, userFollowed) {
  return axios.post('feed-service/feed/unfollow', {userId: userFollowing, followedId: userFollowed});
}

export function getFeedsBySubscriber(userId) {
  return axios.get('feed-service/feed/subscriber/' + userId);
}

export function getFeedsByAuthor(userId) {
  return axios.get('feed-service/feed/author/' + userId);
}

export function createNewFeed(feedRequest) {
  return axios.post('feed-service/feed/new', feedRequest);
}

export function getComments(feedId) {
  return axios.get('feed-service/feed/' + feedId + '/comment');
}

export function addComment(feedId, content) {
  return axios.post('feed-service/feed/' + feedId + '/comment/new', {authorId: getCurrentUser().id, content: content});
}

//notification service
export function getNumberOfNotifications(userId) {
  return axios.get('notification-service/notifications/' + userId + '/count');
}

export function getNumberOfUnreadMessages(userId) {
  return axios.get('notification-service/messages/' + userId + '/count');
}

export function getNotifications(userId) {
  return axios.get('notification-service/notifications/' + userId + '/list');
}
