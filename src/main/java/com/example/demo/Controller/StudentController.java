package com.example.demo.Controller;

import com.example.demo.Model.Game;
import com.example.demo.Model.Student;
import com.example.demo.Repository.GameRepository;
import com.example.demo.ResourceNotFoundException;
import com.example.demo.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GameRepository gameRepository;

    // Get All Notes
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();

    }

    //Add student
    @PostMapping("/student")
    public Student createStudent(@Valid @RequestBody Student student) {
        return studentRepository.save(student);
    }

    // Get a Single Student
    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable(value = "id") int id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", id));
    }

    // Update a Student
    @PutMapping("/student/{id}")
    public Student updateStudent(@PathVariable(value = "id") int studentId,
                           @Valid @RequestBody Student studentDetails) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        student.setName(studentDetails.getName());
        student.setCollege(studentDetails.getCollege());

        Student updatedStudent = studentRepository.save(student);
        return updatedStudent;
    }

    // Delete a Student
    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") int studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", studentId));

        studentRepository.delete(student);

        return ResponseEntity.ok().build();
    }


    //Get all games of a Student
    @GetMapping("/student/{id}/games")
    public Set<Game> getAllGames(@PathVariable(value="id") int student_id){
        Student student=studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", student_id));
        return student.getGames();
    }

    //Add a game to a Student
    @PostMapping("/student/{student_id}/game/{game_id}")
    public Student addGame(@PathVariable(value="student_id") int student_id,
                           @PathVariable(value="game_id") int game_id){

        Student student=studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", student_id));
        Game game=gameRepository.findById(game_id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", game_id));
        student.addGame(game);
        game.addStudent(student);
        gameRepository.save(game);
        return studentRepository.save(student);
    }



}