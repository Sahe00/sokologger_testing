package dev.m3s.sokologger;

import java.util.ArrayList;

public class Game {
	private Deck deck;
	private ArrayList<Player> players;
	private int rounds = 5;
	private int round = 0;
	private int turn = 0;

	/**
	 * Construct a game with set number of players and a set number of cards
	 *
	 * @param numPlayers Number of players in the game
	 * @throws SokoException Thrown if parameters are invalid
	 */
	public Game(int numPlayers) throws SokoException {
		if (numPlayers < 2)
			throw new SokoException("You must have at least 2 players to play the game");
		this.deck = new Deck();
		if (rounds * numPlayers > this.deck.size())
			throw new SokoException("Number of cards dealt exceeds the number of cards in the deck (" + this.deck.size()
					+ " cards in deck, " + rounds * numPlayers + " cards to be dealt)");
		this.players = new ArrayList<>();

		for (int i = 1; i <= numPlayers; i++) {
			players.add(new Player(i));
		}
	}

	/**
	 * Deal cards to all the players
	 */
	public void deal() {
		if (round >= rounds) {
			end();
			return;
		}
		System.out.println("Round " + ++round);
		for (Player player : players)
			player.addCard(deck.deal());
	}

	/**
	 * Deal card to a player
	 * @param card Card to deal
	 */
	public void dealCard(Card card) {
		players.get(turn).addCard(card);

		if (turn >= players.size() - 1)
			turn = 0;
		else
			turn++;
	}

	/**
	 * End the game
	 */
	public void end() {
		for (Player player : players) {
			System.out.println(player.toString());
			System.out.print("\tScore: ");
			System.out.println(player.score());
		}
	}

	/**
	 * Get the current turn
	 * @return turn
	 */
	public int getTurn() {
		return turn;
	}

	/**
	 * Get the current round
	 * @return round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * Stringify the game state
	 *
	 * @return Stringified game state
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (Player player : players) {
			sb.append(player.toString());
			sb.append("\n");
		}

		return sb.toString();
	}
}
