package com.codeindexer.service;

import com.codeindexer.indexer.IndexMatch;

import java.util.Set;

/**
 * Service interface for search
 */
public interface SearchService {
    public Set<IndexMatch> searchIndex(String term, boolean onlyFunctionName, boolean onlyFileName);
}
