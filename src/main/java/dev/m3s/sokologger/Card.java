package dev.m3s.sokologger;

public class Card implements Comparable<Card> {
	private Suite suite;
	private Integer value;

	/**
	 * Construct a new playing card
	 *
	 * @param suite Suite of the card
	 * @param value Value of the card
	 * @throws SokoException Thrown if the value of the card was invalid
	 */
	public Card(Suite suite, Integer value) throws SokoException {
		this.suite = suite;
		if (value < 1 || value > 13)
			throw new SokoException("Value must be between 1 and 13");

		this.value = value;
	}

	/**
	 * Get the suite of the card
	 *
	 * @return Suite of the card
	 */
	public Suite getSuite() {
		return suite;
	}

	/**
	 * Set the suite of the card
	 *
	 * @param suite New suite
	 */
	public void setSuite(Suite suite) {
		this.suite = suite;
	}

	/**
	 * Get the value of the card
	 *
	 * @return Value of the card
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * Set the value of the card
	 *
	 * @param value New Value
	 */
	public void setValue(Integer value) throws SokoException {
		if (value < 1 || value > 13)
			throw new SokoException("Value must be between 1 and 13");

		this.value = value;
	}

	/**
	 * Get the string representation of the card
	 * <p>
	 * See
	 * {@link https://en.wikipedia.org/wiki/Playing_cards_in_Unicode#Playing_cards_deck}
	 * for an explanation
	 * <p>
	 *
	 * <hr>
	 * TL;DR:
	 * <p>
	 * U+1F0A0 is the Unicode code point for the blank playing card. This converted
	 * to UTF-16 is <code>0xd83c 0xdca0</code> hex.
	 * The playing cards are arranged in sequential order (0x01-0x0E) so that 0x0F
	 * is the joker for each suite.
	 * Each of the suites are placed one after the other (0xA0-0xD0).
	 * <hr>
	 *
	 * @return Card unicode symbol
	 */
	@Override
	public String toString() {
		char cardBase = '\udca0';
		String simple = System.getProperty("output.simple");

		if (simple != null && simple.equalsIgnoreCase("true"))
			return Suite.toChar(this.suite) + "" + this.value + " ";

		cardBase += this.suite.value;
		cardBase += this.value;
		// The unicode set of playing cards includes the knight card (0x0C) so we skip
		// it
		if (this.value >= 12)
			cardBase++;

		return "\ud83c" + cardBase;
	}

	@Override
	public int compareTo(Card card) {
		return this.hashCode() - card.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card card) {
			return this.hashCode() == card.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getValue() * 0x1000 + this.getSuite().ordinal();
	}
}
