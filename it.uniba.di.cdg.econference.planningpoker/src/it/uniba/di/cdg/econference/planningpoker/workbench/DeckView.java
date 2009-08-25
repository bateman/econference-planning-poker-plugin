package it.uniba.di.cdg.econference.planningpoker.workbench;

import it.uniba.di.cdg.econference.planningpoker.IPlanningPokerManager;

import it.uniba.di.cdg.econference.planningpoker.actions.AddUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.DeleteUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EditUserStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.EstimateStoryAction;
import it.uniba.di.cdg.econference.planningpoker.actions.SelectCardAction;
import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.ICardDeckUIProvider;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.econference.IEConferenceManager;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IConferenceModel.ConferenceStatus;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant.Role;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.part.ViewPart;

public class DeckView extends ViewPart implements IDeckView {

	public static final String ID = "it.uniba.di.cdg.econference.planningpoker.DeckView";
	
	//public TableViewer viewer;
	public Composite parent;
	public IPlanningPokerManager manager;
	
	private IPokerCard selectedCard;
	private Listener doubleClickListener = null;
	private SelectCardAction selectCardAction; 

	public DeckView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		//viewer = new TableViewer(parent, SWT.HORIZONTAL);	
		this.parent = parent;
		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;
		parent.setLayout(layout);
		
		makeActions();
	}
	
	private void makeActions(){
		selectCardAction = new SelectCardAction(this);
	}
	
	@Override
	public IPokerCard getSelectedCard() {
		return selectedCard;
	}

	@Override
	public void setSelectedCard(IPokerCard selectedCard) {
		this.selectedCard = selectedCard;

	}


	@Override
	@SwtAsyncExec
	public void setManager(IPlanningPokerManager manager) {
		this.manager = manager;
		ICardDeckUIProvider provider = getModel().getFactory().createCardDeckUIProvider();
		//viewer.setContentProvider(provider);
		//viewer.setLabelProvider(provider);
		//viewer.setInput(getModel().getCardDeck().getCards());
		IPokerCard[] cards =  getModel().getCardDeck().getCards();
		for (IPokerCard card : cards) {
			provider.addWidgetFromCard(card,parent);
		}
	}

	public IPlanningPokerModel getModel() {
		return getManager().getService().getModel();
	}

	@Override
	public Role getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReadOnly() {
		return getManager().getStatus().equals(ConferenceStatus.STOPPED);
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		parent.setEnabled(!readOnly);
		selectCardAction.setAccessible(!readOnly);
		if(readOnly){
//			if(doubleClickListener != null){
//				viewer.getTable().removeListener(SWT.MouseDoubleClick, doubleClickListener );
//				doubleClickListener = null;
//			}

		}else{
//			if(doubleClickListener == null)
//				doubleClickListener = new Listener() {
//				public void handleEvent(Event event) {
//					selectCardAction.run();
//				} 
//			};
//			viewer.getTable().addListener(SWT.MouseDoubleClick, doubleClickListener );			
		}		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IPlanningPokerManager getManager() {
		return manager;
	}

//	public TableViewer getViewer() {
//		return viewer;
//	}
	
	/**
	 * 
	 * @param currItemIndex the index of the User Story
	 */
	@SwtAsyncExec
	private void setCurrentCard(int currCardIndex){
		//Bolding the list item of the selected card
//		for( String item : viewer.getList().getItems()){
//			item.setFont(viewer.getList().getFont());
//		}
//		TableItem item = viewer.getList().getItem(currCardIndex);
//		FontData fontData = viewer.getList().getFont().getFontData()[0];		
//		fontData.setStyle(SWT.BOLD);
//		item.setFont(new Font(getSite().getShell().getDisplay(),fontData));

	}

}
