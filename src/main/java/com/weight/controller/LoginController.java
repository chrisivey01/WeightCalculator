package com.weight.controller;

import com.weight.entity.Login;
import com.weight.repository.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginRepository repository;

    @CrossOrigin(origins = "*")
    @PostMapping
    public boolean login(@RequestBody Login login){
        String salt = getSalt(login.getEmail());

        if(login.getEmail().equals("jurbin6090@gmail.com") && login.getPassword().equals(salt + "Fedex"))
           return true;
        return false;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/getSalt")
    public String getSalt(@RequestBody String email){
        Login login = repository.findByEmail(email);
        return login.getSalt();
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public String addLogin(@RequestBody Login login){
        repository.save(login);
        return "Login saved";
    }
}
