package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.House;
import project.Model.Problem;

import java.util.List;
import java.util.Set;

public interface ProblemRepository extends CrudRepository<Problem, Integer> {
    List <Problem> findById(int id);
    List<Problem> findAllByHouse(int house);
}
