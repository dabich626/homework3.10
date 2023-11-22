package controllersandServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.FacultyRepository;

import java.util.Collection;
import java.util.Comparator;

@Service
public class FacultyService {

    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    private final FacultyRepository repository;

    public Faculty addFaculty(Faculty faculty) {
        logger.info("add method was invoked ");

        Faculty saved = repository.save(faculty);
    }

    public Faculty findFaculty(long id) {

        logger.info("add method was invoked ");
        return repository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {

        logger.info("add method was invoked ");

        repository.findById(faculty.getId())
                .map(
                        entity ->
                        {
                            entity.setColor(faculty.getColor());
                            entity.setName(faculty.getName());
                            return repository.save(entity);


                        }
                ).orElse(null);
    }

    public void  deleteFaculty(long id) {
        logger.info("add method was invoked ");

        return repository.deleteById(id).orElse(null);
    }


    public Collection‹Faculty› findByColor(String color) {
        logger.info("add method was invoked ");

        return repository.findAllByColor(color)

}

public Collection<Faculty> filterByNameOrColor(String name, String color){
    logger.info("add method was invoked ");

        return repository.findAllByNameOrColorIgnoreCase(name, color); 
}

    public Faculty findAllStudentsById(long id){
        logger.info("add method was invoked ");

        return repository.findAllStudentsById;
    }

    public Faculty getFaculty(long id) {

        logger.info("add method was invoked ");

        var studentTemporary = repository.findById(id).orElseThrow(() -> new StudentNotFoundException);

        return studentTemporary.getFaculty();

    }

    public String getTheLongestFacultyName(){
        return repository.findAll()
                .stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

}