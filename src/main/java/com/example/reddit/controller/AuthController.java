package com.example.reddit.controller;

import com.example.reddit.dto.*;
import com.example.reddit.service.AuthService;
import com.example.reddit.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authService.signup(registerRequest);
        return new ResponseEntity<>("user Registed Succesfully", HttpStatus.OK);
    }

    @GetMapping("/accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        authService.verifyAccount(token);
        return new ResponseEntity<>("Acctivate Account",HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationRespone login(@RequestBody LoginRequest loginRequest){
      return  this.authService.login(loginRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest refreshTokenRequest){

         this.refreshTokenService.logout(refreshTokenRequest);
         return ResponseEntity.status(HttpStatus.OK).body("refresh token deleted successfuly!");
    }

    @PostMapping("/refresh/token")
    public ResponseEntity<Object> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(authService.refreshToken(refreshTokenRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ExceptionResponse(e.toString(),e.getMessage()));
        }
    }
}
