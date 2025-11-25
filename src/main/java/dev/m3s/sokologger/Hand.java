package dev.m3s.sokologger;

import java.util.HashMap;
import java.util.Map;

public enum Hand {
	HIGH_CARD(1),
	PAIR(3),
	FOUR_STRAIGHT(4),
	FOUR_FLUSH(5),
	TWO_PAIRS(6),
	THREE_OF_A_KIND(7),
	STRAIGHT(8),
	FLUSH(9),
	FULL_HOUSE(10),
	FOUR_OF_A_KIND(11),
	STRAIGHT_FLUSH(17);

	public final int value;
	protected static final Map<Integer, Hand> map = new HashMap<>();

	/**
	 * Allow creating hands with integer values
	 *
	 * @param value
	 */
	private Hand(int value) {
		this.value = value;
	}

	/**
	 * Static block to initialize the internal map of hands
	 */
	static {
		for (Hand hand : Hand.values())
			map.put(hand.value, hand);
	}

	/**
	 * Convert integer value to the corresponding hand
	 *
	 * @param hand Hand value
	 * @return Hand corresponding to the given integer
	 * @throws SokoException If no hand matches the given integer
	 */
	public static Hand valueOf(int hand) throws SokoException {
		Hand val = map.get(hand);

		if (val == null)
			throw new SokoException("Hand " + hand + " Isn't a valid hand value!");

		return val;
	}
}
