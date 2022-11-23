package org.mti.tfidf.querying;

import java.util.List;

public class Document {

    public List<Term> terms;

    public Document(List<Term> terms)
    {
        this.terms = terms;
    }
}
