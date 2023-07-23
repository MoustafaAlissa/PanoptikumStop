package com.example.panoptikumstop.model.entity;


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
@Table(name = "\"cookies\"")
public class Cookie extends BaseEntity  implements Serializable {
    private String platform;
    private String category;
    private String name;
    private String domain;
    @Column(length = 10000)
    private String description;
    private String time;
    @Column(length = 10000)
    private String dataController;
    private boolean isChecked;

}
