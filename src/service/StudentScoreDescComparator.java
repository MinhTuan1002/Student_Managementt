package service;

import model.Student;

import java.util.Comparator;

public class StudentScoreDescComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return Double.compare(o2.getScore(), o1.getScore());
    }
}
