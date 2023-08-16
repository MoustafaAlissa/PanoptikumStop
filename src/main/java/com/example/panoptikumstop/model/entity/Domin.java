package com.example.panoptikumstop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Domin extends BaseEntity implements Serializable {


    private String name;
    @Column(length = 10000)
    private String ListOfCookies;
}
