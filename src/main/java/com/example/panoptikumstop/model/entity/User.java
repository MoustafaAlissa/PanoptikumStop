package com.example.panoptikumstop.model.entity;



import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
/**
 * Die Klasse User repräsentiert ein Objekt, das Informationen über User speichert.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EqualsAndHashCode
@Table(name = "users")
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
