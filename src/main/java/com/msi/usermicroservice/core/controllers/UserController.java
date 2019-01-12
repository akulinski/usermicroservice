package com.msi.usermicroservice.core.controllers;

import com.msi.usermicroservice.core.entites.UserEntity;
import com.msi.usermicroservice.core.repositories.UserRepository;
import com.msi.usermicroservice.requestresponsemodels.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("get-all-users")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("get-user-by-id/{id}")
    public ResponseEntity getUserById(@PathVariable String id) {

        try {
            return findUserByIdElseThrowException(id);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(id, "User with given id(%s) does not exist ");
        }

    }

    private ResponseEntity findUserByIdElseThrowException(@PathVariable String id) {
        UserEntity userFound = getUserEntityFromStringId(id);
        return new ResponseEntity<>(userFound, HttpStatus.OK);
    }

    private UserEntity getUserEntityFromStringId(@PathVariable String id) {
        Integer idIntegerValue = Integer.parseInt(id);
        return userRepository.findById(idIntegerValue).orElseThrow(() -> new NoSuchElementException("No user found"));
    }

    private ResponseEntity getResponseEntityWhenNoteDoesNotExist(@PathVariable String id, String s) {
        return new ResponseEntity<>(String.format(s, id), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("add-user")
    public ResponseEntity addUser(@RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = createNewUser(addUserRequest);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }

    private UserEntity createNewUser(@RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(addUserRequest.getUsername());
        userEntity.setGender(addUserRequest.getGender());
        userRepository.save(userEntity);
        return userEntity;
    }

    @PutMapping("update-user-by-id/{id}")
    public ResponseEntity updateUserById(@PathVariable String id, @RequestBody AddUserRequest addUserRequest) {

        try {
            return getResponseEntityOfUserUpdate(id, addUserRequest);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(id, "User with given id(%s) does not exist ");
        }
    }

    private ResponseEntity getResponseEntityOfUserUpdate(@PathVariable String id, @RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = getUserEntityFromStringId(id);
        userEntity.setUsername(addUserRequest.getUsername());
        userEntity.setGender(addUserRequest.getGender());
        userRepository.save(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
