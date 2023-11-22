package controllersandServices;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("{id}")
    public Faculty getFacultyInfo(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.addFaculty(faculty);
    }

    @PutMapping
    public Faculty editFaculty(@RequestBody Faculty faculty) {
        Faculty foundFaculty = facultyService.editFaculty(faculty);
        if (foundFaculty == null) {
            return Faculty.status(HttpStatus.BAD_REQUEST).build();
        }
        return facultyService.editFaculty(faculty);
    }

    @DeleteMapping("{id}")
    public Faculty deleteFaculty(@PathVariable Long id) {
        return facultyService.deleteFaculty(id);
    }
    @GetMapping
    public Faculty findByNameOrColorFaculties(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        return facultyService.findByNameOrColorFaculties(color, name);
    }

    @GetMapping("/{id}/students")

    public Collection<Student> getStudents(@PathVariable Long id) {

        return facultyService.getStudents(id);

    }

    @GetMapping("/{studentId}/faculty")

    public Faculty getFaculty (@PathVariable long studentId) {

        return facultyService.getFaculty(studentId);

    }

    @GetMapping("/theLongestName")

    public getTheLongestFacultyName(){

        return facultyService.getTheLongestFacultyName();
    }

}





