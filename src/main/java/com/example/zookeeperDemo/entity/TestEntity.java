package com.example.zookeeperDemo.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;


@Entity
@Table(name = "test")
public class TestEntity extends UuidEntity {

    /**
     *
     */
    private static final long serialVersionUID = 3631631709935297499L;

    @Column(name = "name_")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "address")
    private String address;

    @Column(name = "birth")
    private LocalDate birth;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }
}
