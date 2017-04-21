package com.codeindexer.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test cases for StringHelper
 */
public class StringHelperTest {
    @Test
    public void testAlphabets() {
        Assert.assertEquals("IsAlphabet for character a" ,
                true, StringHelper.isValidCharacter('a'));
        Assert.assertEquals("IsAlphabet for character z" ,
                true, StringHelper.isValidCharacter('z'));

        Assert.assertEquals("IsAlphabet for character A" ,
                true, StringHelper.isValidCharacter('A'));
        Assert.assertEquals("IsAlphabet for character Z" ,
                true, StringHelper.isValidCharacter('Z'));

    }

    @Test
    public void testNonAlphabets() {
        Assert.assertEquals("IsAlphabet for character @" ,
                false, StringHelper.isValidCharacter('@'));
        Assert.assertEquals("IsAlphabet for character [" ,
                false, StringHelper.isValidCharacter('['));

        Assert.assertEquals("IsAlphabet for character `" ,
                false, StringHelper.isValidCharacter('`'));
        Assert.assertEquals("IsAlphabet for character {" ,
                false, StringHelper.isValidCharacter('{'));
    }

    @Test
    public void testStringObseleteCharsRemove() {
        String input = "if (syncRead(fd,&c,1,timeout) == -1) return -1;";
        String output = StringHelper.removeUnwantedCharacters(input);
        Assert.assertEquals("checking obselete char removal ",
                "if  syncRead fd  c 1 timeout      1  return  1 ",
                output);
    }

    @Test
    public void testFirstWord() {
        String input = "util.cpp";
        String output = StringHelper.getFirstWord(input);
        Assert.assertEquals("Splitting the word util.cpp ",
                "util", output);
    }
}
