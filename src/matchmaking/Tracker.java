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

    public Tracker(int maximumDraft, int timeOut) {
        this.maximumDraft = maximumDraft;
        this.timeOut = timeOut;
    }

    public void incCounter() {
        draftCount++;
        timeOutCount++;

        if(timeOutCount >= timeOut) {
            System.out.println("Timeout after "+draftCount+" drafts");
            System.exit(0);
        }
    }

    public void initDraft(Draft draft) {
        if(draft.classDraft == Pool.CLASS_1) {
            bestClass1 = draft;
        }
        else {
            bestClass2 = draft;
        }
    }

    public void compareDraftClass1(Draft draft) {
        if(bestClass1.averageScore >= draft.averageScore) {
            GaussFunction championFunction = new GaussFunction(bestClass1.averageScore, bestClass1.deviationScore);
            GaussFunction challengerFunction = new GaussFunction(draft.averageScore, draft.deviationScore);
            double championIntegral = championFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);
            double challengerIntegral = challengerFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);

            if (challengerIntegral - championIntegral > 0) {
                bestClass1 = draft;
                printToFile(draft);
            }
        }
    }

    public void compareDraftClass2(Draft draft) {
        if(bestClass2.averageScore >= draft.averageScore) {
            GaussFunction championFunction = new GaussFunction(bestClass2.averageScore, bestClass2.deviationScore);
            GaussFunction challengerFunction = new GaussFunction(draft.averageScore, draft.deviationScore);
            double championIntegral = championFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);
            double challengerIntegral = challengerFunction.simpson(draft.averageScore - draft.deviationScore, draft.averageScore + draft.deviationScore, 10);

            if (challengerIntegral - championIntegral > 0) {
                bestClass2 = draft;
                printToFile(draft);
            }
        }
    }

    public void printToFile(Draft draft) {
        timeOutCount = 0;

        String fileToCreate = "pool_"+(draft.classDraft==Pool.CLASS_1 ? "1" : "2")+"\\draft_"+draftCount+".txt";
        File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        File jarDirectory = new File(jarFile.getParent());
        File newFile = new File(jarDirectory,fileToCreate);
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
