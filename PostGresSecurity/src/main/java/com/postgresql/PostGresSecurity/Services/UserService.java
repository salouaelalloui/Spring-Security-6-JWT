package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.SignupRequest;
import com.postgresql.PostGresSecurity.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public User saveUser (SignupRequest us);
    public Optional<List<User>> getAllUsers();
    public User getMyDetails();
    public User getUserByid(Long user_id);
    //public User getUserByEmail(String email);
}
