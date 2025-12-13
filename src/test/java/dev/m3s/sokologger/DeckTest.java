package dev.m3s.sokologger;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeckTest {

    private Deck deck;

    @BeforeEach
    void setUp() {
        // Unshuffled deck before each test
        deck = new Deck(false);
    }

    // DECK INITIALIZATION TESTS
    @Test
    void testDeckHas52Cards() {
        assertEquals(52, deck.size(), "Deck should have exactly 52 cards");
    }

    @Test
    void testDeckHasAllValues() throws SokoException {
        Deck testDeck = new Deck(false);
        Set<Integer> values = new HashSet<>(); // Only unique integer values

        for (int i = 0; i < 52; i++) {
            Card card = testDeck.deal();
            values.add(card.getValue());
        }

        // Check if we have 13 different values
        assertEquals(13, values.size(), "Deck should include all 13 card values");
        // Check if we actually have the correct values
        for (int i = 1; i <=13; i++) { 
            assertTrue(values.contains(i), "Deck should contain value: " + i);
        }
    }

    @Test
    void testDeckHasAllSuites() throws SokoException {
        Deck testDeck = new Deck(false);
        Set<Suite> suites = new HashSet<>(); // Only unique suite values
        
        for (int i = 0; i < 52; i++) {
            Card card = testDeck.deal();
            suites.add(card.getSuite());
        }
        
        // Should have all 4 suites in total
        assertEquals(4, suites.size(), "Deck should contain all 4 suites");
        // And exactly the right suites
        assertTrue(suites.contains(Suite.SPADES), "Deck should contain SPADES");
        assertTrue(suites.contains(Suite.HEARTS), "Deck should contain HEARTS");
        assertTrue(suites.contains(Suite.DIAMONDS), "Deck should contain DIAMONDS");
        assertTrue(suites.contains(Suite.CLUBS), "Deck should contain CLUBS");
    }

    @Test
    void testDeckHasNoDuplicates() throws SokoException {
        Deck testDeck = new Deck(false);
        Set<String> cardStrings = new HashSet<>(); // Only unique string values
        
        // Deal all cards and check for duplicates
        for (int i = 0; i < 52; i++) {
            Card card = testDeck.deal();
            String cardKey = card.getSuite() + "-" + card.getValue();
            
            assertFalse(cardStrings.contains(cardKey), "Duplicate card found: " + cardKey); // Only pass if False
            cardStrings.add(cardKey);
        }
        // Actually dealt 52 cards
        assertEquals(52, cardStrings.size(), "Should have 52 unique cards");
    }

    @Test
    void testDeckHasCorrectDistribution() throws SokoException {
        Deck testDeck = new Deck(false);
        int[] valueCounts = new int[14]; // Index 0 unused, 1-13 for card values
        int[] suiteCounts = new int[4];  // Count for each suite
        
        // Deal all cards and count occurrences; essentially a frequency table
        for (int i = 0; i < 52; i++) {
            Card card = testDeck.deal();
            valueCounts[card.getValue()]++;
            suiteCounts[card.getSuite().ordinal()]++;
        }
        
        // Each value should appear exactly 4 times
        for (int i = 1; i <= 13; i++) {
            assertEquals(4, valueCounts[i], 
                "Value " + i + " should appear exactly 4 times");
        }
        
        // Each suite should appear exactly 13 times
        for (int i = 0; i < 4; i++) {
            assertEquals(13, suiteCounts[i], 
                "Each suite should have exactly 13 cards");
        }
    }

    // DEALING TESTS

    @Test
    void testDealReducesDeckSize() { 
        int initialSize = deck.size();
        deck.deal();
        assertEquals(initialSize - 1, deck.size(), // reduction of 1 card
            "Dealing should reduce deck size by 1");
    }

    @Test
    void testDealMultipleCards() {
        int initialSize = deck.size();
        
        deck.deal();
        deck.deal();
        deck.deal();
        
        assertEquals(initialSize - 3, deck.size(), // reduction of 3 cards
            "Dealing 3 cards should reduce deck size by 3");
    }

    @Test
    void testDealReturnsCard() {
        Card card = deck.deal();
        assertNotNull(card, "Deal should return a card");
        assertNotNull(card.getSuite(), "Dealt card should have a suite");
        assertTrue(card.getValue() >= 1 && card.getValue() <= 13, 
            "Dealt card should have valid value");
    }
    
    @Test
    void testDealAllCards() {
        for (int i = 0; i < 52; i++) {
            Card card = deck.deal();
            assertNotNull(card, "Should be able to deal card " + (i + 1));
        }
        assertEquals(0, deck.size(), "Deck should be empty after dealing all cards");
    }

    // SHUFFLE TESTS

    @Test
    void testShuffleDoesNotChangeSize() {
        int sizeBefore = deck.size();
        deck.shuffle();
        int sizeAfter = deck.size();
        
        assertEquals(sizeBefore, sizeAfter, 
            "Shuffling should not change deck size");
    }

    @Test
    void testShuffleChangesOrder() {
        // Create two unshuffled decks
        Deck deck1 = new Deck(false);
        Deck deck2 = new Deck(false);
        
        // Shuffle only one deck
        deck2.shuffle();
        
        // Check if at least some cards are in different positions
        boolean foundDifference = false;
        for (int i = 0; i < 10; i++) {  // Check first 10 cards
            Card card1 = deck1.deal();
            Card card2 = deck2.deal();
            
            if (!card1.equals(card2)) {
                foundDifference = true;
                break;
            }
        }
        
        assertTrue(foundDifference, 
            "Shuffled deck should have different order (may rarely fail due to randomness)");
    }

    @Test
    void testDefaultConstructorCreatesShuffledDeck() {  
    Deck shuffledDeck1 = new Deck();
    Deck shuffledDeck2 = new Deck();
    
    assertEquals(52, shuffledDeck1.size());
    assertEquals(52, shuffledDeck2.size());
    
    // Check if at least some cards differ
    boolean foundDifference = false;
    for (int i = 0; i < 5; i++) {  // Check first 5 cards
        Card card1 = shuffledDeck1.deal();
        Card card2 = shuffledDeck2.deal();
        
        if (!card1.equals(card2)) {
            foundDifference = true;
            break;
        }
    }
    
    assertTrue(foundDifference, 
        "Shuffled decks should have different order (very low probability of false failure)");
}

    @Test
    void testUnshuffledConstructorCreatesOrderedDeck() {
        Deck unshuffled1 = new Deck(false);
        Deck unshuffled2 = new Deck(false);
        
        // First cards should be identical
        Card card1 = unshuffled1.deal();
        Card card2 = unshuffled2.deal();
        
        assertEquals(card1.getSuite(), card2.getSuite(), 
            "Unshuffled decks should have same suite order");
        assertEquals(card1.getValue(), card2.getValue(), 
            "Unshuffled decks should have same value order");
    }

    // EDGE CASE BEHAVIOR TESTS

    @Test
    void testMultiShuffle() {
        int size = deck.size();
        
        deck.shuffle();
        deck.shuffle();
        deck.shuffle();
        
        assertEquals(size, deck.size(), 
            "Multiple shuffles should not change deck size");
    }

    @Test
    void testShuffleAfterDealing() {
        deck.deal();
        deck.deal();
        deck.deal();
        
        int sizeBeforeShuffle = deck.size();
        deck.shuffle();
        int sizeAfterShuffle = deck.size();
        
        assertEquals(sizeBeforeShuffle, sizeAfterShuffle, "Shuffle after dealing should not change size");
        assertEquals(49, deck.size(), "Should have 49 cards after dealing 3 and shuffling");
    }

}
