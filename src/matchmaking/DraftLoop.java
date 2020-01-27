package matchmaking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class DraftLoop {

    public DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public static Tracker tracker;

    /**
     * Draft loop
     */
    public DraftLoop (int maxDraft, int timeOut) {
        // Get time when the loop start
        startTime = LocalDateTime.now();

        //Start draft loop
        draft(maxDraft,timeOut);

        // Get time when the loop end
        endTime = LocalDateTime.now();

        //Final display
        System.out.println("========================================");
        System.out.println("Start time : "+timeFormat.format(startTime));
        System.out.println("  End time : "+timeFormat.format(endTime));
        System.out.println("");
        System.out.println("Best of class 1 :");
        System.out.println("               Average : " + tracker.bestClass1.averageScore);
        System.out.println("    Standard deviation : " + Math.round(tracker.bestClass1.deviationScore*1000)/1000.0);
        System.out.println("");
        System.out.println("Best of class 2 :");
        System.out.println("               Average : " + tracker.bestClass2.averageScore);
        System.out.println("    Standard deviation : " + Math.round(tracker.bestClass2.deviationScore*1000)/1000.0);
        System.out.println("========================================");
    }

    /**
     * Draft loop
     */
    private static void draft(int maxDraft,int timeOut) {
        //Init tracker
        tracker = new Tracker(maxDraft,timeOut);

        //Initialise a best draft for class 1 (GD1/GA1)
        Draft draftClass1 = new Draft(Pool.CLASS_1);
        tracker.initDraft(draftClass1);

        //Initialise a best draft for class 2 (GD2/GA2)
        Draft draftClass2 = new Draft(Pool.CLASS_2);
        tracker.initDraft(draftClass2);

        //While draft count don't reach maximum draft count
        while(tracker.draftCount < tracker.maximumDraft) {
            //Draft for class 1 (GD1/GA1)
            draftClass1 = new Draft(Pool.CLASS_1);
            tracker.compareDraftClass1(draftClass1);

            //Draft for class 2 (GD2/GA2)
            draftClass2 = new Draft(Pool.CLASS_2);
            tracker.compareDraftClass2(draftClass2);

            //Increment counters
            tracker.incCounter();
            //Display processing lines
            if(tracker.draftCount%Main.promptFrequency == 0) {
                System.out.println(Math.round((1.0 * tracker.draftCount) / tracker.maximumDraft * 10000)*1.0/100 + "% - " + tracker.draftCount + " drafts completed"); /*[Best Class 1 : "+tracker.bestClass1.averageScore+"/"+tracker.bestClass1.deviationScore+" || Best Class 2 : "+tracker.bestClass2.averageScore+"/"+tracker.bestClass2.deviationScore+"]");*/
            }
        }
    }

}
