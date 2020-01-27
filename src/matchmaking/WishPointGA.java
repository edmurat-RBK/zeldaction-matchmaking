package matchmaking;

public class WishPointGA implements WishPoint {

    public int charaDesign;
    public int enviroDesign;
    public int techArt;
    public int model3D;
    public int animation;

    public WishPointGA(int charaDesign, int enviroDesign, int techArt, int model3D, int animation) {
        this.charaDesign = charaDesign;
        this.enviroDesign = enviroDesign;
        this.techArt = techArt;
        this.model3D = model3D;
        this.animation = animation;
    }

    @Override
    public int getGameProgramming() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GA student");
    }

    @Override
    public int getNarrativeDesign() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GA student");
    }

    @Override
    public int getLevelDesign() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GA student");
    }

    @Override
    public int getSoundDesign() throws IncorrectStudyException {
        throw new IncorrectStudyException("This skill can't be acceded by GA student");
    }

    @Override
    public int getCharaDesign() {
        return this.charaDesign;
    }

    @Override
    public int getEnvironmentDesign() {
        return this.enviroDesign;
    }

    @Override
    public int getTechArt() {
        return this.techArt;
    }

    @Override
    public int getModel3D() {
        return this.model3D;
    }

    @Override
    public int getAnimation() {
        return this.animation;
    }
}
