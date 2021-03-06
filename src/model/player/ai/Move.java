package model.player.ai;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import controller.utils.TradeOption;
import model.card.Card;

/** calculates next move for AI */
public class Move implements Serializable {
	private static final long serialVersionUID = 1L;
	/** chosen Card of AI */
	private Card chosen;
	/** calculated action for AI */
	private Action action;
	/** calculated trade option for AI */
	private TradeOption tradeOption;

	/**
	 * create new move
	 * 
	 * @param chosen chosen card
	 * @param action action
	 */
	public Move(Card chosen, Action action) {
		this.chosen = chosen;
		this.action = action;
	}

	/**
	 * deep clone move object
	 * 
	 * @param out output stream
	 */
	public void writeToOutput(DataOutputStream out) {
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
			objOut.writeObject(this);
			objOut.flush();
			objOut.close();
			byteOut.close();

			byte[] array = byteOut.toByteArray();
			out.writeInt(array.length);
			out.write(array);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * setter for {@link #tradeOption}
	 * 
	 * @param tradeOption trade
	 */
	public void setTradeOption(TradeOption tradeOption) {
		this.tradeOption = tradeOption;
	}

	/**
	 * getter for {@link #tradeOption}
	 * 
	 * @return tradeOption
	 */
	public TradeOption getTradeOption() {
		return tradeOption;
	}

	/**
	 * getter for {@link #chosen}
	 * 
	 * @return chosen
	 */
	public Card getCard() {
		return chosen;
	}

	/**
	 * getter for {@link #action}
	 * 
	 * @return action
	 */
	public Action getAction() {
		return action;
	}

	/** enum for Action */
	public enum Action {
		OLYMPIA, BUILD, PLACE_SLOT, SELL;
	}

	/**
	 * parses Move from InputStream
	 * 
	 * @param input DataInputStream
	 * @return copy copy
	 */
	public static Move parseFromInput(DataInputStream input) {
		ByteArrayInputStream byteIn = null;
		ObjectInputStream objIn = null;
		try {
			int length = input.readInt();
			byte[] array = new byte[length];
			input.read(array, 0, length);

			byteIn = new ByteArrayInputStream(array);
			objIn = new ObjectInputStream(byteIn);
			Move copy = (Move) objIn.readObject();

			return copy;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		} finally {
			if (byteIn != null)
				try {
					byteIn.close();
				} catch (IOException e1) {}
			if (objIn != null)
				try {
					objIn.close();
				} catch (IOException e) {}
		}

		return null;
	}
}