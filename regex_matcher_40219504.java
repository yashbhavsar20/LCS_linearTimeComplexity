import java.io.*;
import java.util.*;

class DataStructureNode {
    DataStructureNode[] children;
    boolean isEndOfWord;

    DataStructureNode() {
        children = new DataStructureNode[26]; // 26 English letters
        isEndOfWord = false;
    }
}

class TreeStructure {
    DataStructureNode root;

    TreeStructure() {
        root = new DataStructureNode();
    }

    /**
     *
     * Here I am inserting every String into the Trie data Structure it will store Strings in lexographical order
     */
    void insertion_in_tree(String word) {
        DataStructureNode curr = root;
        for (char c : word.toCharArray()) {
            int index = c - 'A'; // Convert to uppercase letter index
            if (index >= 0 && index < 26) {  // Check if the character is a valid English letter
                if (curr.children[index] == null) {
                    curr.children[index] = new DataStructureNode();
                }
                curr = curr.children[index];
            }
        }
        curr.isEndOfWord = true;
    }
}

public class regex_matcher_40219504 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));//Reads the Input file
        int n = Integer.parseInt(reader.readLine());
        TreeStructure trie = new TreeStructure();

        for (int i = 0; i < n; i++) {
            String word = reader.readLine().toUpperCase(); // Here I am converting everything to Upper case
            trie.insertion_in_tree(word);// Here I am Inserting every word into the Trie Data structure
        }

        String pattern = reader.readLine().toUpperCase(); // Converting Pattern to Uppercase
        reader.close();

        Set<String> matchingWords = new TreeSet<>();
        WordsMatched(trie.root, pattern, "", matchingWords);// Starting from the root of the tree and finding all the possible matching String using recursion
        // System.out.println(matchingWords);

        List<String> firstThreeWords = new ArrayList<>(matchingWords);
      //  System.out.println(firstThreeWords);

        String lcs;
        if (firstThreeWords.size() >= 3) {
            lcs = LCS_Three(firstThreeWords);// This function will call LCS for 3 strings
        } else if (firstThreeWords.size() == 2) {
            lcs = LCS_Two(firstThreeWords.get(0), firstThreeWords.get(1));// It will call LCS for 2 Strings
        } else if (firstThreeWords.size() == 1) {
            lcs = firstThreeWords.get(0);// If one word in the list then it will be only answer
        } else {
            lcs = "";
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));// It will store the LCS output in the output file
        writer.write(lcs);
        writer.close();
    }

    static void WordsMatched(DataStructureNode node, String pattern, String currentWord, Set<String> matchingWords) {
        /**
         * This is the base case for the recursion because if the pattern is empty and also if current word is empty the it will return
         * and function will add that word into the matched word.
         */
        if (pattern.isEmpty()) {
            if (node.isEndOfWord) {
                matchingWords.add(currentWord);
            }
            return;
        }
        /**
         * Here I made another base condition to check if there is no characters left in the pattern and there is only .and * pattern left
         * if there is only .... present then i will need to check the whole word matching with it or else it will be okay i can add my word in the
         * matching word.
         */

        char c = pattern.charAt(0);
        int length=pattern.length();
        int n=1;
        /**
         * I made 4 seperate cases for checking different pattern occurences
         */
        if(length==1)
        {
             n=0;
        }
        /**
         * Here this function checks for the current character is dot and next character is not *
         * So it will only  check for single . Traversing all the trie nodes.
         */
        if (c == '.' && pattern.charAt(n)!='*') {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    WordsMatched(node.children[i], pattern.substring(1), currentWord + (char) ('A' + i), matchingWords);
                }
            }
        }
        /**
         * Here this function checks for the current character is Character and next character is *
         * I have called three recursion calls from here
         *          * 1st call :- everytime it will check for character * pattern and it will add the current character in the current word
         *          * 2nd call :- everytime it will check for remaining characters present in the pattern after Character* pattern which matches my
         *          * current word
         *          * 3rd call :- iw will check for the zero occurence if character* is zero occurence.
         */
        else if (c != '.' && pattern.charAt(n)=='*') {
            int index = c - 'A';
            if (index >= 0 && index < 26 && node.children[index] != null) {
                WordsMatched(node.children[index], pattern, currentWord + c, matchingWords);
                WordsMatched(node.children[index], pattern.substring(2), currentWord + c, matchingWords);
                WordsMatched(node , pattern.substring(2), currentWord, matchingWords);
                }

                if (node.isEndOfWord) {
                    WordsMatched(node, pattern.substring(1), currentWord, matchingWords);
                }
            }
        /**
         * Here this function checks for the current character is dot and next character is  *
         * I have called three recursion calls from here
         * 1st call :- everytime it will check for * pattern and it will add the current character in the current word
         * 2nd call :- everytime it will check for remaining characters present in the pattern after .* pattern which matches my
         * current word
         * 3rd call :- iw will check for the zero occurence if .* is zero occurence
         **/

        else if (c == '.' && pattern.charAt(1)=='*') {
            for (int i = 0; i < 26; i++) {
                if (node.children[i] != null) {
                    WordsMatched(node.children[i], pattern, currentWord + (char) ('A' + i), matchingWords);
                    WordsMatched(node.children[i] , pattern.substring(2), currentWord+(char)('A'+i), matchingWords);
                    WordsMatched(node , pattern.substring(2), currentWord, matchingWords);
                }
            }
            if (node.isEndOfWord) {
                WordsMatched(node, pattern.substring(1), currentWord, matchingWords);
            }
        }
        /**
         * Here It will only check for Character matching between pattern and my current word present in the Trie Node.
         */
        else {
            int index = c - 'A';
            if (index >= 0 && index < 26 && node.children[index] != null) {
                WordsMatched(node.children[index], pattern.substring(1), currentWord + c, matchingWords);
            }
        }
    }

    static String LCS_Three(List<String> words) {
        int n = words.size();
        String w1 = words.get(0);
        String w2 = words.get(1);
        String w3 = words.get(2);
        String commonSubsequence = LCS_Three(w1,w2,w3);


        return commonSubsequence;
    }
    private static String LCS_Three(String word1, String word2, String word3) {
        int[][][] dp = new int[word1.length() + 1][word2.length() + 1][word3.length() + 1];

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                for (int k = 1; k <= word3.length(); k++) {
                    if (word1.charAt(i - 1) == word2.charAt(j - 1) && word1.charAt(i - 1) == word3.charAt(k - 1)) {
                        dp[i][j][k] = dp[i - 1][j - 1][k - 1] + 1;
                    } else {
                        dp[i][j][k] = Math.max(Math.max(dp[i - 1][j][k], dp[i][j - 1][k]), dp[i][j][k - 1]);
                    }
                }
            }
        }

        StringBuilder lcsBuilder = new StringBuilder();
        int i = word1.length();
        int j = word2.length();
        int k = word3.length();
        while (i > 0 && j > 0 && k > 0) {
            if (word1.charAt(i - 1) == word2.charAt(j - 1) && word1.charAt(i - 1) == word3.charAt(k - 1)) {
                lcsBuilder.insert(0, word1.charAt(i - 1));
                i--;
                j--;
                k--;
            } else if (dp[i - 1][j][k] >= dp[i][j - 1][k] && dp[i - 1][j][k] >= dp[i][j][k - 1]) {
                i--;
            } else if (dp[i][j - 1][k] >= dp[i - 1][j][k] && dp[i][j - 1][k] >= dp[i][j][k - 1]) {
                j--;
            } else {
                k--;
            }
        }

        return lcsBuilder.toString();
    }
    static String LCS_Two(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];
        for (int i = 1; i <= str1.length(); i++) {
            for (int j = 1; j <= str2.length(); j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder commonSubsequence = new StringBuilder();
        int i = str1.length(), j = str2.length();
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                commonSubsequence.insert(0, str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return commonSubsequence.toString();
    }
}
