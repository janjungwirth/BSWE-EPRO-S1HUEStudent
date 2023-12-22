
/**
 * FH Burgenland
 * (c) Jan Jungwirth
 * Student BSWE WS23
 */


package org.lecture;

import java.util.Scanner;
import java.util.TreeMap;

public class StudentRecordManager {

    private static StudentFileHandler sfh = new StudentFileHandler();
    private static TreeMap<Integer, Student> students = new TreeMap<>();
    private static final String fileName = "StudentRecords.csv";

    private static final String menue = """
            --- === [Student Manager v1 Jungwith] === ---
                    [ 1 | add new Student       ]
                    [ 2 | display all Students  ]
                    [ 3 | Search Student/ID     ]
                    [ 4 | Update information/ID*]
                    [ 5 | delete Student/ID*    ]
                    [ 0 | Save and Exit         ]
                    [ * = Bonus Functions       ]
            """;


    private static final String sub_menue_four = """
            --- === [Student Manager v1 Jungwith] === ---
                    [ 41| update Last Name      ]
                    [ 42| update Exam Score     ]
            """;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        students = sfh.loadStudentsFromFile(fileName);
        System.out.println("Info: Students Loaded...");
        System.out.println(menue);

        Integer selection = 0;

        do {
            System.out.print("Selection: ");
            selection = console.nextInt();
            console.nextLine();

            switch (selection) {
                case 1 -> addStudent(console);
                case 2 -> printStudents();
                case 3 -> printStudents(console);
                case 4 -> updateStudents(console);
                case 5 -> deleteStudent(console);
                case 0 -> saveStudents(fileName,students);
                default -> System.out.println(menue);
            }
        } while (selection != 0);

        console.close();
    }

    private static void saveStudents(String fileName, TreeMap<Integer, Student> students) {
        System.out.println("Saving...");
        sfh.saveStudentsToFile(fileName,students);
        sfh.saveStudentsByGrades(students);
        System.out.println("Saved...");
    }

    private static void updateStudents(Scanner scanner) {
        System.out.println("ID: ");
        Student selectedStudent = students.get(scanner.nextInt());
        scanner.nextLine();

        System.out.println(sub_menue_four);
        Integer selection = scanner.nextInt();
        scanner.nextLine();

        switch (selection) {
            case 41:
                System.out.println("old Last Name: " + selectedStudent.getLastName());
                System.out.print("Last Name: ");
                selectedStudent.setLastName(scanner.nextLine());
                System.out.println("new Last Name: " + selectedStudent.getLastName());
                break;
            case 42:
                System.out.println("old Score: " + selectedStudent.getScore());
                System.out.print("Score: ");
                selectedStudent.setScore(scanner.nextDouble());
                System.out.print("new Score: " + selectedStudent.getScore());
                break;
            default:
                System.out.println("ERR: No valid selection. Exit sub menue without change.");
        }

    }


    private static void addStudent(Scanner scanner) {
        Student tempStudent = new Student();
        System.out.println("--- === [        new Student        ] === ---");

        Integer id;
        System.out.print("ID:");
        tempStudent.setID(id = scanner.nextInt());
        scanner.nextLine();

        //ID already exists check
        if(students.keySet().contains(id)){
            System.out.println("Student with ID already Exists. Would wou like to overwrite? (Y|N)");
            if(!scanner.nextLine().toUpperCase().contains("Y"))
                return;
        }

        System.out.print("First Name:");
        tempStudent.setFirstName(scanner.nextLine());

        System.out.print("Last Name:");
        tempStudent.setLastName(scanner.nextLine());

        System.out.print("Exam Score:");
        tempStudent.setScore(scanner.nextDouble());
        scanner.nextLine();

        students.put(id, tempStudent);
    }

    private static void printStudents() {
        System.out.printf("%-5s %-20s %-20s %-5s\n","ID","First Name", "Last Name", "Score");
        for (Student s : students.values()) {
            System.out.printf("%-5d %-20s %-20s %-5.2f\n",s.getID(), s.getFirstName(), s.getLastName(), s.getScore());
        }
    }

    private static void printStudents(Scanner scanner) {
        System.out.print("ID:");
        Student s =  students.get(scanner.nextInt());
        scanner.nextLine();
        System.out.printf("%-5s %-20s %-20s %-5s\n","ID","First Name", "Last Name", "Score");
        System.out.printf("%-5d %-20s %-20s %-5.2f\n",s.getID(), s.getFirstName(), s.getLastName(), s.getScore());
    }


    private static void deleteStudent(Scanner scanner) {
        System.out.println("ID:");

        Integer id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Are you sure to Delete:\n" + students.get(id) + "\nPlease confirm selection (Y|N)");

        if (scanner.nextLine().toUpperCase().contains("Y")) {
            students.remove(id);
            System.out.println("Info: Student was removed...");
        } else
            System.out.println("Info: Stundet wasn't removed...");
    }
}
