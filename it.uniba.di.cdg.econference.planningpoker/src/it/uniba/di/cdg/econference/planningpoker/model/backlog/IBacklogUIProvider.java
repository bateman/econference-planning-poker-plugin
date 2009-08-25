package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.econference.planningpoker.dialogs.IUserStoryDialog;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * This interface allows to draw the backlog in the Table Viewer
 * 
 * @author Alex
 *
 */
public interface IBacklogUIProvider extends IStructuredContentProvider,
		ITableLabelProvider {
	
	void createColumns(TableViewer viewer);

	IUserStoryDialog getDialog(Shell shell);

}
