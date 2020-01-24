import javax.sound.midi.Track;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\Project\\zeldaction-matching\\data\\student_list_test.csv";
        initialise(path);
        draft();
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

    private static void draft() {
        Tracker tracker = new Tracker();

        Draft draftClass1 = new Draft(Pool.CLASS_1);
        tracker.draftCount++;
        tracker.initDraft(draftClass1);

        Draft draftClass2 = new Draft(Pool.CLASS_2);
        tracker.draftCount++;
        tracker.initDraft(draftClass2);

        while(tracker.draftCount < tracker.maximumDraft) {
            draftClass1 = new Draft(Pool.CLASS_1);
            tracker.draftCount++;
            tracker.compareDraft(draftClass1);

            draftClass2 = new Draft(Pool.CLASS_2);
            tracker.draftCount++;
            tracker.compareDraft(draftClass2);

            if(tracker.draftCount % 10000 == 0) {
                System.out.println(tracker.draftCount+" drafts made");
            }
        }
    }
}
