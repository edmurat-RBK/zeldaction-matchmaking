package matchmaking;

public class SkillPointGD implements SkillPoint{

    public int gameProg;
    public int narrativeDesign;
    public int levelDesign;
    public int soundDesign;

    public SkillPointGD(int gameProg, int narrativeDesign, int levelDesign, int soundDesign) {
        this.gameProg = gameProg;
        this.narrativeDesign = narrativeDesign;
        this.levelDesign = levelDesign;
        this.soundDesign = soundDesign;
    }

    @Override
    public int getGameProgramming() throws IncorrectStudyException {
        return this.gameProg;
    }

    @Override
    public int getNarrativeDesign() throws IncorrectStudyException {
        return this.narrativeDesign;
    }

    @Override
    public int getLevelDesign() throws IncorrectStudyException {
        return this.levelDesign;
    }

    @Override
    public int getSoundDesign() throws IncorrectStudyException {
        return this.soundDesign;
    }

    @Override
    public int getCharaDesign() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GD student");
    }

    @Override
    public int getEnvironmentDesign() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GD student");
    }

    @Override
    public int getTechArt() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GD student");
    }

    @Override
    public int getModel3D() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GD student");
    }

    @Override
    public int getAnimation() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GD student");
    }
}
