import java.util.*;

public class StudentTable {

    public static int studentCount = 100;
    public static HashMap<Integer,Student> studentTable = new HashMap<Integer, Student>();

    public static void addEntry(Student student) {
        student.studentId = studentCount;
        studentTable.put(studentCount, student);
        studentCount++;
    }

    public static void print(HashSet<Student> studentSet) {
        for(Student student : studentSet) {
            System.out.println(student);
        }
    }

    public static HashSet<Student> allTable() {
        HashSet<Student> output = new HashSet<>();
        for(Map.Entry<Integer,Student> mapEntry : studentTable.entrySet()) {
            output.add(mapEntry.getValue());
        }
        return output;
    }

    public static HashSet<Student> inStudy(Study study, HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.study == Study.DESIGN) {
                output.add(student);
            }
        }
        return output;
    }

    public static HashSet<Student> inPool(Pool classPool, HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.classPool == Pool.CLASS_1) {
                output.add(student);
            }
        }
        return output;
    }

    public static HashSet<Student> areProjectManager(HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.projectManager) {
                output.add(student);
            }
        }
        return output;
    }

    public static HashSet<Student> areLeadProgrammer(HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.leadProgrammer) {
                output.add(student);
            }
        }
        return output;
    }

    public static HashSet<Student> areArtDirector(HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.artDirector) {
                output.add(student);
            }
        }
        return output;
    }

    public static HashSet<Student> onProject(String projectName, HashSet<Student> table) {
        HashSet<Student> output = new HashSet<>();
        for(Student student : table) {
            if(student.lastProjectName.equals(projectName)) {
                output.add(student);
            }
        }
        return output;
    }

    public static Student randomOne(HashSet<Student> table) {
        int rdm = new Random().nextInt(table.size()); // In real life, the Random object should be rather more shared than this
        int iter = 0;
        for(Student student : table)
        {
            if (iter == rdm) { return student; }
            iter++;
        }
        return null;
    }
}
