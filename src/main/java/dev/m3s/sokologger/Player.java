package dev.m3s.sokologger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Player {
	private Integer number;
	private ArrayList<Card> cards = new ArrayList<>();

	/**
	 * Create a new player
	 *
	 * @param number Number of the player
	 */
	public Player(Integer number) {
		this.number = number;
	}

	/**
	 * Add a card to the player's hand
	 *
	 * @param card Card to add
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}

	/**
	 * Count the score for the hand for comparision purposes
	 *
	 * @return Calculated score for the hand
	 */
	public Double score() {
		Collections.sort(cards, new CardComparator());
		Map<Integer, Integer> numbers = new HashMap<>();
		Map<Suite, Integer> suites = new EnumMap<>(Suite.class);
		HashMap<Integer, Integer> counts = new HashMap<>();
		for (Card card : cards) {
			numbers.merge(card.getValue(), 1, this::sum);
			suites.merge(card.getSuite(), 1, this::sum);
		}
		for (Iterator<Integer> it = numbers.values().iterator(); it.hasNext();) {
			counts.merge(it.next(), 1, this::sum);
		}
		Double value = (numbers.containsKey(1) ? 14 : cards.get(cards.size() - 1).getValue()) / 100.0;

		if (hasExactly(suites, 5))
			value += (double) Hand.FLUSH.value;

		if (hasStraight(numbers, 5))
			value += (double) Hand.STRAIGHT.value;

		if (hasExactly(numbers, 4))
			return (double) Hand.FOUR_OF_A_KIND.value;

		if (counts.getOrDefault(2, 0) >= 1 && counts.getOrDefault(3, 0) >= 1)
			return (double) Hand.FULL_HOUSE.value;

		if (value >= 0.14)
			return value;

		if (hasExactly(numbers, 3))
			return (double) Hand.THREE_OF_A_KIND.value;

		if (counts.getOrDefault(2, 0) >= 2)
			return (double) Hand.TWO_PAIRS.value;

		if (hasExactly(suites, 4))
			return (double) Hand.FOUR_FLUSH.value;

		if (hasStraight(numbers, 4))
			return (double) Hand.FOUR_STRAIGHT.value;

		if (hasExactly(numbers, 2))
			return (double) Hand.PAIR.value;

		return Hand.HIGH_CARD.value + value;
	}

	/**
	 * Check if the given map has exactly x of a value
	 *
	 * @param data     Map of card data
	 * @param required number of values in the map
	 * @return Did the map have exactly x values
	 */
	public boolean hasExactly(Map<?, Integer> data, Integer required) {
		for (Integer num : data.values())
			if (num.equals(required))
				return true;

		return false;
	}

	/**
	 * Check if the given numbers make a straight, ace counts as both 1 and 14
	 *
	 * @param data     map of card values
	 * @param required number of sequential values
	 * @return Did the given hand have a straight
	 */
	private boolean hasStraight(Map<Integer, Integer> data, Integer required) {
		if (data.keySet().size() >= required) {
			Iterator<Integer> numbers = data.keySet().iterator();
			int longest = 1;
			int i = 1;
			Integer previous = numbers.next();

			while (numbers.hasNext()) {
				Integer current = numbers.next();

				if (!previous.equals(current - 1))
					i = 1;
				else
					i++;

				if (i > longest)
					longest = i;

				previous = current;
			}
			if (longest == required && data.containsKey(1) && previous.equals(13))
				return true;
			return longest == required;
		}
		return false;
	}

	/**
	 * Sum the two integers together
	 *
	 * @param oldValue original value
	 * @param newValue value to be added
	 * @return The sum of the two given values
	 */
	private Integer sum(Integer oldValue, Integer newValue) {
		return oldValue + newValue;
	}

	/**
	 * Stringify the player's details.
	 * Returns the player's number and their hand separated by a newline.
	 *
	 * @return Stringified player
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Player " + number + ":\n\t");

		for (Card card : cards) {
			sb.append(card.toString());
		}

		return sb.toString();
	}
}
