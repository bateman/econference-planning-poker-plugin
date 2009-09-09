package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.DefaultUserStoryDialog;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
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
		case 1: {//PRIORITY
			DefaultUserStory.PRIORITY priority = story.getPriority();
			if(priority == DefaultUserStory.PRIORITY.HIGH){
				label = "High";
			}else if(priority == DefaultUserStory.PRIORITY.NORMAL){
				label = "Medium";
			}else if(priority == DefaultUserStory.PRIORITY.LOW){
				label = "Low";
			}else if(priority == DefaultUserStory.PRIORITY.UNKNOWN){
				label = "?";
			}
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
		String[] titles = new String[] {"Story Text","Priority","Estimate"};
		int[] bounds = new int[] {360,50,70};

		for (int i = 0; i < titles.length; i++) {
			TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
			column.getColumn().setText(titles[i]);
			column.getColumn().setWidth(bounds[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
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
