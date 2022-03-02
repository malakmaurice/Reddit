package com.example.reddit.security;

import com.example.reddit.exception.springRadditException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtProvider {

    private KeyStore keyStore;
    @Value("${jwt.expiration.millsec}")
    private Long jwtExpirationMillsec ;
    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
            System.out.println(getExpire("eyJhbGciOiJSUzI1NiJ9.eyJpYXQiOjE2NDYyNDIyOTYsImV4cCI6MTY0NjI0MzE5Niwic3ViIjoibWFsYWsxMTExMTExMTExMSJ9.eNcdua8MqhDh78yjglKLZLwI0XVfVR6qvPnCVY6eQ10QFKzwpooSxiBgYZgSbDRourYYrIo-QvBGbQpCOVNXJHrocZnruMKuHNlEQRU122PzKsURL30Yaqoajno6vqFT66ccOwswcp3xtlvZzOmbbPbg_qqjRuPggTT0it9Wg5lBGs7nOp74eUoN3a50qty-2KpivY25pb07IknWVtUao2GIJqoVdy2Bi9rBHdQ7-82OJZzwkL_tK9JNne2lowKSpf9J9fFH2vWkQzaVqdA9EeEFkxxSZfM90fiiDsQOVv4ndwS_EjzabOYhDMcI8rxhu2XL3dUDG7qUIrD28KAbWg"));

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
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMillsec)))
                .setSubject(userName)
                .signWith(getPrivateKey())
                .compact();
    }

    public Long getJwtExpirationMillsec() {
        return jwtExpirationMillsec;
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new springRadditException("Exception occured while retrieving private key from keystore");
        }
    }

    public boolean validateToken(String jwtToken) {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwtToken);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
           return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new springRadditException("Exception occured while retrieving public key from keystore");
        }
    }

    public String getUsernameFromJwt(String jwtToken) {
     return Jwts.parser().setSigningKey(getPublicKey())
             .parseClaimsJws(jwtToken).getBody().getSubject();
    }
    public Date getExpire(String jwtToken) {
        return Jwts.parser().setSigningKey(getPublicKey())
                .parseClaimsJws(jwtToken).getBody().getExpiration();
    }
    public String generateTokenByUserName(String userName) {
        return Jwts.builder()
                .signWith(getPrivateKey())
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationMillsec)))
                .compact();
    }
}

