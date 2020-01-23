import java.util.ArrayList;

public class Student {

    public String name;
    public Study study;
    public Pool classPool;
    public Specialisation specialisation;
    public ArrayList<Student> banList;

    public static ArrayList<Student> studentTable = new ArrayList<Student>();

    public Student(String name, Study study, Pool classPool, Specialisation specialisation) {
        this.name = name;
        this.study = study;
        this.classPool = classPool;
        this.specialisation = specialisation;

        studentTable.add(this);
    }



}
