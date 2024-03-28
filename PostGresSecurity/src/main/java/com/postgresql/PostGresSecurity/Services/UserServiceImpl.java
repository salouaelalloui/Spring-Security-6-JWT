package com.postgresql.PostGresSecurity.Services;

import com.postgresql.PostGresSecurity.DTO.SignupRequest;
import com.postgresql.PostGresSecurity.Entities.Role;
import com.postgresql.PostGresSecurity.Entities.User;
import com.postgresql.PostGresSecurity.Repositories.RoleRepository;
import com.postgresql.PostGresSecurity.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements  UserService{
    private final UserRepository userRepo;
    private final RoleRepository roleRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
@Transactional(transactionManager = "transactionManager")
    public User saveUser (SignupRequest us){
        User user= new User();
       // System.out.println("password"+us.getPassword());
       // System.out.println("email"+us.getEmail());
        //System.out.println("roles"+us.getRoles());
        user.setEmail(us.getEmail());
        user.setPassword(passwordEncoder.encode(us.getPassword()));
        Set<Role> roles = new HashSet<>();
        for (String roleName : us.getRoles()) {
            Role role = roleRepository.findByRole(roleName).orElseThrow();
            if (role != null) {
                roles.add(role);
            } else {
              new  RuntimeException("there is no role for this name : "+roleName);
            }
        }
        user.setRoles(roles);
        return userRepo.save(user);
    }
    public Optional<List<User>> getAllUsers(){
        List<User> users = userRepo.findAll();

        return Optional.ofNullable(users);
    }

    public User getMyDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return userRepo.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
        }
        return null; // No authenticated user or username doesn't match
    }
    public User getUserByid(Long user_id){
        return userRepo.findById(user_id).orElseThrow(()->new RuntimeException("there is no user for this id : "+user_id));
    }


}
