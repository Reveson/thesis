import { STORAGE } from './Constants';
import React, { useEffect, useRef } from 'react';
import { toast } from 'react-toastify';

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

//https://stackoverflow.com/questions/46140764/polling-api-every-x-seconds-with-react
export const useInterval = (callback, delay) => {

  const savedCallback = useRef();

  useEffect(() => {
    savedCallback.current = callback;
  }, [callback]);


  useEffect(() => {
    function tick() {
      savedCallback.current();
    }
    if (delay !== null) {
      const id = setInterval(tick, delay);
      return () => clearInterval(id);
    }
  }, [delay]);
}

export function clearToken() {
  localStorage.setItem(STORAGE.token, '');
  localStorage.setItem(STORAGE.tokenExpiresAt, 0)
}

export function toastWarn(text) {
  toast.warn(text, {
    position: 'top-center',
    hideProgressBar: true,
    autoClose: 3000,
    pauseOnHover: false,
  });
}

export function toastSuccess(text, timeout) {
  toast.success(text, {
    position: 'top-center',
    hideProgressBar: true,
    autoClose: timeout,
    pauseOnHover: false,
  });
}

export function toastError(text) {
  toastErrorWithDelay(text, 3000);
}

export function toastErrorWithDelay(text, delay) {
  toast.error(text, {
    position: 'top-center',
    hideProgressBar: true,
    autoClose: delay,
    pauseOnHover: false,
  });
}