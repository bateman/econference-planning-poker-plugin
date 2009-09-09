package it.uniba.di.cdg.econference.planningpoker.model;

import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.xcore.econference.model.InvalidContextException;

import org.w3c.dom.Document;

public interface IBacklogContextLoader {
	
	Backlog load(Document doc) throws InvalidContextException;

}
