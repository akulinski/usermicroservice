package com.msi.usermicroservice.core.controllers;

import com.msi.usermicroservice.core.entites.HistoryEntity;
import com.msi.usermicroservice.core.entites.UserEntity;
import com.msi.usermicroservice.core.repositories.HistoryRepository;
import com.msi.usermicroservice.core.repositories.UserRepository;
import com.msi.usermicroservice.requestresponsemodels.AddHistoryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("history")
public class HistoryController {

    private final UserRepository userRepository;

    private final HistoryRepository historyRepository;

    @Autowired
    public HistoryController(UserRepository userRepository, HistoryRepository historyRepository) {
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    @GetMapping("get-whole-history")
    public ResponseEntity getWholeHistory() {
        return new ResponseEntity<>(historyRepository.findAll(), HttpStatus.OK);
    }

    private UserEntity getUserEntityFromStringId(@PathVariable String id) {
        Integer idIntegerValue = Integer.parseInt(id);
        return userRepository.findById(idIntegerValue).orElseThrow(() -> new NoSuchElementException("No user found"));
    }

    private ResponseEntity getResponseEntityWhenNoteDoesNotExist(@PathVariable String id, String s) {
        return new ResponseEntity<>(String.format(s, id), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("get-history-by-user-id/{id}")
    public ResponseEntity getHistoryOfUser(@PathVariable String id) {
        try {
            UserEntity userEntity = getUserEntityFromStringId(id);
            return new ResponseEntity<>(userEntity.getHistoryEntities(), HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(id, "User with given id(%s) does not exist ");
        }
    }

    @GetMapping("get-history-by-id/{id}")
    public ResponseEntity getHistoryById(@PathVariable String id) {
        try {
            HistoryEntity historyEntity = historyRepository.findById(Integer.parseInt(id)).orElseThrow(() -> new NoSuchElementException("No history found"));
            return new ResponseEntity<>(historyEntity, HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(id, "History with given id(%s) does not exist ");
        }
    }

    @PostMapping("add-history")
    public ResponseEntity addHistory(@RequestBody AddHistoryRequest addHistoryRequest) {
        try {
            UserEntity userEntity = userRepository.findById(Integer.parseInt(addHistoryRequest.getUserId())).orElseThrow(() -> new NoSuchElementException("No user found"));
            HistoryEntity historyEntity = new HistoryEntity();
            historyEntity.setValue(addHistoryRequest.getValue());
            historyEntity.setUserEntity(userEntity);
            historyRepository.save(historyEntity);
            return new ResponseEntity<>(historyEntity, HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(addHistoryRequest.getUserId(), "User with given id(%s) does not exist ");
        }
    }

    @PutMapping("update-history/{historyId}")
    public ResponseEntity updateHistory(@RequestBody AddHistoryRequest addHistoryRequest, @PathVariable String historyId) {

        try {
            UserEntity userEntity = userRepository.findById(Integer.parseInt(addHistoryRequest.getUserId())).orElseThrow(() -> new NoSuchElementException("No user found"));
            HistoryEntity historyEntity = historyRepository.findById(Integer.parseInt(historyId)).orElseThrow(() -> new NoSuchElementException("No history found"));
            historyEntity.setValue(addHistoryRequest.getValue());
            historyEntity.setUserEntity(userEntity);

            return new ResponseEntity<>(historyEntity, HttpStatus.OK);
        } catch (NumberFormatException numberFormatException) {
            return new ResponseEntity<>("Id value of of range ", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException noSuchElementException) {
            return getResponseEntityWhenNoteDoesNotExist(historyId, "History with given id(%s) does not exist ");
        }
    }
}
