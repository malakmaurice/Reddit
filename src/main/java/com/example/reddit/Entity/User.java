package com.example.reddit.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@AllArgsConstructor
@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank(message = "userName is requid")
    private String userName;
    @NotBlank(message = "password is requid")
    private String password;
    @Email
    @NotBlank(message = "email is requid")
    private String email;
    private boolean enabled;
    private Instant created;

}
