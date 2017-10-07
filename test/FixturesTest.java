import models.Book;
import models.LogBook;
import models.Reader;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;


public class FixturesTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
    }

    @Test
    public void loadData() {
        Fixtures.loadModels("test-data.yml");
        assertEquals(1, Reader.findAll().size());
        assertEquals(1, Book.findAll().size());
        assertEquals(1, LogBook.findAll().size());

        LogBook logBook = LogBook.<LogBook>findAll().get(0);
        assertEquals("Имя", logBook.getReader().name);
        assertEquals("Фамилия", logBook.getReader().surname);
        assertEquals("Автор", logBook.getBook().author);
        assertEquals("Название", logBook.getBook().title);
        assertTrue(logBook.isReturned());

    }

}