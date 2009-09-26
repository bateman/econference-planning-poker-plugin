package it.uniba.di.cdg.econference.planningpoker.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;

/**
 * <p>This class is associated to a view and model an action that 
 * is enable only if one element is selected on the View.</p>
 * 
 * <p>The action for delete, edit and estimate User Stories in the
 * BacklogView are an examples of this kind of action</p>
 * 
 * @author Alex
 *
 */
public class SingleSelectionAction extends Action {
	
	protected IViewPart view;
	
	private ISelectionListener listener;
	
	public SingleSelectionAction(IViewPart view) {
		super();
		this.view = view;		
		addSelectionListener();
		setEnabled(false);
	}
	
	/**
	 * 
	 * <p>This method works like setEnable method.</p>
	 * <p>We have created a different method for distinguish between the</p>
	 * <ul>
	 * <li>action enabling in relation to the selection</li>
	 * <li>action enabling permanently (for example because only the moderator can use the action)</li> 
	 * </ul>
	 * This method addresses the latter point
	 * 
	 * @param accessible if this action is accessible or not
	 */
	public void setAccessible(boolean accessible){		
		if(accessible){				
			addSelectionListener();
		}else{
			removeSelectionListener();
			setEnabled(false);
		}
		
	}
		
	private void addSelectionListener() {
		if(listener==null)
		listener = new ISelectionListener(){

			@Override
			public void selectionChanged(IWorkbenchPart part,
					ISelection selection) {
				setEnabled((IStructuredSelection)selection);
				
			}			
		};
		view.getViewSite().getWorkbenchWindow().getSelectionService().addSelectionListener(listener);		
	}
	
	private void removeSelectionListener(){
		if(listener!=null)
		view.getViewSite().getWorkbenchWindow().getSelectionService().removeSelectionListener(listener);
		listener = null;
	}
	
	
	private void setEnabled(IStructuredSelection selection) {		
		//This action is enable only if one element is selected
		boolean enabled = selection.size() == 1;
		setEnabled(enabled);
	}	

}
