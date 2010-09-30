/**
 * This file is part of the eConference project and it is distributed under the 

 * terms of the MIT Open Source license.
 * 
 * The MIT License
 * Copyright (c) 2005 Collaborative Development Group - Dipartimento di Informatica, 
 *                    University of Bari, http://cdg.di.uniba.it
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this 
 * software and associated documentation files (the "Software"), to deal in the Software 
 * without restriction, including without limitation the rights to use, copy, modify, 
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package it.uniba.di.cdg.econference.planningpoker;

import it.uniba.di.cdg.econference.planningpoker.model.IPlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.PlanningPokerModel;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.Backlog;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.DefaultUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.backlog.IUserStory;
import it.uniba.di.cdg.econference.planningpoker.model.deck.CardDeck;
import it.uniba.di.cdg.econference.planningpoker.model.deck.DefaultPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.deck.IPokerCard;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.Estimate;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.EstimateSession;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList;
import it.uniba.di.cdg.econference.planningpoker.model.estimates.IEstimatesList.EstimateStatus;
import it.uniba.di.cdg.econference.planningpoker.utils.DateUtils;
import it.uniba.di.cdg.xcore.econference.service.EConferenceService;
import it.uniba.di.cdg.xcore.m2m.model.IParticipant;
import it.uniba.di.cdg.xcore.m2m.model.ParticipantSpecialPrivileges;
import it.uniba.di.cdg.xcore.m2m.model.SpecialPrivilegesAction;
import it.uniba.di.cdg.xcore.m2m.service.Invitee;
import it.uniba.di.cdg.xcore.network.IBackend;
import it.uniba.di.cdg.xcore.network.events.IBackendEvent;
import it.uniba.di.cdg.xcore.network.events.multichat.MultiChatExtensionProtocolEvent;
import it.uniba.di.cdg.xcore.network.model.tv.ITalkModel;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.jivesoftware.smack.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class PlanningPokerService extends EConferenceService implements
		IPlanningPokerService {

	// DefaultBacklogPacket -
	public static final String ELEMENT_NAME_BACKLOG = "backlog";
	public static final String ELEMENT_STORY_BACKLOG = "story";
	public static final String ELEMENT_STORY_ID_BACKLOG = "id";
	public static final String ELEMENT_STORY_TEXT = "story-text";
	public static final String ELEMENT_MILESTONE_ID = "milestone-id";
	public static final String ELEMENT_MILESTONE_DESCRIPTION = "milestone-description";
	public static final String ELEMENT_MILESTONE_CREATION_DATE = "milestone-created-on";
	public static final String ELEMENT_STORY_NOTES = "notes";
	public static final String ELEMENT_STORY_ESTIMATE = "estimate";
	public static final String ELEMENT_CURRENT_STORY = "current-story";

	// DefaultCardSelectionPacket -
	public static final String ELEMENT_CARD_SELECTION = "card-selection";
	public static final String CARD_CARD_SELECTION = "cardValue";
	public static final String USER_ID_CARD_SELECTION = "who";
	public static final String STORY_ID_CARD_SELECTION = "storyId";

	// DefaultDeckPacket -
	public static final String ELEMENT_NAME_DECK = "deck";
	public static final String ELEMENT_CARD_DECK = "card";
	private static final String ELEMENT_HIDDEN_CARD = "hidden-card";
	private static final String ELEMENT_VALUE = "card-value";
	private static final String ELEMENT_IMAGE = "image-path";

	// EstimateAssignedPacket -
	public static String ELEMENT_ESTIMATE_ASSIGNED = "estimate-assigned";
	public static String STORY_ID_ESTIMATE_ASSIGNED = "storyId";
	public static String ESTIMATE_ASSIGNED = "estimate";

	// EstimateSessionStatusPacket -
	public static String ELEMENT_SESSION_STATUS = "estimate-session";
	public static String SESSION_STATUS = "status";
	public static String SESSION_STATUS_STORY_ID = "storyId";
	public static String SESSION_STATUS_ESTIMATE_ID = "id";

	// SpecialPrivilegeNotificationPacket - arrivano da eConference base
	public static final String ELEMENT_SPECIAL_PRIVILEGE = "ChangedSpecialPrivilege";
	public static final String USER_ID_SPECIAL_PRIVILEGE = "UserId";
	public static final String ACTION_SPECIAL_PRIVILEGE = "RoleAction";
	public static final String SPECIAL_PRIVILEGE = "SpecialRole";

	// CurrentAgendaItemPacket - arrivano da eConference base
	public static final String ELEMENT_CURRENT_AGENDA_ITEM = "CurrentAgendaItem";
	public static final String CURRENT_AGENDA_ITEM_ID = "ItemId";

	// AgendaOperationPacket -
	public static final String ELEMENT_AGENDA_OPERATION = "agenda-operation";
	public static final String AGENDA_OPERATION = "operation";
	// sarebbe l'id dell'item cui si applica operation
	public static final String AGENDA_CONTEXT = "context";

	public PlanningPokerService() {
		super();
	}

	public PlanningPokerService(PlanningPokerContext context,
			IBackend backend) {
		super(context, backend);
	}

	@Override
	public IPlanningPokerModel getModel() {
		return (IPlanningPokerModel) super.getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.uniba.di.cdg.jabber.internal.JabberMultiChatService#createModel()
	 */
	@Override
	protected IPlanningPokerModel createModel() {
		IPlanningPokerModel model = new PlanningPokerModel();
		// Use the backlog provided by the context if it is present
		if (getContext().getBacklog() != null)
			model.setBacklog(getContext().getBacklog());
		if (getContext().getCardDeck() != null) {
			model.setCardDeck(getContext().getCardDeck());
		} else {
			// initializing default card
			CardDeck deck = new CardDeck();
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.UNKNOWN,
					DefaultPokerCard.IMAGE_UNKNOWN));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.ZERO, null));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.ONE,
					DefaultPokerCard.IMAGE_ONE));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWO,
					DefaultPokerCard.IMAGE_TWO));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.THREE,
					DefaultPokerCard.IMAGE_THREE));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.FIVE,
					DefaultPokerCard.IMAGE_FIVE));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.EIGHT,
					DefaultPokerCard.IMAGE_EIGHT));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.TWENTY, null));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.FOURTY, null));
			deck.addCard(new DefaultPokerCard(DefaultPokerCard.HUNDRED, null));
			model.setCardDeck(deck);
		}

		if (getContext().getVoters().length != 0) {
			// assuming that all invitees are voter
			for (Invitee invitee : getContext().getVoters()) {
				model.getVoters().addVoter(invitee.getId());
			}
		}

		return model;
	}

	public PlanningPokerContext getContext() {
		return (PlanningPokerContext) super.getContext();
	}

//	/**
//	 * Adapted for backlog item removing
//	 */
//	protected void registerAgendaOperationListener() {
//		// Listen to incoming packets regarding the item list content
//		PacketListener packetListener = new PacketListener() {
//			public void processPacket(Packet packet) {
//				final AgendaOperationPacket ext = (AgendaOperationPacket) packet
//						.getExtension(AgendaOperationPacket.ELEMENT_NAME,
//								SmackCommons.CDG_NAMESPACE);
//				AgendaOperation op = AgendaOperation
//						.valueOf(ext.getOperation());
//				// Every operation has its context: ADD just want a string
//				// containing
//				// the text associated to the new item, REMOVE probably will
//				// want to
//				// parse the string to an integer index.
//				String context = ext.getContext();
//				int index;
//				try {
//					index = Integer.parseInt(context);
//				} catch (NumberFormatException ex) {
//					index = -1;
//				}
//				if (index != -1) {
//					if (AgendaOperation.REMOVE.equals(op)) {
//						getModel().getBacklog().removeItem(index);
//						System.out
//								.println("Received a remove operation for the User Story "
//										+ index);
//					} else {
//						System.err.println("Unsupported operation "
//								+ ext.getOperation());
//					}
//				} else {
//					System.err.println("Unsupported context for backlog"
//							+ ext.getContext());
//				}
//			}
//		};
//		// addPacketListener(packetListener, AgendaOperationPacket.FILTER);
//	}

	@Override
	public void notifyCardSelection(String storyId, IPokerCard card) {
		String localId = getModel().getLocalUser().getId();
		System.out.println(String.format("notifyCardSelection( %s, %s )",
				localId, card.getStringValue()));

		HashMap<String, String> param = new HashMap<String, String>();
		param.put(USER_ID_CARD_SELECTION, localId);
		param.put(STORY_ID_CARD_SELECTION, storyId);
		param.put(CARD_CARD_SELECTION, card.getStringValue());
		getMultiChatServiceActions().SendExtensionProtocolMessage(
				ELEMENT_CARD_SELECTION, param);
	}

	/**
	 * Override the method that notifies the agenda item list to all participant
	 * In the case of Planning Poker this method is used to notifies the entire
	 * backlog to all participants
	 */
	@Override
	public void notifyItemListToRemote() {
		System.out.println(String.format("%s: notifyItemListToRemote",
				this.toString()));

		Backlog backlog = getModel().getBacklog();
		String xml = String.format("{%s}", ELEMENT_NAME_BACKLOG);
		xml += String.format("{%s}%s{/%s}", ELEMENT_CURRENT_STORY,
				backlog.getCurrentItemIndex(), ELEMENT_CURRENT_STORY);
		for (int i = 0; i < backlog.getUserStories().length; i++) {
			DefaultUserStory story = (DefaultUserStory) backlog
					.getUserStories()[i];
			xml += String.format("{%s}", ELEMENT_STORY_BACKLOG);
			xml += String.format("{%s}%s{/%s}", ELEMENT_STORY_ID_BACKLOG,
					StringUtils.escapeForXML(story.getId()),
					ELEMENT_STORY_ID_BACKLOG);
			xml += String.format("{%s}%s{/%s}",
					ELEMENT_MILESTONE_CREATION_DATE, StringUtils
							.escapeForXML(DateUtils.formatDate(story
									.getMilestoneCreationDate())),
					ELEMENT_MILESTONE_CREATION_DATE);
			xml += String.format("{%s}%s{/%s}", ELEMENT_MILESTONE_ID,
					StringUtils.escapeForXML(story.getMilestoneId()),
					ELEMENT_MILESTONE_ID);
			xml += String.format("{%s}%s{/%s}", ELEMENT_MILESTONE_DESCRIPTION,
					StringUtils.escapeForXML(story.getMilestoneName()),
					ELEMENT_MILESTONE_DESCRIPTION);
			xml += String.format("{%s}%s{/%s}", ELEMENT_STORY_TEXT,
					StringUtils.escapeForXML(story.getStoryText()),
					ELEMENT_STORY_TEXT);
			xml += String.format("{%s}%s{/%s}", ELEMENT_STORY_NOTES,
					StringUtils.escapeForXML(story.getNotes()),
					ELEMENT_STORY_NOTES);

			xml += String.format("{%s}%s{/%s}", ELEMENT_STORY_ESTIMATE,
					StringUtils.escapeForXML(story.getEstimate().toString()),
					ELEMENT_STORY_ESTIMATE);
			xml += String.format("{/%s}", ELEMENT_STORY_BACKLOG);
		}
		xml += String.format("{/%s}", ELEMENT_NAME_BACKLOG);

		HashMap<String, String> param = new HashMap<String, String>();
		param.put(ELEMENT_NAME_BACKLOG, xml);
		getMultiChatServiceActions().SendExtensionProtocolMessage(
				ELEMENT_NAME_BACKLOG, param);
	}

	@Override
	public void notifyCardDeckToRemote(CardDeck deck) {
		System.out.println(String.format("%s: notifyCardDeckToRemote",
				this.toString()));

		String xml = String.format("{%s}",
				ELEMENT_NAME_DECK);
		for (int i = 0; i < deck.getCards().length; i++) {
			DefaultPokerCard story = (DefaultPokerCard) deck.getCards()[i];
			xml += String.format("{%s}", ELEMENT_CARD_DECK);
			xml += String.format("{%s}%s{/%s}", ELEMENT_VALUE,
					StringUtils.escapeForXML(story.getStringValue()),
					ELEMENT_VALUE);
			xml += String.format("{%s}%s{/%s}", ELEMENT_IMAGE,
					StringUtils.escapeForXML(story.getImagePath()),
					ELEMENT_IMAGE);
			xml += String.format("{/%s}", ELEMENT_CARD_DECK);
		}
		for (int i = 0; i < deck.getHiddenCards().length; i++) {
			DefaultPokerCard story = (DefaultPokerCard) deck.getHiddenCards()[i];
			xml += String.format("{%s}", ELEMENT_HIDDEN_CARD);
			xml += String.format("{%s}%s{/%s}", ELEMENT_VALUE,
					StringUtils.escapeForXML(story.getStringValue()),
					ELEMENT_VALUE);
			xml += String.format("{%s}%s{/%s}", ELEMENT_IMAGE,
					StringUtils.escapeForXML(story.getImagePath()),
					ELEMENT_IMAGE);
			xml += String.format("{/%s}", ELEMENT_HIDDEN_CARD);
		}
		xml += String.format("{/%s}", ELEMENT_NAME_DECK);

		HashMap<String, String> param = new HashMap<String, String>();
		param.put(ELEMENT_NAME_DECK, xml);
		getMultiChatServiceActions().SendExtensionProtocolMessage(
				ELEMENT_NAME_DECK, param);
	}

	@Override
	public void notifyEstimateSessionStatusChange(IEstimatesList estimate,
			EstimateStatus status) {
		System.out.println(String.format(
				"notifyEstimateSessionStatusChange: %s Story: %s Id: %s",
				status, estimate.getUserStoryId(), estimate.getId()));
		HashMap<String, String> param = new HashMap<String, String>();
		param.put(SESSION_STATUS_STORY_ID, estimate.getUserStoryId());
		param.put(SESSION_STATUS_ESTIMATE_ID, estimate.getId());
		param.put(SESSION_STATUS, status.name());
		getMultiChatServiceActions().SendExtensionProtocolMessage(
				ELEMENT_SESSION_STATUS, param);
	}

	@Override
	public void notifyEstimateAssigned(String storyId, String estimateValue) {
		System.out.println(String.format(
				"notifyEstimateAssigned Story: %s Value: %s", storyId,
				estimateValue));
		HashMap<String, String> param = new HashMap<String, String>();
		param.put(STORY_ID_ESTIMATE_ASSIGNED, storyId);
		param.put(ESTIMATE_ASSIGNED, estimateValue);
		getMultiChatServiceActions().SendExtensionProtocolMessage(
				ELEMENT_ESTIMATE_ASSIGNED, param);
	}

	@Override
	public void notifyAgendaOperation(AgendaOperation remove, String itemIndex) {
		// XXX why do we have this????

	}

	@Override
	public String toString() {
		return "Planning Poker Service";
	}

	@Override
	public void onBackendEvent(IBackendEvent event) {
			super.onBackendEvent(event);

		if (event instanceof MultiChatExtensionProtocolEvent) {
			MultiChatExtensionProtocolEvent mcepe = (MultiChatExtensionProtocolEvent) event;

			if (mcepe.getExtensionName().equals(ELEMENT_ESTIMATE_ASSIGNED)) {
				String storyId = (String) mcepe
						.getExtensionParameter(STORY_ID_ESTIMATE_ASSIGNED);
				String estimateValue = (String) mcepe
						.getExtensionParameter(ESTIMATE_ASSIGNED);

				System.out.println("Received an estimate " + estimateValue
						+ " assigned to story id: " + storyId);

				getModel().getBacklog().assignEstimateToStory(storyId,
						estimateValue);

			} else if (mcepe.getExtensionName().equals(ELEMENT_SESSION_STATUS)) {
				String storyId = (String) mcepe
						.getExtensionParameter(SESSION_STATUS_STORY_ID);
				String estimateId = (String) mcepe
						.getExtensionParameter(SESSION_STATUS_ESTIMATE_ID);
				String sessionStatus = (String) mcepe
						.getExtensionParameter(SESSION_STATUS);

				System.out.println("Received estimate status " + sessionStatus
						+ " for estimate id: " + estimateId + " and story id: "
						+ storyId);
				EstimateStatus status = EstimateStatus.valueOf(sessionStatus);
				if (status != null) {
					if (EstimateStatus.CREATED.equals(status)) {
						getModel().openEstimateSession(
								new EstimateSession(storyId, estimateId));
					} else if (EstimateStatus.CLOSED.equals(status)
							|| EstimateStatus.COMPLETED.equals(status)
							|| EstimateStatus.REPEATED.equals(status)) {
						if (getModel().getEstimateSession().equals(
								new EstimateSession(storyId, estimateId)))
							getModel().getEstimateSession().setStatus(status);
					}
				} else {
					System.err
							.println("Invalid estimate session status received: "
									+ status);
				}
			} else if (mcepe.getExtensionName().equals(ELEMENT_NAME_DECK)) {
				CardDeck newCardDeck = getModel().getCardDeck();
				newCardDeck.dispose();
				newCardDeck = null;

				// XPath
				String xml = (String) mcepe
						.getExtensionParameter(ELEMENT_NAME_DECK);
				xml = replaceTagDelimiters(xml);
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				factory.setNamespaceAware(true); // never forget this!
				DocumentBuilder builder;
				Document doc;
				try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(new InputSource(new StringReader(xml)));
					XPathFactory xfactory = XPathFactory.newInstance();
					XPath xPath = xfactory.newXPath();

					XPathExpression expr = xPath.compile("//"
							+ ELEMENT_CARD_DECK);

					NodeList cards = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);

					newCardDeck = new CardDeck();
					for (int i = 0; i < cards.getLength(); i++) {
						Node n = cards.item(i);
						String value = n.getFirstChild().getTextContent();
						String imagePath = n.getLastChild().getTextContent();
						IPokerCard card = new DefaultPokerCard(value, imagePath);
						newCardDeck.addCard(card);
					}

					expr = xPath.compile("//" + ELEMENT_HIDDEN_CARD);
					NodeList hiddenCards = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					for (int i = 0; i < hiddenCards.getLength(); i++) {
						Node n = cards.item(i);
						String value = n.getFirstChild().getTextContent();
						String imagePath = n.getLastChild().getTextContent();
						IPokerCard card = new DefaultPokerCard(value, imagePath);
						newCardDeck.addHiddenCard(card);
					}
					getModel().setCardDeck(newCardDeck);
					System.out.println("Received parsed deck with "
							+ newCardDeck.getCards().length + " cards");

				} catch (ParserConfigurationException e1) {
					e1.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}
				;

			} else if (mcepe.getExtensionName().equals(ELEMENT_NAME_BACKLOG)) {
				Backlog newBacklog = getModel().getBacklog();
				String xml = (String) mcepe
						.getExtensionParameter(ELEMENT_NAME_BACKLOG);
				xml = replaceTagDelimiters(xml);
				DocumentBuilderFactory factory = DocumentBuilderFactory
						.newInstance();
				factory.setNamespaceAware(true); // never forget this!
				DocumentBuilder builder;
				Document doc;
				try {
					builder = factory.newDocumentBuilder();
					doc = builder.parse(new InputSource(new StringReader(xml)));
					XPathFactory xfactory = XPathFactory.newInstance();
					XPath xPath = xfactory.newXPath();

					XPathExpression expr = xPath.compile("/"
							+ ELEMENT_NAME_BACKLOG + "/"
							+ ELEMENT_CURRENT_STORY);
					Node node = (Node) expr.evaluate(doc, XPathConstants.NODE);
					newBacklog = new Backlog(Integer.parseInt(node
							.getTextContent()));

					expr = xPath.compile("/" + ELEMENT_NAME_BACKLOG + "/"
							+ ELEMENT_STORY_BACKLOG);
					NodeList stories = (NodeList) expr.evaluate(doc,
							XPathConstants.NODESET);
					for (int i = 0; i < stories.getLength(); i++) {
						NodeList storyNodeChildList = stories.item(i)
								.getChildNodes();
						String id = null;
						String milestoneDescription = "";
						String milestoneId = "";
						String milestoneCreationDate = null;
						Date formattedMilestoneCreationDate = null;
						String storyText = null;
						String estimate = null;
						String notes = "";
						for (int j = 0; j < storyNodeChildList.getLength(); j++) {
							Node n = storyNodeChildList.item(j);
							if (n.getNodeName()
									.equals(ELEMENT_STORY_ID_BACKLOG) && n.hasChildNodes()) {								
								id = n.getFirstChild().getTextContent();
							} else if (n.getNodeName().equals(
									ELEMENT_MILESTONE_DESCRIPTION) && n.hasChildNodes()) {
								milestoneDescription = n.getFirstChild().getTextContent();
							} else if (n.getNodeName().equals(
									ELEMENT_MILESTONE_ID) && n.hasChildNodes()) {
								milestoneId = n.getFirstChild().getTextContent();
							} else if (n.getNodeName().equals(
									ELEMENT_MILESTONE_CREATION_DATE) && n.hasChildNodes()) {
								milestoneCreationDate = n.getFirstChild().getTextContent();
								if ((null != milestoneCreationDate))
									formattedMilestoneCreationDate = DateUtils
											.getDateFromString(milestoneCreationDate);
							} else if (n.getNodeName().equals(
									ELEMENT_STORY_TEXT) && n.hasChildNodes()) {
								storyText = n.getFirstChild().getTextContent();
							} else if (n.getNodeName().equals(
									ELEMENT_STORY_NOTES) && n.hasChildNodes()) {
								notes = n.getFirstChild().getTextContent();
							} else if (n.getNodeName().equals(
									ELEMENT_STORY_ESTIMATE) && n.hasChildNodes()) {
								estimate = n.getFirstChild().getTextContent();
							}
						}
						if (id != null && storyText != null && estimate != null) 
							newBacklog.addUserStory(new DefaultUserStory(id,
									milestoneId, milestoneDescription,
									formattedMilestoneCreationDate, storyText,
									notes, estimate));
						
					}
				} catch (ParserConfigurationException e) {
					e.printStackTrace();
				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (XPathExpressionException e) {
					e.printStackTrace();
				}

				getModel().setBacklog(newBacklog);
				System.out.println("Received parsed backlog with "
						+ newBacklog.getUserStories().length
						+ " user stories and the story number "
						+ newBacklog.getCurrentItemIndex() + " selected");

			} else if (mcepe.getExtensionName().equals(ELEMENT_CARD_SELECTION)) {
				String who = (String) mcepe
						.getExtensionParameter(USER_ID_CARD_SELECTION);
				String storyId = (String) mcepe
						.getExtensionParameter(STORY_ID_CARD_SELECTION);
				IPokerCard card = new DefaultPokerCard(
						(String) mcepe
								.getExtensionParameter(CARD_CARD_SELECTION));
				if (getModel().getEstimateSession() != null
						&& !getModel().getEstimateSession().getStatus()
								.equals(EstimateStatus.CLOSED)) {
					if (getModel().getEstimateSession().getUserStoryId()
							.equals(storyId)) {
						getModel().getEstimateSession().addEstimate(
								new Estimate(who, card));
						System.out.println("Received card selection from "
								+ who + " for User Story " + storyId
								+ " with value " + card.getStringValue());
					} else {
						System.err
								.println("Received an estimation not related to the current estimated User Story");
					}
				} else {
					System.err
							.println("Received an estimation that cannot be registered becouse the estimate "
									+ "session doesn't exists ore it is closed. Who: "
									+ who + " Value: " + card.getStringValue());
				}
				// XXX this part might go away
			} else if (mcepe.getExtensionName().equals(
					ELEMENT_SPECIAL_PRIVILEGE)) {
				String who = (String) mcepe
						.getExtensionParameter(USER_ID_SPECIAL_PRIVILEGE);
				IParticipant p = getLocalUserOrParticipant(who);
				String privilegeAction = (String) mcepe
						.getExtensionParameter(ACTION_SPECIAL_PRIVILEGE);
				String specialPrivilege = (String) mcepe
						.getExtensionParameter(SPECIAL_PRIVILEGE);
				if (p == null) {
					System.out.println("Received special privilege "
							+ privilegeAction + " " + specialPrivilege
							+ " for unknown participant id: " + who);
					return;
				}

				System.out.println("Received special privilege "
						+ privilegeAction + " " + specialPrivilege
						+ " for participant id: " + who);
				// The participant will fire an event
				if (privilegeAction.equals(SpecialPrivilegesAction.GRANT)) {
					// let the econference core plugin keep handling the Scribe thing
					// here we only handle the grant/revoke voter thing.
					//p.addSpecialPriviliges(specialPrivilege);

					if (specialPrivilege
							.equals(ParticipantSpecialPrivileges.VOTER))
						getModel().getVoters().addVoter(p.getId());

				} else if (privilegeAction
						.equals(SpecialPrivilegesAction.REVOKE)) {
					//p.removeSpecialPrivileges(specialPrivilege);

					if (specialPrivilege
							.equals(ParticipantSpecialPrivileges.VOTER))
						getModel().getVoters().removeVoter(p.getId());

				} else {
					System.err.println("Received unknown privilege action "
							+ privilegeAction + " for participant id: " + who);
					return;
				}
			} else if (mcepe.getExtensionName().equals(
					ELEMENT_CURRENT_AGENDA_ITEM)) {
				String itemIdString = mcepe
						.getExtensionParameter(CURRENT_AGENDA_ITEM_ID);
				if (!ITalkModel.FREE_TALK_THREAD_ID.equals(itemIdString)) {
					int itemId = Integer.parseInt(itemIdString);
					// Note that we update *** independently *** the agenda item
					// list
					// and talk model (which handles the discussion threads)
					try {
						getModel().getBacklog().setCurrentItemIndex(itemId);
						String newSubject = ((IUserStory) getModel()
								.getBacklog().getUserStory(itemId))
								.getTextForMultiChatSubject();
						getModel().setSubject(newSubject, "[System]");
					} catch (IllegalArgumentException e) {
						// the item list will protest if we set an out-of-range
						// index:
						// here it is just safe ignoring its cries ...
						e.printStackTrace();
					}
				}
				getTalkModel().setCurrentThread(itemIdString);
			}
		}
	}

	private String replaceTagDelimiters(String xml) {
		xml = xml.replace("{", "<");
		xml = xml.replace("}", ">");
		return xml;
	}
}
