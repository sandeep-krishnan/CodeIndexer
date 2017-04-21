package com.codeindexer.indexer;

import com.codeindexer.model.SourceCodeFile;
import com.codeindexer.util.StringHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by sandeep on 11/04/17.
 */

public class SourceCodeIndexer {
    private String rootSourceDirectory;

    public SourceCodeIndexer(String rootSourceDirectory) {
        this.rootSourceDirectory = rootSourceDirectory;
    }

    /**
     * Builds source code files index
     */
    public void buildSourceCodeFileIndex() {
        List<Path> filesInSourceCodeDirectory = new ArrayList<>();
        try {
            Files.find(Paths.get(".", rootSourceDirectory),
                    Integer.MAX_VALUE,
                    (filePath, fileAttr) -> true)
                    .collect(Collectors.toCollection(() -> filesInSourceCodeDirectory));
            filesInSourceCodeDirectory.remove(Paths.get(rootSourceDirectory));
            buildFileDirectoryIndex(filesInSourceCodeDirectory);
        } catch (IOException e1) {
            System.err.println("Error reading root source directory "+rootSourceDirectory);
            e1.printStackTrace();
            throw new RuntimeException("Error reading source code directory "+rootSourceDirectory);
        }
    }

    /**
     * Builds file index from the list of files
     * @param filesInSourceCodeDirectory the list of source code files
     */
    private void buildFileDirectoryIndex(List<Path> filesInSourceCodeDirectory) {
        long index = 0L;

        for (Path path : filesInSourceCodeDirectory) {
            boolean isDir = false;
            if (Files.isDirectory(path)) {
                isDir = true;
            }
            String fileName = path.getFileName().toString();
            long currIndex = index++;
            SourceCodeFile sourceCodeFile = new SourceCodeFile(currIndex, fileName, path, isDir);
            Index.INDEX.addToFileIndex(currIndex, sourceCodeFile);
        }

    }

    /**
     * Indexes the source code files
     */
    private void indexSourceCodeFiles() {
        synchronized (Index.INDEX.getSourceCodeFileMap()) {
            for (SourceCodeFile sourceCodeFile : Index.INDEX.getSourceCodeFileMap().values()) {
                indexFileContent(sourceCodeFile);
                indexFileName(sourceCodeFile);
            }
        }
    }

    /**
     * Indexes a single source code files
     * @param sourceCodeFile the file to be indexed
     */
    private void indexFileName(SourceCodeFile sourceCodeFile) {
        String word = sourceCodeFile.getName();
        for(int i = 0; i < word.length(); i++) {
            Index.INDEX.addFileNameToSourceCodeIndex(word.substring(i), sourceCodeFile);
        }
    }

    /**
     * Indexes the content of the source code file
     * @param sourceCodeFile the file to be indexed
     */
    private void indexFileContent(SourceCodeFile sourceCodeFile) {
        if(!sourceCodeFile.isDir()) {
            List<String> fileContent = new ArrayList<>();
            try (Stream<String> stream = Files.lines(sourceCodeFile.getPath())) {
                fileContent = stream.collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("Exception while parsing " + sourceCodeFile.getPath());
            }
            sourceCodeFile.setFileContent(fileContent);
            long lineNumber = 1;
            for (String line : fileContent) {
                indexLine(sourceCodeFile, line, lineNumber++);
            }
        }
    }

    /**
     * Indexes the line read from the source code file
     * @param sourceCodeFile the file to which the line belongs
     * @param line the content of the line
     * @param lineNumber the number of this line
     */
    private void indexLine(SourceCodeFile sourceCodeFile, String line, long lineNumber) {
        if (line == null || line.isEmpty())
            return;

        line = StringHelper.removeUnwantedCharacters(line);

        String[] words = line.split("\\s+");
        for (String word : words) {
            if(word != null && !word.isEmpty()) {
                for(int i = 0; i < word.length(); i++) {
                    Index.INDEX.addContentToSourceCodeIndex(word.substring(i), sourceCodeFile, lineNumber);
                }
            }
        }
    }

    /**
     * Entry point for indexing
     */
    public void index() {
        buildSourceCodeFileIndex();
        indexSourceCodeFiles();
    }
}
