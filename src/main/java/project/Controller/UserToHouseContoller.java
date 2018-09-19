package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.Model.User;
import project.Model.UserToHouse;
import project.Repository.UserToHouseRepository;

@Controller
@RequestMapping("/userToHouse")
public class UserToHouseContoller {
    @Autowired
    UserToHouseRepository userToHouseRepository;
    @PostMapping(value="/add")
    public String add (@RequestBody UserToHouse newUserToHouse){
        try {
            UserToHouse userToHouse = new UserToHouse();
            userToHouse.setHouse(newUserToHouse.getHouse());
            userToHouse.setUser(newUserToHouse.getUser());
            userToHouseRepository.save(userToHouse);
            return("Added.");
        }catch(Exception e){
            return("Unknown error.");
        }
    }
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
