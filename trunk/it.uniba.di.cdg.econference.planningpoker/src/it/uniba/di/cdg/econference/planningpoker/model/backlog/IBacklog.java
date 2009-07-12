/**
 * 
 */
package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;
import it.uniba.di.cdg.xcore.econference.model.IDiscussionItem;
import it.uniba.di.cdg.xcore.econference.model.IItemList;
import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Alex
 *
 */
public interface IBacklog extends IStructuredContentProvider, ITableLabelProvider, IItemList{
	

	IUserStory[] getUserStories();
	
	void createColumns(TableViewer viewer);

	IUserStoryDialog getDialog(Shell shell);
	
	void removeUserStory(IUserStory story);
	
	void setBacklogContent(IUserStory[] stories);

}
