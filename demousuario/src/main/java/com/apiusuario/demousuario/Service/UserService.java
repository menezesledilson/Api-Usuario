package com.apiusuario.demousuario.Service;

import Usuario.User;
import com.apiusuario.demousuario.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public User create(User usuario){
        return  this.userRepository.insert(usuario);
    }
    public List<User> getAll(){
        return this.userRepository.findAll();
    }
    public User delete(String id){
        User user = this.userRepository.findById(id).get();
        if(user != null)
            this.userRepository.deleteById(id);
        return user;
    }

}
