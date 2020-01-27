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

    //TODO Create WishStat and SkillStats : Average & SD for each subjects

    /**
     * Setup draft
     */
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
        output += "";
        for(Group group : groupSet) {
            output += group + "\n";
        }
        return output;
    }

    /**
     * Generate draft
     */
    private void makeDraft() {
        //Get a copy of student table
        studentSet = StudentTable.inPool(classDraft,StudentTable.allTable());

        //Create 5 empty group
        groupSet = new HashSet<>();
        for(int i=0; i<5 ; i++) {
            groupSet.add(new Group());
        }

        draftRoleCore();

        for(Group group : groupSet) {
            if(group.pmStudy() == Study.ART) {
                draftProjectManagerGA(group);
            }
            else {
                draftProjectManagerGD(group);
            }
        }

        emptyGD(studentSet);
        emptyGA(studentSet);


        //Calculate relation, skill and wishes of each group
        for(Group group : groupSet) {
            group.evaluateRelation();
            try {
                group.evaluateSkill();
                group.evaluateWish();
            } catch (IncorrectStudyException e) {
                e.printStackTrace();
            }
            evaluateDraft();
        }
    }

    /**
     * Draft 1PM, 1LP and 1DA for each group
     */
    public void draftRoleCore() {
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
    }

    /**
     * Draft team with a GA project manager
     */
    public void draftProjectManagerGA(Group group) {
        for(int i=0; i<2; i++) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
            group.join(pick);
            studentSet.remove(pick);
        }

        for(int i=0; i<3; i++) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.DESIGN,studentSet));
            group.join(pick);
            studentSet.remove(pick);
        }
    }

    /**
     * Draft team with a GD project manager
     */
    public void draftProjectManagerGD(Group group) {
        for(int i=0; i<2; i++) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.DESIGN,studentSet));
            group.join(pick);
            studentSet.remove(pick);
        }

        for(int i=0; i<2; i++) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
            group.join(pick);
            studentSet.remove(pick);
        }
    }

    /**
     * Empty GD list
     */
    public void emptyGD(HashSet<Student> studentSet) {
        while(!StudentTable.inStudy(Study.DESIGN,studentSet).isEmpty()) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.DESIGN,studentSet));
            for(Group group : groupSet) {
                if(group.designCount == 4 && group.artCount == 3) {
                    group.join(pick);
                    studentSet.remove(pick);
                    break;
                }
            }
        }
    }

    /**
     * Empty GA list
     */
    public void emptyGA(HashSet<Student> studentSet) {
        while(!StudentTable.inStudy(Study.ART,studentSet).isEmpty()) {
            Student pick = StudentTable.randomOne(StudentTable.inStudy(Study.ART,studentSet));
            for(Group group : groupSet) {
                if(group.designCount == 4 && group.artCount == 3) {
                    group.join(pick);
                    studentSet.remove(pick);
                    break;
                }
            }
            if(studentSet.contains(pick)) {
                for (Group group : groupSet) {
                    if (group.designCount == 5 && group.artCount == 3) {
                        group.join(pick);
                        studentSet.remove(pick);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Calculate draft score
     */
    public void evaluateDraft() {
        //Get total score
        totalScore = 0;
        for (Group group : groupSet) {
            totalScore += group.relationScore;
        }

        //Get score average
        averageScore = (totalScore * 1.0f) / groupSet.size();

        //Get score standard deviation
        double deviationSum = 0;
        for (Group group : groupSet) {
            deviationSum += Math.pow(group.relationScore - averageScore, 2);
        }
        deviationScore = (float) Math.sqrt(deviationSum / groupSet.size());

        //TODO : Calculate values of WishStat and SkillStat
    }
}
