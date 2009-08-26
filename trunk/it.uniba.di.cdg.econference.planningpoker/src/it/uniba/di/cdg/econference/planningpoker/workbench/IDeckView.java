package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.multichat.IRoleProvider;
import it.uniba.di.cdg.xcore.ui.views.IActivatableView;

public interface IDeckView extends IPlanningPokerView,IActivatableView {

	void setSelectedCard(IPokerCard selectedCard);

	IPokerCard getSelectedCard();

}
