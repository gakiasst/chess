package src;

public enum Color {
    WHITE, BLACK;

    public Color nextColor() {
        if (this == WHITE) {
            return BLACK;
        } else {
            return WHITE;
        }
    }
}

