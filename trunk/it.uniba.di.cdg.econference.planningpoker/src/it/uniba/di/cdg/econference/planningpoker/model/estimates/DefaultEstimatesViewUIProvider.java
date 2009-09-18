package it.uniba.di.cdg.econference.planningpoker.model.estimates;


import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.utils.AutoResizeTableLayout;
import it.uniba.di.cdg.xcore.aspects.SwtAsyncExec;
import it.uniba.di.cdg.xcore.multichat.model.IParticipant;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;

public class DefaultEstimatesViewUIProvider implements IEstimatesViewUIProvider {

	public DefaultEstimatesViewUIProvider() {

	}
	
	@Override
	public Object[] getElements(Object inputElement) {
		return (Object[]) inputElement;
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
	public Image getColumnImage(Object element, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		String label = "";
		Object[] story = (Object[])element;
		IParticipant participant = (IParticipant) story[0];
		IPokerCard card = (IPokerCard) story[1];
		switch(columnIndex){
		case 0: // Name 
			label = participant.getNickName();
		break;
		case 1: {
			if(card!=null)
				label = card.getStringValue();
			else //votes are hidden
				label = "Hidden";
		}
		break;
		}
		return label;
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
	
	
	@Override
	public void createColumns(TableViewer viewer) {
		String[] titles = new String[] {"Participant","Estimate",};
		int[] bounds = new int[] {200,80};
		AutoResizeTableLayout layout = (AutoResizeTableLayout) viewer.getTable().getLayout();
		for (int i = 0; i < titles.length; i++) {
			final int index = i;
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			//column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			layout.addColumnData(new ColumnWeightData(bounds[i]));
			column.setLabelProvider(new CellLabelProvider() {
				
				@Override
				public void update(ViewerCell cell) {
					Object[] result = (Object[])cell.getElement();
					switch(index){
					case 0:
						cell.setText(((IParticipant)result[0]).getNickName());
						break;
					case 1:	
						IPokerCard card = (IPokerCard)result[1];
						if(card!=null)
							cell.setText(card.getStringValue());
						else //votes are hidden
							cell.setText("Hidden");						
						break;
					}
					
				}
			});
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
	}
	

}
