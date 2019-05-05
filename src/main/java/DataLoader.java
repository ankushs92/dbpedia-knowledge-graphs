import lombok.val;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class DataLoader {


    public static void main(String[] args) throws IOException {
        val authorsFile = asFile(FileName.AUTHORS);
        

        val authors = read(authorsFile).map(Author::new);


        authors.forEach(author -> {
            System.out.println(author);
        });

    }

    private static Stream<String> read(final File file) throws IOException {
       return Files.lines(Paths.get(file.getPath()));
    }

    private static File asFile(final String fileName) {
        val absFilePath = DataLoader.class.getClassLoader().getResource(fileName).getFile();
        return new File(absFilePath);
    }
}
