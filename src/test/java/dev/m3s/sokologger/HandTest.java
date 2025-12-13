package dev.m3s.sokologger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandTest {
    
    private Player player;
    
    @BeforeEach
    void setUp() {
        player = new Player(1);
    }
    
    // HAND DETECTION TESTS
    
    @Test
    void testHighCard() throws SokoException {
        // No pairs, no flush, no straight - just high card
        player.addCard(new Card(Suite.SPADES, 2));
        player.addCard(new Card(Suite.HEARTS, 5));
        player.addCard(new Card(Suite.DIAMONDS, 8));
        player.addCard(new Card(Suite.CLUBS, 10));
        player.addCard(new Card(Suite.SPADES, 13));  // King high
        
        double score = player.score();
        assertTrue(score >= 1.0 && score < 3.0, 
            "Expected HIGH_CARD (1.x), got: " + score);
    }
    
    @Test
    void testPair() throws SokoException {
        // One pair of 5s
        player.addCard(new Card(Suite.SPADES, 5));
        player.addCard(new Card(Suite.HEARTS, 5));
        player.addCard(new Card(Suite.DIAMONDS, 8));
        player.addCard(new Card(Suite.CLUBS, 10));
        player.addCard(new Card(Suite.SPADES, 13));
        
        double score = player.score();
        assertEquals(3.0, score, 0.01, "Expected PAIR (3.0), got: " + score);
    }
    
    @Test
    void testFourStraight() throws SokoException {
        // 4 sequential cards: 3, 4, 5, 6
        player.addCard(new Card(Suite.SPADES, 3));
        player.addCard(new Card(Suite.HEARTS, 4));
        player.addCard(new Card(Suite.DIAMONDS, 5));
        player.addCard(new Card(Suite.CLUBS, 6));
        player.addCard(new Card(Suite.SPADES, 10));  // Non-sequential
        
        double score = player.score();
        assertEquals(4.0, score, 0.01, "Expected FOUR_STRAIGHT (4.0), got: " + score);
    }
    
    @Test
    void testFourFlush() throws SokoException {
        // 4 cards same suite (Spades)
        player.addCard(new Card(Suite.SPADES, 2));
        player.addCard(new Card(Suite.SPADES, 5));
        player.addCard(new Card(Suite.SPADES, 8));
        player.addCard(new Card(Suite.SPADES, 11));
        player.addCard(new Card(Suite.HEARTS, 13));  // Different suite
        
        double score = player.score();
        assertEquals(5.0, score, 0.01, "Expected FOUR_FLUSH (5.0), got: " + score);
    }
    
    @Test
    void testTwoPairs() throws SokoException {
        // Two 5s and two 8s
        player.addCard(new Card(Suite.SPADES, 5));
        player.addCard(new Card(Suite.HEARTS, 5));
        player.addCard(new Card(Suite.DIAMONDS, 8));
        player.addCard(new Card(Suite.CLUBS, 8));
        player.addCard(new Card(Suite.SPADES, 13));
        
        double score = player.score();
        assertEquals(6.0, score, 0.01, "Expected TWO_PAIRS (6.0), got: " + score);
    }
    
    @Test
    void testThreeOfAKind() throws SokoException {
        // Three 7s
        player.addCard(new Card(Suite.SPADES, 7));
        player.addCard(new Card(Suite.HEARTS, 7));
        player.addCard(new Card(Suite.DIAMONDS, 7));
        player.addCard(new Card(Suite.CLUBS, 2));
        player.addCard(new Card(Suite.SPADES, 10));
        
        double score = player.score();
        assertEquals(7.0, score, 0.01, "Expected THREE_OF_A_KIND (7.0), got: " + score);
    }
    
    @Test
    void testStraight() throws SokoException {
        // 5 sequential cards: 5, 6, 7, 8, 9
        player.addCard(new Card(Suite.SPADES, 5));
        player.addCard(new Card(Suite.HEARTS, 6));
        player.addCard(new Card(Suite.DIAMONDS, 7));
        player.addCard(new Card(Suite.CLUBS, 8));
        player.addCard(new Card(Suite.SPADES, 9));
        
        double score = player.score();
        assertTrue(score >= 8.0 && score < 9.0, 
            "Expected STRAIGHT (8.x), got: " + score);
    }
    
    @Test
    void testFlush() throws SokoException {
        // 5 cards same suite (Hearts), non-sequential
        player.addCard(new Card(Suite.HEARTS, 2));
        player.addCard(new Card(Suite.HEARTS, 5));
        player.addCard(new Card(Suite.HEARTS, 8));
        player.addCard(new Card(Suite.HEARTS, 10));
        player.addCard(new Card(Suite.HEARTS, 13));
        
        double score = player.score();
        assertTrue(score >= 9.0 && score < 10.0, 
            "Expected FLUSH (9.x), got: " + score);
    }
    
    @Test
    void testFullHouse() throws SokoException {
        // Three 5s and two 8s
        player.addCard(new Card(Suite.SPADES, 5));
        player.addCard(new Card(Suite.HEARTS, 5));
        player.addCard(new Card(Suite.DIAMONDS, 5));
        player.addCard(new Card(Suite.CLUBS, 8));
        player.addCard(new Card(Suite.SPADES, 8));
        
        double score = player.score();
        assertEquals(10.0, score, 0.01, "Expected FULL_HOUSE (10.0), got: " + score);
    }
    
    @Test
    void testFourOfAKind() throws SokoException {
        // Four 9s
        player.addCard(new Card(Suite.SPADES, 9));
        player.addCard(new Card(Suite.HEARTS, 9));
        player.addCard(new Card(Suite.DIAMONDS, 9));
        player.addCard(new Card(Suite.CLUBS, 9));
        player.addCard(new Card(Suite.SPADES, 2));
        
        double score = player.score();
        assertEquals(11.0, score, 0.01, "Expected FOUR_OF_A_KIND (11.0), got: " + score);
    }
    
    @Test
    void testStraightFlush() throws SokoException {
        // 5 sequential cards, same suite: 7♠, 8♠, 9♠, 10♠, J♠
        player.addCard(new Card(Suite.SPADES, 7));
        player.addCard(new Card(Suite.SPADES, 8));
        player.addCard(new Card(Suite.SPADES, 9));
        player.addCard(new Card(Suite.SPADES, 10));
        player.addCard(new Card(Suite.SPADES, 11));
        
        double score = player.score();
        assertTrue(score >= 17.0, "Expected STRAIGHT_FLUSH (17+), got: " + score);
    }
    
    @Test
    void testRoyalFlush() throws SokoException {
        // Ace-high straight flush: 10, J, Q, K, A (all Hearts)
        player.addCard(new Card(Suite.HEARTS, 10));
        player.addCard(new Card(Suite.HEARTS, 11));
        player.addCard(new Card(Suite.HEARTS, 12));
        player.addCard(new Card(Suite.HEARTS, 13));
        player.addCard(new Card(Suite.HEARTS, 1));   // Ace
        
        double score = player.score();
        assertTrue(score >= 17.0, "Expected STRAIGHT_FLUSH/Royal (17+), got: " + score);
    }
    
    // HAND COMPARISON TESTS
    
    @Test
    void testHigherHandTypeWins() throws SokoException {
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        
        // Player 1: Pair
        player1.addCard(new Card(Suite.SPADES, 5));
        player1.addCard(new Card(Suite.HEARTS, 5));
        player1.addCard(new Card(Suite.DIAMONDS, 8));
        player1.addCard(new Card(Suite.CLUBS, 10));
        player1.addCard(new Card(Suite.SPADES, 13));
        
        // Player 2: Three of a Kind
        player2.addCard(new Card(Suite.SPADES, 7));
        player2.addCard(new Card(Suite.HEARTS, 7));
        player2.addCard(new Card(Suite.DIAMONDS, 7));
        player2.addCard(new Card(Suite.CLUBS, 2));
        player2.addCard(new Card(Suite.SPADES, 10));
        
        assertTrue(player2.score() > player1.score(), 
            "Three of a Kind (7.0) should beat Pair (3.0)");
    }
    
    @Test
    void testIdenticalHandsResultInTie() throws SokoException {
        Player player1 = new Player(1);
        Player player2 = new Player(2);
        
        // Both have two pairs: 5s and 8s
        player1.addCard(new Card(Suite.SPADES, 5));
        player1.addCard(new Card(Suite.HEARTS, 5));
        player1.addCard(new Card(Suite.DIAMONDS, 8));
        player1.addCard(new Card(Suite.CLUBS, 8));
        player1.addCard(new Card(Suite.SPADES, 2));
        
        player2.addCard(new Card(Suite.DIAMONDS, 5));
        player2.addCard(new Card(Suite.CLUBS, 5));
        player2.addCard(new Card(Suite.SPADES, 8));
        player2.addCard(new Card(Suite.HEARTS, 8));
        player2.addCard(new Card(Suite.DIAMONDS, 2));
        
        assertEquals(player1.score(), player2.score(), 0.01, 
            "Identical hands should tie");
    }
    
    // CARD VALUE TESTS
    
    @Test
    void testAceIsHighestCard() throws SokoException {
        Player player1 = new Player(1);  // Ace high
        Player player2 = new Player(2);  // King high
        
        // Player 1: Ace high
        player1.addCard(new Card(Suite.SPADES, 1));   // Ace
        player1.addCard(new Card(Suite.HEARTS, 3));
        player1.addCard(new Card(Suite.DIAMONDS, 6));
        player1.addCard(new Card(Suite.CLUBS, 8));
        player1.addCard(new Card(Suite.SPADES, 10));
        
        // Player 2: King high
        player2.addCard(new Card(Suite.SPADES, 13));  // King
        player2.addCard(new Card(Suite.HEARTS, 3));
        player2.addCard(new Card(Suite.DIAMONDS, 6));
        player2.addCard(new Card(Suite.CLUBS, 8));
        player2.addCard(new Card(Suite.SPADES, 10));
        
        assertTrue(player1.score() > player2.score(), 
            "Ace high should beat King high");
    }
}