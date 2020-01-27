package matchmaking;

public interface SkillPoint {

    int getGameProgramming() throws IncorrectStudyException;
    int getNarrativeDesign() throws IncorrectStudyException;
    int getLevelDesign() throws IncorrectStudyException;
    int getSoundDesign() throws IncorrectStudyException;

    int getCharaDesign() throws IncorrectStudyException;
    int getEnvironmentDesign() throws IncorrectStudyException;
    int getTechArt() throws IncorrectStudyException;
    int getModel3D() throws IncorrectStudyException;
    int getAnimation() throws IncorrectStudyException;

}
