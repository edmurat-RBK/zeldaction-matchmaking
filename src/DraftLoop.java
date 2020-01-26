import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DraftLoop {

    public DraftLoop (String filePath, int maxDraft) {
        initialise(filePath);
        draft(maxDraft,10000000);
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
        Tracker tracker = new Tracker(maxDraft,timeOut);

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
        }
    }

}
