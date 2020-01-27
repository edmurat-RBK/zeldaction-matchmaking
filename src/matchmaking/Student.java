package matchmaking;

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
    public int wishGP;
    public int wishND;
    public int wishLD;
    public int wishSD;
    public int skillGP;
    public int skillND;
    public int skillLD;
    public int skillSD;
    public String[] softBanList;
    public String[] hardBanList;
    public String[] favList;

    public Student(String name, Study study, Pool classPool,
                   String boardGameProject, String rogueLikeProject,
                   boolean projectManager, boolean leadProgrammer, boolean artDirector,
                   int wishGP, int wishND, int wishLD, int wishSD,
                   int skillGP, int skillND, int skillLD, int skillSD,
                   String[] softBanList, String[] hardBanList, String[] favList) {
        this.name = name;
        this.study = study;
        this.classPool = classPool;

        this.boardGameProject = boardGameProject;
        this.rogueLikeProject = rogueLikeProject;

        this.projectManager = projectManager;
        this.leadProgrammer = leadProgrammer;
        this.artDirector = artDirector;

        this.wishGP = wishGP;
        this.wishND = wishND;
        this.wishLD = wishLD;
        this.wishSD = wishSD;

        this.skillGP = skillGP;
        this.skillND = skillND;
        this.skillLD = skillLD;
        this.skillSD = skillSD;

        this.softBanList = softBanList;
        this.hardBanList = hardBanList;
        this.favList = favList;
    }

    @Override
    public String toString() {
        return (projectManager ? "[PM]" : (leadProgrammer ? "[LP]" : (artDirector ? "[AD]" : "    "))) + (study==Study.DESIGN ? "(GD) " : "(GA) ") + name;
    }
}
