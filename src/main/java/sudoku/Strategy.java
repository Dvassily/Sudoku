package sudoku;

public enum Strategy {
    SINGLE_CANDIDATE, NAKED_PAIR, NAKED_TRIPLET, NAKED_QUADRUPLET, HIDDEN_PAIR, HIDDEN_TRIPLET, HIDDEN_QUADRUPLET, INTERSECTION_REMOVAL, X_WING;

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
