package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.Model.User;
import project.Model.UserToHouse;
import project.Repository.UserToHouseRepository;

@Controller
@RequestMapping("/userToHouse")
public class UserToHouseContoller {
    @Autowired
    UserToHouseRepository userToHouseRepository;
    @CrossOrigin
    @ResponseBody
    @PostMapping(value="/add",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String add (@RequestBody UserToHouse newUserToHouse){
        try {
            UserToHouse userToHouse = new UserToHouse();
            userToHouse.setHouse(newUserToHouse.getHouse());
            userToHouse.setUser(newUserToHouse.getUser());
            if (userToHouseRepository.findFirstByUserAndHouse(userToHouse.getUser(),userToHouse.getHouse())!=null)
                return ("There is a connection between user and house.");
            else {
                userToHouseRepository.save(userToHouse);
                return ("Added.");
            }
        }catch(Exception e){
            return("Unknown error.");
        }
    }
    @CrossOrigin
    @DeleteMapping(value = "/delete")
    @ResponseBody
    public String delete(@RequestParam UserToHouse oldUserToHouse) {
        try {
            UserToHouse userToHouse = userToHouseRepository.findById(oldUserToHouse.getId());
            userToHouseRepository.delete(userToHouse);
            return ("Deleted");
        } catch (Exception e) {
            return ("Unknown error.");
        }
    }
}
