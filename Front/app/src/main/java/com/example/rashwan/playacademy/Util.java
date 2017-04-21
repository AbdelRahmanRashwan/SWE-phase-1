package com.example.rashwan.playacademy;

import com.example.rashwan.playacademy.Models.Course;
import com.example.rashwan.playacademy.Models.Student;
import com.example.rashwan.playacademy.Models.Teacher;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rashwan on 4/21/2017.
 */

public class Util {
    public static Teacher parseTeacher(JSONObject teacherData){
        Teacher teacher=new Teacher();
        try {
            teacher.setFirstName(teacherData.getString("firstName"));
            teacher.setFirstName(teacherData.getString("firstName"));
            teacher.setLastName(teacherData.getString("lastName"));
            teacher.setEmail(teacherData.getString("email"));
            teacher.setAge(teacherData.getInt("age"));
            teacher.setEducationalMail(teacherData.getString("educationalMail"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teacher;
    }

    public static Student parseStudent(JSONObject studentData){
        Student student=new Student();
        try {
            student.setUserId(studentData.getInt("userId"));
            student.setFirstName(studentData.getString("firstName"));
            student.setLastName(studentData.getString("lastName"));
            student.setEmail(studentData.getString("email"));
            student.setAge(studentData.getInt("age"));
        }catch (JSONException e){
            e.printStackTrace();
        }
        return student;
    }

    public static Course parsCourse(JSONObject courseData){
        Course course=new Course();
        try {
            course.setCourseId(courseData.getInt("courseId"));
            course.setCourseName(courseData.getString("courseName"));
            course.setCourseDescription(courseData.getString("courseDescription"));
            Teacher teacher=parseTeacher(courseData.getJSONObject("creator"));
            course.setCreator(teacher);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return course;
    }

}
