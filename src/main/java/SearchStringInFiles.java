import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class SearchStringInFiles extends RecursiveAction {

    private final Path dir;
    private final String searchString;


    SearchStringInFiles(Path dir, String searchString) {
        this.dir = dir;
        this.searchString = searchString;
    }

    @Override
    protected void compute() {
        final List<SearchStringInFiles> walks = new ArrayList<>();

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                //если дирректория

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (!dir.equals(SearchStringInFiles.this.dir)) {
                        SearchStringInFiles flow = new SearchStringInFiles(dir, searchString);
                        flow.fork();
                        walks.add(flow);

                        return FileVisitResult.SKIP_SUBTREE;
                    } else {
                        return FileVisitResult.CONTINUE;
                    }
                }

                //если файл
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {

                    //построчное чтение файлв
                    //если найдено совпадение с текстом - вывод дирректории
                    try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            if (line.contains(searchString)) {
                                System.out.println(file);
                                break;
                            }
                        }
                    } catch (IOException e) {
                        if (!e.toString().contains("MalformedInputException"))
                            System.out.print(e.toString());
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException io) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }

        for (SearchStringInFiles flow : walks) {
            flow.join();
        }
    }

}
