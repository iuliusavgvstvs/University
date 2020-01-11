import domain.Grade;
import domain.Homework;
import domain.Raport;
import domain.Student;
import domain.validators.ValidationException;
import org.junit.jupiter.api.Test;
import repository.GradeXMLRepo;
import repository.HomeworkXMLRepo;
import repository.StudentXMLRepo;
import service.GradeService;
import service.HomeworkService;
import service.RaportService;
import service.StudentService;

import static org.junit.jupiter.api.Assertions.*;

public class RaportTests {
    StudentXMLRepo<String, Student> repo = new StudentXMLRepo<>("XMLTestsStudentsStudents.xml");
    HomeworkXMLRepo<String, Homework> repo2 = new HomeworkXMLRepo<>("XMLTestsHomeworks.xml");
    GradeXMLRepo<String, Grade> repo3 = new GradeXMLRepo<>("XMLTestsGrades.xml");
    GradeService<String, Grade<String>> service3 = new GradeService<>(repo3);
    StudentService<String, Student<String>> service = new StudentService<>(repo);
    HomeworkService<String, Homework<String>> service2 = new HomeworkService<>(repo2);
    RaportService<String, Raport<String>> service4 = new RaportService<>(service, service3, service2);
    Grade<String> g1 = new Grade<>("1", "1", "1", 10, "prof", "fed");
    Grade<String> g2 = new Grade<>("2", "1", "2", 9, "proffff", "fed2");
    Grade<String> g3 = new Grade<>("3", "2", "2", 8, "proffffff", "fed3");
    Student<String> s1 = new Student<>("1", "David", "Bowie", "111", "dbowie");
    Student<String> s2 = new Student<>("2", "Roger", "Waters", "356", "roggy");
    Student<String> s3 = new Student<>("3", "Nick", "Manson", "356", "nickman");
    Student<String> s4 = new Student<>("4", "David", "Gilmour", "356", "theboss");
    Student<String> s5 = new Student<>("5", "Richard", "Wright", "356", "rick");
    Homework<String> homework1 = new Homework<>("1", 14, "desc");
    Homework<String> homework2 = new Homework<>("2", 13, "desc2");

    @Test
    public void getRaportsTests() throws ValidationException {
        service.add(s1);
        service.add(s2);
        service.add(s3);
        service.add(s4);
        service.add(s5);
        service2.add(homework1);
        service2.add(homework2);
        service3.add(g1);
        service3.add(g2);
        service3.add(g3);

        assertTrue(service4.getAverage(8, 10).iterator().hasNext());
        assertTrue (service4.getAverage(9, 10).iterator().hasNext());
        assertTrue (service4.getAverage(9.4, 10).iterator().hasNext());
        assertFalse(service4.getAverage(9.4, 9.4).iterator().hasNext());
        assertFalse(service4.getAverage(11, 12).iterator().hasNext());
        assertTrue(service4.getAverage(1, 10).iterator().hasNext());
        assertTrue(service4.getGradedAtTime().iterator().hasNext());
        assertTrue(service4.getAverageByHomeworks().iterator().hasNext());
        assertEquals(service4.getAverage(1, 10).iterator().next().getAverage(),9.5);
        assertEquals(service4.getAverageByHomeworks().iterator().next().getAverage(),10);
        assertEquals(service4.getGradedAtTime().iterator().next().getAverage(),9.5);
        assertEquals(service4.getAverage(5, 10).iterator().next().getAverage(),9.5);
        assertEquals(service4.getAverage(5, 9).iterator().next().getAverage(),8.0);
    }

}
