package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
User findById(int id);
User findFirstByName(String name);
List <User> findAll();
}
