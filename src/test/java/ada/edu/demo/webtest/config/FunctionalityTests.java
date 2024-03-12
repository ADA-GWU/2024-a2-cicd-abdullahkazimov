package ada.edu.demo.webtest.config;

import ada.edu.demo.webtest.entity.Student;
import ada.edu.demo.webtest.repository.StudentRepository;
import ada.edu.demo.webtest.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FunctionalityTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    @DisplayName("Test finding s student by ID")
    public void testStudentSearchById() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(new Student()));
        Student result = studentService.getStudentById(1);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Search by first or last name")
    public void testStudentSearch() {
        Student s1 = new Student(1,"Jamal","Hasanov","a@b.com",new Date(),null,null);
        Student s2 = new Student(2,"Aliya","Mammadova","a@b.com",new Date(),null,null);
        Student s3 = new Student(3,"Kamran","Aliyev","a@b.com",new Date(),null,null);

        List<Student> stList = List.of(s1,s2,s3);

        when(studentRepository.findAll()).thenReturn(stList);
        List<Student> students = studentService.getStudentByEitherName("Jamal","Aliyev");
        System.out.printf("Found students: "+students.size());
        assertEquals(2, students.size() );
    }

    @Test
    @DisplayName("Retrieve all active students")
    public void testFindAllActiveStudents() {
        // Prepare some mock students, simulating they are "active" (availTill is null implicitly here)
        Student s1 = new Student(1, "Jamal", "Hasanov", "jamal@example.com", new Date(), null, null);
        Student s2 = new Student(2, "Aliya", "Mammadova", "aliya@example.com", new Date(), null, null);
        List<Student> activeStudents = Arrays.asList(s1, s2);

        // When the findAllActive method is called on the repository, return the mock list
        when(studentRepository.findAllActive()).thenReturn(activeStudents);

        // Call the method under test
        List<Student> result = (List<Student>) studentService.getActiveStudents();

        // Assert that the result is not null and the size matches the expected number of active students
        assertNotNull(result, "The result should not be null.");
        assertEquals(2, result.size(), "There should be exactly 2 active students returned.");
    }
}
