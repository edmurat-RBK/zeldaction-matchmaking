import java.util.HashSet;

public class Group {

    public HashSet<Student> draft;
    public int designCount = 0;
    public int artCount = 0;
    public int projectManagerCount = 0;
    public int leadProgrammerCount = 0;
    public int artDirectorCount = 0;

    @Override
    public String toString() {
        groupCount();

        String output = "Groupe : "+designCount+"GD / "+artCount+"GA"+"\n";
        for(Student s : draft) {
            output += s + " - ";
        }
        return output;
    }

    public Group() {
        this.draft = new HashSet<>();
    }

    public void join(Student student) {
        draft.add(student);
        groupCount();
    }

    public void groupCount() {
        resetCount();

        for(Student student : draft) {
            if(student.study == Study.DESIGN) { designCount++; }
            else if(student.study == Study.ART) { artCount++; }

            if(student.projectManager) { projectManagerCount++; }
            if(student.leadProgrammer) { leadProgrammerCount++; }
            if(student.artDirector) { artDirectorCount++; }
        }
    }

    public void resetCount() {
        designCount = 0;
        artCount = 0;
        projectManagerCount = 0;
        leadProgrammerCount = 0;
        artDirectorCount = 0;
    }
}
