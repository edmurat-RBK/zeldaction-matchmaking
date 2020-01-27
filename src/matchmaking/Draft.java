package matchmaking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;

public class Draft {

    public Pool classDraft;
    public HashSet<Student> studentSet;
    public HashSet<Group> groupSet;
    public int totalScore;
    public float averageScore;
    public float deviationScore;

    public Draft(Pool classDraft) {
        this.classDraft = classDraft;
        this.makeDraft();
    }

    @Override
    public String toString() {
        String output = "Draft ("+ (classDraft==Pool.CLASS_1 ? "Classe 1)" : "Classe 2)") + " : " + "\n";
        output += "==============================\n";
        output += "Total relation score : " + totalScore + "\n";
        output += "Average score : " + averageScore + "\n";
        output += "Standard deviation : " + Math.round(deviationScore*1000)/1000.0 + "\n";
        output += "==============================\n";
        for(Group group : groupSet) {
            output += group + "\n";
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
        for(Group group : groupSet) {
            group.evaluateRelation();
            evaluateDraft();
        }

    }

    public void evaluateDraft() {
        totalScore = 0;
        for(Group group : groupSet) {
            totalScore += group.relationScore;
        }

        averageScore = (totalScore * 1.0f) / groupSet.size();

        double deviationSum = 0;
        for(Group group : groupSet) {
            deviationSum += Math.pow(group.relationScore-averageScore,2);
        }
        deviationScore = (float) Math.sqrt(deviationSum / groupSet.size());
    }

}
