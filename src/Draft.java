import java.util.ArrayList;
import java.util.HashSet;

public class Draft {

    public Pool classDraft;
    public int totalGroup;
    public int minPerson, maxPerson;
    public int minDesigner, maxDesigner;
    public int minArtist, maxArtist;
    public Group[] draftArray;

    public Draft(Pool classDraft, int totalGroup, int minPerson, int maxPerson, int minDesigner, int maxDesigner, int minArtist, int maxArtist) {
        this.classDraft = classDraft;
        this.totalGroup = totalGroup;
        this.minPerson = minPerson;
        this.maxPerson = maxPerson;
        this.minDesigner = minDesigner;
        this.maxDesigner = maxDesigner;
        this.minArtist = minArtist;
        this.maxArtist = maxArtist;
    }

    private void startDraft() {
        //Get 5 PM and put one in each group

        //Get 5 LP and put one in each group

        //Get 5 AD and put one in each group

        //Fill with every GD left in the set

        //Fill with every GA left in the set

        //Calculate "relation score" of each group

    }

}
