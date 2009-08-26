package it.uniba.di.cdg.econference.planningpoker.model.deck;


public class DefaultPokerCard implements IPokerCard {
	
	private String value;
	private String imagePath;
	
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
