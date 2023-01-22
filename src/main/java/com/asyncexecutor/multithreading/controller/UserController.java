package com.asyncexecutor.multithreading.controller;

import com.asyncexecutor.multithreading.entity.User;
import com.asyncexecutor.multithreading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping(value = "/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = {"application/json"})
    public ResponseEntity saveUsers(@RequestParam(value = "files") MultipartFile[] files) {
        for (MultipartFile file : files) {
            userService.saveUser(file);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//
//    @GetMapping(value = "/users", produces = {"application/json"})
//    public CompletableFuture<ResponseEntity> getAllUser() {
//        return userService.getAllUsers().thenApply(ResponseEntity::ok);
//    }

    @GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity getAllUser() {
        CompletableFuture<List<User>> users1 = userService.getAllUsers();
        CompletableFuture<List<User>> users2 = userService.getAllUsers();
        CompletableFuture<List<User>> users3 = userService.getAllUsers();
        CompletableFuture.allOf(users1,users2,users3);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
