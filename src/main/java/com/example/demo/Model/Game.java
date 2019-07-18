package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="game")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Game {
    @Id
    @Column(name="game_id")
    int game_id;
    @Column(name="name")
    String name;
    @Column(name="genre")
    String genre;
    @Column(name="price")
    int price;
    @JsonBackReference
    @ManyToMany(mappedBy = "games", fetch = FetchType.LAZY)
    private Set<Student> students;


    public Game() {
        this.name = null;
        this.genre = null;
        this.price = 0;
        this.students=new HashSet<>();
    }

    public Game(String name, String genre, int price) {
        this.name = name;
        this.genre = genre;
        this.price = price;
        this.students=new HashSet<>();
    }

    public int getId() {
        return game_id;
    }

    public void setId(int id) {
        this.game_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Game) {
            return ((Game) obj).getId() == getId();
        }
        return false;
    }

    /*
    @Override
    public int hashCode() {
        return getGenre().length();
    }

     */
}
