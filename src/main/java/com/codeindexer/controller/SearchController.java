package com.codeindexer.controller;

import com.codeindexer.indexer.IndexMatch;
import com.codeindexer.model.SearchResult;
import com.codeindexer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Set;

/**
 * Controller for the REST api
 */

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

    /**
     * @param term  word to search
     * @param searchType specify which type of search
     *                   to trigger
     * @return the set of matches
     */
    @RequestMapping("/api/search")
    public SearchResult search(@RequestParam(value="term", required=true) String term,
                                  @RequestParam(value="searchType", required=false,
                                          defaultValue = "any") String searchType) {
        long start = System.currentTimeMillis();
        boolean onlyFunctionName = false;
        boolean onlyFileName = false;

        switch (searchType) {
            case "function" :
                onlyFunctionName = true;
                break;

            case "fileName" :
                onlyFileName = true;
                break;

            default:
                break;
        }

        Set<IndexMatch> indexMatchSet = searchService.searchIndex(term, onlyFunctionName, onlyFileName);

        if(indexMatchSet == null) {
            indexMatchSet = Collections.emptySet();
        }

        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("searching " + term +".. search type = " + searchType
                + " Time Taken(in ms) = " + timeTaken);
        SearchResult searchResult = new SearchResult(indexMatchSet, timeTaken);
        return searchResult;
    }
}
