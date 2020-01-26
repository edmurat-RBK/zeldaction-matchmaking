package matchmaking;

public enum Specialisation {
    NONE,
    PROJECT_MANAGER {
        @Override
        public String toString() {
            return "Project Manager";
        }
    },
    LEAD_PROGRAMMER{
        @Override
        public String toString() {
            return "Lead Programmer";
        }
    },
    ART_DIRECTOR{
        @Override
        public String toString() {
            return "Art Director";
        }
    }
}
