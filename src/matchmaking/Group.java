package matchmaking;

import java.util.HashMap;
import java.util.HashSet;

public class Group {

    public HashSet<Student> draft;
    public int designCount = 0;
    public int artCount = 0;
    public int relationScore = 0;
    public HashMap<Ability,AbilityScore> totalWishAndSkill;

    @Override
    public String toString() {
        groupCount();

        String output = "Groupe : "+designCount+"GD / "+artCount+"GA"+"\n";
        output += "------- WISHES -------\n";
        output += "  Game programming : "+totalWishAndSkill.get(Ability.GAME_PROGRAMMING).wish+"\n"+
                  "  Narrative design : "+totalWishAndSkill.get(Ability.NARRATIVE_DESIGN).wish+"\n"+
                  "      Level design : "+totalWishAndSkill.get(Ability.LEVEL_DESIGN).wish+"\n"+
                  "      Sound design : "+totalWishAndSkill.get(Ability.SOUND_DESIGN).wish+"\n";
        output += "  Character design : "+totalWishAndSkill.get(Ability.CHARACTER_DESIGN).wish+"\n"+
                  "Environment design : "+totalWishAndSkill.get(Ability.ENVIRONMENTAL_DESIGN).wish+"\n"+
                  "          Tech art : "+totalWishAndSkill.get(Ability.TECH_ART).wish+"\n"+
                  "   Modelisation 3D : "+totalWishAndSkill.get(Ability.MODELISATION_3D).wish+"\n"+
                  "         Animation : "+totalWishAndSkill.get(Ability.ANIMATION).wish+"\n";
        output += "------- SKILLS -------\n";
        output += "  Game programming : "+totalWishAndSkill.get(Ability.GAME_PROGRAMMING).skill+"\n"+
                  "  Narrative design : "+totalWishAndSkill.get(Ability.NARRATIVE_DESIGN).skill+"\n"+
                  "      Level design : "+totalWishAndSkill.get(Ability.LEVEL_DESIGN).skill+"\n"+
                  "      Sound design : "+totalWishAndSkill.get(Ability.SOUND_DESIGN).skill+"\n";
        output += "  Character design : "+totalWishAndSkill.get(Ability.CHARACTER_DESIGN).skill+"\n"+
                  "Environment design : "+totalWishAndSkill.get(Ability.ENVIRONMENTAL_DESIGN).skill+"\n"+
                  "          Tech art : "+totalWishAndSkill.get(Ability.TECH_ART).skill+"\n"+
                  "   Modelisation 3D : "+totalWishAndSkill.get(Ability.MODELISATION_3D).skill+"\n"+
                  "         Animation : "+totalWishAndSkill.get(Ability.ANIMATION).skill+"\n";
        output += "------- SCORE -------\n";
        output += "         "+relationScore+"\n";
        output += "---------------------\n";
        for(Student s : draft) {
            output += s + "\n";
        }
        output += "\n\n";
        return output;
    }

    /**
     * Group constructor
     */
    public Group() {
        this.draft = new HashSet<>();
        totalWishAndSkill = new HashMap<>();
        for(Ability ability : Ability.values()) {
            totalWishAndSkill.put(ability, new AbilityScore(ability,0,0));
        }
    }

    /**
     * Add student to the group
     */
    public void join(Student student) {
        draft.add(student);
        groupCount();
    }

    public Study pmStudy() {
        for(Student student : draft) {
            if(student.projectManager) {
                return student.study;
            }
        }
        return null;
    }

    /**
     * Count designer and artist in group
     */
    public void groupCount() {
        designCount = 0;
        artCount = 0;

        for(Student student : draft) {
            if(student.study == Study.DESIGN) { designCount++; }
            else if(student.study == Study.ART) { artCount++; }
        }
    }

    /**
     * Calculate group score
     */
    public void evaluateRelation() {
        //For each student in the group...
        for(Student student : draft) {
            //...then for each other student in the group...
            for(Student other : draft) {
                if(!student.equals(other)) {
                    // Rogue-like relation
                    if(student.rogueLikeProject.equals(other.rogueLikeProject)) {
                        relationScore += ScoreSystem.rogueLikeRelation;
                    }
                    //Board game relation
                    if(student.boardGameProject.equals(other.boardGameProject)) {
                        relationScore += ScoreSystem.boardGameRelation;
                    }
                    //No common project relation
                    if(!student.rogueLikeProject.equals(other.rogueLikeProject) && !student.boardGameProject.equals(other.boardGameProject)) {
                        relationScore += ScoreSystem.noWorkRelation;
                    }
                    //Soft ban relation
                    for(String banName : student.softBanList) {
                        if(banName.equals(other.name)) {
                            relationScore += ScoreSystem.softBannedRelation;
                        }
                    }
                    //Hard ban relation
                    for(String banName : student.hardBanList) {
                        if(banName.equals(other.name)) {
                            relationScore += ScoreSystem.hardBannedRelation;
                        }
                    }
                    //Fav relation
                    for(String favName : student.favList) {
                        if(favName.equals(other.name)) {
                            relationScore += ScoreSystem.favoredRelation;
                        }
                    }
                }
            }
        }
    }

    public void evaluateWishAndSkill() throws IncorrectStudyException {
        for(Ability ability : Ability.values()) {
            float tmpWish = 0;
            float tmpSkill = 0;
            switch(ability) {
                case GAME_PROGRAMMING:
                    for(Student student : StudentTable.inStudy(Study.DESIGN,draft)) {
                        tmpWish += student.wish.getGameProgramming();
                        tmpSkill += student.skill.getGameProgramming();
                    }
                    break;

                case NARRATIVE_DESIGN:
                    for(Student student : StudentTable.inStudy(Study.DESIGN,draft)) {
                        tmpWish += student.wish.getNarrativeDesign();
                        tmpSkill += student.skill.getNarrativeDesign();
                    }
                    break;

                case LEVEL_DESIGN:
                    for(Student student : StudentTable.inStudy(Study.DESIGN,draft)) {
                        tmpWish += student.wish.getLevelDesign();
                        tmpSkill += student.skill.getLevelDesign();
                    }
                    break;

                case SOUND_DESIGN:
                    for(Student student : StudentTable.inStudy(Study.DESIGN,draft)) {
                        tmpWish += student.wish.getSoundDesign();
                        tmpSkill += student.skill.getSoundDesign();
                    }
                    break;

                case CHARACTER_DESIGN:
                    for(Student student : StudentTable.inStudy(Study.ART,draft)) {
                        tmpWish += student.wish.getCharaDesign();
                        tmpSkill += student.skill.getCharaDesign();
                    }
                    break;

                case ENVIRONMENTAL_DESIGN:
                    for(Student student : StudentTable.inStudy(Study.ART,draft)) {
                        tmpWish += student.wish.getEnvironmentDesign();
                        tmpSkill += student.skill.getEnvironmentDesign();
                    }
                    break;

                case TECH_ART:
                    for(Student student : StudentTable.inStudy(Study.ART,draft)) {
                        tmpWish += student.wish.getTechArt();
                        tmpSkill += student.skill.getTechArt();
                    }
                    break;

                case MODELISATION_3D:
                    for(Student student : StudentTable.inStudy(Study.ART,draft)) {
                        tmpWish += student.wish.getModel3D();
                        tmpSkill += student.skill.getModel3D();
                    }
                    break;

                case ANIMATION:
                    for(Student student : StudentTable.inStudy(Study.ART,draft)) {
                        tmpWish += student.wish.getAnimation();
                        tmpSkill += student.skill.getAnimation();
                    }
                    break;

                default:
                    break;
            }
            totalWishAndSkill.put(ability,new AbilityScore(ability,tmpWish,tmpSkill));
        }
    }
}
