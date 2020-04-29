package com.muppet.lifepartner.util;


import com.muppet.lifepartner.mode.LetterCityMode;

import java.util.Comparator;

public class PinyinComparator implements Comparator<LetterCityMode> {
    @Override
    public int compare(LetterCityMode o1, LetterCityMode o2) {
        if (o1.getLetters().equals("@")
                || o2.getLetters().equals("#")) {
            return 1;
        } else if (o1.getLetters().equals("#")
                || o2.getLetters().equals("@")) {
            return -1;
        } else {
            return o1.getLetters().compareTo(o2.getLetters());
        }
    }
}
