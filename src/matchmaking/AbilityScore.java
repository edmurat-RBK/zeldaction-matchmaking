package matchmaking;

public class AbilityScore {

    public Ability ability;
    public float wish = 0;
    public float skill= 0;

    public AbilityScore(Ability ability, float wish, float skill) {
        this.ability = ability;
        this.wish = wish;
        this.skill = skill;
    }
}
