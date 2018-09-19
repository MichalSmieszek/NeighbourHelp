package project.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import project.Model.Problem;
import project.Model.House;
import project.Model.User;
import project.Model.UserToHouse;
import project.Repository.HouseRepository;
import project.Repository.ProblemRepository;
import project.Repository.UserRepository;
import project.Repository.UserToHouseRepository;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Controller
@RequestMapping(path="/problem")
public class ProblemController {
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserToHouseRepository userToHouseRepository;

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/addToOneHouse")
    public String addToOneHouse (@RequestParam String name,
                                 @RequestParam int danger,
                                 @RequestParam String description,
                                 @RequestParam String userName){
        try {
            Problem problem = new Problem();
            //System.out.println(userRepository.findByName(userName).getId());
            problem.setHouse(houseRepository.findFirstByName(name).getId());
            problem.setDanger(danger);
            problem.setDescription(description);
            problem.setApplicant(userRepository.findFirstByName(userName).getId());
            problemRepository.save(problem);
            if (danger==1){
                Set <UserToHouse> userToHouses = new HashSet<>();
                userToHouses=userToHouseRepository.findAllByHouse(houseRepository.findByName(name).getId());
                for (UserToHouse userToHouse: userToHouses)
                    sendEmail(userToHouse.getUser(),userRepository.findFirstByName(userName).getId(),description);
            }

            return ("Added.");
        }catch (Exception e){
            return("Unknown error");
        }
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/addToAll")
    public String addToAll (@RequestParam int danger,
                            @RequestParam String description,
                            @RequestParam String userName){
        try {
            Set <House> houses = new HashSet<>();
            houses = houseRepository.findAll();
            for (House house : houses) {
                Problem problem = new Problem();
                problem.setDanger(danger);
                problem.setDescription(description);
                problem.setApplicant(userRepository.findFirstByName(userName).getId());
                problem.setHouse(house.getId());
                problemRepository.save(problem);
            }
            if(danger==1) {
                List<User> users = new LinkedList<>();
                users = userRepository.findAll();

                for (User user : users)
                    sendEmail(user.getId(), userRepository.findFirstByName(userName).getId(), description);
            }
            return ("Added.");

        }catch (Exception e){
            return("Unknown error");
        }
    }

    @CrossOrigin
    @ResponseBody
    @GetMapping(value="/all")
    public List<Problem> show (@RequestParam String houseName){

        int houseId = houseRepository.findByName(houseName).getId();
        return(problemRepository.findAllByHouse(houseId));
    }

    public String sendEmail(int newUser, int newApplicant,String messageText) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "*");
        String greetings;
        User user = userRepository.findById(newUser);
        User applicant=userRepository.findById(newApplicant);
        if (user.getSex()=='m')
            greetings = "Dear Sir,";
        else
            greetings ="Dear Madame,";
        final String username = "safetyhouseapplication";
        final String password = "asd1fgh2jkl3";
        final String fromEmail = "safetyhouseapplication@gmail.com";
        final String toEmail = user.getEmail();
        final String subject = "New information";
        final String text = greetings+ "\n" + "user called " + applicant.getName() + " has already reported " +messageText+
                ".  \nWith regards, \nAdministration of Safety Home." ;
        Session session = Session.getDefaultInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return("Mail has been sent.");
    }

}
