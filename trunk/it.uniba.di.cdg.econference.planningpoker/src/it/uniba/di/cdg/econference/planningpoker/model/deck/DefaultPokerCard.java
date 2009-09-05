package it.uniba.di.cdg.econference.planningpoker.model.deck;


public class DefaultPokerCard implements IPokerCard {
	
	public static final String UNKNOWN ="Unknown";
	public static final String ONE ="1";
	public static final String TWO ="2";
	public static final String THREE ="3";
	public static final String FIVE ="5";
	public static final String EIGHT ="8";
	public static final String TWENTY ="20";
	public static final String FOURTY ="40";
	public static final String HUNDRED ="100";
	
	public static final String IMAGE_ONE = "icons/deck/1.ico";
	public static final String IMAGE_TWO = "icons/deck/2.ico";
	public static final String IMAGE_THREE = "icons/deck/3.ico";
	public static final String IMAGE_FIVE = "icons/deck/5.ico";
	public static final String IMAGE_EIGHT = "icons/deck/8.ico";	
	public static final String IMAGE_UNKNOWN = "icons/deck/0.ico";
	
	private String value;
	private String imagePath;
	
	public DefaultPokerCard(String value){
		this.value = value;		
	}
	
	public DefaultPokerCard(String value, String imagePath){
		this.value = value;		
		this.imagePath = imagePath;
	}
	
	@Override
	public String getStringValue() {
		return value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		this.value = (String) value;
	}

	@Override
	public String getImagePath() {		
		return imagePath;
	}

	@Override
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
		
	}

	@Override
	public boolean hasImagePath() {
		return imagePath!=null;
	}

}
