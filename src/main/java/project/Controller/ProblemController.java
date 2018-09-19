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
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
    @PostMapping(value = "/addToOneHouse")
    public String addToOneHouse (Problem newProblem){
        try {
            Problem problem = new Problem();
            problem.setHouse(newProblem.getHouse());
            problem.setDanger(newProblem.getDanger());
            problem.setDescription(newProblem.getDescription());
            problem.setApplicant(newProblem.getApplicant());
            problemRepository.save(problem);
            if (newProblem.getDanger()==1){
                Set <UserToHouse> userToHouses = new HashSet<>();
                userToHouses=userToHouseRepository.findAllByHouse(newProblem.getHouse());
                for (UserToHouse userToHouse: userToHouses)
                    sendEmail(userToHouse.getUser(),newProblem.getApplicant(),newProblem.getDescription());
            }

            return ("Added.");
        }catch (Exception e){
            return("Unknown error");
        }
    }
    @CrossOrigin
    @PostMapping(value = "/addToAll")
    public String addToAll (Problem newProblem){
        try {
            Problem problem = new Problem();
            problem.setDanger(newProblem.getDanger());
            problem.setDescription(newProblem.getDescription());
            problem.setApplicant(newProblem.getApplicant());
            Set <House> houses = new HashSet<>();
            houses = houseRepository.findAll();
            for (House house : houses) {
                problem.setHouse(house.getId());
                problemRepository.save(problem);
            }
            return ("Added.");

        }catch (Exception e){
            return("Unknown error");
        }
    }
    @CrossOrigin
    @GetMapping(value="/all")
    public List<Problem> show (House house){
        return(problemRepository.findAllByHouse(house.getId()));
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
        final String subject = "Danger at home";
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
