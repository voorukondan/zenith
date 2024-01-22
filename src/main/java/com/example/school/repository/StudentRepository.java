// Write your code here
package com.example.school.repository;

import com.example.school.model.Student;
import java.util.*;

public interface StudentRespository {
    ArrayList<Student> getStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    String addMultipleStudents(ArrayList<Student> studentsList);

    Student updatStudent(int studentId, Student student);

    void deleteStudent(int studentId);
}