package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.xcore.econference.model.IItemListListener;

public interface IBacklogListener extends IItemListListener {
	
	void estimateAssigned(String storyId, String estimateValue);
	
	void contentChanged(Backlog newBacklog);

}
