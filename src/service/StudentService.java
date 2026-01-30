package service;

import exception.StudentException;
import model.Student;
import sun.security.krb5.internal.ccache.FileCredentialsCache;
import until.FileUntils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class StudentService {

    private final FileUntils fileUtils = null;
    private List<Student> list = new ArrayList<>();

    public StudentService() {
        FileUntils FileUtils = fileUtils;
        list = FileUtils.load();
        list = FileUtils.load();
        if (list.isEmpty()) {
            list.add(new Student("SV001", "Nguyễn Văn A", 8.5));
            list.add(new Student("SV002", "Trần Thị B", 6.8));
            list.add(new Student("SV003", "Lê Văn C", 4.9));
            list.add(new Student("SV004", "Phạm Minh D", 9.2));
            FileUtils.save(list);
        }
    }



    public boolean isIdExist(String id) {
        for (Student s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    // Thêm sinh viên
    public void add(String id, String name, double score) throws StudentException {
        if (!Pattern.matches("SV\\d{3}", id)) {
            throw new StudentException("ID phải đúng dạng SV001");
        }
        if (isIdExist(id)) {
            throw new StudentException("ID đã tồn tại");
        }
        if (name.length() > 20) {
            throw new StudentException("Tên tối đa 20 ký tự");
        }

        if (score < 0 || score > 10) {
            throw new StudentException("Điểm phải từ 0 đến 10");
        }

        list.add(new Student(id, name, score));
        FileUtils.save(list);
    }

    // Hiển thị danh sách
    public void show() {
        if (list.isEmpty()) {
            System.out.println("Danh sách rỗng");
            return;
        }

        for (Student s : list) {
            System.out.println(s);
        }
    }

    // Tìm sinh viên theo ID
    private Student findById(String id) throws StudentException {
        for (Student s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        throw new StudentException("Không tìm thấy sinh viên");
    }

    // Xóa sinh viên
    public void delete(String id) throws StudentException {
        for (Student s : list) {
            if (s.getId().equalsIgnoreCase(id)) {
                list.remove(s);
                FileUtils.save(list);
                return;
            }
        }
        throw new StudentException("Không tìm thấy sinh viên để xóa");
    }

    // Sửa sinh viên
    public void update(String id, String name, double score) throws StudentException {
        Student s = findById(id);
        s.setName(name);
        s.setScore(score);
        FileUtils.save(list);
    }

    // Tìm kiếm theo tên
    public boolean search(String keyword) {
        boolean found = false;
        for (Student s : list) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(s);
                found = true;
            }
        }
        return found;
    }

    // Sắp xếp theo điểm (tăng dần)
    public void sortByScoreAsc() {
        Collections.sort(list, new StudentScoreComparator());
        System.out.println("✅ Đã sắp xếp theo điểm tăng dần");
    }

    //Sắp xếp theo điểm (giảm dần)
    public void sortByScoreDesc() {
        Collections.sort(list, new StudentScoreDescComparator());
        System.out.println("✅ Đã sắp xếp theo điểm giảm dần");
    }
    public void showAll() {
        System.out.printf("%-10s %-20s %-5s\n", "ID", "Tên", "Điểm");
        for (Student s : list) {
            System.out.printf("%-10s %-20s %-5.1f\n",
                    s.getId(),
                    s.getName(),
                    s.getScore());
        }
    }

    // Tính điểm trung bình
    public void avgScore() {
        if (list.isEmpty()) {
            System.out.println("Chưa có sinh viên");
            return;
        }

        double sum = 0;
        for (Student s : list) {
            sum += s.getScore();
        }

        double avg = sum / list.size();
        System.out.println("Điểm trung bình: " + avg);
    }


}
