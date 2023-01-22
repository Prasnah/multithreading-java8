package com.asyncexecutor.multithreading.service;

import com.asyncexecutor.multithreading.MultithreadingApplication;
import com.asyncexecutor.multithreading.entity.User;
import com.asyncexecutor.multithreading.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Async
    public CompletableFuture<List<User>> saveUser(MultipartFile multipartFile) {
        long startTime = System.currentTimeMillis();
        List<User> listOfUsers = parseCSVFile(multipartFile);
        log.info("List of users saved by thread - {}", Thread.currentThread().getName());
        listOfUsers = userRepository.saveAll(listOfUsers);
        long endTime = System.currentTimeMillis();
        log.info("Time taken - {}", endTime - startTime);
        return CompletableFuture.completedFuture(listOfUsers);
    }

    @Async
    public CompletableFuture<List<User>> getAllUsers() {
        log.info("Retriving Users...");
        log.info("List of users fetched by thread - {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(userRepository.findAll());
    }

    @SneakyThrows
    private List<User> parseCSVFile(MultipartFile file) {
        List<User> listOfUsers = new ArrayList<>();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User();
                user.setName(data[0]);
                user.setEmail(data[1]);
                user.setGender(data[2]);
                listOfUsers.add(user);
            }
        }
        return listOfUsers;
    }

}
