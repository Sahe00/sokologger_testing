package dev.m3s.sokologger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;

public class SuiteTest {
    
    // ENUM VALUES TESTS 
    
    @Test
    void testAllSuitesExist() {
        // Verify all 4 suites are defined
        Suite[] suites = Suite.values();
        assertEquals(4, suites.length, "Should have exactly 4 suites");
    }
    
    @Test
    void testSpadesExists() {
        assertNotNull(Suite.SPADES, "SPADES should exist");
    }
    
    @Test
    void testHeartsExists() {
        assertNotNull(Suite.HEARTS, "HEARTS should exist");
    }
    
    @Test
    void testDiamondsExists() {
        assertNotNull(Suite.DIAMONDS, "DIAMONDS should exist");
    }
    
    @Test
    void testClubsExists() {
        assertNotNull(Suite.CLUBS, "CLUBS should exist");
    }
    
    // VALUE TESTS
    
    @Test
    void testSpadesValue() {
        assertEquals(0x00, Suite.SPADES.value, "SPADES value should be 0x00");
    }
    
    @Test
    void testHeartsValue() {
        assertEquals(0x10, Suite.HEARTS.value, "HEARTS value should be 0x10");
    }
    
    @Test
    void testDiamondsValue() {
        assertEquals(0x20, Suite.DIAMONDS.value, "DIAMONDS value should be 0x20");
    }
    
    @Test
    void testClubsValue() {
        assertEquals(0x30, Suite.CLUBS.value, "CLUBS value should be 0x30");
    }
    
    // VALUEOF TESTS
    
    @Test
    void testValueOfSpades() {
        assertEquals(Suite.SPADES, Suite.valueOf(0x00), 
            "valueOf(0x00) should return SPADES");
    }
    
    @Test
    void testValueOfHearts() {
        assertEquals(Suite.HEARTS, Suite.valueOf(0x10), 
            "valueOf(0x10) should return HEARTS");
    }
    
    @Test
    void testValueOfDiamonds() {
        assertEquals(Suite.DIAMONDS, Suite.valueOf(0x20), 
            "valueOf(0x20) should return DIAMONDS");
    }
    
    @Test
    void testValueOfClubs() {
        assertEquals(Suite.CLUBS, Suite.valueOf(0x30), 
            "valueOf(0x30) should return CLUBS");
    }
    
    @Test
    void testValueOfInvalidReturnsNull() {
        // valueOf() returns null for invalid values (not throwing exception)
        assertNull(Suite.valueOf(99), 
            "valueOf(99) should return null for invalid suite");
    }
    
    @Test
    void testValueOfNegativeReturnsNull() {
        assertNull(Suite.valueOf(-1), 
            "valueOf(-1) should return null for negative value");
    }
    
    // TOCHAR TESTS
    
    @Test
    void testToCharSpades() {
        assertEquals('S', Suite.toChar(Suite.SPADES), 
            "SPADES should convert to 'S'");
    }
    
    @Test
    void testToCharHearts() {
        assertEquals('H', Suite.toChar(Suite.HEARTS), 
            "HEARTS should convert to 'H'");
    }
    
    @Test
    void testToCharDiamonds() {
        assertEquals('D', Suite.toChar(Suite.DIAMONDS), 
            "DIAMONDS should convert to 'D'");
    }
    
    @Test
    void testToCharClubs() {
        assertEquals('C', Suite.toChar(Suite.CLUBS), 
            "CLUBS should convert to 'C'");
    }
    
    // ORDINAL TESTS 
    
    @Test
    void testSpadesOrdinal() {
        assertEquals(0, Suite.SPADES.ordinal(), "SPADES ordinal should be 0");
    }
    
    @Test
    void testHeartsOrdinal() {
        assertEquals(1, Suite.HEARTS.ordinal(), "HEARTS ordinal should be 1");
    }
    
    @Test
    void testDiamondsOrdinal() {
        assertEquals(2, Suite.DIAMONDS.ordinal(), "DIAMONDS ordinal should be 2");
    }
    
    @Test
    void testClubsOrdinal() {
        assertEquals(3, Suite.CLUBS.ordinal(), "CLUBS ordinal should be 3");
    }
    
    // EDGE CASE BEHAVIOR TESTS
    
    @Test
    void testValueOfZeroReturnsSpades() {
        // Test that 0 (not 0x00) also maps correctly
        assertEquals(Suite.SPADES, Suite.valueOf(0), 
            "valueOf(0) should return SPADES");
    }
    
    @Test
    void testAllSuitesHaveUniqueValues() {
        // Ensure no duplicate values
        Suite[] suites = Suite.values();
        assertEquals(4, suites.length);
        
        // Check all values are different
        assertNotEquals(Suite.SPADES.value, Suite.HEARTS.value);
        assertNotEquals(Suite.SPADES.value, Suite.DIAMONDS.value);
        assertNotEquals(Suite.SPADES.value, Suite.CLUBS.value);
        assertNotEquals(Suite.HEARTS.value, Suite.DIAMONDS.value);
        assertNotEquals(Suite.HEARTS.value, Suite.CLUBS.value);
        assertNotEquals(Suite.DIAMONDS.value, Suite.CLUBS.value);
    }
    
    @Test
    void testEnumEquality() {
        // Test that enum equality works correctly
        Suite spades1 = Suite.SPADES;
        Suite spades2 = Suite.valueOf(0x00);
        
        assertSame(spades1, spades2, "Same suite should be identical reference");
        assertEquals(spades1, spades2, "Same suite should be equal");
    }
    
    @Test
    void testToStringNotNull() {
        // Verify toString() works for all suites
        assertNotNull(Suite.SPADES.toString());
        assertNotNull(Suite.HEARTS.toString());
        assertNotNull(Suite.DIAMONDS.toString());
        assertNotNull(Suite.CLUBS.toString());
    }
}