package model.player;

import java.io.Serializable;
import java.util.ArrayList;

import model.board.WonderBoard;
import model.card.Card;

public class Player implements Serializable {

	private static final long serialVersionUID = 8987094360643416900L;
	/** name */
	private String name;
	/** coins */
	private int coins = 0;
	/** victory points */
	private int victoryPoints = 0;
	/** lose points */
	private int losePoints = 0;
	/** conflict winning points */
	private int conflictPoints = 0;
	/** true if the player has finished the mausoleum stage 2 in the current round */
	private boolean mausoleum = false;
	/** the cards currently at the players "hand" */
	private ArrayList<Card> hand;
	/** the chosen card */
	private Card chosenCard;
	/** the associated wonder bord */
	private WonderBoard board;

	/**
	 * create new player and assign to a wonder board
	 * 
	 * @param name  player's name
	 * @param board player's wonder
	 */
	public Player(String name, WonderBoard board) {
		this.name = name;
		this.board = board;
		this.hand = new ArrayList<Card>();
	}

	/**
	 * setter for {@link #choosenCard}
	 * 
	 * @param chosen card
	 */
	public void setChooseCard(Card card) {
		this.chosenCard = card;
	}


	/**
	 * getter for {@link #chosenCard}
	 * 
	 * @return chosen card
	 */
	public Card getChosenCard() {
		return this.chosenCard;
	}

	/**
	 * getter for {@link #hand}
	 * 
	 * @return hand
	 */
	public ArrayList<Card> getHand() {
		return this.hand;
	}

	/**
	 * adds coins to {@link #coins}
	 * 
	 * @param coins coins
	 */
	public void addCoins(int coins) {
		this.coins += coins;
	}

	/**
	 * adds victory points to {@link #victoryPoints}
	 * 
	 * @param points victory points
	 */
	public void addVictoryPoints(int points) {
		this.victoryPoints += points;
	}

	/**
	 * adds one lose point to {@link #losePoints}
	 */
	public void addLosePoint() {
		this.losePoints++;
	}

	/**
	 * adds conflict points to {@link #conflictPoints}
	 * 
	 * @param points conflict points
	 */
	public void addConflictPoints(int points) {
		this.conflictPoints += points;
	}

	/**
	 * getter for {@link #name}
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getter for {@link #coins}
	 * 
	 * @return coins
	 */
	public int getCoins() {
		return this.coins;
	}

	/**
	 * getter for {@link #victoryPoints}
	 * 
	 * @return victory points
	 */
	public int getVictoryPoints() {
		return this.victoryPoints;
	}

	/**
	 * getter for {@link #losePoints}
	 * 
	 * @return lose points
	 */
	public int getLosePoints() {
		return this.losePoints;
	}

	/**
	 * getter for {@link #conflictPoints}
	 * 
	 * @return conflict points
	 */
	public int getConflictPoints() {
		return this.conflictPoints;
	}

	/**
	 * getter for {@link #mausoleum}
	 * 
	 * @return true if the player has finished mausoleum stage 2 in the current round
	 */
	public boolean isMausoleum() {
		return this.mausoleum;
	}

	/**
	 * getter for {@link #board}
	 * 
	 * @return board
	 */
	public WonderBoard getBoard() {
		return this.board;
	}

	/**
	 * setter for {@link #mausoleum}
	 * 
	 * @param mausoleum true if the player has finished mausoleum stage 2 in the current round
	 */
	public void setMausoleum(boolean mausoleum) {
		this.mausoleum = mausoleum;
	}

	/**
	 * setter for {@link #hand}
	 * 
	 * @param hand hand cards
	 */
	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
}
