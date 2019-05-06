import lombok.val;
import model.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        File reviewersArticles = asFile(FileName.REVIEWERS_ARTICLES);


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
        List<String> keywords = read(keywordsFile).collect(Collectors.toList());
        Stream<Volume> volumes = read(volumesFile).map(record -> {
                                                    String volumeName = record.split(",")[0];
                                                    Integer year = YEARS.get(generateRandomIntegersBetween(0, YEARS.size() - 1));
                                                    String city = CITIES.get(generateRandomIntegersBetween(0, CITIES.size() - 1));
                                                    return new Volume(volumeName, year, city);
                                                });

        Stream<ReviewerArticle> reviewerArticles = read(reviewersArticles).map(record -> {
                                                    try {
                                                        String[] arrayValues = record.split(",");
                                                        return new ReviewerArticle(arrayValues[0], arrayValues[1]);
                                                    }
                                                    catch (final Exception ex) {
                                                        //pass
                                                        return null;
                                                    }
                                                }).filter(Objects::nonNull);

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
        Property reviewerNameProp = model.createProperty(REVIEWER_NAME_BASE_PROPERTY_URL);
        Property reviewContentProp = model.createProperty(REVIEW_CONTENT_BASE_PROPERTY_URL);
        Property reviewDateProp = model.createProperty(REVIEW_DATE_BASE_PROPERTY_URL);
        Property keywordsProp = model.createProperty(KEYWORDS_BASE_PROPERTY_URL);

        Property givesProp = model.createProperty(REVIEWER_GIVES_REVIEW_BASE_PROPERTY_URL);



        authors.forEach(author -> {
            val authorName = author.getName();
            model.createResource(AUTHOR_BASE_URL + "/" + asUtf8(authorName))
                 .addLiteral(authorNameProp, authorName);
        });


        articles.forEach(article -> {
            val articleName = article.getName();
            Resource articleResource = model.createResource(ARTICLE_BASE_URL + "/" + asUtf8(articleName))
                                        .addLiteral(articleNameProp, articleName);

            int randomNum = generateRandomIntegersBetween(0, keywords.size() - 1);
            for(int i = 0 ; i < randomNum ; i ++) {
                articleResource.addLiteral(keywordsProp, keywords.get(i));
            }
        });

        conferences.forEach(conf -> {
            val confName = conf.getName();
            model.createResource(CONF_BASE_URL + "/" + asUtf8(confName))
                 .addLiteral(confNameProp, confName);
        });

        editions.forEach(edition -> {
            String editionName = edition.getName();
            model.createResource(EDITION_BASE_URL + "/" + asUtf8(editionName))
                 .addLiteral(editionNameProp, editionName)
                 .addLiteral(editionYearProp, String.valueOf(edition.getYear()))
                 .addLiteral(editionCityProp, String.valueOf(edition.getCity()));
        });

        journals.forEach(journal -> {
            String journalName = journal.getName();
            model.createResource(JOURNAL_BASE_URL + "/" + asUtf8(journalName))
                    .addLiteral(journalNameProp, journalName);
        });
        volumes.forEach(volume-> {
            String volumeName = volume.getName();
            model.createResource(VOLUME_BASE_URL + "/" + asUtf8(volumeName))
                 .addLiteral(volumeNameProp, volumeName)
                 .addLiteral(volumeYearProp, String.valueOf(volume.getYear()))
                 .addLiteral(volumeCityProp, String.valueOf(volume.getCity()));
        });
//
        reviewerArticles.forEach(reviewerArticle -> {
            String reviewerName = reviewerArticle.getAuthorName();
            String paperName = reviewerArticle.getPaperName();
            Review review = new Review(DUMMY_REVIEW, Util.randomDate());

            String reviewUri =  REVIEW_BASE_URL + "/" + asUtf8(review.getContent());
            Resource reviewResource = model.createResource(reviewUri)
                 .addLiteral(reviewContentProp, review.getContent())
                 .addLiteral(reviewDateProp, review.getDate());

            Resource reviewerResource = model.createResource(REVIEWER_BASE_URL + "/" + asUtf8(reviewerName))
                                             .addLiteral(reviewerNameProp, reviewerName);

            model.add(model.createStatement(reviewerResource, givesProp, reviewResource));
        });

        model.write(new FileOutputStream(asFile(FileName.OUTPUT)), "N-TRIPLE");

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
