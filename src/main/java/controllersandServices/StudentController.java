package controllersandServices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public Student getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return Student.notFound().build();
        }
        return Student.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public Student editStudent(@RequestBody Student student, @PathVariable long id) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return Student.status(HttpStatus.BAD_REQUEST).build();
        }
        return Student.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public Student deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return Student.ok().build();
    }


    @GetMapping
    public Student findStudents(@RequestParam(required = false) int age) {
        if (age › 0) {
            return Student.ok(studentService.findByAge(age));
        }
        return Student.ok(Collections.emptyList());
    }

    @GetMapping ("/byAgeBetween")
    public Collection<Student> byAgeBetween(@RequestParam int min, @RequestParam int max){

        return service.filteredByAgeBetween(min, max);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity‹String› uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() › 1024 * 300) {
            return ResponseEntity.badRequest().body("File is too big");
        }

        studentService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/avatar/preview")
    public ResponseEntity‹byte[]› downloadAvatar(@PathVariable Long id) {
        Avatar avatar = studentService.findAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = studentService.findAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/count")
    public long getCount(){
        return service.studentsCount();
    }

    @GetMapping("/averageAge")
    public double averageAge(){
        return service.getAverageAge();
    }

    @GetMapping("/lastFive")
    public Collection<Student> lastFiveStudents() {
        return service.GetLastFiveStudents();
    }

    @GetMapping("/namesStartsA")
    public Collection<String> getStudentsNameStartsA(){
return studentService.getStudentsNameStartsA();
    }
    @GetMapping("/streamAverageAge")
    public streamAverageAge(){

        return studentService.streamAverageAge();
    }


}


