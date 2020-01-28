package nl.fantasynetworkmc.fantasy20;

public enum FloorTypes {

	NONE(0), WOODEN(1), STONE(2), METAL(3);
	
	private final int value;
    private FloorTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
