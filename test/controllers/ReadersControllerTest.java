package controllers;

import models.LogBook;
import models.Reader;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.List;

public class ReadersControllerTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }

    @Test
    public void testSearchByNameAndSurname() {
        Reader response = ReadersController.searchByNameAndSurname("Имя", "Фамилия");
        assertNotNull(response);

        Reader reader = ReadersController.searchByNameAndSurname("", "");
        assertNull(reader);
    }

    @Test
    public void testBooksBySearchReader() {
        List<LogBook> logBooks = ReadersController.booksBySearchReader("Имя", "Фамилия");
        assertNotNull(logBooks);

        logBooks = ReadersController.booksBySearchReader("", "");
        assertNull(logBooks);
    }
}
