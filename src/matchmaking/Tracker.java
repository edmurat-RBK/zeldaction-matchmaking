package matchmaking;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Tracker {

    public int draftCount = 0;
    public int timeOutCount = 0;
    public int maximumDraft;
    public int timeOut;
    public DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public Draft bestClass1;
    public Draft bestClass2;

    /**
     * Tracker constructor
     */
    public Tracker(int maximumDraft, int timeOut) {
        this.maximumDraft = maximumDraft;
        this.timeOut = timeOut;
    }

    /**
     * Increment counters
     */
    public void incCounter() {
        draftCount++;
        timeOutCount++;

        if(timeOutCount >= timeOut) {
            System.out.println("Timeout after "+draftCount+" drafts");
            System.exit(0);
        }
    }

    /**
     * Initialise first best draft
     */
    public void initDraft(Draft draft) {
        if(draft.classDraft == Pool.CLASS_1) {
            bestClass1 = draft;
        }
        else {
            bestClass2 = draft;
        }
    }

    /**
     * Compare a draft to best draft of class 1
     */
    public void compareDraftClass1(Draft draft) {
        //If draft average better than best draft
        if(bestClass1.averageScore >= draft.averageScore) {

            //Calculate integral bewteen best gauss function and draft gauss function
            GaussFunction championFunction = new GaussFunction(bestClass1.averageScore, bestClass1.deviationScore);
            GaussFunction challengerFunction = new GaussFunction(draft.averageScore, draft.deviationScore);
            double championIntegral = championFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);
            double challengerIntegral = challengerFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);

            //If integral positive, replace best draft by given draft
            if (challengerIntegral - championIntegral > 0) {
                bestClass1 = draft;
                printToFile(draft);
            }
        }
    }

    /**
     * Compare a draft to best draft of class 2
     */
    public void compareDraftClass2(Draft draft) {
        //If draft average better than best draft
        if(bestClass2.averageScore >= draft.averageScore) {

            //Calculate integral bewteen best gauss function and draft gauss function
            GaussFunction championFunction = new GaussFunction(bestClass2.averageScore, bestClass2.deviationScore);
            GaussFunction challengerFunction = new GaussFunction(draft.averageScore, draft.deviationScore);
            double championIntegral = championFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);
            double challengerIntegral = challengerFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);

            //If integral positive, replace best draft by given draft
            if (challengerIntegral - championIntegral > 0) {
                bestClass2 = draft;
                printToFile(draft);
            }
        }
    }

    /**
     * Generate file and print draft inside
     */
    public void printToFile(Draft draft) {
        timeOutCount = 0;

        //Get file path
        String fileToCreate = "pool_"+(draft.classDraft==Pool.CLASS_1 ? "1" : "2")+"\\draft_"+draftCount+".txt";
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File jarDirectory = new File(jarFile.getParent());
        File newFile = new File(jarDirectory,fileToCreate);

        //Create and fill file
        try {
            newFile.createNewFile();
            FileWriter writer = new FileWriter(newFile, StandardCharsets.UTF_8);
            writer.write("Draft n°"+draftCount+"\n"+ timeFormat.format(LocalDateTime.now()) +"\n\n\n"+draft);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
