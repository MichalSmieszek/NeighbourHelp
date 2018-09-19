package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Model.User;
import project.Model.UserToHouse;
import project.Repository.UserRepository;
import project.Repository.UserToHouseRepository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path="/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserToHouseRepository userToHouseRepository;

    @CrossOrigin
    @GetMapping(path="/add")
    @ResponseBody
    public String addUser (@RequestParam String name,
                           @RequestParam  String password,
                           @RequestParam  String email,
                           @RequestParam  char sex){
        if (sex=='f' || sex=='m') {
            User user = new User();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setSex(sex);
            userRepository.save(user);
            return ("Added");
        }
        else
            return ("Please use f or m to describe your sex");
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/changeEmail", method = RequestMethod.POST)
    public String updateUserEmail(@RequestBody User newUser) {
        try {
            User user = userRepository.findById(newUser.getId());
            user.setEmail(newUser.getEmail());
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return("Data hasn't been changed");
        }
        return("User's email updated");
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(value = "/delete")
    public String deleteUser(@RequestParam User oldUser){
        User user = userRepository.findById(oldUser.getId());
        Set<UserToHouse> usersToHouse =new HashSet<>();
        usersToHouse = userToHouseRepository.findAllByUser(user.getId());
        userRepository.delete(user);
        for (UserToHouse userToHouse : usersToHouse){
            userToHouseRepository.delete(userToHouse);
        }
        return("User deleted");
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/all")
    public List<User> showAllUser(){
        List <User> users= userRepository.findAll();
        List <User> newValuesUsers = new LinkedList<>();
        for (User user : users){
            User newUser =new User();
            String email=user.getEmail();
            String name=user.getName();
            System.out.println(name);
            int id=user.getId();
            char sex=user.getSex();
            newUser.setName(name);
            newUser.setEmail(email);
            newUser.setSex(sex);
            newUser.setId(id);
            newValuesUsers.add(newUser);
        }
        return (newValuesUsers);
    }
}
