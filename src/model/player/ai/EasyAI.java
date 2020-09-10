package model.player.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import application.Main;
import controller.PlayerController;
import controller.utils.BuildCapability;
import controller.utils.TradeOption;
import model.GameState;
import model.board.OlympiaBoard;
import model.board.WonderBoard;
import model.card.Card;
import model.card.Effect;
import model.card.Resource;
import model.card.ResourceType;
import model.player.Player;
import model.player.ai.Move.Action;

public class EasyAI extends ArtInt{

	
	public EasyAI(String name, WonderBoard board) {
		super(name, board);
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * calculates next move
	 * Priority age1: 	get resources (max 1 from every resource, max 2 cards in total) prio wonderboard
	 * 					adasfasf
	 * 			age2: 	take card with highest amount of victory points
	 * 			age3: 	take card with highest amount of victory points based on cards on board
	 */
	public void calculateNextMove() {
		ArrayList<Move> generate = generateMoves();
		
		for(Card c : getHand())System.out.println("HANDCARD ->    "+c+"   "+c.getName());
		
		
		Move best = generate.get(0);
		double rating = doMove1(best,false);
		for(int i = 1; i < generate.size(); i++) {
			double value = doMove1(generate.get(i),false);
			
			if(value > rating) {
				rating = value;
				best = generate.get(i);
			}
		}

		doMove1(best,true);
		System.out.println("BEST["+rating+"]: "+best.getCard().getName()+" "+best.getAction().name()+" "+best.getTradeOption());
		this.next = best;
	}
	

	public TradeOption getBestTradeOption(Card card) {
		ArrayList<TradeOption> trade = Main.getSWController().getPlayerController().getTradeOptions(this, card.getRequired());

		if (trade.isEmpty()) {
			return null;
		} else {
			TradeOption best = trade.get(0);
			for(int i = 1; i < trade.size(); i++) {
				if( (best.getLeftCost()+best.getRightCost()) < (trade.get(i).getLeftCost()+trade.get(i).getRightCost()) )
					best = trade.get(i);
			}
			return best;
		}
	}
	
	/**
	 * plays give Move for AI
	 * 
	 * @param move Move to play for the AI
	 * @return v Victory points
	 */
	public double doMove1(Move move, boolean debug) {
		GameState state = Main.getSWController().getGame().getCurrentGameState();
		PlayerController pcon = Main.getSWController().getPlayerController();
		Card card = move.getCard();
		BuildCapability capa;
		int age = state.getAge();
		boolean twoPlayer = state.isTwoPlayers();
		double rating = 0;
		int coins = this.getCoins();

		switch (move.getAction()) {
		case OLYMPIA: break;
		case BUILD:
			capa = pcon.canBuild(this, card);
			switch (capa) {
			case FREE:
				rating += 4;
			case TRADE:
				if(capa == BuildCapability.TRADE) {
					if(card.getRequired() == null) {
						if(debug)System.out.println("CAPA:"+capa.name()+" card.getRequired() == NULL ");
						break;
					}
					
					
					TradeOption best = getBestTradeOption(card);
					
					if(best==null) {
						if(debug)System.out.println("CAPA:"+capa.name()+" TradeOptions-Size: EMPTY SOLLTE NICHT PASSIEREN!!!!!!!!");
						return Double.NEGATIVE_INFINITY;
					} else {
						move.setTradeOption(best);
						int price = best.getLeftCost()+best.getRightCost();
						
						double percentage = price/coins;
						
						if(percentage < 0.3) {
							rating -= 0.5;
							if(debug)System.out.println("1.1) RATING add -0.5");
						}else if(percentage < 0.6) {
							rating -= 1;
							if(debug)System.out.println("1.2) RATING add -1");
						}
					}
				}
			case OWN_RESOURCE:
				switch (card.getType()) {
				// CIVIL
				case BLUE:
					rating += card.getvPoints();
					if(debug)System.out.println("2.1) RATING add "+card.getvPoints());
					break;
				// RESOURCE
				case BROWN:
				case GRAY:
					ArrayList<ResourceType> list = new ArrayList<ResourceType>(Arrays.asList(ResourceType.CLOTH, 
							ResourceType.GLASS, 
							ResourceType.PAPYRUS, 
							ResourceType.WOOD, 
							ResourceType.BRICK, 
							ResourceType.STONE, 
							ResourceType.ORE));
					
					Player left = pcon.getNeighbour(state, true, this);
					for(Card rs_card : left.getBoard().getResources()) {
						for(Resource produce : rs_card.getProducing()) {
							if(list.contains(produce.getType()))list.remove(produce.getType());
						}
					}
					
					if(!twoPlayer) {
						Player right = pcon.getNeighbour(state, true, this);
						for(Card rs_card : right.getBoard().getResources()) {
							for(Resource produce : rs_card.getProducing()) {
								if(list.contains(produce.getType()))list.remove(produce.getType());
							}
						}
					}
					
					ArrayList<Card> own_producing = new ArrayList<Card>();
					getBoard().getResources().forEach( value -> { own_producing.add(value); } );
					getBoard().getTrade().forEach( value -> { own_producing.add(value); } );
					
					for(Card rs_card : own_producing) {
						for(Resource produce : rs_card.getProducing()) {
							if(list.contains(produce.getType()))list.remove(produce.getType());
						}
					}
					
					WonderBoard board = getBoard();
					for(int slot = 0; slot < 3; slot++) {
						if(!board.isFilled(slot)) {
							Resource rs = board.getSlotResquirement(slot);
							
							boolean add = false;
							for(Resource r : card.getProducing()) {
								if(r.getType() == rs.getType()) {
									add=true;
									break;
								}
							}
							
							if(add && list.contains(rs.getType())) {
								rating += 2.6;
								if(debug)System.out.println("3.1) RATING add 2.6");
							}
						}
					}
					
					if(list.size() > 2)
						rating += (list.size() > 5 ? 3 : 1.5);
					if(debug)System.out.println("3.2) RATING add "+(list.size() > 5 ? 3 : 1.5));
					break;
				// SCIENCE
				case GREEN:
					HashMap<String, Integer> science = new HashMap<String, Integer>();
					for(Card scard : getBoard().getResearch()) {
						if(!science.containsKey(scard.getInternalName()))
							science.put(scard.getInternalName(), 1);
						else {
							int amount = science.get(scard.getInternalName());
							science.remove(scard.getInternalName());
							science.put(scard.getInternalName(), amount+1);
						}
							
					}
					
					if(science.containsKey(card.getInternalName())) {
						int amount = science.get(card.getInternalName());
						
						if(amount >= 2) {
							rating += 4;
							if(debug)System.out.println("4.1) RATING add 4");
						}else{
							rating += 1;
							if(debug)System.out.println("4.2) RATING add 4");
						}
					}
					//Age = 1 && 3 Clockwise
					ArrayList<Card> nextHand = (age==1 || age == 3 ? pcon.getNeighbour(state, false, this).getHand() : pcon.getNeighbour(state, true, this).getHand());
					
					for(Card scard : nextHand) {
						if(scard.getInternalName().equalsIgnoreCase(card.getInternalName())) {
							rating += 2.5;
							if(debug)System.out.println("4.3) RATING add 2.5");
							break;
						}
					}
					
					break;
				// MILITARY
				case RED:
					int military = pcon.getMilitaryPoints(this);
					Player leftN = pcon.getLeftNeighbour(this);
					int left_mili = pcon.getMilitaryPoints(leftN);
					int new_military = military + card.getProducing().get(0).getQuantity();
					
					if(!twoPlayer) {
						Player rightN = pcon.getRightNeighbour(this);
						int right_mili = pcon.getMilitaryPoints(rightN);
						
						if(right_mili < new_military && (new_military-right_mili) < 3) {
							rating += 3;
							if(debug)System.out.println("5.1) RATING add 3");
						}else {
							rating += 0.5;
							if(debug)System.out.println("5.2) RATING add 0.5");
						}
					}
					
					if(left_mili < new_military && (new_military-left_mili) < 3) {
						rating += 3;
						if(debug)System.out.println("5.3) RATING add 3");
					}else {
						rating += 0.5;
						if(debug)System.out.println("5.4) RATING add 0.5");
					}
					break;
					// GUILD
				case PURPLE:
					// TRADING
				case YELLOW:
					GameState copy = Main.getSWController().getGame().getCurrentGameState().deepClone();
					ArrayList<Effect> effects = card.getEffects();
					if (effects != null) {
						for (Effect effect : effects) {
							switch (effect.getType()) {
							case AT_MATCH_END:
							case WHEN_PLAYED:
								Player player = copy.getPlayer();
								if(debug)System.out.println("RIGHT PLAYER "+player.getName()+" == "+getName());

								// Do Effect!
								effect.run(player, copy, copy.isTwoPlayers());

								// Check changes
								rating += player.getVictoryPoints() - getVictoryPoints();
								rating += (player.getCoins() - getCoins())/3;
								if(debug)System.out.println("6.1) RATING add "+(player.getVictoryPoints() - getVictoryPoints()));
								if(debug)System.out.println("6.2) RATING add "+((player.getCoins() - getCoins())/3));
								break;
							}
						}
					}

					break;
				}
				break;
			case NONE:
				return Double.NEGATIVE_INFINITY;
			}
			break;
		case PLACE_SLOT:
			WonderBoard board = getBoard();
			int next = board.nextSlot();

			if (next != -1) {
				Resource requirement = board.getSlotResquirement(next);
				capa = pcon.hasResources(this, new ArrayList<Resource>(Arrays.asList(requirement)));

				switch (capa) {
				case FREE:
					rating += 5;
					if(debug)System.out.println("7.1) RATING add 5");
					break;
				case OWN_RESOURCE:
					rating += 3;
					if(debug)System.out.println("8.1) RATING add 3");
					break;
				case TRADE:
					if(capa == BuildCapability.TRADE) {
						if(card.getRequired() == null) {
							if(debug)System.out.println("1CAPA:"+capa.name()+" card.getRequired() == NULL ");
							break;
						}
						
						TradeOption best = getBestTradeOption(card);
						
						if(best==null) {
							if(debug)System.out.println("1CAPA:"+capa.name()+" TradeOptions-Size: EMPTY SOLLTE NICHT PASSIEREN!!!!!!!!");
							return Double.NEGATIVE_INFINITY;
						} else {
							move.setTradeOption(best);
							int price = best.getLeftCost()+best.getRightCost();
							
							double percentage = price/coins;
							
							if(percentage < 0.2) {
								if(debug)System.out.println("9.1) RATING add -1");
								rating -= 1;
							}else if(percentage < 0.4) {
								rating -= 2;
								if(debug)System.out.println("8.2) RATING add -2");
							}
						}
					}
					break;
				case NONE:
					return Integer.MIN_VALUE;
				}
			}
			break;
		case SELL:
			if(coins < 4) {
				rating += 2.5;
				if(debug)System.out.println("9.1) RATING add 2.5");
			}else if(coins < 10) {
				rating += 1.5;
				if(debug)System.out.println("9.2) RATING add 1.5");
			}
			break;
		}

		return rating;
	}
	
	/**
	 * generates list with all possible Moves for the AI for one round
	 * @return list 	contains all possible Moves for the AI for one round
	 */
	public ArrayList<Move> generateMoves() {
		ArrayList<Move> list = new ArrayList<Move>();
		
		for(int i = 0; i < this.getHand().size(); i++) {
			for(Action action : Move.Action.values()) {
				if(action == Action.OLYMPIA) 
					if(!(getBoard() instanceof OlympiaBoard) || !getBoard().isFilled(1))continue;
				list.add(new Move(this.getHand().get(i), action));
			}
		}
		return list;
	}
}
