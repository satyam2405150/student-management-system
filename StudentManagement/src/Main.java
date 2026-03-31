import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== STUDENT MANAGEMENT SYSTEM ==========");

            // STUDENT
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student by Name");
            System.out.println("6. Search by Department");
            System.out.println("7. Count Students per Department");

            // COURSE
            System.out.println("8. Add Course");
            System.out.println("9. View Courses");

            // ENROLLMENT
            System.out.println("10. Enroll Student (using Roll No)");
            System.out.println("11. View Enrollments");
            System.out.println("12. Count Students per Course");
            System.out.println("13. Show Courses of a Student");

            // ATTENDANCE
            System.out.println("14. Mark Attendance");
            System.out.println("15. View Attendance");
            System.out.println("16. Attendance Percentage");

            // EXIT
            System.out.println("17. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                // ================= STUDENT =================
                case 1:
                    System.out.print("Roll No: ");
                    String roll = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Department: ");
                    String dept = sc.nextLine();

                    studentDAO.addStudent(roll, name, email, dept);
                    break;

                case 2:
                    studentDAO.getAllStudents();
                    break;

                case 3:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.print("Enter Student ID: ");
                    int uid = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New Roll No: ");
                    String newRoll = sc.nextLine();

                    System.out.print("New Name: ");
                    String newName = sc.nextLine();

                    System.out.print("New Email: ");
                    String newEmail = sc.nextLine();

                    System.out.print("New Department: ");
                    String newDept = sc.nextLine();

                    studentDAO.updateStudent(uid, newRoll, newName, newEmail, newDept);
                    break;

                case 4:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.print("Enter Student ID: ");
                    int did = sc.nextInt();

                    studentDAO.deleteStudent(did);
                    break;

                case 5:
                    System.out.print("Enter Name: ");
                    String search = sc.nextLine();

                    studentDAO.searchStudentByName(search);
                    break;

                case 6:
                    System.out.print("Enter Department: ");
                    String d = sc.nextLine();

                    studentDAO.searchByDepartment(d);
                    break;

                case 7:
                    studentDAO.countStudentsPerDepartment();
                    break;

                // ================= COURSE =================
                case 8:
                    System.out.print("Course Name: ");
                    String cname = sc.nextLine();

                    System.out.print("Credits: ");
                    int credits = sc.nextInt();

                    courseDAO.addCourse(cname, credits);
                    break;

                case 9:
                    courseDAO.getAllCourses();
                    break;

                // ================= ENROLLMENT =================
                case 10:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.println("\nAvailable Courses:");
                    courseDAO.getAllCourses();

                    System.out.print("Enter Roll No: ");
                    String r = sc.nextLine();

                    System.out.print("Enter Course ID: ");
                    int cid = sc.nextInt();

                    enrollmentDAO.enrollStudentByRoll(r, cid);
                    break;

                case 11:
                    enrollmentDAO.viewEnrollments();
                    break;

                case 12:
                    enrollmentDAO.countStudentsPerCourse();
                    break;

                case 13:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();

                    enrollmentDAO.getCoursesOfStudent(sid);
                    break;

                // ================= ATTENDANCE =================
                case 14:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.println("\nAvailable Courses:");
                    courseDAO.getAllCourses();

                    System.out.print("Enter Roll No: ");
                    String ar = sc.nextLine();

                    System.out.print("Enter Course ID: ");
                    int ac = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Status (Present/Absent): ");
                    String status = sc.nextLine();

                    attendanceDAO.markAttendance(ar, ac, status);
                    break;

                case 15:
                    attendanceDAO.viewAttendance();
                    break;

                case 16:
                    System.out.println("\nAvailable Students:");
                    studentDAO.getAllStudents();

                    System.out.println("\nAvailable Courses:");
                    courseDAO.getAllCourses();

                    System.out.print("Enter Student ID: ");
                    int psid = sc.nextInt();

                    System.out.print("Enter Course ID: ");
                    int pcid = sc.nextInt();

                    attendanceDAO.calculateAttendance(psid, pcid);
                    break;

                // ================= EXIT =================
                case 17:
                    System.out.println("Exiting...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}