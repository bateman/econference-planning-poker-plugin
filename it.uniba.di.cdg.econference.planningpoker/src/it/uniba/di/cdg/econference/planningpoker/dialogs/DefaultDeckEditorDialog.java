package it.uniba.di.cdg.econference.planningpoker.dialogs;

import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class DefaultDeckEditorDialog extends TitleAreaDialog implements IDeckEditorDialog {

	private CardDeck deck;
	
	private TableViewer actualCardsViewer;
	
	private TableViewer availableCardsViewer;
	
	private ITableLabelProvider labelProvider = new ITableLabelProvider(){

		@Override
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		@Override
		public String getColumnText(Object element, int columnIndex) {
			DefaultPokerCard card = (DefaultPokerCard)element;
			String label ="";
			switch(columnIndex){
			case 0: 
				label = card.getStringValue();
			break;
			}
			return label;
		}

		@Override
		public void addListener(ILabelProviderListener listener) {}

		@Override
		public void dispose() {}

		@Override
		public boolean isLabelProperty(Object element, String property) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeListener(ILabelProviderListener listener) {
			// TODO Auto-generated method stub
			
		}
		
	};

	
	private IStructuredContentProvider contentProvider = new IStructuredContentProvider(){

		@Override
		public Object[] getElements(Object inputElement) {
			return (Object[]) inputElement;
		}

		@Override
		public void dispose() {}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput,
				Object newInput) {}
		
	};
	
	
	
	
	public DefaultDeckEditorDialog(Shell parentShell, CardDeck deck) {
		super(parentShell);	
		this.deck = deck;		
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("Default Deck Editor");
		super.configureShell(newShell);
	}
	
	
	private void fillLists(){
		actualCardsViewer.setInput(deck.getCards());
		availableCardsViewer.setInput(deck.getHiddenCards());
	}
	
	@Override
	public CardDeck getFinalDeck() {
		return deck;
	}

	@Override
	public void setCardDeck(CardDeck deck) {
		this.deck = deck;	
		fillLists();
	}

	@Override
	public int show() {
		return this.open();
	}
	
	
	@Override
	protected Control createDialogArea(Composite parent) {
		setTitle("Change your deck configuration");		
		
		Composite root = parent;
		root.setLayout(new GridLayout(3,false));
		
		GridData gd = new GridData();
		
		//If you want add new card, uncomment following lines
//		gd.horizontalSpan = 3;
//		
//		Button newCard = new Button(root, SWT.PUSH);
//		newCard.setText("Add new Card");
//		newCard.setLayoutData(gd);
//		
//		gd = new GridData();
		
		gd.verticalSpan = 2;
		gd.widthHint = 150;
		gd.heightHint = 120;
		
		actualCardsViewer = new TableViewer(root, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL  | SWT.V_SCROLL);	
		actualCardsViewer.getTable().setLayoutData(gd);
		actualCardsViewer.setContentProvider(contentProvider);
		actualCardsViewer.setLabelProvider(labelProvider);
		
		gd = new GridData();	
		gd.widthHint = 25;
		
		Button removeCard = new Button(root, SWT.PUSH);
		removeCard.setText(">>");
		removeCard.setLayoutData(gd);
		removeCard.addSelectionListener(new SelectionAdapter(){
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		removeSelectedCard();
	    	}
		});

		gd = new GridData();
		gd.verticalSpan = 2;
		gd.widthHint = 150;
		gd.heightHint = 120;
		
		availableCardsViewer = new TableViewer(root, SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL  | SWT.V_SCROLL);	
		availableCardsViewer.getTable().setLayoutData(gd);
		availableCardsViewer.setContentProvider(contentProvider);
		availableCardsViewer.setLabelProvider(labelProvider);
		
		
		gd = new GridData();
		gd.widthHint = 25;
		gd.verticalAlignment = SWT.BOTTOM;
		
		Button addCard = new Button(root, SWT.PUSH);
		addCard.setText("<<");
		addCard.setLayoutData(gd);
		addCard.addSelectionListener(new SelectionAdapter(){
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		addSelectedCard();
        	}
		});
		
		fillLists();
		
		
		return root;
	}

	@SwtAsyncExec
	private void addSelectedCard() {
		IPokerCard card = getSelectedCard(availableCardsViewer);
		if(card!=null){
			deck.addCard(card);
			deck.removeHiddenCard(card);			
			fillLists();
		}
		
	}

	@SwtAsyncExec
	private void removeSelectedCard() {
		IPokerCard card = getSelectedCard(actualCardsViewer);
		if(card!=null){
			deck.removeCard(card);
			deck.addHiddenCard(card);	
			fillLists();
		}
	}
	
	
	private IPokerCard getSelectedCard(TableViewer viewer){
		ISelection selection = viewer.getSelection();
		if(selection!=null && selection instanceof IStructuredSelection){
			IStructuredSelection sel = (IStructuredSelection)selection;
			return (IPokerCard) sel.getFirstElement();
		}else{
			return null;
		}
	}

}
