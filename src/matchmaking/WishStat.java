package matchmaking;

public class WishStat {

    public int totalGameProgramming;
    public float averageGameProgramming;
    public float deviationGameProgramming;
    
    public int totalNarrativeDesign;
    public float averageNarrativeDesign;
    public float deviationNarrativeDesign;
    
    public int totalLevelDesign;
    public float averageLevelDesign;
    public float deviationLevelDesign;
    
    public int totalSoundDesign;
    public float averageSoundDesign;
    public float deviationSoundDesign;
    
    public int totalCharaDesign;
    public float averageCharaDesign;
    public float deviationCharaDesign;
    
    public int totalEnvironmentDesign;
    public float averageEnvironmentDesign;
    public float deviationEnvironmentDesign;
    
    public int totalTechArt;
    public float averageTechArt;
    public float deviationTechArt;
    
    public int totalModel3D;
    public float averageModel3D;
    public float deviationModel3D;
    
    public int totalAnimation;
    public float averageAnimation;
    public float deviationAnimation;
    
    public void calculateAverage() {
        averageGameProgramming = totalGameProgramming/5;
        averageNarrativeDesign = totalNarrativeDesign/5;
        averageLevelDesign = totalLevelDesign/5;
        averageSoundDesign = totalSoundDesign/5;
        averageCharaDesign = totalCharaDesign/5;
        averageEnvironmentDesign = totalEnvironmentDesign/5;
        averageTechArt = totalTechArt/5;
        averageModel3D = totalModel3D/5;
        averageAnimation = totalAnimation/5;
    }
    
}
