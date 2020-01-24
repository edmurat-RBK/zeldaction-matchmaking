import java.util.HashSet;

public class Group {

    public HashSet<Student> draft;
    public int designCount = 0;
    public int artCount = 0;
    public int projectManagerCount = 0;
    public int leadProgrammerCount = 0;
    public int artDirectorCount = 0;

    public void groupCount() {
        for(Student student : draft) {
            if(student.study == Study.DESIGN) { designCount++; }
            else if(student.study == Study.ART) { artCount++; }

            if(student.projectManager) { projectManagerCount++; }
            if(student.leadProgrammer) { leadProgrammerCount++; }
            if(student.artDirector) { artDirectorCount++; }
        }
    }

}
