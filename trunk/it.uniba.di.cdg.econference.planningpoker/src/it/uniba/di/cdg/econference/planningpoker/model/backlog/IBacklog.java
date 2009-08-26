/**
 * 
 */
package it.uniba.di.cdg.econference.planningpoker.model.backlog;

import it.uniba.di.cdg.xcore.econference.model.IItemList;

/**
 * @author Alex
 *
 */
public interface IBacklog extends IItemList{
	

	IUserStory[] getUserStories();	
	
	void removeUserStory(IUserStory story);
	
	void setBacklogContent(IUserStory[] stories);

}
