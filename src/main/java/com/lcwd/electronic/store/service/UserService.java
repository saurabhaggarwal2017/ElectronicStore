package com.lcwd.electronic.store.service;

import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.UserDto;

import java.util.List;

public interface UserService {
    // create
    UserDto createUser(UserDto userDto);

    // update
    UserDto updateUser(UserDto userDto, String userId);

    // delete
    void deleteUser(String userId);

    // getAllUsers
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);

    // getSingleUserById
    UserDto getUserById(String userId);

    // getSingleUserByEmail
    UserDto getUserByEmail(String email);

    // searchUser
    List<UserDto> searchUser(String keyword);

}
