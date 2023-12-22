package org.lecture;

public class Student {

    private Integer ID;
    private String firstName;
    private String lastName;
    private Double score;
    //added to not loose negative score values. stored for later use, no getter/setter needed, Class internal use only
    private boolean scoreValid=true;


    public Student(Integer ID, String firstName, String lastName, Double score) {
        //internal set called for savety check, see scoreValid field.
        this.setID(ID);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setScore(score);
    }

    public Student() {
    }

    @Override
    public String toString() {
        return "Student{" +
                "ID=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", score=" + score +
                '}';
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    //negative values not wanted in programm
    public Double getScore() {
        if(this.scoreValid)
            return score;
        else
            return Double.NaN;
    }

    //workaround to optain negative score values but formated so only useable in save functionality.
    public String getScoreAsFormatedString(){
        return String.format("%.2f", this.score).replace(",",".");
    }

    public void setScore(Double score) {
        if(score>100||score<0)
            this.scoreValid = false;
        this.score = score;
    }


}
