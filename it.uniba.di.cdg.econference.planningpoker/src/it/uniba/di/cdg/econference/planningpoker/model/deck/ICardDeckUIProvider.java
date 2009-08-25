package it.uniba.di.cdg.econference.planningpoker.model.deck;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.swt.widgets.Composite;

public interface ICardDeckUIProvider extends IStructuredContentProvider,
		ILabelProvider {

	void addWidgetFromCard(IPokerCard card, Composite parent);

}
