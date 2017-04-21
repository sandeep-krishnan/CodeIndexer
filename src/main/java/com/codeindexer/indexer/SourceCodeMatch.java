package com.codeindexer.indexer;

import com.codeindexer.model.SourceCodeFile;

import java.util.*;

/**
 *  Each Indexed word is mapped to an instance of SourceCodeMatch
 */
public class SourceCodeMatch {
    private String word;
    private Map<SourceCodeFile, Set<IndexMatch>> sourceFileToIndexMap
            = new HashMap<>();

    public SourceCodeMatch(String word) {
        this.word = word;
    }

    public Map<SourceCodeFile, Set<IndexMatch>> getSourceFileToIndexMap() {
        return sourceFileToIndexMap;
    }

    /**
     * Adds a source code file & line number to the word
     * i.e. the SourceCodeMatch instance
     * @param sourceCodeFile the file details of where the word is
     * @param position the line number of where the word is
     */
    public void addContentToSourceCodeIndex(SourceCodeFile sourceCodeFile, long position) {
        Set<IndexMatch> indexMatches = sourceFileToIndexMap.get(sourceCodeFile);
        if(indexMatches == null) {
            sourceFileToIndexMap.put(sourceCodeFile, new TreeSet<>());
            indexMatches = sourceFileToIndexMap.get(sourceCodeFile);
        }
        indexMatches.add(new IndexMatch(sourceCodeFile, position));
    }

    /**
     * Adds the file name to index
     * @param sourceCodeFile the file who's name is indexed
     */
    public void addFileNameToSourceCodeIndex(SourceCodeFile sourceCodeFile) {
        Set<IndexMatch> indexMatches = sourceFileToIndexMap.get(sourceCodeFile);
        if(indexMatches == null) {
            sourceFileToIndexMap.put(sourceCodeFile, new TreeSet<>());
            indexMatches = sourceFileToIndexMap.get(sourceCodeFile);
        }
        indexMatches.add(new IndexMatch(sourceCodeFile, true));
    }

    /**
     * Finds all the index matches based on the criteria
     * @param onlyFunctionName is true only if function names need to be searched
     * @param onlyFileName is true only is file name needs to be searched
     * @return a Set of IndexMatch based on the criteria
     */
    public Set<IndexMatch> getAllIndexMatches(boolean onlyFunctionName, boolean onlyFileName) {
        Set<IndexMatch> matches = new TreeSet<>();

        for(Set<IndexMatch> indexMatchSet : sourceFileToIndexMap.values()) {
            if(!onlyFunctionName && !onlyFileName) {
                matches.addAll(indexMatchSet);
            } else
                for (IndexMatch indexMatch : indexMatchSet) {
                    if(onlyFileName && indexMatch.getIsFileName()) {
                        matches.add(indexMatch);
                    } else if(onlyFunctionName && indexMatch.getIsFunction()) {
                        matches.add(indexMatch);
                    }
                }
        }

        return matches;
    }

    public Set<SourceCodeFile> getMatchingSourceCodeFileIds() {
        return sourceFileToIndexMap.keySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceCodeMatch that = (SourceCodeMatch) o;

        return word != null ? word.equals(that.word) : that.word == null;
    }

    @Override
    public int hashCode() {
        return word != null ? word.hashCode() : 0;
    }
}
