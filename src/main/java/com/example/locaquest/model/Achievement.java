package com.example.locaquest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "achievements")
public class Achievement {

    @Id
    @Column(name = "achv_id", nullable = false, unique = true, updatable = false)
    private int achvId;

    @Column(name = "achv_name", nullable = false, updatable = false)
    private String name;

    @Column(name = "achv_desc", nullable = false, updatable = false)
    private String desc;
}
