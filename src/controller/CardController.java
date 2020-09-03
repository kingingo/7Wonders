/**
 * The Card-Controller controls the ingame cards.
 */

package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.board.WonderBoard;
import model.card.Card;
import model.card.CardType;
import model.card.Effect;
import model.card.EffectType;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;

public class CardController {
	
	Map<String, int[]> countCards = new HashMap<>();
	
	private SevenWondersController swController;

	public CardController(SevenWondersController swController) {
		this.swController=swController;
		
		
	}
	
	/**
	 * Loading all ingame cards with their parameters and frequency
	 * Matrix-scheme: amount of players when another card needs to be created
	 */
	public void loadCards() {
		countCards.put("academy", new int[]{3,7,0});
		countCards.put("altar", new int[]{3,5,0});
		countCards.put("apothecary", new int[]{3,5,0});
		countCards.put("aqueduct", new int[]{3,7,0});
		countCards.put("archeryrange", new int[]{3,6,0});
		countCards.put("arena", new int[]{3,5,7});
		countCards.put("arsenal", new int[]{3,4,7});
		countCards.put("barracks", new int[]{3,5,0});
		countCards.put("baths", new int[]{3,7,0});
		countCards.put("bazar", new int[]{4,7,0});
		countCards.put("brickyard", new int[]{3,4,0});
		countCards.put("buildersguild", new int[]{0,0,0});
		countCards.put("caravansery", new int[]{3,5,6});
		countCards.put("chamberofcommerce", new int[]{4,6,0});
		countCards.put("circus", new int[]{4,6,6});
		countCards.put("claypit", new int[]{3,0,0});
		countCards.put("claypool", new int[]{3,5,0});
		countCards.put("courthouse", new int[]{3,5,0});
		countCards.put("craftsmensguild", new int[]{0,0,0});
		countCards.put("dispensary", new int[]{3,4,0});
		countCards.put("easttradingpost", new int[]{3,7,0});
		countCards.put("excavation", new int[]{4,0,0});
		countCards.put("forestcave", new int[]{5,0,0});
		countCards.put("fortifications", new int[]{3,7,0});
		countCards.put("forum", new int[]{3,6,7});
		countCards.put("foundry", new int[]{3,4,0});
		countCards.put("gardens", new int[]{3,4,0});
		countCards.put("glassworks1", new int[]{3,6,0});
		countCards.put("glassworks2", new int[]{3,5,0});
		countCards.put("guardtower", new int[]{3,4,0});
		countCards.put("haven", new int[]{3,4,0});
		countCards.put("laboratory", new int[]{3,5,0});
		countCards.put("library", new int[]{3,6,0});
		countCards.put("lighthouse", new int[]{3,6,0});
		countCards.put("lodge", new int[]{3,6,0});
		countCards.put("loom1", new int[]{3,6,0});
		countCards.put("loom2", new int[]{3,5,0});
		countCards.put("lumberyard", new int[]{3,4,0});
		countCards.put("magistratesguild", new int[]{0,0,0});
		countCards.put("marketplace", new int[]{3,6,0});
		countCards.put("mine", new int[]{6,0,0});
		countCards.put("observatory", new int[]{3,7,0});
		countCards.put("orevein", new int[]{3,4,0});
		countCards.put("palace", new int[]{3,7,0});
		countCards.put("pantheon", new int[]{3,6,0});
		countCards.put("pawnshop", new int[]{4,7,0});
		countCards.put("philosophersguild", new int[]{0,0,0});
		countCards.put("press1", new int[]{3,6,0});
		countCards.put("press2", new int[]{3,5,0});
		countCards.put("quarry", new int[]{3,4,0});
		countCards.put("sawmill", new int[]{3,4,0});
		countCards.put("school", new int[]{3,7,0});
		countCards.put("scientistsguild", new int[]{0,0,0});
		countCards.put("scriptorium", new int[]{3,4,0});
		countCards.put("senate", new int[]{3,5,0});
		countCards.put("shipownersguild", new int[]{0,0,0});
		countCards.put("siegeworkshop", new int[]{3,5,0});
		countCards.put("spiesguild", new int[]{0,0,0});
		countCards.put("stables", new int[]{3,5,0});
		countCards.put("statue", new int[]{3,7,0});
		countCards.put("stockade", new int[]{3,7,0});
		countCards.put("stonepit", new int[]{3,5,0});
		countCards.put("strategistsguild", new int[]{0,0,0});
		countCards.put("study", new int[]{3,5,0});
		countCards.put("tavern", new int[]{4,5,7});
		countCards.put("temple", new int[]{3,6,0});
		countCards.put("theater", new int[]{3,6,0});
		countCards.put("timberyard", new int[]{3,0,0});
		countCards.put("townhall", new int[]{3,5,6});
		countCards.put("tradersguild", new int[]{0,0,0});
		countCards.put("trainingground", new int[]{4,6,7});
		countCards.put("treefarm", new int[]{6,0,0});
		countCards.put("university", new int[]{3,4,0});
		countCards.put("vineyard", new int[]{3,6,0});
		countCards.put("walls", new int[]{3,7,0});
		countCards.put("westtradingpost", new int[]{3,7,0});
		countCards.put("workersguild", new int[]{0,0,0});
		countCards.put("workshop", new int[]{3,7,0});

	}

	public Card[] generateCardStack() {
		Card[] cards = new Card[78];
		
		cards[0] = new Card(ResourceType.COMPASS,3,"Akademie", "acadamy", CardType.GREEN, null, addRArray(new Resource(3,ResourceType.STONE),new Resource(1,ResourceType.GLASS)),new String[]{"school"},null);
		cards[1] = new Card(1,"Altar", "altar", CardType.BLUE, null, null, null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {p.addVictoryPoints(2);}))
		);
		cards[2] = new Card(ResourceType.COMPASS,1,"Apotheke", "apothecary", CardType.GREEN, null, addRArray(new Resource(3,ResourceType.CLOTH)),new String[]{"school"},null);
		
		cards[3] = new Card(2,"Aqu�dukt", "aqueduct", CardType.BLUE, null, addRArray(new Resource(3,ResourceType.STONE)), new String[]{"baths"},
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {p.addVictoryPoints(5);}))
		);
		cards[3] = new Card(2,"Schie�platz", "archeryrange", CardType.RED, addRArray(new Resource(2,ResourceType.MILITARY)), addRArray(new Resource(2,ResourceType.WOOD),new Resource(1,ResourceType.ORE)), new String[]{"workshop"}, null);
		cards[4] = new Card(3,"Arena", "arena", CardType.YELLOW, null, addRArray(new Resource(2,ResourceType.STONE),new Resource(1,ResourceType.ORE)),new String[]{"dispensary"},
				addEArray(
						new Effect(EffectType.WHEN_PLAYED, p -> {p.addCoins(3*(p.getBoard().nextSlot() == -1 ? 3 : p.getBoard().nextSlot()));}),
						new Effect(EffectType.AT_MATCH_END, p -> {p.addVictoryPoints(p.getBoard().nextSlot() == -1 ? 3 : p.getBoard().nextSlot());})
		));
		cards[5] = new Card(3,"Waffenlager", "arsenal", CardType.RED, addRArray(new Resource(3,ResourceType.MILITARY)), addRArray(new Resource(2,ResourceType.WOOD),new Resource(1,ResourceType.ORE),new Resource(1,ResourceType.CLOTH)), new String[]{"workshop"}, null);
		cards[6] = new Card(1,"Kaserne", "barracks", CardType.RED, addRArray(new Resource(1,ResourceType.MILITARY)), addRArray(new Resource(1,ResourceType.ORE)), new String[]{"workshop"}, null);
		cards[7] = new Card(1,"B�der", "baths", CardType.BLUE, null, addRArray(new Resource(1,ResourceType.STONE)), null,
				addEArray(new Effect(EffectType.WHEN_PLAYED, p -> {p.addVictoryPoints(3);}))
		);
		cards[8] = new Card(2,"Basar", "bazar", CardType.YELLOW, null, null, null,
				addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					ArrayList<Card> brown1 = swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources();
					ArrayList<Card> brown2 = swController.getPlayerController().getRightNeighbour(p).getBoard().getResources();
					ArrayList<Card> brown3 = p.getBoard().getResources();
					int count = 0;
					for(Card el : brown1) 
						if(el.getType() == CardType.BROWN) count++;
					for(Card el : brown2) 
						if(el.getType() == CardType.BROWN) count++;
					for(Card el : brown3) 
						if(el.getType() == CardType.BROWN) count++;
					p.addVictoryPoints(count);
				})
		));
		cards[9] = new Card(2,"Ziegelbrennerei", "brickyard", CardType.BROWN, addRArray(new Resource(2, ResourceType.BRICK)), addRArray(new Resource(1,ResourceType.COINS)),null,null);
		cards[10] = new Card(3,"Gilde der Baumeister", "buildersguild", CardType.PURPLE, null, addRArray(new Resource(2, ResourceType.BRICK),new Resource(2, ResourceType.STONE),new Resource(1, ResourceType.GLASS)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					int purple1 = swController.getPlayerController().getLeftNeighbour(p).getBoard().nextSlot();
					int purple2 = swController.getPlayerController().getRightNeighbour(p).getBoard().nextSlot();
					int purple3 = p.getBoard().nextSlot();
					purple1 = purple1 == -1 ? 3 : purple1;
					purple2 = purple2 == -1 ? 3 : purple2;
					purple3 = purple3 == -1 ? 3 : purple3;
					p.addVictoryPoints(purple1+purple2+purple3);
				})
		));
		cards[11] = new Card(2,"Karawanserei", "caravansery", CardType.YELLOW, null, addRArray(new Resource(2, ResourceType.WOOD)), null,
				addEArray(new Effect(EffectType.AT_MATCH_END, p -> {
					ArrayList<Card> brown1 = swController.getPlayerController().getLeftNeighbour(p).getBoard().getResources();
					ArrayList<Card> brown2 = swController.getPlayerController().getRightNeighbour(p).getBoard().getResources();
					ArrayList<Card> brown3 = p.getBoard().getResources();
					int count = 0;
					for(Card el : brown1) 
						if(el.getType() == CardType.BROWN) count++;
					for(Card el : brown2) 
						if(el.getType() == CardType.BROWN) count++;
					for(Card el : brown3) 
						if(el.getType() == CardType.BROWN) count++;
					p.addVictoryPoints(count);
				})
		));
		cards[13] = new Card(3,"Zirkus", "circus", CardType.RED, addRArray(new Resource(3,ResourceType.MILITARY)), addRArray(new Resource(3,ResourceType.STONE),new Resource(1,ResourceType.ORE)), new String[]{"trainingground"}, null);
		cards[22] = new Card(3,"Verteidigungsanlage", "fortifications", CardType.RED, addRArray(new Resource(3,ResourceType.MILITARY)), addRArray(new Resource(3,ResourceType.ORE),new Resource(1,ResourceType.STONE)), new String[]{"walls"}, null);
		cards[26] = new Card(1,"Glash�tte", "glassworks1", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)), null, null, null);
		cards[27] = new Card(2,"Glash�tte", "glassworks2", CardType.GRAY, addRArray(new Resource(1, ResourceType.GLASS)), null, null, null);
		cards[28] = new Card(1,"Wachturm", "guardtower", CardType.RED, addRArray(new Resource(1,ResourceType.MILITARY)), addRArray(new Resource(1,ResourceType.BRICK)), null, null);
		cards[34] = new Card(1,"Webstuhl", "loom1", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null, null, null);
		cards[35] = new Card(2,"Webstuhl", "loom2", CardType.GRAY, addRArray(new Resource(1, ResourceType.CLOTH)), null, null, null);
		cards[46] = new Card(1,"Presse", "press1", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null, null);
		cards[47] = new Card(2,"Presse", "press2", CardType.GRAY, addRArray(new Resource(1, ResourceType.PAPYRUS)), null, null, null);	
		cards[55] = new Card(3,"Belagerungsmaschinen", "siegeworkshop", CardType.RED, addRArray(new Resource(3,ResourceType.MILITARY)), addRArray(new Resource(3,ResourceType.BRICK),new Resource(1,ResourceType.WOOD)), new String[]{"laboratory"}, null);
		cards[57] = new Card(2,"St�lle", "stables", CardType.RED, addRArray(new Resource(2,ResourceType.MILITARY)), addRArray(new Resource(1,ResourceType.ORE),new Resource(1,ResourceType.BRICK),new Resource(1,ResourceType.WOOD)), new String[]{"apothecary"}, null);
		cards[59] = new Card(3,"Befestigungsanlage", "stockade", CardType.RED, addRArray(new Resource(1,ResourceType.MILITARY)), addRArray(new Resource(1,ResourceType.WOOD)), null, null);
		cards[69] = new Card(2,"Trainingsgel�nde", "trainingground", CardType.RED, addRArray(new Resource(2,ResourceType.MILITARY)), addRArray(new Resource(2,ResourceType.ORE),new Resource(1,ResourceType.WOOD)), null, null);
		cards[73] = new Card(2,"Mauern", "walls", CardType.RED, addRArray(new Resource(2,ResourceType.MILITARY)), addRArray(new Resource(3,ResourceType.STONE)), null, null);
		return cards;
	}
	
	public boolean hasCard(Player player, String cardname) {
		WonderBoard board = player.getBoard();
		
		for(Card card : board.getCivil())
			if(card.getName().equalsIgnoreCase(cardname))return true;
		for(Card card : board.getGuilds())
			if(card.getName().equalsIgnoreCase(cardname))return true;
		for(Card card : board.getMilitary())
			if(card.getName().equalsIgnoreCase(cardname))return true;
		for(Card card : board.getResearch())
			if(card.getName().equalsIgnoreCase(cardname))return true;
		
		return false;
	}
	private ArrayList<Resource> addRArray(Resource... ressource) {
		ArrayList<Resource> array = new ArrayList<Resource>();
		for(Resource res : ressource) {
			array.add(res);
		}
		return array;
	}
	
	private ArrayList<Effect> addEArray(Effect... effect) {
		ArrayList<Effect> array = new ArrayList<Effect>();
		for(Effect eff : array) {
			array.add(eff);
		}
		return array;
	}

	public Card[] loadCardStack(String filepath) {
		return null;
	}

	/**
	 *  
	 */
	public void sellCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void placeCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public void setSlotCard(Card card, Player player) {

	}

	/**
	 *  
	 */
	public Card getCard(String cardname) {
		return null;
	}

}