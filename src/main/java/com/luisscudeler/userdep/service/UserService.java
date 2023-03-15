package com.luisscudeler.userdep.service;

import com.luisscudeler.userdep.entities.User;
import com.luisscudeler.userdep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        List<User> list = userRepository.findAll();
        return list;
    }

    public User findById(Long id){
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException());
    }



    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public User update(Long id, User user){
        try {
            User newUser = userRepository.getReferenceById(id);
            updateData(newUser, user);
            return userRepository.save(newUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateData(User newUser, User user){
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setDepartment(user.getDepartment());
    }
}
