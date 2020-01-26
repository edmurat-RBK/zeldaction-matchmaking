package matchmaking;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;


public class Main {

    public static String csvPath;
    public static int maxDraft;
    public static int timeOut;

    public static void main(String[] args) throws URISyntaxException {
        URI path = Main.class.getResource("..\\config.txt").toURI();
        initialise(path);

        DraftLoop loop = new DraftLoop(csvPath,maxDraft,timeOut);
    }

    private static void initialise(URI fileToParse) {
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

                        case "point-ban":
                            ScoreSystem.bannedRelation = Integer.parseInt(value);
                            break;

                        case "point-fav":
                            ScoreSystem.favoredRelation = Integer.parseInt(value);
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
}
