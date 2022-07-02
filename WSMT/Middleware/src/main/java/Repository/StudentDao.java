package Repository;

import Domain.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public static int saveStudent(String name) {
        int status = 0;
        try {
            Connection conn = DbConnection.getConnection();

            PreparedStatement pstmt = conn.prepareStatement("insert into students(name) values (?)");
            pstmt.setString(1, name);
            status = pstmt.executeUpdate();
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return status;
    }
    public static Student getStudentById(int id) {
        Student student = new Student();
        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from students where id=?");
            pstmt.setInt(1, id);
            ResultSet result = pstmt.executeQuery();
           if(result.next()){
               student.setId(result.getInt(1));
               student.setName(result.getString(2));
           }
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return student;
    }
    public static List<Student> getAllStudents() {
        List<Student> list = new ArrayList<Student>();
        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from students");
            ResultSet result = pstmt.executeQuery();
            while(result.next()){
                list.add(new Student(result.getInt(1), result.getString(2)));
            }
            conn.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return list;
    }

    public static int removeStudent(int id) {

        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("delete from students where id=?");
            pstmt.setInt(1, id);
            conn.close();
            return pstmt.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return -1;
    }
    public static int updateStudent(int id, String name) {

        try{
            Connection conn = DbConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update students set name = ? where id=?");
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
