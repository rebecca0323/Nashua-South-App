//Made by Rebecca Zhu
//purpose is for the Grade objects from X2

package com.example.myapplication;

public class Grade {
    //each grade object has a teacher and the grade
    private String teacher;
    private String grade; //if it's eblock, then grade is empty string

    //constructor to instantiate grade objects
    public Grade(String teacher, String grade){
        this.setTeacher(teacher);
        this.setGrade(grade);
    }

    //getter for the teacher
    public String getTeacher() {
        return teacher;
    }

    //setter for teacher
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    //getter for grade
    public String getGrade() {
        return grade;
    }

    //setter for grade
    public void setGrade(String grade) {
        this.grade = grade;
    }
}
