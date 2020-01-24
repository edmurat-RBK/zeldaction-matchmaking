import java.util.ArrayList;

public class Student {

    public int studentId = -1;
    public String name;
    public Study study;
    public Pool classPool;
    public String lastProjectName;
    public boolean projectManager;
    public boolean leadProgrammer;
    public boolean artDirector;

    public Student(String name, Study study, Pool classPool, String lastProjectName, boolean projectManager, boolean leadProgrammer, boolean artDirector) {
        this.name = name;
        this.study = study;
        this.classPool = classPool;
        this.lastProjectName = lastProjectName;
        this.projectManager = projectManager;
        this.leadProgrammer = leadProgrammer;
        this.artDirector = artDirector;
    }

    @Override
    public String toString() {
        return (projectManager ? "[PM] " : "") + (leadProgrammer ? "[LP] " : "") + (artDirector ? "[AD] " : "") + (study==Study.DESIGN ? "GD - " : "GA - ") + name + " (" + lastProjectName + ")";
    }
}
