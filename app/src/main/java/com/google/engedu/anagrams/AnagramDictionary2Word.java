package com.google.engedu.anagrams;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class AnagramDictionary2Word extends AnagramDictionary {

    public AnagramDictionary2Word(InputStream wordListStream) throws IOException {
        super(wordListStream);
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word){
        ArrayList<String> result = new ArrayList<String>();

        for(char i = 'a'; i < ('a'+26) ; i++) {
            for (char j = 'a'; j < ('a' + 26); j++) {
                String temp = sortLetters(word + i + j);
                if (lettersToWord.containsKey(temp)) {
                    result.addAll(lettersToWord.get(temp));
                }
            }
        }
        return result;
    }
}
