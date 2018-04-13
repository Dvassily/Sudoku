package sudoku;

public enum Strategy {
    SINGLE_CANDIDATE(10),
    NAKED_PAIR(20),
    NAKED_TRIPLET(30),
    NAKED_QUADRUPLET(40),
    HIDDEN_PAIR(20),
    HIDDEN_TRIPLET(30),
    HIDDEN_QUADRUPLET(40),
    INTERSECTION_REMOVAL(20),
    X_WING(50),
    SINGLE_CHAIN_TWICE_IN_UNIT(100),
    SINGLE_CHAIN_TWO_COLORS_ELSEWHERE(110),
    Y_WING(75);

    private int score;
    
    Strategy(int score) {
	this.score = score;
    }

    public int getScore() {
	return score;
    }
    
    // TODO: Throw exception
    public static Strategy nakedSet(int cardinality) {
	switch(cardinality) {
	case 2:
	    return NAKED_PAIR;
	case 3:
	    return NAKED_TRIPLET;
	case 4:
	    return NAKED_QUADRUPLET;
	default:
	    return null;
	}
    }

    public static Strategy hiddenSet(int cardinality) {
	switch(cardinality) {
	case 2:
	    return HIDDEN_PAIR;
	case 3:
	    return HIDDEN_TRIPLET;
	case 4:
	    return HIDDEN_QUADRUPLET;
	default:
	    return null;
	}
    }
}
