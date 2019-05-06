package model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static final String DUMMY_REVIEW = "Lorem Ipsum";

    public static String BASE_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3";
    public static String BASE_PROPERTY_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3/property";

    public static String KEYWORDS_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#keyword";


    public static String AUTHOR_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#authorName";
    public static String ARTICLE_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#articleName";
    public static String CONF_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#confName";
    public static String EDITION_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionName";
    public static String EDITION_YEAR_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionYear";
    public static String EDITION_CITY_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#editionCity";
    public static String JOURNAL_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#journalName";


    public static String VOLUME_YEAR_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeYear";
    public static String VOLUME_CITY_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeCity";
    public static String VOLUME_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volumeName";

    public static String REVIEWER_NAME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewerName";
    public static String REVIEWER_GIVES_REVIEW_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#gives";

    public static String REVIEW_CONTENT_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewContent";
    public static String REVIEW_DATE_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#reviewDate";

    public static String CITATIONS_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#cites";
    public static String REVIEW_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#review";
    public static String AUTHOR_WROTE_PAPER_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#wrote";
    public static String VOLUME_PART_OF_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#wrote";
    public static String PAPER_VOLUME_BASE_PROPERTY_URL = BASE_PROPERTY_URL + "#volume";



    public static String AUTHOR_BASE_URL = BASE_URL + "/author";
    public static String ARTICLE_BASE_URL = BASE_URL + "/article";
    public static String CONF_BASE_URL = BASE_URL + "/conf";
    public static String EDITION_BASE_URL = BASE_URL + "/edition";
    public static String JOURNAL_BASE_URL = BASE_URL + "/journal";
    public static String VOLUME_BASE_URL = BASE_URL + "/volume";
    public static String REVIEWER_BASE_URL = BASE_URL + "/reviewer";
    public static String REVIEW_BASE_URL = BASE_URL + "/review";


    public static final List<Integer> YEARS = Collections.unmodifiableList(
            Arrays.asList(2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012)
    );


    public static final List<String> CITIES = Collections.unmodifiableList(
            Arrays.asList("Barcelona", "New Delhi", "Melbourne", "Sydney", "Beejing", "Madrid", "Moscow")
    );
}
