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
        Stream<Article> articles = read(articlesFile).map(record -> new Article(record.split(",")[0]));
        Stream<Conference> conferences = read(confFile).map(Conference::new);
        Stream<Edition> editions = read(editionFile).map(record -> {
                                                    String editionName = record.split(",")[0];
                                                    Integer year = YEARS.get(generateRandomIntegersBetween(0, YEARS.size() - 1));
                                                    String city = CITIES.get(generateRandomIntegersBetween(0, CITIES.size() - 1));
                                                    return new Edition(editionName, year, city);
                                                });

        Stream<Journal> journals = read(journalsFile).map(Journal::new);
        Stream<PaperKeyword> paperKeywords = read(keywordsFile).map(record -> {
                                                                try {
                                                                    String[] arrayValues = record.split(",");
                                                                    return new PaperKeyword(arrayValues[1], arrayValues[0]);
                                                                }
                                                                catch (final Exception ex) {
                                                                    //pass
                                                                }
                                                                return null;
                                                          }).filter(Objects::nonNull);

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

        Stream<PaperCitations> paperCitations = read(citedPapersFile).map(record -> {
                                                        try {
                                                            String[] arrayValues = record.split(",");
                                                            return new PaperCitations(arrayValues[0], arrayValues[1]);
                                                        }
                                                        catch (final Exception ex) {
                                                            // pass
                                                            return null;
                                                        }
                                                    }).filter(Objects::nonNull);


        Stream<AuthorWrotePaper> authorWrotePapers = read(authorWroteArticleFile).map(record -> {
                                                            try {
                                                                String[] arrayValues = record.split(",");
                                                                return new AuthorWrotePaper(arrayValues[0], arrayValues[1]);
                                                            }
                                                            catch (final Exception ex) {
                                                                // pass
                                                                return null;
                                                            }
                                                     }).filter(Objects::nonNull);

        Stream<VolumeOfJournal> volumeOfJournals = read(volumeOfJournalFile).map(record -> {
                                                            try {
                                                                String[] arrayValues = record.split(",");
                                                                return new VolumeOfJournal(arrayValues[0], arrayValues[1]);
                                                            }
                                                            catch (final Exception ex) {
                                                                // pass
                                                                return null;
                                                            }
                                                        }).filter(Objects::nonNull);

        Stream<PaperInVolume> paperInVolumes = read(paperInVolumeFile).map(record -> {
                                                            try {
                                                                String[] arrayValues = record.split(",");
                                                                return new PaperInVolume(arrayValues[1], arrayValues[0]);
                                                            }
                                                            catch (final Exception ex) {
                                                                // pass
                                                                return null;
                                                            }
                                                        }).filter(Objects :: nonNull);

        //Jena
        Model model = ModelFactory.createDefaultModel();
        Resource authorClassResource = model.createResource(AUTHOR_BASE_URL);
        Resource articleClassResource = model.createResource(ARTICLE_BASE_URL);
        Resource confClassResource = model.createResource(CONF_BASE_URL);
        Resource editionClassResource = model.createResource(EDITION_BASE_URL);
        Resource journalClassResource = model.createResource(JOURNAL_BASE_URL);
        Resource reviewerClassResource = model.createResource(REVIEWER_BASE_URL);
        Resource reviewClassResource = model.createResource(REVIEW_BASE_PROPERTY_URL);

        Property rdfTypeProp = model.createProperty(RDFS_BASE_PROPERTY_URL);

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
        Property citationsProp = model.createProperty(CITATIONS_BASE_PROPERTY_URL);
        Property reviewProp = model.createProperty(REVIEW_BASE_PROPERTY_URL);
        Property wroteProp = model.createProperty(AUTHOR_WROTE_PAPER_BASE_PROPERTY_URL);
        Property volumePartOfProp = model.createProperty(VOLUME_PART_OF_BASE_PROPERTY_URL);
        Property paperVolumeProp = model.createProperty(PAPER_VOLUME_BASE_PROPERTY_URL);

        authors.forEach(author -> {
            String authorName = author.getName();
            Resource authorResource = model.createResource(AUTHOR_BASE_URL + "/" + asUtf8(authorName))
                                           .addLiteral(authorNameProp, authorName);

            model.add(model.createStatement(authorResource, rdfTypeProp, authorClassResource));
        });

        articles.forEach(article -> {
            String articleName = article.getName();
            Resource articleResource = model.createResource(ARTICLE_BASE_URL + "/" + asUtf8(articleName))
                                            .addLiteral(articleNameProp, articleName);

            model.add(model.createStatement(articleResource, rdfTypeProp, articleClassResource));
        });

        conferences.forEach(conf -> {
            String confName = conf.getName();
            Resource confResource = model.createResource(CONF_BASE_URL + "/" + asUtf8(confName))
                                            .addLiteral(confNameProp, confName);

            model.add(model.createStatement(confResource, rdfTypeProp, confClassResource));

        });

        editions.forEach(edition -> {
            String editionName = edition.getName();
            Resource editionResource = model.createResource(EDITION_BASE_URL + "/" + asUtf8(editionName))
                                             .addLiteral(editionNameProp, editionName)
                                             .addLiteral(editionYearProp, String.valueOf(edition.getYear()))
                                             .addLiteral(editionCityProp, edition.getCity());
            model.add(model.createStatement(editionResource, rdfTypeProp, editionClassResource));
        });

        journals.forEach(journal -> {
            String journalName = journal.getName();
            Resource journalResource = model.createResource(JOURNAL_BASE_URL + "/" + asUtf8(journalName))
                                             .addLiteral(journalNameProp, journalName);

            model.add(model.createStatement(journalResource, rdfTypeProp, journalClassResource));
        });

        volumes.forEach(volume-> {
            String volumeName = volume.getName();
            Resource volumeResource = model.createResource(VOLUME_BASE_URL + "/" + asUtf8(volumeName))
                                             .addLiteral(volumeNameProp, volumeName)
                                             .addLiteral(volumeYearProp, String.valueOf(volume.getYear()))
                                             .addLiteral(volumeCityProp, volume.getCity());

            model.add(model.createStatement(volumeResource, rdfTypeProp, journalClassResource));
        });

        reviewerArticles.forEach(reviewerArticle -> {
            String reviewerName = reviewerArticle.getAuthorName();
            String paperName = reviewerArticle.getPaperName();
            String paperUri =  ARTICLE_BASE_URL + "/" + asUtf8(paperName);
            Review review = new Review(DUMMY_REVIEW, Util.randomDate());

            String reviewUri =  REVIEW_BASE_URL + "/" + asUtf8(review.getContent());
            Resource reviewResource = model.createResource(reviewUri)
                                           .addLiteral(reviewContentProp, review.getContent())
                                           .addLiteral(reviewDateProp, review.getDate());
            model.add(model.createStatement(reviewResource, rdfTypeProp, reviewClassResource));

            Resource reviewerResource = model.createResource(REVIEWER_BASE_URL + "/" + asUtf8(reviewerName))
                                             .addLiteral(reviewerNameProp, reviewerName);

            model.add(model.createStatement(reviewerResource, rdfTypeProp, reviewerClassResource));
            model.add(model.createStatement(reviewerResource, givesProp, reviewResource));

            Resource paperResource = model.getResource(paperUri);

            model.add(model.createStatement(paperResource, reviewProp, reviewResource));

        });

        paperCitations.forEach(paperCitation -> {
            String paperName = paperCitation.getPaperName();
            String citedPaperName = paperCitation.getCitedPaperName();
            String paperUri = ARTICLE_BASE_URL + "/" + asUtf8(paperName);
            String citedPaperUri = ARTICLE_BASE_URL + "/" + asUtf8(citedPaperName);
            Resource paperResource = model.getResource(paperUri);
            Resource citedPaperResource = model.getResource(citedPaperUri);

            model.add(model.createStatement(paperResource, citationsProp, citedPaperResource));
        });

        authorWrotePapers.forEach(authorWrotePaper -> {
            String authorName = authorWrotePaper.getAuthorName();
            String paperName = authorWrotePaper.getPaperName();

            String authorUri = AUTHOR_BASE_URL + "/" + asUtf8(authorName);
            String paperUri = ARTICLE_BASE_URL + "/" + asUtf8(paperName);

            Resource authorResource = model.getResource(authorUri);
            Resource paperResource = model.getResource(paperUri);

            model.add(model.createStatement(authorResource, wroteProp, paperResource));
        });

        volumeOfJournals.forEach(volumeOfJournal -> {
            String volName = volumeOfJournal.getVolume();
            String journalName = volumeOfJournal.getJournalName();

            String volUri = VOLUME_BASE_URL + "/" + asUtf8(volName);
            String journalUri = JOURNAL_BASE_URL + "/" + asUtf8(journalName);

            Resource volResource = model.getResource(volUri);
            Resource journalResource = model.getResource(journalUri);

            model.add(model.createStatement(volResource, volumePartOfProp, journalResource));
        });

        paperInVolumes.forEach(paperInVolume -> {
            String paperName = paperInVolume.getPaperName();
            String volumeName = paperInVolume.getVolumeName();

            String paperUri = ARTICLE_BASE_URL + "/" + asUtf8(paperName);
            String volumeUri = VOLUME_BASE_URL + "/" + asUtf8(volumeName);

            Resource paperResource = model.getResource(paperUri);
            Resource volumeResource = model.getResource(volumeUri);
            model.add(model.createStatement(paperResource, paperVolumeProp, volumeResource));
        });

        paperKeywords.forEach(keywordInPaper -> {
            String paperName = keywordInPaper.getPaperName();

            String paperUri = ARTICLE_BASE_URL + "/" + asUtf8(paperName);
            Resource paperResource = model.getResource(paperUri);
            paperResource.addLiteral(keywordsProp, keywordInPaper.getKeyword());
        });

        model.write(System.out, "N-TRIPLE");

//        model.write(new FileOutputStream(asFile(FileName.OUTPUT)), "N-TRIPLE");

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
