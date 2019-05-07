package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static final String DUMMY_REVIEW = "Lorem Ipsum";

    public static final String BASE_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3";
    public static final String BASE_PROPERTY_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3/property";

    public static final String KEYWORDS_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#keyword";
    public static final String RDFS_BASE_PROPERTY_URL = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";

    public static final String AUTHOR_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#authorName";
    public static final String ARTICLE_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#articleName";
    public static final String CONF_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#confName";
    public static final String EDITION_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionName";
    public static final String EDITION_YEAR_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionYear";
    public static final String EDITION_CITY_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionCity";
    public static final String JOURNAL_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#journalName";


    public static final String VOLUME_YEAR_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeYear";
    public static final String VOLUME_CITY_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeCity";
    public static final String VOLUME_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeName";

    public static final String REVIEWER_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewerName";
    public static final String REVIEWER_GIVES_REVIEW_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#gives";

    public static final String REVIEW_CONTENT_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewContent";
    public static final String REVIEW_DATE_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewDate";

    public static final String CITATIONS_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#cites";
    public static final String REVIEW_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#review";
    public static final String AUTHOR_WROTE_PAPER_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#wrote";
    public static final String VOLUME_PART_OF_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volume_part_of";
    public static final String PAPER_VOLUME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volume";



    public static final String AUTHOR_BASE_URL = BASE_URL + "/author";
    public static final String ARTICLE_BASE_URL = BASE_URL + "/article";
    public static final String CONF_BASE_URL = BASE_URL + "/conf";
    public static final String EDITION_BASE_URL = BASE_URL + "/edition";
    public static final String JOURNAL_BASE_URL = BASE_URL + "/journal";
    public static final String VOLUME_BASE_URL = BASE_URL + "/volume";
    public static final String REVIEWER_BASE_URL = BASE_URL + "/reviewer";
    public static final String REVIEW_BASE_URL = BASE_URL + "/review";


    public static final List<Integer> YEARS = Collections.unmodifiableList(
            Arrays.asList(2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012)
    );


    public static final List<String> CITIES = Collections.unmodifiableList(
            Arrays.asList("Barcelona", "New Delhi", "Melbourne", "Sydney", "Beejing", "Madrid", "Moscow")
    );
}
