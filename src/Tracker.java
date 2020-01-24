import java.io.*;

public class Tracker {

    public int draftCount = 0;
    public int maximumDraft = 50000000;
    public Draft bestClass1;
    public Draft bestClass2;

    public void initDraft(Draft draft) {
        if(draft.classDraft == Pool.CLASS_1) {
            bestClass1 = draft;
        }
        else {
            bestClass2 = draft;
        }
    }

    public void compareDraft(Draft draft) {
        if(draft.classDraft == Pool.CLASS_1) {
            if(draft.averageScore <= bestClass1.averageScore && draft.deviationScore <= bestClass1.deviationScore) {
                bestClass1 = draft;
                File newFile = new File("D:\\Project\\zeldaction-matching\\data\\output\\pool_1\\draft_"+draftCount+".txt");
                try {
                    newFile.createNewFile();
                    FileWriter writer = new FileWriter(newFile);
                    writer.write("Draft n°"+draftCount+"\n\n\n"+draft);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            if(draft.averageScore <= bestClass2.averageScore && draft.deviationScore <= bestClass2.deviationScore) {
                bestClass2 = draft;
                File newFile = new File("D:\\Project\\zeldaction-matching\\data\\output\\pool_2\\draft_"+draftCount+".txt");
                try {
                    newFile.createNewFile();
                    FileWriter writer = new FileWriter(newFile);
                    writer.write("Draft n°"+draftCount+"\n\n\n"+draft);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
