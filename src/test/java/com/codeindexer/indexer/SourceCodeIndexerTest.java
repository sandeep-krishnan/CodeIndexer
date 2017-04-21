package com.codeindexer.indexer;

import com.codeindexer.service.SearchService;
import com.codeindexer.service.SearchServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * Test cases for the SourceCodeIndexer
 */
public class SourceCodeIndexerTest {

    private static SearchService searchService;

    private static SourceCodeIndexer sourceCodeIndexer;

    @BeforeClass
    public static void setup() {
        sourceCodeIndexer = new SourceCodeIndexer
                ("source-code");
        sourceCodeIndexer.index();

        searchService = new SearchServiceImpl();
        System.out.println(Index.INDEX.getIndexSourceCodeWordMap().size());
    }

    @Test
    public void testSlashes() {
        Set<IndexMatch> indexMatches = searchService.searchIndex("lru.html", false, false);

        Assert.assertEquals("Testing if size of result is 0 ", 0, indexMatches.size());
    }

    @Test
    public void testFileName() {
        Set<IndexMatch> indexMatches = searchService.searchIndex("util.c", false, false);
        Assert.assertEquals("Testing if size of result is 3 for util.c ", 3, indexMatches.size());

        indexMatches = searchService.searchIndex("util", false, true);
        Assert.assertEquals("Testing if size of result is 6 for util filename search  ", 6, indexMatches.size());

        indexMatches = searchService.searchIndex("utilTest(int", false, false);
        Assert.assertEquals("Testing if size of result is 6 for \"utilTest(int\" search  ", 177, indexMatches.size());

    }

    @Test
    public void testNearAnswer() {
        Set<IndexMatch> indexMatches = searchService.searchIndex("READM", false, false);
        System.out.println(indexMatches);
        Assert.assertEquals("Testing if size of near result is  for READ ", 41, indexMatches.size());
    }

    @Test
    public void testWeirdChar() {
        Set<IndexMatch> indexMatches = searchService.searchIndex("<html>", false, false);
        Assert.assertEquals("Testing if size of near result is  for <html> ", 501, indexMatches.size());
    }

    @Test
    public void testUnderscore() {
        Set<IndexMatch> indexMatches = searchService.searchIndex("DBL_MANT_DIG", false, false);
        System.out.println(indexMatches);
        Assert.assertEquals("Testing if size of near result is  for DBL_MANT_DIG ", 2, indexMatches.size());
    }
}
