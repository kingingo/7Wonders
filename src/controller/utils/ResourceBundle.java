package controller.utils;

import java.util.ArrayList;

import application.Main;
import model.card.Resource;
import model.player.Player;

public class ResourceBundle {
	/** counters for resource quantities */
	private int wood, stone, ore, cloth, glass, brick, papyrus;

	/**
	 * creates a bundle and adds the given resource using {@link #add(Resource)}
	 * 
	 * @param resource a resource to be added as an initial value
	 */
	public ResourceBundle(Resource resource) {
		add(resource);
	}

	/** create a bundle and add all given resources using {@link #add(Resource)} */
	public ResourceBundle(ArrayList<Resource> resources) {
		resources.forEach(resource -> add(resource));
	}

	/** default constructor, all values are 0 */
	public ResourceBundle() {
	}

	/**
	 * hidden constructor to allow adding two bundles
	 * 
	 * @param wood    sets {@link #wood}
	 * @param stone   sets {@link #stone}
	 * @param ore     sets {@link #ore}
	 * @param cloth   sets {@link #cloth}
	 * @param glass   sets {@link #glass}
	 * @param brick   sets {@link #brick}
	 * @param papyrus sets {@link #papyrus}
	 */
	private ResourceBundle(int wood, int stone, int ore, int cloth, int glass, int brick, int papyrus) {
		this.wood = wood;
		this.stone = stone;
		this.ore = ore;
		this.cloth = cloth;
		this.glass = glass;
		this.brick = brick;
		this.papyrus = papyrus;
	}

	/**
	 * calculates the cost of the resources represented by this object for a specified player
	 * 
	 * @param player          player
	 * @param toLeftNeighbour true if these resources are bought from the player's left neighbour
	 * @return cost of these resources
	 */
	public int getCostForPlayer(Player player, boolean toLeftNeighbour) {
		int cost = brick + stone + wood + ore;
		if (!(toLeftNeighbour && Main.getSWController().getCardController().hasCard(player, "westtradingpost")) && !(!toLeftNeighbour && Main.getSWController().getCardController().hasCard(player, "easttradingpost")))
			cost *= 2;

		int cost2 = glass + cloth + papyrus;
		if (!Main.getSWController().getCardController().hasCard(player, "marketplace"))
			cost2 *= 2;

		return cost + cost2;
	}

	/**
	 * adds the given resource by increasing the associated integer value by it's quantity
	 * 
	 * @param resource a resource
	 */
	public void add(Resource resource) {
		switch (resource.getType()) {
		case WOOD:
			wood += resource.getQuantity();
			break;
		case STONE:
			stone += resource.getQuantity();
			break;
		case ORE:
			ore += resource.getQuantity();
			break;
		case CLOTH:
			cloth += resource.getQuantity();
			break;
		case GLASS:
			glass += resource.getQuantity();
			break;
		case BRICK:
			brick += resource.getQuantity();
			break;
		case PAPYRUS:
			papyrus += resource.getQuantity();
			break;
		default:
			break;
		}
	}

	/**
	 * add a resource to this one and get the result
	 * 
	 * @param bundle a resource bundle
	 * @return a new resource bundle representing the combined resource requirements
	 */
	public ResourceBundle add(ResourceBundle bundle) {
		return new ResourceBundle(wood + bundle.wood, stone + bundle.stone, ore + bundle.ore, cloth + bundle.cloth, glass + bundle.glass, brick + bundle.brick, papyrus + bundle.papyrus);
	}

	/**
	 * get missing amount of resources relativ to the given bundle
	 * 
	 * @param bundle a resource bundle
	 * @return a new bundle that represents the atomic differences of all resource types: (bundle - this). If this bundle has more or the same amount of a resource than the given
	 *         bundle, the asociated value of the returned bundle is 0.
	 */
	public ResourceBundle getMissing(ResourceBundle bundle) {
		return new ResourceBundle(Math.max(bundle.wood - wood, 0), Math.max(bundle.stone - stone, 0), Math.max(bundle.ore - ore, 0), Math.max(bundle.cloth - cloth, 0),
				Math.max(bundle.glass - glass, 0), Math.max(bundle.brick - brick, 0), Math.max(bundle.papyrus - papyrus, 0));
	}

	/**
	 * compare two resource bundles
	 * 
	 * @param bundle another bundle
	 * @return true if and only if all atomic values of this bundle are greater or equal than the same value of bundle
	 */
	public boolean greaterOrEqualThan(ResourceBundle bundle) {
		return wood >= bundle.wood && stone >= bundle.stone && ore >= bundle.ore && cloth >= bundle.cloth && glass >= bundle.glass && brick >= bundle.brick && papyrus >= bundle.papyrus;
	}

	/**
	 * checks if all quantities are the same
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			ResourceBundle bundle = (ResourceBundle) obj;
			return wood == bundle.wood && stone == bundle.stone && ore == bundle.ore && cloth == bundle.cloth && glass == bundle.glass && brick == bundle.brick && papyrus == bundle.papyrus;
		} catch (Exception e) {
			return false;
		}
	}

}