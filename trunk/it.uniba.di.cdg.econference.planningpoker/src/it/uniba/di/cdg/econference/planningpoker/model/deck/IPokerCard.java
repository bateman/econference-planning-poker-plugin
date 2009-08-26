package it.uniba.di.cdg.econference.planningpoker.model.deck;


public interface IPokerCard {
	
	void setValue(Object value);
	
	Object getValue();	
	
	String getStringValue();
	
	void setImagePath(String path);
	
	String getImagePath();
	
	boolean hasImagePath();
}
