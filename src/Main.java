import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\Project\\zeldaction-matching\\data\\student_list.csv";
        initialise(path);

    }

    private static void initialise(String path) {
        //Input file which needs to be parsed
        String fileToParse = path;
        BufferedReader fileReader = null;

        //Delimiter used in CSV file
        final String DELIMITER = ",";

        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(DELIMITER);
                String tmpName = tokens[0];
                Study tmpStudy = (tokens[1]=="GD" ? Study.DESIGN : Study.ART);
                Pool tmpClassPool = (tokens[2]=="1" ? Pool.CLASS_1 : Pool.CLASS_2);
                Specialisation tmpSpecialisation;
                if(tokens[4] == "Oui") {
                    tmpSpecialisation = Specialisation.PROJECT_MANAGER;
                }
                else if(tokens[5] == "Oui") {
                    tmpSpecialisation = Specialisation.LEAD_PROGRAMMER;
                }
                else if(tokens[6] == "Oui") {
                    tmpSpecialisation = Specialisation.ART_DIRECTOR;
                }
                else {
                    tmpSpecialisation = Specialisation.NONE;
                }

                new Student(tmpName,tmpStudy,tmpClassPool,tmpSpecialisation);

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
