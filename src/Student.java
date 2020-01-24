import java.util.ArrayList;

public class Student {

    public int studentId = -1;
    public String name;
    public Study study;
    public Pool classPool;
    public String boardGameProject;
    public String rogueLikeProject;
    public boolean projectManager;
    public boolean leadProgrammer;
    public boolean artDirector;
    public String[] banList = new String[3];
    public String[] favList = new String[1];

    public Student(String name, Study study, Pool classPool, String boardGameProject, String rogueLikeProject, boolean projectManager, boolean leadProgrammer, boolean artDirector, String[] banList, String[] favList) {
        this.name = name;
        this.study = study;
        this.classPool = classPool;
        this.boardGameProject = boardGameProject;
        this.rogueLikeProject = rogueLikeProject;
        this.projectManager = projectManager;
        this.leadProgrammer = leadProgrammer;
        this.artDirector = artDirector;
        this.banList = banList;
        this.favList = favList;
    }

    @Override
    public String toString() {
        return (projectManager ? "[PM]" : "") + (leadProgrammer ? "[LP]" : "") + (artDirector ? "[AD]" : "") + (study==Study.DESIGN ? "(GD) " : "(GA) ") + name;
    }
}
