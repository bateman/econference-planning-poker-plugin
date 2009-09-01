package it.uniba.di.cdg.econference.planningpoker.model.estimates;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;

public interface IEstimatesViewUIProvider extends IStructuredContentProvider, ITableLabelProvider{

	void createColumns(TableViewer viewer);

}
