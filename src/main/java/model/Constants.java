package model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Constants {

    public static String BASE_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3";
    public static String BASE_PROPERTY_URL = "http://semanticweb.org/ankushsharma/ontologies/2019/4/untitled-ontology-3/property";

    public static String AUTHOR_BASE_URL = BASE_URL + "/author";
    public static String ARTICLE_BASE_URL = BASE_URL + "/article";
    public static String CONF_BASE_URL = BASE_URL + "/conf";


    public static final List<Integer> YEARS = Collections.unmodifiableList(
            Arrays.asList(2000, 2001, 2002, 2003, 2004, 2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012)
    );


    public static final List<String> CITIES = Collections.unmodifiableList(
            Arrays.asList("Barcelona", "New Delhi", "Melbourne", "Sydney", "Beejing", "Madrid", "Moscow")
    );
}
