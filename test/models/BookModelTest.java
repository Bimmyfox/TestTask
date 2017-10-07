package models;

import controllers.BooksController;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class BookModelTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }


    @Test
    public void testSaveBook() {
        Book book = new Book("Ноунейм", "Что-то", "Печать", 2017);
        book.save();
        Book foundBook = BooksController.searchBookByTitleAndAuthor("Что-то", "Ноунейм");
        assertNotNull(foundBook);
    }


    @Test
    public void testDeleteBook() {
        Book book = new Book("Ноунейм", "Что-то", "Печать", 2017);
        book.save();
        book.delete();
        Book foundDeletedBook = BooksController.searchBookByTitleAndAuthor("Что-то", "Ноунейм");
        assertNull(foundDeletedBook);
    }
}
