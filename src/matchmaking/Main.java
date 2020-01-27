package matchmaking;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class Main {

    public static String csvPath;
    public static int maxDraft;
    public static int timeOut;
    public static int promptFrequency;

    /**
     * Main method
     */
    public static void main(String[] args) throws URISyntaxException {
        URI path;
        if(args.length >= 1) {
            path = new URI(args[0]);
        }
        else {
            String fileToFind = "config.txt";
            File jarFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            File jarDirectory = new File(jarFile.getParent());
            path = new File(jarDirectory,fileToFind).toURI();
        }
        initialiseConfig(path);
        initialiseData(csvPath);

        DraftLoop loop = new DraftLoop(maxDraft,timeOut);
    }

    /**
     * Initialisation
     * Read config.txt file
     * Apply parameters
     */
    private static void initialiseConfig(URI fileToParse) {
        //Input file which needs to be parsed
        String delimiter = "=";
        BufferedReader fileReader = null;
        try
        {
            fileReader = new BufferedReader(new FileReader(new File(fileToParse)));
            String line = "";

            while ((line = fileReader.readLine()) != null)
            {
                String[] tokens = line.split(delimiter);
                String key = tokens[0];
                if(!key.equals("") && key.charAt(0) != '#') {

                    String value = tokens[1];
                    switch (key) {
                        case "csv-absolute-path":
                            csvPath = value;
                            break;

                        case "max-draft":
                            maxDraft = Integer.parseInt(value);
                            break;

                        case "max-failure":
                            timeOut = Integer.parseInt(value);
                            break;

                        case "point-roguelike":
                            ScoreSystem.rogueLikeRelation = Integer.parseInt(value);
                            break;

                        case "point-boardgame":
                            ScoreSystem.boardGameRelation = Integer.parseInt(value);
                            break;

                        case "point-nowork":
                            ScoreSystem.noWorkRelation = Integer.parseInt(value);
                            break;

                        case "point-soft-ban":
                            ScoreSystem.softBannedRelation = Integer.parseInt(value);
                            break;

                        case "point-hard-ban":
                            ScoreSystem.hardBannedRelation = Integer.parseInt(value);
                            break;

                        case "point-fav":
                            ScoreSystem.favoredRelation = Integer.parseInt(value);
                            break;

                        case "prompt-frequency":
                            Main.promptFrequency = Integer.parseInt(value);
                            break;

                        default:
                            break;
                    }
                }
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

    /**
     * Initialisation
     * Read student data CSV
     */
    private static void initialiseData(String fileToParse) {

        //Delimiter used in CSV file
        final String DELIMITER = ",";

        //Init BufferedReader
        BufferedReader fileReader = null;
        try
        {
            //Load file into BufferedReader
            fileReader = new BufferedReader(new FileReader(fileToParse));
            //Skip header line
            fileReader.readLine();

            //Go through every lines
            String line = "";
            while ((line = fileReader.readLine()) != null)
            {
                //Split line
                String[] tokens = line.split(DELIMITER);

                //Store token in Student object
                String tmpName = tokens[0];
                Study tmpStudy = (tokens[1].equals("GD") ? Study.DESIGN : Study.ART);
                Pool tmpClassPool = (tokens[2].equals("1")  ? Pool.CLASS_1 : Pool.CLASS_2);
                String tmpBoardGame = tokens[3];
                String tmpRogueLike = tokens[4];
                boolean tmpProjectManager = tokens[5].equals("TRUE");
                boolean tmpLeadProgrammer = tokens[6].equals("TRUE");
                boolean tmpArtDirector = tokens[7].equals("TRUE");
                WishPoint tmpWish;
                SkillPoint tmpSkill;
                if(tmpStudy == Study.DESIGN) {
                    tmpWish = new WishPointGD(Integer.parseInt(tokens[8]),Integer.parseInt(tokens[9]),Integer.parseInt(tokens[10]),Integer.parseInt(tokens[11]));
                    tmpSkill = new SkillPointGD(Integer.parseInt(tokens[17]),Integer.parseInt(tokens[18]),Integer.parseInt(tokens[19]),Integer.parseInt(tokens[20]));
                }
                else {
                    tmpWish = new WishPointGA(Integer.parseInt(tokens[12]),Integer.parseInt(tokens[13]),Integer.parseInt(tokens[14]),Integer.parseInt(tokens[15]),Integer.parseInt(tokens[16]));
                    tmpSkill = new SkillPointGA(Integer.parseInt(tokens[21]),Integer.parseInt(tokens[22]),Integer.parseInt(tokens[23]),Integer.parseInt(tokens[24]),Integer.parseInt(tokens[25]));
                }
                String[] tmpSoftBanList = new String[]{tokens[26]};
                String[] tmpHardBanList = new String[]{tokens[27],tokens[28],tokens[29],tokens[30],tokens[31]};
                String[] tmpFavList = new String[]{tokens[32],tokens[33],tokens[34]};

                //Send Student object to StudentTable
                StudentTable.addEntry(new Student(
                        tmpName,tmpStudy,tmpClassPool,
                        tmpBoardGame,tmpRogueLike,
                        tmpProjectManager,tmpLeadProgrammer,tmpArtDirector,
                        tmpWish,tmpSkill,
                        tmpSoftBanList,tmpHardBanList,tmpFavList));
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
}
