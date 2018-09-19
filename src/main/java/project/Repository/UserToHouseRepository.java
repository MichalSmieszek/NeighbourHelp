package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.House;
import project.Model.User;
import project.Model.UserToHouse;

import java.util.List;
import java.util.Set;

public interface UserToHouseRepository  extends CrudRepository<UserToHouse, Integer> {
    Set<UserToHouse> findAllByHouse(int house);
    UserToHouse findById(int id);
    Set<UserToHouse> findAllByUser(int user);
    UserToHouse findFirstByUserAndHouse(int user,int house);
}
