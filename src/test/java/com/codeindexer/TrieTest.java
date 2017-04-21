package com.codeindexer;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Test cases for trie
 */
public class TrieTest {
    @Test
    public void testTrie() {
        Trie trie = new Trie();
        trie.insert("hello");
        trie.insert("hell");
        trie.insert("helloworld");
        //System.out.println(trie);

        System.out.println("============");
        List<String> words = trie.getNearestMatchingWords("hellowo", 20);
        System.out.println(words);

        Assert.assertEquals("Checking size of words nearest to \"hellowo\"",
                1,words.size());
        Assert.assertEquals("Checking words nearest to \"hellowo\"",
                "helloworld",words.get(0));
    }

    @Test
    public void testSingleCharMaxSize() {
        Trie trie = new Trie();
        trie.insert("Shambu");
        trie.insert("Sunitha");
        trie.insert("Sandeep");
        //System.out.println(trie);

        System.out.println("============");
        List<String> words = trie.getNearestMatchingWords("S", 2);
        Collections.sort(words);
        System.out.println(words);

        Assert.assertEquals("Checking size of words nearest to \"S\"",
                2,words.size());
    }

    @Test
    public void testBlankChar() {
        Trie trie = new Trie();
        trie.insert("Shambu");
        trie.insert("Sunitha");
        trie.insert("Sandeep");
        //System.out.println(trie);

        System.out.println("============");
        List<String> words = trie.getNearestMatchingWords("", 10);
        Collections.sort(words);
        System.out.println(words);

        Assert.assertEquals("Checking size of words nearest to \"S\"",
                3,words.size());

        Assert.assertArrayEquals(new String[]{"Sandeep", "Shambu", "Sunitha"}, words.toArray(new String[0]));
    }
}
