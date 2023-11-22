package controllersandServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collector;

@Service
public class StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Value("${avatars.dir.path}")
    private String avatarsDir;


    private final AvatarRepository aRepository;

    public StudentService(StudentRepository repository, AvatarRepository aRepository) {
        this.studentRepository = repository;
        this.avatarRepository = aRepository;
    }


    public StudentService(StudentRepository repository) {

        this.repository = repository;
    }

    private final StudentRepository repository;

    public StudentRepository addStudent(Student student) {
        logger.info("add method was invoked ");

        Student saved = repository.save(student);
    }

    public Student findStudent(int age) {
        logger.info("add method was invoked ");

        return repository.findByAge(age).orElse(null);
    }

    public Student editStudent(Student student) {

        logger.info("add method was invoked ");

        repository.findByAge(student.getAge())
                .map(
                        entity ->
                        {
                            entity.setColor(student.getAge());
                            entity.setName(student.getName());
                            return repository.save(entity);


                        }
                ).orElse(null);
    }

    public void  deleteStudent(int age) {
        logger.info("add method was invoked ");

        return repository.deleteByAge(age).orElse(null);
    }


    public Collection‹Student› findByAge(int age) {
        logger.info("add method was invoked ");

        return repository.findByAge(age);
    }

    public Collection<Student> filteredByAgeBetween(int min, int max){

        logger.info("add method was invoked ");

        return repository.findAllByAgeBetween(min, max);
    }

    public Avatar findAvatar(long studentId) {
        logger.info("add method was invoked ");

        return aRepository.findByStudentId(studentId).orElseThrow();
    }

    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {

        logger.info("add method was invoked ");
        Student student = findStudent(studentId);

        Path filePath = Path.of(avatarsDir, studentId + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = aRepository.findByStudentId(studentId).orElseGet(Avatar::new);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());

        aRepository.save(avatar);
    }

    private String getExtension(String fileName) {
        logger.info("add method was invoked ");

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public long studentsCount(){
        logger.info("add method was invoked ");

        return studentRepository.getStudentsCount();
    }

    public double averageAge(){
        logger.info("add method was invoked ");

        return  studentRepository.getAverageAge();
    }

    Collection<Student> lastFiveStudents(){
        logger.info("add method was invoked ");

        return studentRepository.getLastFiveStudents();
    }

    public Collection<String> getStudentsNameStartsA(){
        return studentRepository.findAll().stream()
                .nap(Student::getName)
                .filter(name -> name.toUpperCase().startsWith("A"))
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());

    }

    public double streamAverageAge(){
        return studentRepository.findAll()
                .stream()
                .map(Student::getAge)
                .mapToInt(o -> 0)
                .average()
                .orElse(0d);
    }
}

