package com.example.cuckoofilter;


import java.util.Map;

public class Trie {
    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }

    // Insert a word into the Trie
    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            node.children.putIfAbsent(c, new TrieNode());
            node = node.children.get(c);
        }
        node.isEndOfWord = true;
    }

    // Search for a word in the Trie
    public boolean search(String word) {
        TrieNode node = searchNode(word);
        return node != null && node.isEndOfWord;
    }

    // Check if any word in the Trie starts with the given prefix
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    // Delete a word from the Trie
    public void delete(String word) {
        delete(root, word, 0);
    }

    private boolean delete(TrieNode node, String word, int index) {
        if (index == word.length()) {
            if (!node.isEndOfWord) {
                return false;
            }
            node.isEndOfWord = false;
            return node.children.isEmpty();
        }

        char c = word.charAt(index);
        TrieNode childNode = node.children.get(c);
        if (childNode == null) {
            return false;
        }

        boolean shouldDeleteChild = delete(childNode, word, index + 1);

        if (shouldDeleteChild) {
            node.children.remove(c);
            return node.children.isEmpty() && !node.isEndOfWord;
        }

        return false;
    }

    private TrieNode searchNode(String str) {
        TrieNode node = root;
        for (char c : str.toCharArray()) {
            node = node.children.get(c);
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    // Utility method to print all words in the Trie
    public void printAllWords() {
        StringBuilder sb = new StringBuilder();
        printAllWords(root, sb);
    }

    private void printAllWords(TrieNode node, StringBuilder prefix) {
        if (node.isEndOfWord) {
            System.out.print(prefix.toString()+"|");
        }

        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            prefix.append(entry.getKey());
            printAllWords(entry.getValue(), prefix);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }
}
