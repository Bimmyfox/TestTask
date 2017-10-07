import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner in = new Scanner(System.in);

        System.out.println("Введите искомую фразу: ");
        final String searchString = in.nextLine();

        for (final Path name: FileSystems.getDefault().getRootDirectories()) {
            System.out.println(name);
            new ForkJoinPool().invoke(new SearchStringInFiles(name, searchString));
        }

    }

}

