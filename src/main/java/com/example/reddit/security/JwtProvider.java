package com.example.reddit.security;

import com.example.reddit.exception.springRadditException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new springRadditException("Exception occurred while loading keystore");
        }

    }

    public String generateToken(Authentication authentication) {
        Object principal= authentication.getPrincipal();
        String userName;
        if(principal instanceof UserDetails){
            userName=((UserDetails) principal).getUsername();
        }else {
            userName=principal.toString();
        }
        return Jwts.builder()
                .setSubject(userName)
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new springRadditException("Exception occured while retrieving public key from keystore");
        }
    }
}

