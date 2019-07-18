package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Student {
    @Id
    @Column(name="student_id")
    int student_id;
    @Column(name="name")
    String name;
    @Column(name="college")
    String college;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JsonManagedReference
    @JoinTable(name="map",joinColumns = {@JoinColumn(name = "students_student_id")},
            inverseJoinColumns = {@JoinColumn(name = "games_game_id")})
    private Set<Game> games;

    public Student()
    {
        this.student_id=0;
        this.name=null;
        this.college=null;
        this.games=new HashSet<>();
    }
    public Student(String name,String college)
    {
        this.college=college;
        this.name=name;
        this.games=new HashSet<>();
    }

    public int getId() {
        return student_id;
    }

    public void setId(int id) {
        this.student_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void addGame(Game game){
        this.games.add(game);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student) {
            return ((Student) obj).getId() == this.getId();
        }
        return false;
    }

    /*
    @Override
    public String hashCode() {
        return this.getId()+this.getName()+this.getCollege();
    }

     */
}
