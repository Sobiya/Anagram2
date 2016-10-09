package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import static android.R.attr.key;
import static java.util.Arrays.sort;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private HashSet<String> wordSet;
    protected HashMap<String, ArrayList> lettersToWord;
    private HashMap<Integer, ArrayList> sizeToWords;
    private int wordLength;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        sizeToWords = new HashMap<>();
        wordLength = DEFAULT_WORD_LENGTH;


        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);

            String key = sortLetters(word);

            if(!lettersToWord.containsKey(key)){
                lettersToWord.put(key, new ArrayList<String>());
                lettersToWord.get(key).add(word);
            }
            else {
                lettersToWord.get(key).add(word);
            }

            if (!sizeToWords.containsKey(word.length())) {

                sizeToWords.put(word.length(), new ArrayList<String>() );
                sizeToWords.get(word.length()).add(word);
            }
            else {
                sizeToWords.get(word.length()).add(word);
            }

        }
    }

    public boolean isGoodWord(String word, String base) {

        return (wordSet.contains(word) && !word.contains(base));
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String temp = sortLetters(targetWord);

        for(String word : wordSet){
            if(temp.length() == word.length() && sortLetters(word).equalsIgnoreCase(temp))
                result.add(word);
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();

        for(char i = 'a'; i < ('a'+26) ; i++){
            String temp = sortLetters(word+i);
            if(lettersToWord.containsKey(temp)){
                result.addAll(lettersToWord.get(temp));
            }
        }


        return result;
    }

    public String pickGoodStarterWord() {

        ArrayList<String> temp = new ArrayList<>(wordSet);
        Collections.shuffle(temp);

        for(String word : temp){
            if(getAnagramsWithOneMoreLetter(word).size() >= MIN_NUM_ANAGRAMS && word.length() == wordLength) {
                if (wordLength != MAX_WORD_LENGTH) {
                    wordLength++;
                }
                return word;
            }
        }

        return "ERROR";
    }

    /****Helper Functions***/

    protected String sortLetters(String word) {

        char[] temp = word.toCharArray();
        Arrays.sort(temp);
        String sorted = new String(temp);
        return sorted;

    }
}
