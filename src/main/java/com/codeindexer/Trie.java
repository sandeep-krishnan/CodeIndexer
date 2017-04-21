package com.codeindexer;

import java.util.*;

/**
 * Implementation of Trie
 */
public class Trie {
    private TrieNode root = new TrieNode();
    class TrieNode{
        private Character character;
        private Map<Character, TrieNode> nodeMap = new HashMap<>();
        private boolean isLeafNode;

        public TrieNode() {}

        public TrieNode(Character character){
            this.character = character;
        }

        public Map<Character, TrieNode> getNodeMap() {
            return nodeMap;
        }

        public boolean isLeafNode() {
            return isLeafNode;
        }

        public void setLeafNode(boolean leafNode) {
            isLeafNode = leafNode;
        }

        @Override
        public String toString() {
            return character + " || " + isLeafNode;
        }
    }

    /**
     * Inserts the given word into Trie
     * @param word the String to be inserted
     */
    public void insert(String word){
        if(word == null || word.length() == 0)
            return;

        TrieNode node = root;
        for (Character character : word.toCharArray()){
            Map<Character, TrieNode> trieNodeMap = node.getNodeMap();
            if (!trieNodeMap.containsKey(character)){
                TrieNode newNode = new TrieNode(character);
                trieNodeMap.put(character, newNode);
            }
            node = trieNodeMap.get(character);
        }
        node.setLeafNode(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        printNode(root, sb);
        return sb.toString();
    }

    /**
     * Prints the node
     * @param node to be printed
     * @param stringBuilder the temporary storage for the string
     */
    private void printNode(TrieNode node, StringBuilder stringBuilder){
        System.out.println(node);
        for (TrieNode temp : node.getNodeMap().values()){
            printNode(temp, stringBuilder);
        }
    }

    /**
     * Gets the nearest matching words with the input as prefix
     * @param input is the prefix based on which the nearest words
     *              are searched
     * @param max is the max number of results to be returned
     * @return a List of String of nearest matching words
     */
    public List<String> getNearestMatchingWords(String input, int max) {
        TrieNode node = root;
        int index = 0;

        while (node != null && index < input.length()) {
            node = node.getNodeMap().get(input.charAt(index));
            if(node != null) {
                index++;
            }
        }

        if(node == null)
            return null;

        String prefix = input.substring(0, index);
        Set<String> resultSet = findChildWords(node, prefix, max);
        if(node.isLeafNode) {
            resultSet.add(prefix);
        }

        List<String> nearestWords = new ArrayList<String>(resultSet)   ;
        if(nearestWords.size() > max) {
            nearestWords = nearestWords.subList(0, max);
        }
        return nearestWords;
    }

    /**
     * Finds nearest child words
     * @param trieNode the node from which child are required
     * @param prefix the prefix of the parent node
     * @param max the maximum number of results required
     * @return a Set of String of child words
     */
    private Set<String> findChildWords(TrieNode trieNode, String prefix, int max) {
        Set<String> resultSet = new TreeSet<>();
        StringBuffer sb  = new StringBuffer(prefix);
        for(Character currChar : trieNode.getNodeMap().keySet()) {
            TrieNode child = trieNode.getNodeMap().get(currChar);
            String newWord = prefix + currChar;
            if(child.isLeafNode) {
                resultSet.add(newWord);
            }
            resultSet.addAll(findChildWords(child, newWord, max));
            if(resultSet.size() > max)
                return resultSet;
        }

        return resultSet;
    }
}
