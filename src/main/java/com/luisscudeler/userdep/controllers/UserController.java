package com.luisscudeler.userdep.controllers;

import com.luisscudeler.userdep.entities.User;
import com.luisscudeler.userdep.repository.UserRepository;
import com.luisscudeler.userdep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired //mecanismo de injeção de dependencia para nao ter que colocar repository=new Repository
    private UserService userService;


    @GetMapping
    public List<User> findAll(){
        List<User> list = userService.findAll();
        return list;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user){
        user = userService.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user){
        user = userService.update(id, user);
        return ResponseEntity.ok().body(user);

    }

//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(Long id){
//        userService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
