package it.uniba.di.cdg.econference.planningpoker.model.deck;

public class DefaultPokerCard implements IPokerCard {
	
	private String value;
	
	public DefaultPokerCard(String value){
		this.value = value;
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

}
