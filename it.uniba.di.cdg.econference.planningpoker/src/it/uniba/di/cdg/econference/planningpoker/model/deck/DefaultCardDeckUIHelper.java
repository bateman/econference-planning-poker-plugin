package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class DefaultCardDeckUIHelper implements ICardDeckUIHelper {

	private String selectedValue;
	private static final String KEY_VALUE = "value";
	private static final int SELECTION_COLOR = SWT.COLOR_RED;
	private static final int DEFAULT_COLOR = SWT.COLOR_GRAY;
	
	private Set<ICardSelectionListener> listeners;
	
	public DefaultCardDeckUIHelper() {
		listeners = new HashSet<ICardSelectionListener>();
	}
	
	@Override
	public void addWidgetFromCard(final IPokerCard card, final Composite parent) {		
		Image image = null;
		Button button = new Button(parent, SWT.TOGGLE );
		button.setData(KEY_VALUE, card.getStringValue());
		button.setBackground(parent.getDisplay().getSystemColor(DEFAULT_COLOR));
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {		
				setSelectedButton((Button) event.widget);
				selectedValue = card.getStringValue();	
				for(ICardSelectionListener l : listeners){
					l.cardSelected(card);
				}
			}
			
			private void setSelectedButton(Button button) {								
				Control[] controls = parent.getChildren();
				for(Control control: controls){
					if(control instanceof Button){
						Button current = (Button) control;
						current.setBackground(parent.getDisplay().getSystemColor(DEFAULT_COLOR));
						current.setSelection(false);
					}
				}
				button.setBackground(parent.getDisplay().getSystemColor(SELECTION_COLOR));
				button.setSelection(true);
			}
				
		});
				
		if(card.hasImagePath()){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(card.getImagePath());
			image = ImageDescriptor.createFromURL(url).createImage();			
			button.setImage(image);
		}
		parent.layout();
		
	}
	
	
	
	public void addCardSelectionListener(){
		
	}
	
	public void setSelectedValue(String value){
		this.selectedValue = value;
	}
	
	public String getSelectedValue(){
		return selectedValue;
	}

	@Override
	public void removeWidgetFromCard(IPokerCard card, Composite parent) {
		Control[] controls = parent.getChildren();
		for(Control control: controls){
			if(control.getData(KEY_VALUE).equals(card.getStringValue())){
				control.dispose();
			}
		}		
	}	
	
	public void addCardSelectionListener(ICardSelectionListener listener){
		listeners.add(listener);		
	}

	@Override
	public void removeCardSelectionListener(ICardSelectionListener listener) {
		listeners.remove(listener);
		
	}

}


