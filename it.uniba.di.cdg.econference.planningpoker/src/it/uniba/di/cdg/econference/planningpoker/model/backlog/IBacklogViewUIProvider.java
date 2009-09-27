package it.uniba.di.cdg.econference.planningpoker.model.backlog;


import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;

/**
 * 
 * This interface allows to draw the backlog in the Table Viewer
 * 
 * @author Alex
 *
 */
public interface IBacklogViewUIProvider extends IStructuredContentProvider,
		ITableLabelProvider {
	
	void createColumns(TableViewer viewer);

}
