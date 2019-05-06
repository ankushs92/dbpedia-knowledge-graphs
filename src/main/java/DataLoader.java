import lombok.val;
import model.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.VCARD;
import org.apache.jena.vocabulary.VCARD4;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.net.URLEncoder.encode;
import static java.util.stream.Collectors.*;
import static model.Constants.*;

public class DataLoader {


    public static void main(String[] args) throws IOException {
        File authorsFile = asFile(FileName.AUTHORS);
        File articlesFile = asFile(FileName.ARTICLES);
        File confFile = asFile(FileName.CONFERENCE);
        File editionFile = asFile(FileName.EDITION);
        File journalsFile = asFile(FileName.JOURNAL);
        File keywordsFile = asFile(FileName.KEYWORDS);
        File volumesFile = asFile(FileName.VOLUME);
//        File reviewersFile = asFile(FileName.REVIEWERS);


        Stream<Author> authors = read(authorsFile).map(Author::new).limit(10);
        Stream<Article> articles = read(articlesFile).map(record -> new Article(record.split(",")[0])).limit(10);
        Stream<Conference> conferences = read(confFile).map(Conference::new);
        Stream<Edition> editions = read(editionFile).map(record -> {
            String editionName = record.split(",")[0];
            Integer year = YEARS.get(generateRandomIntegersBetween(0, YEARS.size() - 1));
            String city = CITIES.get(generateRandomIntegersBetween(0, CITIES.size() - 1));
            return new Edition(editionName, year, city);
        });

        Stream<Journal> journals = read(journalsFile).map(Journal::new);
        Stream<String> keywords = read(keywordsFile);
        Stream<Volume> volumes = read(volumesFile).map(record -> {
            String volumeName = record.split(",")[0];
            Integer year = YEARS.get(generateRandomIntegersBetween(0, YEARS.size() - 1));
            String city = CITIES.get(generateRandomIntegersBetween(0, CITIES.size() - 1));
            return new Volume(volumeName, year, city);
        });


        //Jena
        Model model = ModelFactory.createDefaultModel();
        Property articleNameProp = model.createProperty(ARTICLE_NAME_BASE_PROPERTY_URL);
        Property authorNameProp = model.createProperty(AUTHOR_NAME_BASE_PROPERTY_URL);
        Property confNameProp = model.createProperty(CONF_NAME_BASE_PROPERTY_URL);
        Property editionNameProp = model.createProperty(EDITION_NAME_BASE_PROPERTY_URL);
        Property editionYearProp = model.createProperty(EDITION_YEAR_BASE_PROPERTY_URL);
        Property editionCityProp = model.createProperty(EDITION_CITY_BASE_PROPERTY_URL);
        Property journalNameProp = model.createProperty(JOURNAL_NAME_BASE_PROPERTY_URL);
        Property volumeNameProp = model.createProperty(VOLUME_NAME_BASE_PROPERTY_URL);
        Property volumeYearProp = model.createProperty(VOLUME_YEAR_BASE_PROPERTY_URL);
        Property volumeCityProp = model.createProperty(VOLUME_CITY_BASE_PROPERTY_URL);



//        authors.forEach(author -> {
//            val authorName = author.getName();
//            model.createResource(AUTHOR_BASE_URL + "/" + asUtf8(authorName))
//                 .addLiteral(authorNameProp, authorName);
//        });
//

//        articles.forEach(article -> {
//            val articleName = article.getName();
//            model.createResource(ARTICLE_BASE_URL + "/" + asUtf8(articleName))
//                 .addLiteral(articleNameProp, articleName);
//        });

//        conferences.forEach(conf -> {
//            val confName = conf.getName();
//            model.createResource(CONF_BASE_URL + "/" + asUtf8(confName))
//                 .addLiteral(confNameProp, confName);
//        });
//
//        editions.forEach(edition -> {
//            String editionName = edition.getName();
//            model.createResource(EDITION_BASE_URL + "/" + asUtf8(editionName))
//                 .addProperty(editionNameProp, editionName)
//                 .addProperty(editionYearProp, String.valueOf(edition.getYear()))
//                 .addProperty(editionCityProp, String.valueOf(edition.getCity()));
//        });

//        journals.forEach(journal -> {
//            String journalName = journal.getName();
//            model.createResource(EDITION_BASE_URL + "/" + asUtf8(journalName))
//                    .addProperty(journalNameProp, journalName);
//        });
        volumes.forEach(volume-> {
            String volumeName = volume.getName();
            model.createResource(EDITION_BASE_URL + "/" + asUtf8(volumeName))
                 .addProperty(volumeNameProp, volumeName)
                 .addProperty(volumeYearProp, String.valueOf(volume.getYear()))
                 .addProperty(volumeCityProp, String.valueOf(volume.getCity()));
        });


        model.write(System.out);

    }

    private static Stream<String> read(final File file) throws IOException {
       return Files
               .lines(Paths.get(file.getPath()))
               .skip(1);
    }

    private static File asFile(final String fileName) {
        val absFilePath = DataLoader.class.getClassLoader().getResource(fileName).getFile();
        return new File(absFilePath);
    }
    private static final Random random = new Random();

    private static int generateRandomIntegersBetween(final int from, final int to) {
        //Set Will ensure uniqueness.
        final List<Integer> result = new ArrayList<>();
        int range = to - from + 1;

        while(result.size() < 1) {
            result.add(random.nextInt(range) + from);
        }
        return result.get(0);
    }
    private static String asUtf8(final String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            System.out.println(e);
            return "";
        }
    }
}
