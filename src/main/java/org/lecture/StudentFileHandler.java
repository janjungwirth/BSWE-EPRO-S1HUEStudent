package org.lecture;

import com.sun.source.tree.Tree;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.TreeMap;

public class StudentFileHandler {

    private static String headline;


    /**
     * Function: reads line by line a file, converts to a Student, loads in a TreeMap<Integer:ID, Student>
     * Returns: TreeMap of loaded Students
     * Errors: returns empty Student Map, IOExeption possible (checked), returns null if file not found
    * */

    public TreeMap<Integer,Student> loadStudentsFromFile(String fileName){
        TreeMap<Integer,Student> students = new TreeMap<>();
        Path path = Paths.get("src","main", "resources", fileName);

        if(Files.notExists(path)){
            System.err.println("File not found: "+ path.toAbsolutePath());
            return null;
        }

        try(BufferedReader reader = Files.newBufferedReader(path)) {
            //save headline for later use
            headline = reader.readLine();

            //read first student line
            String line = reader.readLine();

            while(line!=null){
                //Split a line in its components with ',' as split e.g.: 1,Lucien,Wicht,100.00 => ID:1 firstName:Lucien lastName:Wicht score:100.00
                String[] studentBits = line.split(",");
                Integer id = Integer.parseInt(studentBits[0]);
                Student tempStudent = new Student(
                        id, // ID
                        studentBits[1], //first Name
                        studentBits[2], //last Name
                        Double.parseDouble(studentBits[3]) //score
                );
                //add student to students map
                students.put(id, tempStudent);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return students;
    }


    public void saveStudentsToFile(String fileName, TreeMap<Integer, Student> students){
        Path path = Paths.get("src","main", "resources", fileName);


        //if error with headline, it will appear here. headline saved while reading file.
        if (headline.isEmpty()){
            System.err.println("Error with loaded Student Headline!");
            System.err.println("Student File was not saved!");
            return;
        }

        //alternative BufferedWriter writer = Files.newBufferedReader(path)
        //PrintWriter can be used with writer.print functions, similar to the System.out.print commands
        try(PrintWriter writer = new PrintWriter(new FileWriter(path.toString()))) {
            writer.println(headline);
            for(Student s:students.values()){
                writer.printf("%d,%s,%s,%s\n",
                        s.getID(),
                        s.getFirstName(),
                        s.getLastName(),
                        s.getScoreAsFormatedString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveStudentsByGrades(TreeMap<Integer,Student> students){

        TreeMap<Integer, Student> sehrgut=new TreeMap<>();
        TreeMap<Integer, Student> gut=new TreeMap<>();
        TreeMap<Integer, Student> befriedigend=new TreeMap<>();
        TreeMap<Integer, Student> genuegend=new TreeMap<>();
        TreeMap<Integer, Student> nichtgenuegend=new TreeMap<>();
        TreeMap<Integer, Student> NaN=new TreeMap<>();

        for(Student s:students.values()){
            Double score = s.getScore();
            if(score>87.5f) { sehrgut.put(s.getID(), s);}
             else if(score>75.0f) { gut.put(s.getID(), s);}
              else if(score>62.5f) { befriedigend.put(s.getID(), s);}
               else if(score>50.0f) { genuegend.put(s.getID(), s);}
                else if(score<=50.0f) { nichtgenuegend.put(s.getID(), s);}
                 else NaN.put(s.getID(),s);
        }

        saveStudentsToFile("grades\\1.csv",sehrgut);
        saveStudentsToFile("grades\\2.csv",gut);
        saveStudentsToFile("grades\\3.csv",befriedigend);
        saveStudentsToFile("grades\\4.csv",genuegend);
        saveStudentsToFile("grades\\5.csv",nichtgenuegend);
        saveStudentsToFile("grades\\NaN.csv",NaN);
    }


}
