package matchmaking;

public enum Study {
    DESIGN {
        @Override
        public String toString() {
            return "GD";
        }
    },
    ART {
        @Override
        public String toString() {
            return "GA";
        }
    }
}
