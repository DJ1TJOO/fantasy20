package nl.fantasynetworkmc.fantasy20;

public enum PanelTypes {
	/*
	 * Note: update floorframe and wallframe when changing values
	 * */
	NONE(0), WOODEN(1), STONE(2), METAL(3);
	
	private final int value;
    private PanelTypes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
