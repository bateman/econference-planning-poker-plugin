package it.uniba.di.cdg.econference.planningpoker.model.deck;

import it.uniba.di.cdg.econference.planningpoker.PlanningPokerPlugin;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class DefaultDeckViewUIHelper implements IDeckViewUIHelper {

//	private String selectedValue;
	private static final String KEY_VALUE = "value";
	private static final int SELECTION_COLOR = SWT.COLOR_RED;
	private static final int DEFAULT_COLOR = SWT.COLOR_GRAY;
	
	private Set<ICardSelectionListener> listeners;
	private Composite parent;
	
	public DefaultDeckViewUIHelper(Composite parent) {
		this.parent = parent;
		listeners = new HashSet<ICardSelectionListener>();
		createPartControl();
	}
	
	private void createPartControl() {
		FillLayout layout = new FillLayout();
		layout.type = SWT.HORIZONTAL;
		parent.setLayout(layout);		
	}

	@Override
	public void addWidgetFromCard(final IPokerCard card) {		
		Image image = null;
		Button button = new Button(parent, SWT.TOGGLE );
		button.setData(KEY_VALUE, card.getStringValue());
		button.setBackground(parent.getDisplay().getSystemColor(DEFAULT_COLOR));
		button.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event event) {		
				setSelectedButton((Button) event.widget);
//				selectedValue = card.getStringValue();	
				for(ICardSelectionListener l : listeners){
					l.cardSelected(card);
				}
			}
				
		});
				
		if(card.hasImagePath()){
			URL url = Platform.getBundle(PlanningPokerPlugin.ID).getEntry(card.getImagePath());
			image = ImageDescriptor.createFromURL(url).createImage();			
			button.setImage(image);
		}
		parent.layout();
		
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
		if(button!=null){
			button.setBackground(parent.getDisplay().getSystemColor(SELECTION_COLOR));
			button.setSelection(true);
		}
	}
	
	
	private void clearSelection(){
		setSelectedButton(null);
	}
		
//	public void setSelectedValue(String value){
//		this.selectedValue = value;
//	}
//	
//	public String getSelectedValue(){
//		return selectedValue;
//	}
	


	@Override
	public void removeWidgetFromCard(IPokerCard card) {
		Control[] controls = parent.getChildren();
		for(Control control: controls){
			if(control.getData(KEY_VALUE).equals(card.getStringValue())){
				control.dispose();
			}
		}		
	}	
	
	@Override
	public void addCardSelectionListener(ICardSelectionListener listener){
		listeners.add(listener);		
	}

	@Override
	public void removeCardSelectionListener(ICardSelectionListener listener) {
		listeners.remove(listener);
		
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		parent.setEnabled(!readOnly);	
		clearSelection();
	}

	@Override
	public boolean isReadOnly() {
		return !parent.isEnabled();
	}
	
	@Override
	public void setFocus(){
		parent.setFocus();
	}

}


