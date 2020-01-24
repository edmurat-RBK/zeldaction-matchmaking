import java.util.HashSet;

public class Draft {

    public Pool classDraft;
    public int totalGroup;
    public int minPerson, maxPerson;
    public int minDesigner, maxDesigner;
    public int minArtist, maxArtist;
    public HashSet<Student> studentSet;
    public HashSet<Group> groupSet;

    public Draft(Pool classDraft, int totalGroup, int minPerson, int maxPerson, int minDesigner, int maxDesigner, int minArtist, int maxArtist) {
        this.classDraft = classDraft;
        this.totalGroup = totalGroup;
        this.minPerson = minPerson;
        this.maxPerson = maxPerson;
        this.minDesigner = minDesigner;
        this.maxDesigner = maxDesigner;
        this.minArtist = minArtist;
        this.maxArtist = maxArtist;

        this.makeDraft();
    }

    @Override
    public String toString() {
        String output = "Draft ("+ (classDraft==Pool.CLASS_1 ? "Classe 1)" : "Classe 2)") + " : " + "\n";
        for(Group g : groupSet) {
            output += g + "\n\n";
        }
        return output;
    }

    private void makeDraft() {
        //Get a copy of table
        studentSet = StudentTable.inPool(classDraft,StudentTable.allTable());

        //Create empty group
        groupSet = new HashSet<>();
        for(int i=0; i<5 ; i++) {
            groupSet.add(new Group());
        }

        //Get 5 PM and put one in each group
        for(Group group : groupSet) {
            Student pick = StudentTable.randomOne(StudentTable.areProjectManager(studentSet));
            group.draft.add(pick);
            studentSet.remove(pick);
        }

        //Get 5 LP and put one in each group
        for(Group group : groupSet) {
            Student pick = StudentTable.randomOne(StudentTable.areLeadProgrammer(studentSet));
            group.draft.add(pick);
            studentSet.remove(pick);
        }

        //Get 5 AD and put one in each group
        for(Group group : groupSet) {
            Student pick = StudentTable.randomOne(StudentTable.areArtDirector(studentSet));
            group.draft.add(pick);
            studentSet.remove(pick);
        }

        //Fill with every GD left in the set
        while(!StudentTable.inStudy(Study.DESIGN,studentSet).isEmpty()) {
            for(Group group : groupSet) {
                Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.DESIGN,studentSet));
                group.join(pick);
                studentSet.remove(pick);
                if(StudentTable.inStudy(Study.DESIGN,studentSet).isEmpty()) { break; }
            }
        }

        //Fill with every GA left in the set
        while(!StudentTable.inStudy(Study.ART,studentSet).isEmpty()) {
            for(Group group : groupSet) {
                if(group.designCount == 5) {
                    for(int i=0; i<2; i++) {
                        Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
                        group.join(pick);
                        studentSet.remove(pick);
                    }
                }
                else if(group.designCount == 4) {
                    for(int i=0; i<3; i++) {
                        Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
                        group.join(pick);
                        studentSet.remove(pick);
                    }
                }
            }

            if(!StudentTable.inStudy(Study.ART,studentSet).isEmpty()) {
                for(Group group : groupSet) {
                    if(group.designCount == 5 && group.artCount == 3) {
                        Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
                        group.join(pick);
                        studentSet.remove(pick);
                        if(StudentTable.inStudy(Study.ART,studentSet).isEmpty()) { break; }
                    }
                }
            }
        }

        //Calculate "relation score" of each group


    }

}
