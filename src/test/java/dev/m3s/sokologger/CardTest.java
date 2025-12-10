package dev.m3s.sokologger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CardTest {
    
    @AfterEach
    void resetCardTest() {
        System.clearProperty("output.simple");
    }

    // CONSTRUCTOR TESTS

    @Test
    void testCardCreation() throws SokoException {
        Card card = new Card(Suite.SPADES, 4);
        assertNotNull(card);
        assertEquals(Suite.SPADES, card.getSuite());
        assertEquals(4 , card.getValue());
    }

    @Test
    void testCardValueTooLow() {
        var e = assertThrows(SokoException.class, 
            () -> new Card(Suite.SPADES, 0));
        
        assertTrue(e.getMessage().contains("between 1 and 13"));
    }

    @Test
    void testCardValueTooHigh() {
        var e = assertThrows(SokoException.class, 
            () -> new Card(Suite.HEARTS, 14));

        assertTrue(e.getMessage().contains("between 1 and 13"));
    }

    @Test
    void testCardValueNegative() {
        var e = assertThrows(SokoException.class, 
            () -> new Card(Suite.DIAMONDS, -3));
        
        assertTrue(e.getMessage().contains("between 1 and 13"));
    }

    // BOUNDARY VALUE TESTS / OTHERS TESTED ABOVE ALREADY

    @Test
    void testValueAtLowerBoundaryValid() throws SokoException {
        Card card = new Card(Suite.HEARTS, 1);
        assertEquals(1, card.getValue());
    }

    @Test
    void testValueAtUpperBoundaryValid() throws SokoException {
        Card card = new Card(Suite.CLUBS, 13);
        assertEquals(13, card.getValue());
    }

    // GETTER TESTS

    @Test
    void testGetSuite() throws SokoException {
        Card card = new Card(Suite.CLUBS, 9);
        assertEquals(Suite.CLUBS, card.getSuite());
    }

    @Test
    void testGetValue() throws SokoException {
        Card card = new Card(Suite.CLUBS, 3);
        assertEquals(3, card.getValue());
    }

    // SETTER TESTS

    @Test
    void testSetSuite() throws SokoException {
        Card card = new Card(Suite.HEARTS, 2);
        card.setSuite(Suite.DIAMONDS);
        assertEquals(Suite.DIAMONDS, card.getSuite());
    }

    @Test
    void testSetValueValid() throws SokoException {
        Card card = new Card(Suite.HEARTS, 2);
        card.setValue(10);
        assertEquals(10, card.getValue());
    }

    @Test
    void testSetValueInvalid() throws SokoException {
        Card card = new Card(Suite.SPADES, 1);
        var e = assertThrows(SokoException.class, 
            () -> card.setValue(-62));
        
        assertTrue(e.getMessage().contains("between 1 and 13"));
    }

    // 
    

}