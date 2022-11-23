package org.mti.tfidf.querying;

import java.util.ArrayList;
import java.util.List;

public class Term {

    public String value;
    public double frequency;
    public List<Integer> occurences;

    public Term(String value, double frequency, List<Integer> occurences)
    {
        this.value = value;
        this.frequency = frequency;
        this.occurences = occurences;
    }
}
