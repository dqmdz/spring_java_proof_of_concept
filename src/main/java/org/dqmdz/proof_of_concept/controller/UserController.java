package org.dqmdz.proof_of_concept.controller;

import org.dqmdz.proof_of_concept.model.User;
import org.dqmdz.proof_of_concept.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        return repository.findById(id).orElse(null);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return repository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User newUser) {
        return repository.findById(id).map(user -> {
            user = User.builder()
                    .id(id)
                    .name(newUser.getName())
                    .password(newUser.getPassword())
                    .email(newUser.getEmail())
                    .build();
            return repository.save(user);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        repository.deleteById(id);
    }

}
