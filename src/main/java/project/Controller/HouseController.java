package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.House;
import project.Model.User;
import project.Model.UserToHouse;
import project.Repository.HouseRepository;
import project.Repository.UserRepository;
import project.Repository.UserToHouseRepository;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path="/house")
public class HouseController {
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserToHouseRepository userToHouseRepository;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value ="/add")
    public String add (@RequestParam String name){
        try {
            House house = new House();
            if(houseRepository.findFirstByName(name)!=null)
                return("There is house with the same name.");
            else {
                house.setName(name);
                houseRepository.save(house);
                return ("House is added");
            }
        }catch(Exception e){
            return ("Unknown error");
        }
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value="/showUsers")
    public Set<User> showUsers(@RequestParam String name) {
        Set<User> users  = new HashSet<>();
        try{
            House house = houseRepository.findByName(name);
            Set <UserToHouse> userToHouses = userToHouseRepository.findAllByHouse(house.getId());
            for(UserToHouse userToHouse :userToHouses)
            users.add(userRepository.findById(userToHouse.getUser()));
        }catch(Exception e){
            e.printStackTrace();
            }
      return(users)  ;
    }
    @CrossOrigin
    @ResponseBody
    @DeleteMapping(value="/delete")
    public String delete (String name){
        try {
            House house = houseRepository.findByName(name);
            houseRepository.delete(house);
            return("House deleted.");
        }catch(Exception e){
            return("Unknown error");
        }
    }

}
