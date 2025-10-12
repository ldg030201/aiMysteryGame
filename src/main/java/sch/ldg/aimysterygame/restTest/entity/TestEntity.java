package sch.ldg.aimysterygame.restTest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "test")
@Getter
@Setter
public class TestEntity {
    @Id
    private Integer id;

    private String test;
}