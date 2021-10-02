import { STORAGE } from './Constants';

export function getUsername(user) {
  if (!user)
    return '';

  let username = '';

  username += user.name ? user.name + ' ' : '';
  username += user.surname ? user.surname : '';

  if (username)
    return username;
  return user.login;
}

export function getCurrentUser() {
  try {
    return JSON.parse(localStorage.getItem(STORAGE.user));
  } catch (e) {
    return null;
  }

}

export function isLoggedIn() {
  const tokenExpireTime = localStorage.getItem(STORAGE.tokenExpiresAt);
  return getCurrentUser()
    && tokenExpireTime
    && tokenExpireTime > Date.now();
}