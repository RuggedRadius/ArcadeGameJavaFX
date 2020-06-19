/**
 * @author Benjamin Royans
 * @studentID P205225
 * @date Friday, 19 June 2020
 * @program TAFE Invaders
 * @description Java III Project.
 */

package main.hash;

import static org.junit.jupiter.api.Assertions.*;

class HashTest {

    @org.junit.jupiter.api.Test
    void MD5() {
        String testString = "This is a test string.";
        String hashedString = Hash.MD5(testString);
        assert (!testString.equals(hashedString));
    }
}