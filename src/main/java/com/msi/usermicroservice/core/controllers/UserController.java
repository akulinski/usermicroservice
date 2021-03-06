package com.msi.usermicroservice.core.controllers;

import com.msi.usermicroservice.core.entites.Authority;
import com.msi.usermicroservice.core.entites.AuthorityEntity;
import com.msi.usermicroservice.core.entites.UserEntity;
import com.msi.usermicroservice.core.repositories.AuthorityRepository;
import com.msi.usermicroservice.core.repositories.UserRepository;
import com.msi.usermicroservice.requestresponsemodels.AddUserRequest;
import com.msi.usermicroservice.requestresponsemodels.LoginRequest;
import com.msi.usermicroservice.requestresponsemodels.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    @Autowired
    public UserController(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
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
        createAuthorityForUser(addUserRequest, userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }


    private void createAuthorityForUser(@RequestBody AddUserRequest addUserRequest, UserEntity userEntity) {
        AuthorityEntity authorityEntity = new AuthorityEntity();
        if (addUserRequest.getAuthority().equalsIgnoreCase("patient")) {
            authorityEntity.setAuthority(Authority.PATIENT);
        } else if (addUserRequest.getAuthority().equalsIgnoreCase("doctor")) {
            authorityEntity.setAuthority(Authority.DOCTOR);
        } else {
            authorityEntity.setAuthority(Authority.ADMIN);
        }
        authorityEntity.setUserEntity(userEntity);
        authorityRepository.save(authorityEntity);
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        Optional<UserEntity> userEntity = userRepository.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());

        if (userEntity.isPresent()) {
            return createLoginResponse(true);
        }else{
            return createLoginResponse(false);
        }
    }

    private ResponseEntity createLoginResponse(boolean value) {
        return new ResponseEntity<>(new LoginResponse(value), HttpStatus.OK);
    }

    private UserEntity createNewUser(@RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(addUserRequest.getUsername());
        userEntity.setGender(addUserRequest.getGender());
        userEntity.setPassword(addUserRequest.getPassword());
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

    @GetMapping("get-authority/{userid}")
    public ResponseEntity getAuthorityById(@PathVariable String userid) {
        UserEntity userEntity = userRepository.findById(Integer.parseInt(userid)).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return new ResponseEntity<>(userEntity.getAuthorityEntitySet(), HttpStatus.OK);
    }

    private ResponseEntity getResponseEntityOfUserUpdate(@PathVariable String id, @RequestBody AddUserRequest addUserRequest) {
        UserEntity userEntity = getUserEntityFromStringId(id);
        userEntity.setUsername(addUserRequest.getUsername());
        userEntity.setGender(addUserRequest.getGender());
        userEntity.setPassword(addUserRequest.getPassword());
        userRepository.save(userEntity);
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
