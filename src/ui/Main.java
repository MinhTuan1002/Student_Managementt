package ui;

import exception.StudentException;
import service.StudentService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentService sv = new StudentService();

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Thêm sinh viên");
            System.out.println("2. Hiển thị danh sách");
            System.out.println("3. Sửa sinh viên");
            System.out.println("4. Xóa sinh viên");
            System.out.println("5. Tìm kiếm");
            System.out.println("6. Sắp xếp theo điểm");
            System.out.println("7. Điểm trung bình lớp");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            try {
                int c;
                while (true) {
                    try {
                        c = Integer.parseInt(sc.nextLine());
                        if (c < 0 || c > 7) {
                            System.out.println(" Chỉ được chọn từ 0 đến 7");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(" Vui lòng nhập số!");
                    }
                }
                switch (c) {
                    case 1:
                        boolean backtoMenu = false;
                        String id;
                        while (true) {
                            System.out.print("ID: ");
                            id = sc.nextLine();
                            try {
                                if (!id.matches("SV\\d{3}")) {
                                    throw new StudentException("ID phải đúng dạng SVxxx");
                                }
                                if (sv.isIdExist(id)) {
                                    throw new StudentException("ID đã tồn tại");
                                }
                                break; // đúng thì thoát vòng lặp
                            } catch (StudentException e) {
                                System.out.println(" Lỗi " + e.getMessage());
                                if (backToMenu(sc)) {
                                    backtoMenu = true;
                                    break;
                                }
                            }
                        }
                        if (backtoMenu) {
                            break;
                        }
                        String name;
                        while (true) {
                            System.out.print("Tên: ");
                            name = sc.nextLine();
                            if (name.isEmpty()) {
                                System.out.println(" Tên không được để trống");
                            }
                            else if (name.matches("\\d+")) {
                                System.out.println(" Tên không được là số");
                                if (backToMenu(sc)) {
                                    backtoMenu = true;
                                    break;
                                }
                            }
                            else if (name.length() > 20) {
                                System.out.println(" Tên tối đa 20 ký tự");
                                if (backToMenu(sc)) {
                                    backtoMenu = true;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        if (backtoMenu) {
                            break;
                        }
                        double score = -1;
                        while (true) {
                            System.out.print("Điểm: ");
                            try {
                                score = Double.parseDouble(sc.nextLine());
                                if (score < 0 || score > 10) {
                                    throw new StudentException("Điểm phải từ 0 đến 10");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Điểm phải là số!");
                                if (backToMenu(sc)) {
                                    backtoMenu = true;
                                    break;
                                }
                            }
                        }
                        if (backtoMenu) {
                            break;
                        }
                        sv.add(id, name, score);
                        break;

                    case 2: sv.show(); break;

                    case 3:
                        while (true) {
                            try {
                                System.out.print("Nhập ID cần sửa: ");
                                id = sc.nextLine();

                                System.out.print("Tên mới: ");
                                name = sc.nextLine();

                                System.out.print("Điểm mới: ");
                                score = Double.parseDouble(sc.nextLine());

                                sv.update(id, name, score);

                                System.out.println("✔ Sửa sinh viên thành công");
                                break; // thoát vòng lặp nếu sửa OK

                            } catch (StudentException e) {
                                System.out.println(e.getMessage());
                                System.out.println(" Vui lòng nhập lại ID\n");
                            } catch (NumberFormatException e) {
                                System.out.println(" Điểm phải là số, nhập lại\n");
                            }
                        }
                        break;

                    case 4:
                        System.out.print("Nhập ID cần xóa: ");
                        id = sc.nextLine();

                        try {
                            sv.delete(id);
                            System.out.println(" Xóa thành công");
                        } catch (StudentException e) {
                            System.out.println(" Lỗi " + e.getMessage());
                        }
                        break;

                    case 5:
                        while (true) {
                            System.out.print("Nhập tên cần tìm: ");
                            String keyword = sc.nextLine();

                            boolean found = sv.search(keyword);

                            if (!found) {
                                System.out.println(" Không tìm thấy sinh viên");

                                if (backToMenu(sc)) {
                                    break; // quay về menu
                                }
                            } else {
                                break; // tìm thấy thì thoát case
                            }
                        }
                        break;

                    case 6:
                        System.out.println("1. Sắp xếp tăng dần");
                        System.out.println("2. Sắp xếp giảm dần");
                        System.out.print("Chọn: ");
                        int choose = Integer.parseInt(sc.nextLine());

                        if (choose == 1) {
                            sv.sortByScoreAsc();
                        } else if (choose == 2) {
                            sv.sortByScoreDesc();
                        } else {
                            System.out.println(" Lựa chọn không hợp lệ");
                        }
                        sv.showAll();
                        break;

                    case 7:
                        sv.avgScore();
                        break;

                    case 0:
                        return;
                }

            } catch (StudentException e) {
                System.out.println(" Lỗi " + e.getMessage());

                if (backToMenu(sc)) {
                    continue; //  QUAY VỀ MENU
                }
            } catch (Exception e) {
                System.out.println(" Nhập sai kiểu dữ liệu");

                if (backToMenu(sc)) {
                    continue; //  QUAY VỀ MENU
                }
            }
        }
    }
    public static boolean backToMenu(Scanner sc) {
        while (true) {
            System.out.print(" Quay về menu? (Y/N): ");
            String choice = sc.nextLine().trim().toUpperCase();
            if (choice.equals("Y")) return true;
            if (choice.equals("N")) return false;
            System.out.println(" Chỉ nhập Y hoặc N");
        }
    }
}

