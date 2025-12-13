package dev.m3s.sokologger;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
        assertThrows(SokoException.class, 
            () -> new Card(Suite.SPADES, 0)); 
    }

    @Test
    void testCardValueTooHigh() {
        assertThrows(SokoException.class, 
            () -> new Card(Suite.HEARTS, 14));
    }

    @Test
    void testCardValueNegative() {
        assertThrows(SokoException.class, 
            () -> new Card(Suite.DIAMONDS, -3));
    }

    @Test
    void testAllSuitesInConstructor() throws SokoException {
    
    Card spades = new Card(Suite.SPADES, 5);
    Card hearts = new Card(Suite.HEARTS, 5);
    Card diamonds = new Card(Suite.DIAMONDS, 5);
    Card clubs = new Card(Suite.CLUBS, 5);
    
    assertNotNull(spades);
    assertNotNull(hearts);
    assertNotNull(diamonds);
    assertNotNull(clubs);
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
        assertThrows(SokoException.class, 
            () -> card.setValue(-62));
    }

    // COMPARE-EQUALS-HASH TESTS

    @Test // BUG 1: COMPARETO METHOD CAN CAUSE ISSUES DUE TO HASHCODE BEING USED ?
    void testCompareTo() throws SokoException {
        Card lower = new Card(Suite.CLUBS, 5);
        Card higher = new Card(Suite.HEARTS, 10);
        assertTrue(higher.compareTo(lower) > 0);
        assertTrue(lower.compareTo(higher) < 0);
    }

    @Test
    void testCompareToIdentical() throws SokoException {
        Card card1 = new Card(Suite.DIAMONDS, 10);
        Card card2 = new Card(Suite.DIAMONDS, 10);
        assertEquals(0, card1.compareTo(card2));
    }

    @Test
    void testEqualsIdentical() throws SokoException {
        Card card1 = new Card(Suite.CLUBS, 4);
        Card card2 = new Card(Suite.CLUBS, 4);
        assertEquals(card1, card2);
    }

    @Test
    void testEqualsDifferent() throws SokoException {
        Card card1 = new Card(Suite.CLUBS, 4);
        Card card2 = new Card(Suite.DIAMONDS, 12);
        assertNotEquals(card1, card2);
    }

    @Test
    void testEqualsNull() throws SokoException {
        Card card1 = new Card(Suite.CLUBS, 8);
        assertNotEquals(card1, null);
    }

    @Test
    void testEqualsWrongType() throws SokoException {
        Card card1 = new Card(Suite.HEARTS, 2);
        assertNotEquals(card1, "Def not a card");
    }
    

    @Test
    void testHashCodeConsistency() throws SokoException{
        Card card1 = new Card(Suite.SPADES, 7);
        Card card2 = new Card(Suite.SPADES, 7);
        assertEquals(card1.hashCode(), card2.hashCode());
    }

    @Test
    void testCompareToSameValueDifferentSuite() throws SokoException {
    // When values are equal, suite ordinal affects comparison
    Card spades5 = new Card(Suite.SPADES, 5);
    Card hearts5 = new Card(Suite.HEARTS, 5);
    
    // Since hashCode formula was: value * 0x1000 + suite.ordinal()
    assertTrue(spades5.compareTo(hearts5) < 0);
}

    @Test
    void testCompareToAceVsKing() throws SokoException {
    Card ace = new Card(Suite.SPADES, 1);
    Card king = new Card(Suite.SPADES, 13);
    
    assertTrue(ace.compareTo(king) < 0);
    assertTrue(king.compareTo(ace) > 0);
}

    // TO-STRING TESTS

    @Test
    void testToStringSimpleMode() throws SokoException {
        System.setProperty("output.simple", "true");
        Card card = new Card(Suite.SPADES, 5);
        String result = card.toString();
        assertNotNull(result);
        assertTrue(result.length() > 0);
    }

    @Test
    void testToStringUnicodeMode() throws SokoException {
        Card card = new Card(Suite.DIAMONDS, 1);
        String result = card.toString();
        assertNotNull(result);
    }
}