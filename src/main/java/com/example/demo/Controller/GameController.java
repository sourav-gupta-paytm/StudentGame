package com.example.demo.Controller;

import com.example.demo.Model.Game;
import com.example.demo.Model.Student;
import com.example.demo.Repository.GameRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("")
public class GameController {
    @Autowired
    GameRepository gameRepository;
    @Autowired
    StudentRepository studentRepository;

    // Get All Notes
    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    //Add a game
    @PostMapping("/game")
    public Game createGame(@Valid @RequestBody Game Game) {
        return gameRepository.save(Game);
    }

    // Get a Single Game
    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable(value = "id") int id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));
    }

    // Update a Game
    @PutMapping("/game/{id}")
    public Game updateGame(@PathVariable(value = "id") int GameId,
                           @Valid @RequestBody Game GameDetails) {
        Game Game = gameRepository.findById(GameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", GameId));
        Game.setName(GameDetails.getName());
        Game.setGenre(GameDetails.getGenre());
        Game.setPrice(GameDetails.getPrice());

        Game updatedGame = gameRepository.save(Game);
        return updatedGame;
    }

    // Delete a Game
    @DeleteMapping("/game/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable(value = "id") int GameId) {
        Game Game = gameRepository.findById(GameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", GameId));
        gameRepository.delete(Game);
        return ResponseEntity.ok().build();
    }


    //Get all students
    @GetMapping("/game/{id}/students")
    public Set<Student> getAllStudents(@PathVariable(value="id") int id){
        Game game=gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", id));;
        return game.getStudents();
    }

    //Add a student
    @PostMapping("/game/{game_id}/student/{student_id}")
    public Game addStudent(@PathVariable(value="game_id") int game_id,
                           @PathVariable(value="student_id") int student_id){
        Student student=studentRepository.findById(student_id)
                .orElseThrow(() -> new ResourceNotFoundException("Student", "id", student_id));
        Game game=gameRepository.findById(game_id)
                .orElseThrow(() -> new ResourceNotFoundException("Game", "id", game_id));
        student.addGame(game);
        game.addStudent(student);
        studentRepository.save(student);
        return gameRepository.save(game);
    }

}
