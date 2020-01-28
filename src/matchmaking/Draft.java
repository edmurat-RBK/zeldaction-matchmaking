package matchmaking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;

public class Draft {

    public Pool classDraft;
    public HashSet<Student> studentSet;
    public HashSet<Group> groupSet;
    public int totalScore;
    public float averageScore;
    public float deviationScore;
    public HashMap<Ability,AbilityScore> totalAbility;
    public HashMap<Ability,AbilityScore> averageAbility;
    public HashMap<Ability,AbilityScore> deviationAbility;

    /**
     * Setup draft
     */
    public Draft(Pool classDraft) {
        this.classDraft = classDraft;
        this.totalAbility = new HashMap<>();
        this.averageAbility = new HashMap<>();
        this.deviationAbility = new HashMap<>();
        this.makeDraft();
    }

    @Override
    public String toString() {
        String output = "Draft ("+ (classDraft==Pool.CLASS_1 ? "Classe 1)" : "Classe 2)") + " : " + "\n";
        output += "===== SCORE ==================\n";
        output += "Average score : " + averageScore + "\n";
        output += "Standard deviation : " + Math.round(deviationScore*1000)/1000.0 + "\n";
        if(Main.showAbilities) {
            output += "===== WISHES =================\n";
            output += "  Game programming >   Moyenne : " + averageAbility.get(Ability.GAME_PROGRAMMING).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.GAME_PROGRAMMING).wish + "\n" +
                    "  Narrative design >   Moyenne : " + averageAbility.get(Ability.NARRATIVE_DESIGN).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.NARRATIVE_DESIGN).wish + "\n" +
                    "      Level design >   Moyenne : " + averageAbility.get(Ability.LEVEL_DESIGN).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.LEVEL_DESIGN).wish + "\n" +
                    "      Sound design >   Moyenne : " + averageAbility.get(Ability.SOUND_DESIGN).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.SOUND_DESIGN).wish + "\n";
            output += "  Character design >   Moyenne : " + averageAbility.get(Ability.CHARACTER_DESIGN).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.CHARACTER_DESIGN).wish + "\n" +
                    "Environment design >   Moyenne : " + averageAbility.get(Ability.ENVIRONMENTAL_DESIGN).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.ENVIRONMENTAL_DESIGN).wish + "\n" +
                    "          Tech art >   Moyenne : " + averageAbility.get(Ability.TECH_ART).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.TECH_ART).wish + "\n" +
                    "   Modelisation 3D >   Moyenne : " + averageAbility.get(Ability.MODELISATION_3D).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.MODELISATION_3D).wish + "\n" +
                    "         Animation >   Moyenne : " + averageAbility.get(Ability.ANIMATION).wish + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.ANIMATION).wish + "\n";
            output += "===== SKILLS =================\n";
            output += "  Game programming >   Moyenne : " + averageAbility.get(Ability.GAME_PROGRAMMING).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.GAME_PROGRAMMING).skill + "\n" +
                    "  Narrative design >   Moyenne : " + averageAbility.get(Ability.NARRATIVE_DESIGN).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.NARRATIVE_DESIGN).skill + "\n" +
                    "      Level design >   Moyenne : " + averageAbility.get(Ability.LEVEL_DESIGN).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.LEVEL_DESIGN).skill + "\n" +
                    "      Sound design >   Moyenne : " + averageAbility.get(Ability.SOUND_DESIGN).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.SOUND_DESIGN).skill + "\n";
            output += "  Character design >   Moyenne : " + averageAbility.get(Ability.CHARACTER_DESIGN).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.CHARACTER_DESIGN).skill + "\n" +
                    "Environment design >   Moyenne : " + averageAbility.get(Ability.ENVIRONMENTAL_DESIGN).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.ENVIRONMENTAL_DESIGN).skill + "\n" +
                    "          Tech art >   Moyenne : " + averageAbility.get(Ability.TECH_ART).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.TECH_ART).skill + "\n" +
                    "   Modelisation 3D >   Moyenne : " + averageAbility.get(Ability.MODELISATION_3D).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.MODELISATION_3D).skill + "\n" +
                    "         Animation >   Moyenne : " + averageAbility.get(Ability.ANIMATION).skill + "\n" +
                    "                     Deviation : " + deviationAbility.get(Ability.ANIMATION).skill + "\n";
        }
        output += "==============================\n\n\n";
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
                group.evaluateWishAndSkill();
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


        //Calculate draft totals for each ability
        for(Ability ability : Ability.values()) {
            float tmpWish = 0;
            float tmpSkill = 0;

            for(Group group : groupSet) {
                tmpWish += group.totalWishAndSkill.get(ability).wish;
                tmpSkill += group.totalWishAndSkill.get(ability).skill;
            }

            totalAbility.put(ability,new AbilityScore(ability,tmpWish,tmpSkill));
        }

        //Calculate average draft ability
        for(Ability ability : Ability.values()) {
            averageAbility.put(ability,new AbilityScore(ability,totalAbility.get(ability).wish/groupSet.size(),totalAbility.get(ability).skill/groupSet.size()));
        }

        //Calculate deviation draft ability
        for(Ability ability : Ability.values()) {
            float deviationWishSum = 0;
            float deviationSkillSum = 0;
            for(Group group : groupSet) {
                deviationWishSum += Math.pow(group.totalWishAndSkill.get(ability).wish - averageAbility.get(ability).wish,2);
                deviationSkillSum += Math.pow(group.totalWishAndSkill.get(ability).skill - averageAbility.get(ability).skill,2);
            }
            deviationAbility.put(ability,new AbilityScore(ability,(float)Math.sqrt(deviationWishSum/groupSet.size()),(float)Math.sqrt(deviationSkillSum/groupSet.size())));
        }
    }
}
