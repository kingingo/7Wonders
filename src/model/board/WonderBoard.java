package model.board;

import java.util.ArrayList;

import model.card.Card;
import model.card.Resource;
import model.player.Player;

public abstract class WonderBoard {

	private boolean[] filled;

	private ArrayList<Card> resources, military, trade, guilds, civil, research;

	private Resource resource;

	protected Resource[] slotRequirements;
	
	protected Player player;
	
	public WonderBoard() {
		resources = new ArrayList<>();
		military = new ArrayList<>();
		trade = new ArrayList<>();
		guilds = new ArrayList<>();
		civil = new ArrayList<>();
		research = new ArrayList<>();
		filled = new boolean[3];
	}

	public void slot1() {
		player.addVictoryPoints(3);
	}

	public abstract void slot2();

	public void slot3() {
		player.addVictoryPoints(7);
	}
	
	public boolean isFilled(int slot) {
		return filled[slot];
	}
	
	public Resource getSlotResquirement(int slot) {
		return slotRequirements[slot];
	}
	
	/**
	 * getter to find the next unfinished build stage
	 * @return the first unfinished build stage of the wonder or -1 if it is finished
	 */
	public int nextSlot() {
		return filled[0] ? (filled[1] ? (filled[2] ? -1 : 2) : 1) : 0;
	}
	
	/**
	 * getter for the resources needed for the next wonder build stage
	 * @return null if the wonder is finished or the resource requirement of the first unfinished build stage
	 */
	public Resource getNextSlotRequirement() {
		int next = nextSlot();
		return next == -1 ? null : slotRequirements[next];
	}
	
	public Resource getResource() {
		return resource;
	}
	
	public ArrayList<Card> getResources() {
		return resources;
	}
	
	public ArrayList<Card> getMilitary() {
		return military;
	}
	
	public ArrayList<Card> getTrade() {
		return trade;
	}
	
	public ArrayList<Card> getGuilds() {
		return guilds;
	}
	
	public ArrayList<Card> getCivil() {
		return civil;
	}
	
	public ArrayList<Card> getResearch() {
		return research;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}

}