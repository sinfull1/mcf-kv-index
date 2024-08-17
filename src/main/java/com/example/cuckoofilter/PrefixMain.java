
package com.example.cuckoofilter;

public class PrefixMain {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("apple");
        trie.insert("app");
        trie.insert("bat");
        trie.insert("ball");
        trie.insert("batman");

        System.out.println("Words in the Trie:");
        trie.printAllWords();

        String searchWord = "app";
        System.out.println("\nSearching for \"" + searchWord + "\":");
        System.out.println(trie.search(searchWord) ? "Found" : "Not Found");

        String prefix = "ba";
        System.out.println("\nPrefix \"" + prefix + "\" exists:");
        System.out.println(trie.startsWith(prefix) ? "Yes" : "No");

        String deleteWord = "app";
        trie.delete(deleteWord);
        System.out.println("\nWords in the Trie after deleting \"" + deleteWord + "\":");
        trie.printAllWords();
    }
}
