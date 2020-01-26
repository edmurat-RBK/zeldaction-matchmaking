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

    public DraftLoop (String filePath, int maxDraft, int timeOut) {
        startTime = LocalDateTime.now();
        initialise(filePath);
        draft(maxDraft,timeOut);
        endTime = LocalDateTime.now();

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

    private static void initialise(String path) {
        //Input file which needs to be parsed
        String fileToParse = path;
        //Delimiter used in CSV file
        final String DELIMITER = ",";

        BufferedReader fileReader = null;
        try
        {
            fileReader = new BufferedReader(new FileReader(fileToParse));
            //Skip header line
            fileReader.readLine();

            String line = "";
            while ((line = fileReader.readLine()) != null)
            {
                String[] tokens = line.split(DELIMITER);
                String tmpName = tokens[0];
                Study tmpStudy = (tokens[1].equals("GD") ? Study.DESIGN : Study.ART);
                Pool tmpClassPool = (tokens[2].equals("1")  ? Pool.CLASS_1 : Pool.CLASS_2);
                String tmpBoardGame = tokens[3];
                String tmpRogueLike = tokens[4];
                boolean tmpProjectManager = tokens[5].equals("TRUE");
                boolean tmpLeadProgrammer = tokens[6].equals("TRUE");
                boolean tmpArtDirector = tokens[7].equals("TRUE");
                String[] tmpBanList = new String[]{tokens[8],tokens[9],tokens[10]};
                String[] tmpFavList = new String[]{tokens[11]};

                StudentTable.addEntry(new Student(tmpName,tmpStudy,tmpClassPool,tmpBoardGame,tmpRogueLike,tmpProjectManager,tmpLeadProgrammer,tmpArtDirector,tmpBanList,tmpFavList));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void draft(int maxDraft,int timeOut) {
        tracker = new Tracker(maxDraft,timeOut);

        Draft draftClass1 = new Draft(Pool.CLASS_1);
        tracker.initDraft(draftClass1);

        Draft draftClass2 = new Draft(Pool.CLASS_2);
        tracker.initDraft(draftClass2);

        while(tracker.draftCount < tracker.maximumDraft) {
            draftClass1 = new Draft(Pool.CLASS_1);
            tracker.compareDraftClass1(draftClass1);

            draftClass2 = new Draft(Pool.CLASS_2);
            tracker.compareDraftClass2(draftClass2);

            tracker.incCounter();
            if(tracker.draftCount%1000 == 0) {
                System.out.println(Math.round((1.0 * tracker.draftCount) / tracker.maximumDraft * 10000)*1.0/100 + "% - " + tracker.draftCount + " drafts completed"); /*[Best Class 1 : "+tracker.bestClass1.averageScore+"/"+tracker.bestClass1.deviationScore+" || Best Class 2 : "+tracker.bestClass2.averageScore+"/"+tracker.bestClass2.deviationScore+"]");*/
            }
        }
    }

}
