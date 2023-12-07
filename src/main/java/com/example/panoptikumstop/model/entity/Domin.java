package com.example.panoptikumstop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * Die Klasse Domin repräsentiert ein Objekt, das Informationen über Domin speichert.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "domin")
public class Domin extends BaseEntity implements Serializable {


    private String name;
    @Column(length = 10000)
    private String ListOfCookies;
}
