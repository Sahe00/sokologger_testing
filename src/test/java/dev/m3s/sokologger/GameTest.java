package dev.m3s.sokologger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class GameTest {
    
    private Game game;
    
    // PLAYER COUNT VALIDATION TESTS 
    
    @Test
    void testMinimumTwoPlayers() throws SokoException {
        game = new Game(2);
        assertNotNull(game);
    }
    
    @Test
    void testMaximumSixPlayers() throws SokoException {
        game = new Game(6);
        assertNotNull(game);
    }
    
    @Test
    void testRejectOnlyOnePlayer() {
        assertThrows(SokoException.class, () -> {
            new Game(1);
        });
    }
    
    @Test
    void testRejectZeroPlayers() {
        assertThrows(SokoException.class, () -> {
            new Game(0);
        });
    }
    
    @Test
    void testRejectNegativePlayers() {
        assertThrows(SokoException.class, () -> {
            new Game(-3);
        });
    }
    
    @Test
    void testRejectTooManyPlayers() { // Probably found a bug
        assertThrows(SokoException.class, () -> {
            new Game(7);
        });
    }
    
    @Test
    void testRejectExcessivePlayers() {
        assertThrows(SokoException.class, () -> {
            new Game(11);
        });
    }
    
    // DEALING TESTS 
    
    @Test
    void testDealMethodExists() throws SokoException {
        game = new Game(3);
        // Should be able to call deal() without exception
        game.deal();
    }
    
    @Test
    void testDealIncreasesRound() throws SokoException {
        game = new Game(2);
        
        assertEquals(0, game.getRound(), "Round should start at 0");
        
        game.deal();
        assertEquals(1, game.getRound(), "Round should be 1 after first deal");
        
        game.deal();
        assertEquals(2, game.getRound(), "Round should be 2 after second deal");
    }
    
    @Test
    void testDealFiveRounds() throws SokoException {
        game = new Game(3);
        
        // Deal 5 rounds (5 cards per player)
        for (int i = 0; i < 5; i++) {
            game.deal();
        }
        
        assertEquals(5, game.getRound(), "Should have completed 5 rounds");
    }
    
    @Test
    void testDealAfterFiveRoundsCallsEnd() throws SokoException {
        game = new Game(2);
        
        // Deal 5 rounds
        for (int i = 0; i < 5; i++) {
            game.deal();
        }
        
        game.deal();
        assertEquals(5, game.getRound(), "Round should still be 5");
    }
    
    @Test
    void testDealCardRotatesTurn() throws SokoException {
        game = new Game(3);  // 3 players
        
        assertEquals(0, game.getTurn(), "Turn should start at 0");
        
        game.dealCard(new Card(Suite.SPADES, 5));
        assertEquals(1, game.getTurn(), "Turn should be 1 after dealing to player 0");
        
        game.dealCard(new Card(Suite.HEARTS, 7));
        assertEquals(2, game.getTurn(), "Turn should be 2 after dealing to player 1");
        
        game.dealCard(new Card(Suite.DIAMONDS, 9));
        assertEquals(0, game.getTurn(), "Turn should wrap to 0 after dealing to player 2");
    }
    
    // GAME STATE TESTS 
    
    @Test
    void testToStringNotNull() throws SokoException {
        game = new Game(2);
        
        String result = game.toString();
        assertNotNull(result, "toString() should not return null");
    }
    
    @Test
    void testToStringContainsPlayerInfo() throws SokoException {
        game = new Game(2);
        game.deal();  // Deal one round
        
        String result = game.toString();
        assertTrue(result.length() > 0, "toString() should contain game state");
    }
    
    @Test
    void testInitialRoundIsZero() throws SokoException {
        game = new Game(4);
        assertEquals(0, game.getRound(), "Initial round should be 0");
    }
    
    @Test
    void testInitialTurnIsZero() throws SokoException {
        game = new Game(5);
        assertEquals(0, game.getTurn(), "Initial turn should be 0");
    }
    
    // EDGE CASES 
    
    @Test
    void testBoundaryTwoPlayers() throws SokoException {
        // Minimum boundary - should work
        game = new Game(2);
        assertNotNull(game);
    }
    
    @Test
    void testBoundaryBelowMinimum() {
        // Just below minimum - should fail
        assertThrows(SokoException.class, () -> {
            new Game(1);
        });
    }
    
    @Test
    void testDeckCapacityLimit() {
        // Should throw SokoException about deck capacity
        assertThrows(SokoException.class, () -> {
            new Game(11);
        });
    }
    
    @Test
    void testMaxPlayersWithinDeckLimit() throws SokoException { // Probably bug related
        game = new Game(10);
        assertNotNull(game);
    }
}