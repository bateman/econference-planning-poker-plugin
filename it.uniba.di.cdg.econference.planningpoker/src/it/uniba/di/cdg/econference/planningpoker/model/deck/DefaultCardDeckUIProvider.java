package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class DefaultCardDeckUIProvider implements ICardDeckUIProvider {

	private static final String IMAGE_ONE = "icons/deck/as.gif";
	private static final String IMAGE_TWO = "icons/deck/2s.gif";
	private static final String IMAGE_THREE = "icons/deck/3s.gif";
	private static final String IMAGE_FIVE = "icons/deck/5s.gif";
	private static final String IMAGE_EIGHT = "icons/deck/8s.gif";
	private static final String IMAGE_TWELVE = "icons/deck/one.gif";
	
	@Override
	public Object[] getElements(Object inputElement) {		
		return (Object[])inputElement;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image getImage(Object element) {
		Image image = null;
		IPokerCard card = (IPokerCard)element;
		Object value = card.getValue();		
		if(value.equals(DefaultCardDeck.ONE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_ONE);
			image = ImageDescriptor.createFromURL(url).createImage();
		}else if(value.equals(DefaultCardDeck.TWO)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_TWO);
			image = ImageDescriptor.createFromURL(url).createImage();
		}else if(value.equals(DefaultCardDeck.THREE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_THREE);
			image = ImageDescriptor.createFromURL(url).createImage();
		}else if(value.equals(DefaultCardDeck.FIVE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_FIVE);
			image = ImageDescriptor.createFromURL(url).createImage();
		}else if(value.equals(DefaultCardDeck.EIGHT)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_EIGHT);
			image = ImageDescriptor.createFromURL(url).createImage();
		}else if(value.equals(DefaultCardDeck.UNKNOWN)){
		
		}
		return image;
		
	}

	@Override
	public String getText(Object element) {
		//IPokerCard card = (IPokerCard)element; 
		//return (String)card.getStringValue();
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
	
	private IPokerCard selectedCard;

	@Override
	public void addWidgetFromCard(IPokerCard card, Composite parent) {
		Object value = card.getValue();
		Image image = null;
		Button button = new Button(parent,SWT.PUSH | SWT.TOGGLE | SWT.RADIO);
		if(value.equals(DefaultCardDeck.ONE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_ONE);
			image = ImageDescriptor.createFromURL(url).createImage();			
			button.setImage(image);
		}else if(value.equals(DefaultCardDeck.TWO)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_TWO);
			image = ImageDescriptor.createFromURL(url).createImage();
			button.setImage(image);
		}else if(value.equals(DefaultCardDeck.THREE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_THREE);
			image = ImageDescriptor.createFromURL(url).createImage();
			button.setImage(image);
		}else if(value.equals(DefaultCardDeck.FIVE)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_FIVE);
			image = ImageDescriptor.createFromURL(url).createImage();
			button.setImage(image);
		}else if(value.equals(DefaultCardDeck.EIGHT)){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(IMAGE_EIGHT);
			image = ImageDescriptor.createFromURL(url).createImage();
			button.setImage(image);
		}else if(value.equals(DefaultCardDeck.UNKNOWN)){
		
		}
		
	}
	
	public void setSelectedCard(IPokerCard card){
		this.selectedCard = card;
	}
	
	public IPokerCard getSelectedCard(){
		return selectedCard;
	}	

}
