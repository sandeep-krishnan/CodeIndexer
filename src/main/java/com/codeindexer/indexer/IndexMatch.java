package com.codeindexer.indexer;

import com.codeindexer.model.SourceCodeFile;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The indexes are stored as IndexMatch instances
 */
public class IndexMatch implements Comparable<IndexMatch>{
    private SourceCodeFile sourceCodeFile;
    private long lineNumber;
    private boolean isFunction = false;
    private boolean isFileName = false;

    public IndexMatch(SourceCodeFile sourceCodeFile, long lineNumber) {
        this.sourceCodeFile = sourceCodeFile;
        this.lineNumber = lineNumber;
    }

    public IndexMatch(SourceCodeFile sourceCodeFile, boolean isFileName) {
        this.sourceCodeFile = sourceCodeFile;
        this.isFileName = isFileName;
    }

    @JsonIgnore
    public SourceCodeFile getSourceCodeFile() {
        return sourceCodeFile;
    }

    public String getFile() {
        return sourceCodeFile.getPath().toString();
    }

    public boolean getIsFileName () {
        return isFileName;
    }

    public long getLineNumber() {
        return lineNumber;
    }

    public boolean getIsFunction() {
        return isFunction;
    }

    public String getSnippet() {
        if(lineNumber != 0) {
            return sourceCodeFile.getFileContent().get((int)lineNumber - 1);
        }
        return "";
    }

    @Override
    public String toString() {
        return "FilePosition{" +
                "document=" + sourceCodeFile.getPath() +
                ", lineNumber=" + lineNumber +
                ", isFunction=" + isFunction +
                ", isFileName=" + isFileName +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexMatch that = (IndexMatch) o;

        if (getLineNumber() != that.getLineNumber()) return false;
        if (isFunction != that.isFunction) return false;
        if (isFileName != that.isFileName) return false;
        return getSourceCodeFile() != null ? getSourceCodeFile().equals(that.getSourceCodeFile()) : that.getSourceCodeFile() == null;
    }

    @Override
    public int hashCode() {
        int result = getSourceCodeFile() != null ? getSourceCodeFile().hashCode() : 0;
        result = 31 * result + (int) (getLineNumber() ^ (getLineNumber() >>> 32));
        result = 31 * result + (isFunction ? 1 : 0);
        result = 31 * result + (isFileName ? 1 : 0);
        return result;
    }

    @Override
    public int compareTo(IndexMatch o) {
        int fileDiff = sourceCodeFile.getPath().toString().compareTo(
                o.getSourceCodeFile().getPath().toString());
        if(fileDiff == 0) {
            return (int)(lineNumber - o.getLineNumber());
        }
        return fileDiff;
    }
}
