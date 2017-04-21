package com.codeindexer.service;

import com.codeindexer.indexer.Index;
import com.codeindexer.indexer.IndexMatch;
import com.codeindexer.indexer.SourceCodeMatch;
import com.codeindexer.model.SourceCodeFile;
import com.codeindexer.util.StringHelper;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Service implementation of Search
 */
@Component
public class SearchServiceImpl implements SearchService{

    private static Map<String, SourceCodeMatch> indexWordMap = Index.INDEX.getIndexSourceCodeWordMap();

    /**
     * Searches the index based on the input
     * @param term is the word to be searched
     * @param onlyFunctionName specifies if only function name needs to be searched
     * @param onlyFileName specifies if only file name needs to be searched
     * @return a Set of IndexMatch matching the criteria
     */
    public Set<IndexMatch> searchIndex(String term, boolean onlyFunctionName, boolean onlyFileName) {
        if(term == null || term.isEmpty()) {
            return Collections.emptySet();
        }

        term = term.toLowerCase();
        String[] termArr = StringHelper.cleanupSpaceAndGetWords(term);
        System.out.println("SearchService cleaned up.. term = " +
                    Arrays.toString(termArr));

        if(termArr == null || termArr.length == 0) {
            return Collections.emptySet();
        }

        if(termArr.length > 1) {
            return multiTermSearch(termArr, onlyFunctionName, onlyFileName);
        }

        return singleTermSearch(termArr[0], onlyFunctionName, onlyFileName, new HashSet<>());
    }

    /**
     * Multi term search
     * @param terms is the word list to be searched
     * @param onlyFunctionName specifies if only function name needs to be searched
     * @param onlyFileName specifies if only file name needs to be searched
     * @return a Set of IndexMatch matching the criteria
     */
    private Set<IndexMatch> multiTermSearch(String[] terms, boolean onlyFunctionName, boolean onlyFileName) {
        System.out.println("Multi term search.. " + Arrays.toString(terms));
        Map<String, Set<SourceCodeFile>> termToFileMap = new HashMap<>();

        Set<IndexMatch> indexMatches = new TreeSet<>();
        for (String term : terms) {
            SourceCodeMatch perfectMatch = indexWordMap.get(term);
            Set<SourceCodeFile> matchingDocIds = new HashSet<>();

            Set<IndexMatch> matches = singleTermSearch(term, onlyFunctionName,
                    onlyFileName, matchingDocIds);
            indexMatches.addAll(matches);
            termToFileMap.put(term, matchingDocIds);

            //if any of the terms are not available, then
            //it doesn't make sense to continue
            if(termToFileMap.get(term).size() == 0) {
                System.out.println("termToFileMap is 0.. returning");
                return Collections.emptySet();
            }
        }

        String firstTerm = terms[0];
        Set<SourceCodeFile> finalFiles = new HashSet<>(termToFileMap.get(firstTerm));

        for (String term : termToFileMap.keySet()) {
            if(term.equals(firstTerm))
                continue;

            finalFiles.retainAll(termToFileMap.get(term));
        }

        Set<IndexMatch> resultsToBeRemoved = new HashSet<>();
        for(IndexMatch indexMatch : indexMatches) {
            if(!finalFiles.contains(indexMatch.getSourceCodeFile())) {
                resultsToBeRemoved.add(indexMatch);
            }
        }
        indexMatches.removeAll(resultsToBeRemoved);

        return indexMatches;
    }

    /**
     * Single term search
     * @param term is the word list to be searched
     * @param onlyFunctionName specifies if only function name needs to be searched
     * @param onlyFileName specifies if only file name needs to be searched
     * @return a Set of IndexMatch matching the criteria
     */
    private Set<IndexMatch> singleTermSearch(String term, boolean onlyFunctionName,
                                             boolean onlyFileName,Set<SourceCodeFile> matchingDocIds) {
        Set<IndexMatch> finalResult = new LinkedHashSet<>();

        SourceCodeMatch perfectMatch = indexWordMap.get(term);
        System.out.println("Perfect Match size is "+perfectMatch + "... for term "+term);
        if(perfectMatch != null) {
            finalResult.addAll(perfectMatch.getAllIndexMatches(onlyFunctionName, onlyFileName));
            matchingDocIds.addAll(perfectMatch.getMatchingSourceCodeFileIds());
        }

        List<String> nearestWords = findNearestMatch(term);
        System.out.println("singleTermSearch nearestWords = " + nearestWords);
        if(nearestWords != null) {
            for (String nearestWord : nearestWords) {
                if (nearestWord != null) {
                    SourceCodeMatch nearMatch = indexWordMap.get(nearestWord);
                    finalResult.addAll(nearMatch.getAllIndexMatches(onlyFunctionName, onlyFileName));
                    matchingDocIds.addAll(nearMatch.getMatchingSourceCodeFileIds());
                }
            }
        }

        return finalResult;
    }

    /**
     * Finds the nearest 10 words close to the given term
     * @param term for which the nearest words is reqd.
     * @return the list of words nearest to the term
     */
    private List<String> findNearestMatch(String term) {
        //find nearest 10 words with this prefix
        List<String> nearestWords = Index.INDEX.getTrie().getNearestMatchingWords(term, 10);
        return nearestWords;
    }
}
