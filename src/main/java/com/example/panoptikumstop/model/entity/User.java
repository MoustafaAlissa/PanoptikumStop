package com.example.panoptikumstop.model.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"users\"")
public class User extends BaseEntity implements Serializable {

    private String firstname;
    private String lastname;
    @NotNull
    @Column(unique = true)

    private String email;
    private String password;
    private String role;
    private boolean datenSpenden;
}
