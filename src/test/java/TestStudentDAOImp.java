import com.sppp.dao.StudentDAO;
import com.sppp.dao.StudentDAOImp;
import com.sppp.gui.StudentProjectForm;
import com.sppp.model.Student;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestStudentDAOImp {
    @Test
    public void getAllTest(){
        StudentDAO studentDAO = new StudentDAOImp();
        try {
            List<Student> studentList = (ArrayList<Student>)studentDAO.getAllStudents();
            for(Student student : studentList){
                System.out.println(student.getName());
                assertNotNull(student);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void assignProject(){
        SwingUtilities.invokeLater(() -> {
            StudentProjectForm studentProjectForm = new StudentProjectForm();
            studentProjectForm.setVisible(true);
        });
    }
}

