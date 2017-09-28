package com.weight.controller;

import com.weight.entity.Login;
import com.weight.repository.LoginRepository;
import org.apache.commons.crypto.random.CryptoRandom;
import org.apache.commons.crypto.random.CryptoRandomFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginRepository repository;
    byte[] salt = new byte[32];

    @CrossOrigin(origins = "*")
    @PostMapping
    public boolean login(@RequestBody Login login) {
        Login retrievedLogin = repository.findByEmail(login.getEmail());
        String hashedPassword = md5Hash(login.getPassword(), retrievedLogin.getSalt());

        if (login.getEmail().equals("jurbin6090@gmail.com") && retrievedLogin.getPassword().equals(hashedPassword))
            return true;
        return false;
    }

    @CrossOrigin(origins = "*")
    @PutMapping
    public String addLogin(@RequestBody Login login) {
        random();

        String hashedPassword = md5Hash(login.getPassword(), salt.toString());

        login.setSalt(salt.toString());
        login.setPassword(hashedPassword);

        repository.save(login);
        return "Login saved " + hashedPassword;
    }

    private void random() {
        Properties properties = new Properties();
        properties.put(CryptoRandomFactory.CLASSES_KEY, CryptoRandomFactory.RandomProvider.OPENSSL.getClassName());

        try (CryptoRandom random = CryptoRandomFactory.getCryptoRandom(properties)) {
            random.nextBytes(salt);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String md5Hash(String message, String salt) {
        String md5 = "";
        if (null == message)
            return null;

        message = message + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(message.getBytes(), 0, message.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }

}
