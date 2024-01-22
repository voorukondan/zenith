
package com.example.school.service;

import com.example.school.Student;
import com.example.school.StudentRowMapper;
import com.example.school.repository.StudentRespository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.sterotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

import javax.validation.OverridesAttribute;

@Service
public class StudentH2Service implements StudentRespository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Student> getStudents() {
        List<Student> studentData = db.query("select * from student", new StudentRowMapper());
        ArrayList<Student> students = new ArrayList<>(studentData);
        return students;
    }

    @Override
    public Student getStudentById(int studentId) {
        try {
            Student student = db.queryForObject("select * from student where student where studentId = ?",
                    new StudentRowmapper(), studentId);
            return student;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Student addStudent(com.example.school.model.Student student) {
        db.update("insert into student(studentName,Gender,Standard) values (?,?,?)", student.getStudentName(),
                student.getGender(), student.getStandard());
        Student savedStudent = db.queryForObject(
                "select * from student where studentName = ? and Gender = ? and Standard = ?",
                new com.example.school.model.StudentRowMapper(),
                student.getStudentName(), student.getGender(), student.getStandard());
        return savedStudent;
    }

    @Override
    public String addMultipleStudents(ArrayList<Student> studentsList) {
        for (Student eachStudent : studentsList) {
            db.update("insert into student(studentName,gender,standard) values (?,?,?)", eachStudent.getStudentName(),
                    eachStudent.getGender(), eachStudent.getStandard());
        }
        String responseMessage = String.format("Successfully added %d students", studentsList.size());
        return responseMessage;
    }

    @Override
    public void deleteStudent(int studentId) {
        db.update("delete from student where studentId = ?", studentId);
    }

    @Override
    public Student updateStudent(int studentId, Student student) {
        if (student.getStudentName() != null) {
            db.update("update student set studentName =?", student.getStudentName(), studentId);
        }
        if (student.getGender() != null) {
            db.update("update student set Gender = ?  where studentId =?", student.getGender(), studentId);
        }
        if (student.getStandard() != 0) {
            db.update("update student set Standard = ? where studentId = ?", student.getStandard(), studentId);
        }
        return getStudentById(studentId);
    }
}
