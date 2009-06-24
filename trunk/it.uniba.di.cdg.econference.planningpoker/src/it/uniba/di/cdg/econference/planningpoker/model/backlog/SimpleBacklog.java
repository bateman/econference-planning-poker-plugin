package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.dialogs.SimpleUserStoryDialog;
import it.uniba.di.cdg.econference.planningpoker.model.StoryPoints;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

public class SimpleBacklog implements IBacklog {

	private static SimpleBacklog uniqInstance;

	private List<SimpleUserStory> stories = new ArrayList<SimpleUserStory>();

	public static synchronized SimpleBacklog getInstance() {
		if (uniqInstance == null) {
			uniqInstance = new SimpleBacklog();
		}
		return uniqInstance;
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
		SimpleUserStory story = (SimpleUserStory)element;
		String label ="";
		switch(columnIndex){
		case 0: // Name 
			label = story.getName();
		break;
		case 1: {//PRIORITY
			SimpleUserStory.PRIORITY priority = story.getPriority();
			if(priority == SimpleUserStory.PRIORITY.HIGH){
				label = "High";
			}else if(priority == SimpleUserStory.PRIORITY.MEDIUM){
				label = "Medium";
			}else if(priority == SimpleUserStory.PRIORITY.LOW){
				label = "Low";
			}else if(priority == SimpleUserStory.PRIORITY.UNKNOWN){
				label = "Unknow";
			}
		}
		break;
		case 2:{ //Description
			label = story.getDescription();
		}
		break;
		case 3:{
			StoryPoints estimate = story.getEstimate();
			if(estimate==StoryPoints.UNKNOW)
				label = "Unknow";
			else
				label = String.valueOf(estimate.getPoints());
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
	public void addUserStory(IUserStory story) {
		stories.add((SimpleUserStory) story);
		
	}


	@Override
	public IUserStory[] getUserStories() {
		IUserStory[] result = new IUserStory[stories.size()];
		for (int i = 0; i < stories.size(); i++) {
			result[i] = stories.get(i);
		}
		return result;
	}


	@Override
	public void createColumns(TableViewer viewer) {
		String[] titles = new String[] {"Name","Priority","Description","Estimate"};
		int[] bounds = new int[] {50,50,200,50};

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
		return new SimpleUserStoryDialog(shell);
	}


	@Override
	public void removeUserStory(IUserStory story) {
		stories.remove((SimpleUserStory)story);		
	}


	public void initializeTestData() {
		
		
	}

}
