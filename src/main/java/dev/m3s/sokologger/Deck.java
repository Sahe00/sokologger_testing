package dev.m3s.sokologger;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> cards;

	/**
	 * Initialize a deck of 52 cards
	 *
	 * @param shuffle Should the deck be shuffled
	 */
	public Deck(boolean shuffle) {
		this.cards = new ArrayList<>();

		/**
		 * Iterate over suites
		 * See {@link Suite.java} for suites
		 */
		for (int i = 0x00; i < 0x40; i += 0x10) {
			// Iterate over card values
			for (int j = 1; j < 14; j++) {
				try {
					cards.add(new Card(Suite.valueOf(i), j));
				} catch (SokoException e) {
					System.err.println("Error: Generated invalid card: Suite " + i + ", Value " + j);
					System.err.println(e.getMessage());
				}
			}
		}

		if (shuffle)
			this.shuffle();
	}

	/**
	 * Initialize a shuffled deck of 52 cards
	 */
	public Deck() {
		this(true);
	}

	/**
	 * Shuffle the deck
	 */
	public void shuffle() {
		Collections.shuffle(cards);
	}

	/**
	 * Deak a card from the deck
	 *
	 * @return dealt card
	 */
	public Card deal() {
		return cards.removeFirst();
	}

	/**
	 * Get the number of cards in the deck
	 *
	 * @return Number of cards
	 */
	public int size() {
		return cards.size();
	}
}
