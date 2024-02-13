package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.UserDto;
import com.lcwd.electronic.store.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    // create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.createUser(userDto);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @Valid @RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(savedUserDto, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        ApiResponseMessage response = ApiResponseMessage.builder().message("User deleted successfully!").success(true).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //get all users
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> allUserDto = userService.getAllUser();
        return new ResponseEntity<>(allUserDto, HttpStatus.OK);
    }

    //get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //get user by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //search user
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        List<UserDto> userDtos = userService.searchUser(keyword);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
