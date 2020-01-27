package matchmaking;

import java.util.HashSet;

public class Group {

    public HashSet<Student> draft;
    public int designCount = 0;
    public int artCount = 0;
    public int relationScore = 0;

    @Override
    public String toString() {
        groupCount();

        String output = "Groupe : "+designCount+"GD / "+artCount+"GA"+"\n";
        output += "Score : "+relationScore + "\n";
        for(Student s : draft) {
            output += s + "\n";
        }
        return output;
    }

    /**
     * Group constructor
     */
    public Group() {
        this.draft = new HashSet<>();
    }

    /**
     * Add student to the group
     */
    public void join(Student student) {
        draft.add(student);
        groupCount();
    }

    /**
     * Count designer and artist in group
     */
    public void groupCount() {
        designCount = 0;
        artCount = 0;

        for(Student student : draft) {
            if(student.study == Study.DESIGN) { designCount++; }
            else if(student.study == Study.ART) { artCount++; }
        }
    }

    /**
     * Calculate group score
     */
    public void evaluateRelation() {
        //For each student in the group...
        for(Student student : draft) {
            //...then for each other student in the group...
            for(Student other : draft) {
                if(!student.equals(other)) {
                    // Rogue-like relation
                    if(student.rogueLikeProject.equals(other.rogueLikeProject)) {
                        relationScore += ScoreSystem.rogueLikeRelation;
                    }
                    //Board game relation
                    if(student.boardGameProject.equals(other.boardGameProject)) {
                        relationScore += ScoreSystem.boardGameRelation;
                    }
                    //No common project relation
                    if(!student.rogueLikeProject.equals(other.rogueLikeProject) && !student.boardGameProject.equals(other.boardGameProject)) {
                        relationScore += ScoreSystem.noWorkRelation;
                    }
                    //Ban relation
                    for(String banName : student.banList) {
                        if(banName.equals(other.name)) {
                            relationScore += ScoreSystem.bannedRelation;
                        }
                    }
                    //Fav relation
                    for(String favName : student.favList) {
                        if(favName.equals(other.name)) {
                            relationScore += ScoreSystem.favoredRelation;
                        }
                    }
                }
            }
        }
    }
}
