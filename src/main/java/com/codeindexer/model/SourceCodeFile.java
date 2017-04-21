package com.codeindexer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * SourceCodeFile stores contents of all the files for easy access
 */
public class SourceCodeFile {
    private long docId;
    private String name;
    private Path path;
    private boolean isDir = false;
    private List<String> fileContent = new ArrayList<>();

    public SourceCodeFile(long docId, String name, Path path, boolean isDir) {
        this.docId = docId;
        this.path = path;
        this.name = name;
        this.isDir = isDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public long getDocId() {
        return docId;
    }

    public void setDocId(long docId) {
        this.docId = docId;
    }

    @JsonIgnore
    public List<String> getFileContent() {
        return fileContent;
    }

    public void setFileContent(List<String> fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceCodeFile that = (SourceCodeFile) o;

        if (docId != that.docId) return false;
        if (isDir != that.isDir) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (docId ^ (docId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (isDir ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SourceCodeFile{" +
                "docId=" + docId +
                ", name='" + name + '\'' +
                ", path=" + path +
                ", isDir=" + isDir +
                '}';
    }
}
