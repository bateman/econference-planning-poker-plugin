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
 * @author Alessandro Brucoli
 *
 */
public interface IBacklog extends 
	IStructuredContentProvider, 
	ITableLabelProvider {
	
	/**
	 * Add a new User Story to this Backlog.
	 * 
	 * @param story The story to add
	 */
	void addUserStory(IUserStory story);
	
	
	/**
	 * Remove a story from Backlog.
	 * 
	 * @param story The story to remove
	 */
	void removeUserStory(IUserStory story);

	/**
	 * Return all of the Story that are part of the Backlog.
	 * 
	 * @return an array of User Story
	 */
	IUserStory[] getUserStories();
	
	
	void createColumns(TableViewer viewer);

	IUserStoryDialog getDialog(Shell shell);
	

}
