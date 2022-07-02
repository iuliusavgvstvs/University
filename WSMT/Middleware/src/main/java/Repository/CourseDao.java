package Repository;

import Domain.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    public static int saveCourse(String name) {
        int status = 0;
        try {
            Connection conn = DbConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("insert into courses(name) values (?)");
            pstmt.setString(1, name);
            status = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return status;
    }
    public static Course getCourseById(int id) {
        Course course = new Course();
        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from courses where id=?");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
            if(result.next()){
                course.setId(result.getInt(1));
                course.setName(result.getString(2));
            }
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return course;
    }
    public static List<Course> getAllCourses() {
        List<Course> list = new ArrayList<Course>();
        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from courses");
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                list.add(new Course(result.getInt(1), result.getString(2)));
            }
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public static int removeCourse(int id) {

        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("delete from courses where id=?");
            pstmt.setInt(1, id);
            conn.close();
            return pstmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1;
    }
    public static int updateCourse(int id, String name) {

        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update courses set name = ? where id=?");
            pstmt.setString(1,name);
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
