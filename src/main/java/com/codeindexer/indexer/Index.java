package com.codeindexer.indexer;

import com.codeindexer.Trie;
import com.codeindexer.model.SourceCodeFile;

import java.util.*;

/**
 * The core class having details of the index
 * Is a Singleton
 */
public enum Index {
    INDEX;

    private Map<String, SourceCodeMatch> indexSourceCodeWordMap;
    private Map<Long, SourceCodeFile> sourceCodeFileMap;
    private Trie trie;

    /**
     * Initializes the required data structures
     */
    private Index() {
        indexSourceCodeWordMap = new HashMap<>();
        sourceCodeFileMap = new HashMap<>();
        trie = new Trie();
    }

    /**
     * Initializes the source code match
     * @param term used as the key to the SourceCodeMatch
     * @return the SourceCodeMatch which was created
     */
    private SourceCodeMatch initializeSourceCodeMatch(String term) {
        SourceCodeMatch sourceCodeMatch = new SourceCodeMatch(term);
        indexSourceCodeWordMap.put(term, sourceCodeMatch);
        return sourceCodeMatch;
    }

    /**
     * Adds the content to the source code index
     * @param term used to identify the indexed word
     * @param sourceCodeFile the file where the term is present
     * @param position the line number where the term exists
     */
    public void addContentToSourceCodeIndex(String term, SourceCodeFile sourceCodeFile, long position) {
        term = term.toLowerCase();
        trie.insert(term);
        SourceCodeMatch sourceCodeMatch = indexSourceCodeWordMap.get(term);

        if(sourceCodeMatch == null) {
            sourceCodeMatch = initializeSourceCodeMatch(term);
        }

        sourceCodeMatch.addContentToSourceCodeIndex(sourceCodeFile, position);
    }

    /**
     * Indexes the file name
     * @param name the name of the file
     * @param sourceCodeFile the reference to the source code file
     */
    public void addFileNameToSourceCodeIndex(String name, SourceCodeFile sourceCodeFile) {
        name = name.toLowerCase();
        trie.insert(name);
        SourceCodeMatch sourceCodeMatch = indexSourceCodeWordMap.get(name);

        if(sourceCodeMatch == null) {
            sourceCodeMatch = initializeSourceCodeMatch(name);
        }

        sourceCodeMatch.addFileNameToSourceCodeIndex(sourceCodeFile);
    }

    /**
     * Adds the file to the file index
     * @param index the primary key of the file
     * @param sourceCodeFile the reference to the file
     */
    public void addToFileIndex(Long index, SourceCodeFile sourceCodeFile) {
        synchronized (sourceCodeFileMap) {
            sourceCodeFileMap.put(index, sourceCodeFile);
        }
    }

    public Map<Long, SourceCodeFile> getSourceCodeFileMap() {
        return sourceCodeFileMap;
    }

    public Map<String, SourceCodeMatch> getIndexSourceCodeWordMap() {
        return indexSourceCodeWordMap;
    }

    public Trie getTrie() {
        return trie;
    }

    @Override
    public String toString() {
        return indexSourceCodeWordMap.toString();
    }
}
