package it.uniba.di.cdg.econference.planningpoker.ui;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IBacklogViewUIProvider;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.DefaultUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.ui.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.utils.AutoResizeTableLayout;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class DefaultBacklogViewUIProvider implements IBacklogViewUIProvider {

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
		DefaultUserStory story = (DefaultUserStory)element;
		String label ="";
		switch(columnIndex){
		case 0: // Name 
			label = story.getStoryText();
		break;
		case 1: {//STATUS
			label = story.getStatus();

		}		
		break;
		case 2:{
			label = story.getEstimate().toString();	
			if(label=="")
				label ="?";
		}
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
		String[] titles = new String[] {"Story Text","Status","Estimate"};
		int[] bounds = new int[] {350,60,70};
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
					DefaultUserStory story = (DefaultUserStory) cell.getElement();
					switch(index){
					case 0:
						cell.setText(story.getStoryText());
						break;
					case 1:
						cell.setText(story.getStatus());
						break;
					case 2:
						cell.setText(story.getEstimate().toString());
						break;
					}
					
					
					
				}
			});
			//column.setEditingSupport(new SimpleBacklogEditingSupport(viewer,i));
		}
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(false);
	}
	
	@Override
	public IUserStoryDialog getDialog(Shell shell) {
		return new DefaultUserStoryDialog(shell);
	}

}
