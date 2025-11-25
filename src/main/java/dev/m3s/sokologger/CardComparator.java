package dev.m3s.sokologger;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {
	@Override
	public int compare(Card card1, Card card2) {
		return card1.compareTo(card2);
	}
}
