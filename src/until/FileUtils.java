package until;



import model.Student;

import java.io.*;
import java.util.*;

public class FileUtils {
    private static final String FILE = "students.csv";

    public static void save(List<Student> list) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Student s : list) {
                pw.println(s.toFile());
            }
        } catch (Exception e) {
            System.out.println("Lỗi ghi file");
        }
    }

    public static List<Student> read() {
        List<Student> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(Student.fromFile(line));
            }
        } catch (Exception e) {
            // file chưa tồn tại thì bỏ qua
        }
        return list;
    }

}



