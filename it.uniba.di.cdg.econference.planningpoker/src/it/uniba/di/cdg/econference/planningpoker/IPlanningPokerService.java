package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.xcore.econference.IEConferenceService;
import it.uniba.di.cdg.xcore.network.services.Capability;
import it.uniba.di.cdg.xcore.network.services.ICapability;

public interface IPlanningPokerService extends IEConferenceService {
    /**
     * Constant indicating that the backend supports multi-peers chat.
     */
    public static final ICapability PLANNINGPOKER_SERVICE = new Capability( "planning-poker" );
  
    
    void notifyCardSelection( String cardValue );


}
