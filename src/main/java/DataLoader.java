import lombok.val;
import model.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
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
        val authorsFile = asFile(FileName.AUTHORS);
        val articlesFile = asFile(FileName.ARTICLES);
        val confFile = asFile(FileName.CONFERENCE);
        val editionFile = asFile(FileName.EDITION);
        val journalsFile = asFile(FileName.JOURNAL);
        val keywordsFile = asFile(FileName.KEYWORDS);


        val authors = read(authorsFile).map(Author::new).limit(10);
        val articles = read(articlesFile).map(record -> new Article(record.split(",")[0]));
        val conferences = read(confFile).map(Conference::new);
        val editions = read(editionFile).map(record -> {
            val editionName = record.split(",")[0];
            return Edition
                    .builder()
                        .name(editionName)
                        .year(YEARS.get(generateRandomIntegersBetween(0, YEARS.size() - 1)))
                        .city(CITIES.get(generateRandomIntegersBetween(0, CITIES.size() - 1)))
                    .build();
        });
        val journals = read(journalsFile).map(Journal::new);
        val keywords = read(keywordsFile).map(Keyword::new);



        //Jena
        val model = ModelFactory.createDefaultModel();
        val articleNameProp = model.createProperty(BASE_PROPERTY_URL);

        authors.forEach(author -> {
            val authorName = author.getName();
            model.createResource(AUTHOR_BASE_URL + "/" + asUtf8(authorName))
                 .addLiteral(articleNameProp, authorName);
        });


        articles.forEach(article -> {
            val articleName = article.getName();
            model.createResource(ARTICLE_BASE_URL + "/" + asUtf8(articleName))
                 .addLiteral(articleNameProp, articleName);
        });

        conferences.forEach(conf -> {
            val confName = conf.getName();
            model.createResource(CONF_BASE_URL + "/" + asUtf8(confName))
                 .addLiteral(articleNameProp, confName);
        });

        System.out.println(model.getResource(CONF_BASE_URL + "/conf/Conf1"));

        //Properties
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
