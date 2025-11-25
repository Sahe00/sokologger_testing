package dev.m3s.sokologger;

import java.util.HashMap;
import java.util.Map;

public enum Suite {
	SPADES(0x00),
	HEARTS(0x10),
	DIAMONDS(0x20),
	CLUBS(0x30);

	public final int value;
	protected static final Map<Integer, Suite> map = new HashMap<>();

	/**
	 * Allow creating suites with integer values
	 *
	 * @param value
	 */
	private Suite(int value) {
		this.value = value;
	}

	/**
	 * Static block to initialize the internal map of suites
	 */
	static {
		for (Suite suite : Suite.values())
			map.put(suite.value, suite);
	}

	/**
	 * Convert integer value to the corresponding suite
	 *
	 * @param suite Suite integer value, 0-3 will be converted to 0x00-0x30
	 * @return Suite corresponding to the given integer
	 * @throws SokoException If no suite matches the given integer
	 */
	public static Suite valueOf(int suite) {
		return map.get(suite);
	}

	/**
	 * Get the character representation of the suite
	 *
	 * @param suite Suite to convert to character
	 * @return Character representing the suite
	 */
	public static Character toChar(Suite suite) {
		switch (suite) {
			case Suite.SPADES:
				return 'S';
			case Suite.HEARTS:
				return 'H';
			case Suite.DIAMONDS:
				return 'D';
			case Suite.CLUBS:
				return 'C';
			default:
				return null;
		}
	}
}
