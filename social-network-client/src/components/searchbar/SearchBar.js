import './searchBar.css';
import { useEffect, useState } from 'react';
import { Autocomplete, InputBase } from '@mui/material';
import { useHistory } from 'react-router-dom';
import { Search } from '@mui/icons-material';

export default function SearchBar() {
  let history = useHistory();
  const [searchText, setSearchText] = useState('');
  const [searchTextDebounce, setSearchTextDebounce] = useState(searchText);
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const timer = setTimeout(() => setSearchText(searchTextDebounce), 1000);
    return () => clearTimeout(timer);
  }, [searchTextDebounce]);

  useEffect(() => {
    if (searchText)
      searchUsers(searchText);
    else
      clearUsersList();
  }, [searchText]);

  function searchUsers(searchText) {
    //TODO from API
    setUsers([
      {id: 1, name: 'John ' + searchText + 1},
      {id: 2, name: 'Max ' + searchText + 2},
      {id: 3, name: 'Robbert ' + searchText + 3}
    ])
  }

  function clearUsersList() {
    setUsers([]);
  }

  return (
    <div className='searchBar'>
      <Autocomplete
        id="user-search-bar"
        options={users}
        getOptionLabel={user => user?.name ? user.name : ''}
        onInput={e => setSearchTextDebounce(e.target.value)}
        clearOnBlur={true}
        onClose={clearUsersList}
        onChange={(e, user) => {
          if (user?.id) {
            history.push('/user/' + user.id);
          }
        }}
        renderInput={(params) => {
          const {InputLabelProps,InputProps,...rest} = params;
          return <InputBase {...params.InputProps} {...rest}
                                            placeholder="Find a user"
                                            variant="standard"
                                            startAdornment={<Search/>}
                                            style={{ width: "60%", backgroundColor: "white", outline: "none", borderRadius: "10px" }}
                                            size="small"
        />}}
      />
    </div>
  );

}