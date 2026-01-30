package model;

public class Student {
    private String id;
    private String name;
    private double score;

    public Student() {
    }

    public Student(String id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setScore(double score) {
        this.score = score;
    }

    public String getRank() {
        if (score >= 8) return "Giỏi";
        if (score >= 6.5) return "Khá";
        if (score >= 5) return "Trung bình";
        return "Yếu";
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + score + " | " + getRank();
    }

    public String toFile() {
        return id + "," + name + "," + score;
    }

    public static Student fromFile(String line) {
        String[] arr = line.split(",");
        return new Student(arr[0], arr[1], Double.parseDouble(arr[2]));
    }
}

