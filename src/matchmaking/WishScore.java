package matchmaking;

public class WishScore {

    public float gameProgramming;
    public float narrativeDesign;
    public float levelDesign;
    public float soundDesign;

    public float charaDesign;
    public float environmentDesign;
    public float techArt;
    public float model3D;
    public float animation;

    public void convertToAverage(int designCount, int artCount) {
        gameProgramming /= designCount*1.0;
        narrativeDesign /= designCount*1.0;
        levelDesign /= designCount*1.0;
        soundDesign /= designCount*1.0;

        charaDesign /= artCount*1.0;
        environmentDesign /= artCount*1.0;
        techArt /= artCount*1.0;
        model3D /= artCount*1.0;
        animation /= artCount*1.0;
    }

}
