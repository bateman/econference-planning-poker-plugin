/**
 * 
 */
package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Alex
 *
 */
public interface IBacklog extends IStructuredContentProvider, ITableLabelProvider{
	
	
	public void addUserStory(IUserStory story);
	
	public void removeUserStory(IUserStory story);

	public IUserStory[] getUserStories();
	
	public void createColumns(TableViewer viewer);

	public IUserStoryDialog getDialog(Shell shell);
	

}
