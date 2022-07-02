package Repository;

import Domain.Grade;
import Domain.GradeEntry;

import java.util.ArrayList;
import java.util.List;

public class GradeEntryDao {
    public static List<GradeEntry> getAllGradesEntry() {
        List<GradeEntry> result = new ArrayList<>();
        List<Grade> grades = GradeDao.getAllGrades();
        for(Grade grade: grades) {
            System.out.println(grade.toString());
            String studentName = StudentDao.getStudentById(grade.getStudentId()).getName();
            System.out.println(studentName);
            String courseName = CourseDao.getCourseById(grade.getCourseId()).getName();
            System.out.println(courseName);
            result.add(new GradeEntry(grade.getId(), grade.getGrade(), studentName, courseName));
        }
        return result;
    }
}
