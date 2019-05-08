import model.*;
import org.apache.jena.ontology.OntModelSpec;
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
import java.util.stream.Stream;

import static model.Constants.*;

public class DataLoader {


    public static void main(String[] args) throws IOException {
        File authorsFile = asFile(FileName.AUTHORS);
        File articlesFile = asFile(FileName.ARTICLES);
        File confFile = asFile(FileName.CONFERENCE);
        File editionFile = asFile(FileName.EDITION);
        File journalsFile = asFile(FileName.JOURNAL);
        File keywordsFile = asFile(FileName.PAPER_HAS_KEYWORD);
        File volumesFile = asFile(FileName.VOLUME);
        File reviewersArticles = asFile(FileName.REVIEWERS_ARTICLES);
        File citedPapersFile = asFile(FileName.PAPER_CITATIONS);
        File authorWroteArticleFile = asFile(FileName.AUTHOR_WROTE_ARTICLE);
        File volumeOfJournalFile = asFile(FileName.VOLUME_OF_JOURNAL);
        File paperInVolumeFile = asFile(FileName.VOLUME_HAS_ARTICLE);

        Stream<Author> authors = read(authorsFile).map(Author::new).limit(10);
        Stream<Article> articles = read(articlesFile).map(record -> new Article(record.split(",")[0])).limit(10);
        Stream<Conference> conferences = read(confFile).map(Conference::new).limit(10);
        Stream<Edition> editions = read(editionFile).map(record -> {
            String editionName = record.split(",")[0];
            Integer year = YEARS.get(generateRandomIntegerBetween(0, YEARS.size() - 1));
            String city = CITIES.get(generateRandomIntegerBetween(0, CITIES.size() - 1));
            return new Edition(editionName, year, city);
        }).limit(10);

        Stream<Journal> journals = read(journalsFile).map(Journal::new).limit(10);
        Stream<PaperKeyword> paperKeywords = read(keywordsFile).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new PaperKeyword(arrayValues[1], arrayValues[0]);
            }
            catch (final Exception ex) {
                //pass
            }
            return null;
        }).filter(Objects::nonNull).limit(10);

        Stream<Volume> volumes = read(volumesFile).map(record -> {
            String volumeName = record.split(",")[0];
            Integer year = YEARS.get(generateRandomIntegerBetween(0, YEARS.size() - 1));
            String city = CITIES.get(generateRandomIntegerBetween(0, CITIES.size() - 1));
            return new Volume(volumeName, year, city);
        }).limit(10);

        Stream<ReviewerArticle> reviewerArticles = read(reviewersArticles).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new ReviewerArticle(arrayValues[0], arrayValues[1]);
            }
            catch (final Exception ex) {
                //pass
                return null;
            }
        }).filter(Objects::nonNull).limit(10);

        Stream<PaperCitations> paperCitations = read(citedPapersFile).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new PaperCitations(arrayValues[0], arrayValues[1]);
            }
            catch (final Exception ex) {
                // pass
                return null;
            }
        }).filter(Objects::nonNull).limit(10);


        Stream<AuthorWrotePaper> authorWrotePapers = read(authorWroteArticleFile).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new AuthorWrotePaper(arrayValues[0], arrayValues[1]);
            }
            catch (final Exception ex) {
                // pass
                return null;
            }
        }).filter(Objects::nonNull).limit(10);

        Stream<VolumeOfJournal> volumeOfJournals = read(volumeOfJournalFile).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new VolumeOfJournal(arrayValues[0], arrayValues[1]);
            }
            catch (final Exception ex) {
                // pass
                return null;
            }
        }).filter(Objects::nonNull).limit(10);

        Stream<PaperInVolume> paperInVolumes = read(paperInVolumeFile).map(record -> {
            try {
                String[] arrayValues = record.split(",");
                return new PaperInVolume(arrayValues[1], arrayValues[0]);
            }
            catch (final Exception ex) {
                // pass
                return null;
            }
        }).filter(Objects :: nonNull).limit(10);

        //Jena
        Model model = ModelFactory.createOntologyModel();
        model.read(asFile("tbox.owl").toURI().toString());


        Resource authorClassResource = model.getResource(AUTHOR_BASE_URL);
        Resource articleClassResource = model.getResource(ARTICLE_BASE_URL);
        Resource confClassResource = model.getResource(CONF_BASE_URL);
        Resource editionClassResource = model.getResource(EDITION_BASE_URL);
        Resource journalClassResource = model.getResource(JOURNAL_BASE_URL);
        Resource reviewerClassResource = model.getResource(REVIEWER_BASE_URL);
        Resource reviewClassResource = model.getResource(REVIEW_BASE_URL);

        Property rdfTypeProp = model.getProperty(RDFS_BASE_PROPERTY_URL);

        Property articleNameProp = model.getProperty(ARTICLE_NAME_BASE_PROPERTY_URL);
        Property authorNameProp = model.getProperty(AUTHOR_NAME_BASE_PROPERTY_URL);
        Property confNameProp = model.getProperty(CONF_NAME_BASE_PROPERTY_URL);
        Property editionNameProp = model.getProperty(EDITION_NAME_BASE_PROPERTY_URL);
        Property editionYearProp = model.getProperty(EDITION_YEAR_BASE_PROPERTY_URL);
        Property editionCityProp = model.getProperty(EDITION_CITY_BASE_PROPERTY_URL);
        Property journalNameProp = model.getProperty(JOURNAL_NAME_BASE_PROPERTY_URL);
        Property volumeNameProp = model.getProperty(VOLUME_NAME_BASE_PROPERTY_URL);
        Property volumeYearProp = model.getProperty(VOLUME_YEAR_BASE_PROPERTY_URL);
        Property volumeCityProp = model.getProperty(VOLUME_CITY_BASE_PROPERTY_URL);
        Property reviewerNameProp = model.getProperty(REVIEWER_NAME_BASE_PROPERTY_URL);
        Property reviewContentProp = model.getProperty(REVIEW_CONTENT_BASE_PROPERTY_URL);
        Property reviewDateProp = model.getProperty(REVIEW_DATE_BASE_PROPERTY_URL);
        Property keywordsProp = model.getProperty(KEYWORDS_BASE_PROPERTY_URL);

        Property givesProp = model.getProperty(REVIEWER_GIVES_REVIEW_BASE_PROPERTY_URL);
        Property citationsProp = model.getProperty(CITATIONS_BASE_PROPERTY_URL);
        Property reviewProp = model.getProperty(REVIEW_BASE_PROPERTY_URL);
        Property wroteProp = model.getProperty(AUTHOR_WROTE_PAPER_BASE_PROPERTY_URL);
        Property volumePartOfProp = model.getProperty(VOLUME_PART_OF_BASE_PROPERTY_URL);
        Property paperVolumeProp = model.getProperty(PAPER_VOLUME_BASE_PROPERTY_URL);

        authors.forEach(author -> {
            String authorName = author.getName();
            Resource authorResource = model.createResource(AUTHOR_BASE_URL + "/" + urlEncode(authorName))
                    .addLiteral(authorNameProp, authorName);

            model.add(model.createStatement(authorResource, rdfTypeProp, authorClassResource));
        });

        articles.forEach(article -> {
            String articleName = article.getName();
            Resource articleResource = model.createResource(ARTICLE_BASE_URL + "/" + urlEncode(articleName))
                    .addLiteral(articleNameProp, articleName);

            model.add(model.createStatement(articleResource, rdfTypeProp, articleClassResource));
        });

        conferences.forEach(conf -> {
            String confName = conf.getName();
            Resource confResource = model.createResource(CONF_BASE_URL + "/" + urlEncode(confName))
                    .addLiteral(confNameProp, confName);

            model.add(model.createStatement(confResource, rdfTypeProp, confClassResource));

        });

        editions.forEach(edition -> {
            String editionName = edition.getName();
            Resource editionResource = model.createResource(EDITION_BASE_URL + "/" + urlEncode(editionName))
                    .addLiteral(editionNameProp, editionName)
                    .addLiteral(editionYearProp, String.valueOf(edition.getYear()))
                    .addLiteral(editionCityProp, edition.getCity());
            model.add(model.createStatement(editionResource, rdfTypeProp, editionClassResource));
        });

        journals.forEach(journal -> {
            String journalName = journal.getName();
            Resource journalResource = model.createResource(JOURNAL_BASE_URL + "/" + urlEncode(journalName))
                    .addLiteral(journalNameProp, journalName);

            model.add(model.createStatement(journalResource, rdfTypeProp, journalClassResource));
        });

        volumes.forEach(volume-> {
            String volumeName = volume.getName();
            Resource volumeResource = model.createResource(VOLUME_BASE_URL + "/" + urlEncode(volumeName))
                    .addLiteral(volumeNameProp, volumeName)
                    .addLiteral(volumeYearProp, String.valueOf(volume.getYear()))
                    .addLiteral(volumeCityProp, volume.getCity());

            model.add(model.createStatement(volumeResource, rdfTypeProp, journalClassResource));
        });

        reviewerArticles.forEach(reviewerArticle -> {
            String reviewerName = reviewerArticle.getAuthorName();
            String paperName = reviewerArticle.getPaperName();
            String paperUri =  ARTICLE_BASE_URL + "/" + urlEncode(paperName);
            Review review = new Review(DUMMY_REVIEW, Util.randomDate());

            //Because the content of each review is same, we append a unique id with it
            String reviewUri =  REVIEW_BASE_URL + "/" + urlEncode(review.getContent()) + "_" + generateRandomIntegerBetween(0, 99999);

            Resource reviewResource = model.createResource(reviewUri)
                    .addLiteral(reviewContentProp, review.getContent())
                    .addLiteral(reviewDateProp, review.getDate());
            model.add(model.createStatement(reviewResource, rdfTypeProp, reviewClassResource));

            Resource reviewerResource = model.createResource(REVIEWER_BASE_URL + "/" + urlEncode(reviewerName))
                    .addLiteral(reviewerNameProp, reviewerName);

            model.add(model.createStatement(reviewerResource, rdfTypeProp, reviewerClassResource));
            model.add(model.createStatement(reviewerResource, givesProp, reviewResource));

            Resource paperResource = model.getResource(paperUri);

            model.add(model.createStatement(paperResource, reviewProp, reviewResource));
        });

        paperCitations.forEach(paperCitation -> {
            String paperName = paperCitation.getPaperName();
            String citedPaperName = paperCitation.getCitedPaperName();
            String paperUri = ARTICLE_BASE_URL + "/" + urlEncode(paperName);
            String citedPaperUri = ARTICLE_BASE_URL + "/" + urlEncode(citedPaperName);
            Resource paperResource = model.getResource(paperUri);
            Resource citedPaperResource = model.getResource(citedPaperUri);

            model.add(model.createStatement(paperResource, citationsProp, citedPaperResource));
        });

        authorWrotePapers.forEach(authorWrotePaper -> {
            String authorName = authorWrotePaper.getAuthorName();
            String paperName = authorWrotePaper.getPaperName();

            String authorUri = AUTHOR_BASE_URL + "/" + urlEncode(authorName);
            String paperUri = ARTICLE_BASE_URL + "/" + urlEncode(paperName);

            Resource authorResource = model.getResource(authorUri);
            Resource paperResource = model.getResource(paperUri);

            model.add(model.createStatement(authorResource, wroteProp, paperResource));
        });

        volumeOfJournals.forEach(volumeOfJournal -> {
            String volName = volumeOfJournal.getVolume();
            String journalName = volumeOfJournal.getJournalName();

            String volUri = VOLUME_BASE_URL + "/" + urlEncode(volName);
            String journalUri = JOURNAL_BASE_URL + "/" + urlEncode(journalName);

            Resource volResource = model.getResource(volUri);
            Resource journalResource = model.getResource(journalUri);

            model.add(model.createStatement(volResource, volumePartOfProp, journalResource));
        });

        paperInVolumes.forEach(paperInVolume -> {
            String paperName = paperInVolume.getPaperName();
            String volumeName = paperInVolume.getVolumeName();

            String paperUri = ARTICLE_BASE_URL + "/" + urlEncode(paperName);
            String volumeUri = VOLUME_BASE_URL + "/" + urlEncode(volumeName);

            Resource paperResource = model.getResource(paperUri);
            Resource volumeResource = model.getResource(volumeUri);
            model.add(model.createStatement(paperResource, paperVolumeProp, volumeResource));
        });

        paperKeywords.forEach(keywordInPaper -> {
            String paperName = keywordInPaper.getPaperName();

            String paperUri = ARTICLE_BASE_URL + "/" + urlEncode(paperName);
            Resource paperResource = model.getResource(paperUri);
            paperResource.addLiteral(keywordsProp, keywordInPaper.getKeyword());
        });

//        model.write(System.out, "N-TRIPLE");

        model.write(new FileOutputStream(asFile(FileName.OUTPUT)), "N-TRIPLE");

    }

    private static Stream<String> read(final File file) throws IOException {
        return Files
                .lines(Paths.get(file.getPath()))
                .skip(1);
    }

    private static File asFile(final String fileName) {
        String absFilePath = DataLoader.class.getClassLoader().getResource(fileName).getFile();
        return new File(absFilePath);
    }
    private static final Random random = new Random();

    private static int generateRandomIntegerBetween(final int from, final int to) {
        //Set Will ensure uniqueness.
        final List<Integer> result = new ArrayList<>();
        int range = to - from + 1;

        while(result.size() < 1) {
            result.add(random.nextInt(range) + from);
        }
        return result.get(0);
    }

    private static String urlEncode(final String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (final UnsupportedEncodingException e) {
            System.out.println(e);
            return "";
        }
    }
}
