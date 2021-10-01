export function getUsername(user) {
  if (!user)
    return '';

  let username = '';

  username += user.name ? user.name + ' ' : '';
  username += user.surname ? user.surname : '';

  if (username)
    return username;
  return user.login
}