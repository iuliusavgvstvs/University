package Repository;

import Domain.Course;
import Domain.Grade;
import Domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GradeDao {
    public static int saveGrade(int studentId, int courseId, int grade) {
        int status = 0;
        try {
            Connection conn = DbConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("insert into grades(student_id, course_id, grade) values (?, ?, ?)");
            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);
            pstmt.setInt(3, grade);
            status = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return status;
    }

    public static List<Grade> getAllGrades() {
        List<Grade> list = new ArrayList<Grade>();
        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from grades");
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                list.add(new Grade(result.getInt(1), result.getInt(2), result.getInt(3), result.getInt(4)));
            }
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public static int updateGrade(int id, int grade) {

        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update grades set grade = ? where id=?");
            pstmt.setInt(1,grade);
            pstmt.setInt(2, id);
            int status = pstmt.executeUpdate();
            conn.close();
            return status;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1;
    }
}
