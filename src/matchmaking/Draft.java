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
    public WishStat wishStat;
    public SkillStat skillStat;

    /**
     * Setup draft
     */
    public Draft(Pool classDraft) {
        this.classDraft = classDraft;
        this.wishStat = new WishStat();
        this.skillStat = new SkillStat();
        this.makeDraft();
    }

    @Override
    public String toString() {
        String output = "Draft ("+ (classDraft==Pool.CLASS_1 ? "Classe 1)" : "Classe 2)") + " : " + "\n";
        output += "===== SCORE ==================\n";
        output += "Average score : " + averageScore + "\n";
        output += "Standard deviation : " + Math.round(deviationScore*1000)/1000.0 + "\n";
        /*output += "===== WISHES =================\n";
        output += "  Game programming >   Moyenne : "+wishStat.averageGameProgramming+"\n"+
                  "                     Deviation : "+wishStat.deviationGameProgramming+"\n"+
                  "  Narrative design >   Moyenne : "+wishStat.averageNarrativeDesign+"\n"+
                  "                     Deviation : "+wishStat.deviationNarrativeDesign+"\n"+
                  "      Level design >   Moyenne : "+wishStat.averageLevelDesign+"\n"+
                  "                     Deviation : "+wishStat.deviationLevelDesign+"\n"+
                  "      Sound design >   Moyenne : "+wishStat.averageSoundDesign+"\n"+
                  "                     Deviation : "+wishStat.deviationSoundDesign+"\n";
        output += "  Character design >   Moyenne : "+wishStat.averageCharaDesign+"\n"+
                  "                     Deviation : "+wishStat.deviationCharaDesign+"\n"+
                  "Environment design >   Moyenne : "+wishStat.averageEnvironmentDesign+"\n"+
                  "                     Deviation : "+wishStat.deviationEnvironmentDesign+"\n"+
                  "          Tech art >   Moyenne : "+wishStat.averageTechArt+"\n"+
                  "                     Deviation : "+wishStat.deviationTechArt+"\n"+
                  "   Modelisation 3D >   Moyenne : "+wishStat.averageModel3D+"\n"+
                  "                     Deviation : "+wishStat.deviationModel3D+"\n"+
                  "         Animation >   Moyenne : "+wishStat.averageAnimation+"\n"+
                  "                     Deviation : "+wishStat.deviationAnimation+"\n";
        output += "===== SKILLS =================\n";
        output += "  Game programming >   Moyenne : "+skillStat.averageGameProgramming+"\n"+
                  "                     Deviation : "+skillStat.deviationGameProgramming+"\n"+
                  "  Narrative design >   Moyenne : "+skillStat.averageNarrativeDesign+"\n"+
                  "                     Deviation : "+skillStat.deviationNarrativeDesign+"\n"+
                  "      Level design >   Moyenne : "+skillStat.averageLevelDesign+"\n"+
                  "                     Deviation : "+skillStat.deviationLevelDesign+"\n"+
                  "      Sound design >   Moyenne : "+skillStat.averageSoundDesign+"\n"+
                  "                     Deviation : "+skillStat.deviationSoundDesign+"\n";
        output += "  Character design >   Moyenne : "+skillStat.averageCharaDesign+"\n"+
                  "                     Deviation : "+skillStat.deviationCharaDesign+"\n"+
                  "Environment design >   Moyenne : "+skillStat.averageEnvironmentDesign+"\n"+
                  "                     Deviation : "+skillStat.deviationEnvironmentDesign+"\n"+
                  "          Tech art >   Moyenne : "+skillStat.averageTechArt+"\n"+
                  "                     Deviation : "+skillStat.deviationTechArt+"\n"+
                  "   Modelisation 3D >   Moyenne : "+skillStat.averageModel3D+"\n"+
                  "                     Deviation : "+skillStat.deviationModel3D+"\n"+
                  "         Animation >   Moyenne : "+skillStat.averageAnimation+"\n"+
                  "                     Deviation : "+skillStat.deviationAnimation+"\n";*/
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
            /*try {
                group.evaluateSkill();
                group.evaluateWish();
            } catch (IncorrectStudyException e) {
                e.printStackTrace();
            }*/
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


        /*for (Group group : groupSet) {
            wishStat.totalGameProgramming += group.wishScore.gameProgramming;
            wishStat.totalNarrativeDesign += group.wishScore.narrativeDesign;
            wishStat.totalLevelDesign += group.wishScore.levelDesign;
            wishStat.totalSoundDesign += group.wishScore.soundDesign;
            wishStat.totalCharaDesign += group.wishScore.charaDesign;
            wishStat.totalEnvironmentDesign += group.wishScore.environmentDesign;
            wishStat.totalTechArt += group.wishScore.techArt;
            wishStat.totalModel3D += group.wishScore.model3D;
            wishStat.totalAnimation += group.wishScore.animation;

            skillStat.totalGameProgramming += group.skillScore.gameProgramming;
            skillStat.totalNarrativeDesign += group.skillScore.narrativeDesign;
            skillStat.totalLevelDesign += group.skillScore.levelDesign;
            skillStat.totalSoundDesign += group.skillScore.soundDesign;
            skillStat.totalCharaDesign += group.skillScore.charaDesign;
            skillStat.totalEnvironmentDesign += group.skillScore.environmentDesign;
            skillStat.totalTechArt += group.skillScore.techArt;
            skillStat.totalModel3D += group.skillScore.model3D;
            skillStat.totalAnimation += group.skillScore.animation;
        }


        //Calculate wishes statistics
        wishStat.calculateAverage();
        double progWishDeviationSum = 0;
        double narraWishDeviationSum = 0;
        double levelWishDeviationSum = 0;
        double soundWishDeviationSum = 0;
        double charaWishDeviationSum = 0;
        double enviroWishDeviationSum = 0;
        double techWishDeviationSum = 0;
        double model3DWishDeviationSum = 0;
        double animWishDeviationSum = 0;
        for(Group group : groupSet) {
            progWishDeviationSum += Math.pow(group.wishScore.gameProgramming - wishStat.averageGameProgramming,2);
            narraWishDeviationSum += Math.pow(group.wishScore.narrativeDesign - wishStat.averageNarrativeDesign,2);
            levelWishDeviationSum += Math.pow(group.wishScore.levelDesign - wishStat.averageLevelDesign,2);
            soundWishDeviationSum += Math.pow(group.wishScore.soundDesign - wishStat.averageSoundDesign,2);
            charaWishDeviationSum += Math.pow(group.wishScore.charaDesign - wishStat.averageCharaDesign,2);
            enviroWishDeviationSum += Math.pow(group.wishScore.environmentDesign - wishStat.averageEnvironmentDesign,2);
            techWishDeviationSum += Math.pow(group.wishScore.techArt - wishStat.averageTechArt,2);
            model3DWishDeviationSum += Math.pow(group.wishScore.model3D - wishStat.averageModel3D,2);
            animWishDeviationSum += Math.pow(group.wishScore.animation - wishStat.averageAnimation,2);
        }
        wishStat.deviationGameProgramming = Math.round((float) Math.sqrt(progWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationNarrativeDesign = Math.round((float) Math.sqrt(narraWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationLevelDesign = Math.round((float) Math.sqrt(levelWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationSoundDesign = Math.round((float) Math.sqrt(soundWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationCharaDesign = Math.round((float) Math.sqrt(charaWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationEnvironmentDesign = Math.round((float) Math.sqrt(enviroWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationTechArt = Math.round((float) Math.sqrt(techWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationModel3D = Math.round((float) Math.sqrt(model3DWishDeviationSum/groupSet.size())*100)/100;
        wishStat.deviationAnimation = Math.round((float) Math.sqrt(animWishDeviationSum/groupSet.size())*100)/100;

        

        //Calculate skills statistics
        skillStat.calculateAverage();
        double progSkillDeviationSum = 0;
        double narraSkillDeviationSum = 0;
        double levelSkillDeviationSum = 0;
        double soundSkillDeviationSum = 0;
        double charaSkillDeviationSum = 0;
        double enviroSkillDeviationSum = 0;
        double techSkillDeviationSum = 0;
        double model3DSkillDeviationSum = 0;
        double animSkillDeviationSum = 0;
        for(Group group : groupSet) {
            progSkillDeviationSum += Math.pow(group.skillScore.gameProgramming - skillStat.averageGameProgramming,2);
            narraSkillDeviationSum += Math.pow(group.skillScore.narrativeDesign - skillStat.averageNarrativeDesign,2);
            levelSkillDeviationSum += Math.pow(group.skillScore.levelDesign - skillStat.averageLevelDesign,2);
            soundSkillDeviationSum += Math.pow(group.skillScore.soundDesign - skillStat.averageSoundDesign,2);
            charaSkillDeviationSum += Math.pow(group.skillScore.charaDesign - skillStat.averageCharaDesign,2);
            enviroSkillDeviationSum += Math.pow(group.skillScore.environmentDesign - skillStat.averageEnvironmentDesign,2);
            techSkillDeviationSum += Math.pow(group.skillScore.techArt - skillStat.averageTechArt,2);
            model3DSkillDeviationSum += Math.pow(group.skillScore.model3D - skillStat.averageModel3D,2);
            animSkillDeviationSum += Math.pow(group.skillScore.animation - skillStat.averageAnimation,2);
        }
        skillStat.deviationGameProgramming = Math.round((float) Math.sqrt(progSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationNarrativeDesign = Math.round((float) Math.sqrt(narraSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationLevelDesign = Math.round((float) Math.sqrt(levelSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationSoundDesign = Math.round((float) Math.sqrt(soundSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationCharaDesign = Math.round((float) Math.sqrt(charaSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationEnvironmentDesign = Math.round((float) Math.sqrt(enviroSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationTechArt = Math.round((float) Math.sqrt(techSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationModel3D = Math.round((float) Math.sqrt(model3DSkillDeviationSum/groupSet.size())*100)/100;
        skillStat.deviationAnimation = Math.round((float) Math.sqrt(animSkillDeviationSum/groupSet.size())*100)/100;*/
    }
}
