package it.uniba.di.cdg.econference.planningpoker.ui.workbench;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.ui.views.IActivatableView;

public interface IDeckView extends IPlanningPokerView,IActivatableView {

	/**
	 * <p>Get the card that user has selected</p>
	 * 
	 * @return the Selected Card
	 */
	IPokerCard getSelectedCard();
	
	/**
	 * <p>Allow/Forbid card selection in the deck</p>
	 * 
	 * @param enable if <code>true</code> allow card selection,
	 * if <code>false</code> forbid.
	 */
	void setDeckEnable(boolean enable);

}
