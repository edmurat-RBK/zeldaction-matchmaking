package matchmaking;

import java.util.HashSet;

public class Group {

    public HashSet<Student> draft;
    public int designCount = 0;
    public int artCount = 0;
    public int relationScore = 0;
    //public SkillScore skillScore;
    //public WishScore wishScore;

    @Override
    public String toString() {
        groupCount();

        String output = "Groupe : "+designCount+"GD / "+artCount+"GA"+"\n";
        /*output += "--- SKILLS ---\n";
        output += "Prog:"+skillScore.gameProgramming+"  ND:"+skillScore.narrativeDesign+"  LD:"+skillScore.levelDesign+"  SD:"+skillScore.soundDesign+"\n";
        output += "Chara:"+skillScore.charaDesign+"  Enviro:"+skillScore.environmentDesign+"  Tech:"+skillScore.techArt+"  3D:"+skillScore.model3D+"  Anim:"+skillScore.animation+"\n";
        output += "--- WISHES ---\n";
        output += "GameProg:"+wishScore.gameProgramming+
                  " NarratD:"+wishScore.narrativeDesign+
                  "  LevelD:"+wishScore.levelDesign+
                  "  SoundD:"+wishScore.soundDesign+"\n";
        output += "   Chara:"+wishScore.charaDesign+
                  "  Enviro:"+wishScore.environmentDesign+
                  "    Tech:"+wishScore.techArt+
                  "      3D:"+wishScore.model3D+
                  "    Anim:"+wishScore.animation+"\n";*/
        output += "--- SCORE -----\n";
        output += "     "+relationScore+"\n";
        output += "---------------\n";
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
        //this.skillScore = new SkillScore();
        //this.wishScore = new WishScore();
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

    /*public void evaluateSkill() throws IncorrectStudyException {
        for(Student student : draft) {
            if(student.study == Study.DESIGN) {
                skillScore.gameProgramming += student.skill.getGameProgramming();
                skillScore.narrativeDesign += student.skill.getNarrativeDesign();
                skillScore.levelDesign += student.skill.getLevelDesign();
                skillScore.soundDesign += student.skill.getSoundDesign();
            }
            else {
                skillScore.charaDesign += student.skill.getCharaDesign();
                skillScore.environmentDesign += student.skill.getEnvironmentDesign();
                skillScore.techArt += student.skill.getTechArt();
                skillScore.model3D += student.skill.getModel3D();
                skillScore.animation += student.skill.getAnimation();
            }
        }
        
        skillScore.convertToAverage(designCount,artCount);
    }
    
    public void evaluateWish() throws IncorrectStudyException {
        for(Student student : draft) {
            if(student.study == Study.DESIGN) {
                wishScore.gameProgramming += student.wish.getGameProgramming();
                wishScore.narrativeDesign += student.wish.getNarrativeDesign();
                wishScore.levelDesign += student.wish.getLevelDesign();
                wishScore.soundDesign += student.wish.getSoundDesign();
            }
            else {
                wishScore.charaDesign += student.wish.getCharaDesign();
                wishScore.environmentDesign += student.wish.getEnvironmentDesign();
                wishScore.techArt += student.wish.getTechArt();
                wishScore.model3D += student.wish.getModel3D();
                wishScore.animation += student.wish.getAnimation();
            }
        }
        
        wishScore.convertToAverage(designCount,artCount);
    }*/
}
