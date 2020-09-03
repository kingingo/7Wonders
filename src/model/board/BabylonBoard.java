package model.board;

import model.card.Resource;
import model.card.ResourceType;

public class BabylonBoard extends WonderBoard {
	private static ResourceType[] types = new ResourceType[]{ResourceType.TABLET, ResourceType.COMPASS, ResourceType.GEAR};

	public BabylonBoard() {
		super();
		slotRequirements = new Resource[] {new Resource(2, ResourceType.BRICK), new Resource(3, ResourceType.WOOD), new Resource(4, ResourceType.BRICK)};
	}
	
	@Override
	public void slot2() {
		
		
	}

}
