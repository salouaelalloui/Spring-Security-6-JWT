package com.postgresql.PostGresSecurity.Controllers;

import com.postgresql.PostGresSecurity.Entities.User;
import com.postgresql.PostGresSecurity.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public String Home(){
        return "this is home";
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/all")

    public ResponseEntity<List<User>> getAllUsers(){
        Optional<List<User>> optionalUsers = userService.getAllUsers();
        return optionalUsers.map(users -> ResponseEntity.ok(users))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/users/single")
    public User getMyDetails(){
        return  userService.getMyDetails();

    }
    @GetMapping("/user/{id}")
    public User getUserByid(@PathVariable Long id ){
        return userService.getUserByid(id);
    }
}
