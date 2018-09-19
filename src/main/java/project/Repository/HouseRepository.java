package project.Repository;

import org.springframework.data.repository.CrudRepository;
import project.Model.House;

import java.util.Set;

public interface HouseRepository extends CrudRepository<House, Integer> {
    House findById(int id);
    Set<House> findAll();
    House findByName (String name);
}
