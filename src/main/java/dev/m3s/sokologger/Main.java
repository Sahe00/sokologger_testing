package dev.m3s.sokologger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.cli.AlreadySelectedException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.help.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	static Scanner sc;
	private static Integer players = -1;
	private static boolean auto = false;
	private static HashMap<Integer, ArrayList<Card>> hands = null;

	public static void main(String[] args) throws SokoException, IOException {
		parseArgs(args);
		sc = new Scanner(System.in);
		Utf8Console.enableUtf8ConsoleOnWindows();

		try {
			if (players < 0)
				players = askPlayerCount();
		} catch (NumberFormatException e) {
			System.out.println("Invalid number of players: NumberFormatException " + e.getMessage());
			return;
		}

		System.out.println("Auto: " + auto + " Hands: " + hands);

		if (!auto && hands == null)
			auto = askManualDeal();

		Game game = new Game(players);

		if (!auto && hands == null) {
			System.out.println("S = Spades");
			System.out.println("H = Hearts");
			System.out.println("D = Diamonds");
			System.out.println("C = Clubs");
			System.out.println("Give cards in the format XV, where X is suite and V is value");
			for (int i = 0; i < 5 * players; i++) {
				game.dealCard(askCard(game.getRound(), game.getTurn()));
			}
		} else if (hands != null) {
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < players; i++) {
					game.dealCard(hands.get(i).get(j));
				}
			}
		} else {
			for (int i = 0; i < 5; i++) {
				game.deal();
				System.out.println(game.toString());
			}
		}

		sc.close();
		game.end();
	}

	/**
	 * Parse command line arguments
	 *
	 * @param args Arguments given to the program
	 * @throws IOException if the output could not be written to
	 */
	private static void parseArgs(String[] args) throws IOException {
		Options options = new Options();
		Option helpOption = new Option("h", "help", false, "Print help and exit");
		helpOption.setRequired(false);
		options.addOption(helpOption);

		Option simpleOption = new Option("s", "simple", false, "Display simple output");
		simpleOption.setRequired(false);
		options.addOption(simpleOption);

		Option playersOption = new Option("p", "players", true, "Number of players");
		playersOption.setRequired(false);
		playersOption.setType(Integer.class);

		Option autoOption = new Option("a", "auto", false, "Deal cards automatically");
		autoOption.setRequired(false);

		Option handsOption = Option
				.builder("H")
				.longOpt("hands")
				.desc("Predefined hands to deal to the players\nThe hand should be in the following format: `XV`, where X is the suite and V is the value. The cards must be separated by a comma.\nS = Spades\nH = Hearts\nD = Diamonds\nC = Clubs")
				.hasArgs()
				.required(false)
				.get();
		handsOption.setRequired(false);

		OptionGroup autohands = new OptionGroup();
		autohands.addOption(autoOption);
		autohands.addOption(handsOption);
		options.addOptionGroup(autohands);

		OptionGroup handplayers = new OptionGroup();
		handplayers.addOption(playersOption);
		handplayers.addOption(handsOption);
		options.addOptionGroup(handplayers);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = HelpFormatter.builder().get();

		try {
			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption(helpOption)) {
				formatter.printHelp("sökölogger", null, options, null, false);
				System.exit(0);
			}

			System.setProperty("output.simple", String.valueOf(cmd.hasOption(simpleOption)));
			players = cmd.getParsedOptionValue(playersOption, players);
			auto = cmd.hasOption(autoOption);

			if (cmd.hasOption(handsOption)) {
				parseHands(cmd.getOptionValues(handsOption));
			}
		} catch (AlreadySelectedException e) {
			System.out.println(e.getMessage());
			System.exit(1);
		} catch (ParseException e) {
			formatter.printHelp("sökölogger", null, options, null, false);
			e.printStackTrace();
		}
	}

	/**
	 * Parse the hand strings to cards for each player
	 *
	 * @param handsArray Array containing the hand string for each player
	 */
	private static void parseHands(String[] handsArray) {
		hands = new HashMap<>();
		players = handsArray.length;
		for (int i = 0; i < handsArray.length; i++) {
			String hand = handsArray[i];
			String[] cards = hand.split(",");
			ArrayList<Card> cardsList = new ArrayList<>();
			for (String card : cards) {
				try {
					cardsList.add(parseCard(card));
				} catch (SokoException e) {
					System.out.println(e.getMessage() + " Card: " + card);
				}
			}
			if (cardsList.size() >= 5) {
				hands.put(i, cardsList);
			} else {
				System.out.println(
						"Each player requires 5 cards in hand. Player " + (i + 1) + " had " + cardsList.size()
								+ " cards!");
				System.exit(1);
			}
		}
	}

	/**
	 * Asks user for:
	 * Number of players must be between 1-6
	 *
	 * @return the number of players
	 */
	public static int askPlayerCount() {
		int inputPlayers = 0;
		while (true) {
			System.out.println("How many players?");
			inputPlayers = sc.nextInt();
			sc.nextLine();
			if (inputPlayers > 0 && inputPlayers < 6)
				break;
		}

		return inputPlayers;
	}

	/**
	 * Asks user if they want to deal cards manually
	 *
	 * @return true if yes false if no
	 */
	static boolean askManualDeal() {
		String dealManually;
		while (true) {
			System.out.println("Do you want to manually deal cards? (Y(es), N(o))");
			dealManually = sc.nextLine();

			if (dealManually.equalsIgnoreCase("y")) {
				// Don't close scanner used later
				return false;
			}
			if (dealManually.equalsIgnoreCase("n")) {
				return true;
			}
		}
	}

	/**
	 * Ask the user for a card to deal to a player
	 *
	 * @param round current round
	 * @param turn  current player
	 * @return Created card
	 */
	static Card askCard(int round, int turn) {
		while (true) {
			System.out.println("Deal card " + (round + 1) + " for player " + (turn + 1) + " :");
			System.out.print("Give suite and value: ");
			String line = sc.nextLine();

			try {
				return parseCard(line);
			} catch (SokoException e) {
				System.out.println("Give valid values!!");
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * Parse a card from the given string
	 *
	 * @param cardString String containing the suite and value of a card
	 * @return Created card
	 * @throws SokoException if the string could not be parsed to a card
	 */
	private static Card parseCard(String cardString) throws SokoException {
		int valueNum = 0;
		int suiteNum = 0;
		switch (cardString.toLowerCase().charAt(0)) {
			case 's':
				suiteNum = 0;
				break;
			case 'h':
				suiteNum = 1;
				break;
			case 'd':
				suiteNum = 2;
				break;
			case 'c':
				suiteNum = 3;
				break;
			default:
				throw new SokoException("Invalid suite");
		}
		valueNum = Integer.parseInt(cardString, 1, cardString.length(), 10);
		if (valueNum > 13 || valueNum < 1) {
			throw new SokoException("Invalid value");
		}

		return new Card(Suite.valueOf(suiteNum), valueNum);
	}
}
