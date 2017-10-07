package controllers;

import models.Book;
import models.LogBook;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

public class BookControllerTest extends UnitTest {


    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }


    @Test
    public void testSearchBookByTitleAndAuthor() {
        Book book = BooksController.searchBookByTitleAndAuthor("Название", "Автор");
        assertNotNull(book);

        book = BooksController.searchBookByTitleAndAuthor(" ", " ");
        assertNull(book);

        book = BooksController.searchBookByTitleAndAuthor("", "");
        assertNull(book);
    }


    @Test
    public void testBooksBySearchReader() {
        List<LogBook> logBook = BooksController.readersBySearchBook("Имя", "Фамилия");
        assertNotNull(logBook);

        logBook = BooksController.readersBySearchBook("", "");
        assertNull(logBook);

        logBook = BooksController.readersBySearchBook(" ", " ");
        assertNull(logBook);
    }


    @Test
    public void testCheckReturnedBook() {
        Book book = BooksController.searchBookByTitleAndAuthor("Название", "Автор");
        boolean result = BooksController.checkReturnedBook(book);
        assertTrue(result);
    }
}
