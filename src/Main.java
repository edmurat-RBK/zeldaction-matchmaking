import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String path = "D:\\Project\\zeldaction-matching\\data\\student_list.csv";
        initialise(path);

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
                String tmpLastProjectName = tokens[3];
                boolean tmpProjectManager = tokens[4].equals("TRUE");
                boolean tmpLeadProgrammer = tokens[5].equals("TRUE");
                boolean tmpArtDirector = tokens[6].equals("TRUE");

                StudentTable.addEntry(new Student(tmpName,tmpStudy,tmpClassPool,tmpLastProjectName,tmpProjectManager,tmpLeadProgrammer,tmpArtDirector));
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

    private static void draft(ArrayList<Student> table) {

    }
}
