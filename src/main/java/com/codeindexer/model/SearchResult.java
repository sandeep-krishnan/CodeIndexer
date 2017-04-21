package com.codeindexer.model;

import com.codeindexer.indexer.IndexMatch;

import java.util.Set;

/**
 * Class to store Search result
 */
public class SearchResult {
    private long timeTaken;
    private int count;
    private Set<IndexMatch> searchResults;

    public SearchResult(Set<IndexMatch> searchResults, long timeTaken) {
        this.searchResults = searchResults;
        this.timeTaken = timeTaken;
        this.count = searchResults.size();
    }

    /**
     * @return the time taken to fetch the results in milliseconds
     */
    public String getTimeTaken() {
        return timeTaken + " ms";
    }

    /**
     * @return the number of results in search
     */
    public int getCount() {
        return count;
    }

    /**
     * @return the results of the search
     */
    public Set<IndexMatch> getSearchResults() {
        return searchResults;
    }
}
