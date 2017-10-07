package models;

import controllers.ReadersController;
import org.junit.Before;
import org.junit.Test;
import play.test.Fixtures;
import play.test.UnitTest;

public class ReaderModelTest extends UnitTest {


    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }


    @Test
    public void testSaveReader() {
        Reader reader = new Reader("Никишин", "Никита", "Никитович", "464646", "Пенза главная 1", "0000 000000");
        reader.save();
        Reader foundReader = ReadersController.searchByNameAndSurname("Никита", "Никишин");
        assertNotNull(foundReader);
    }


    @Test
    public void testDeleteReader() {
        Reader reader = new Reader("Никишин", "Никита", "Никитович", "464646", "Пенза главная 1", "0000 000000");
        reader.save();
        reader.delete();
        Reader foundDeletedReader = ReadersController.searchByNameAndSurname("Никита", "Никишин");
        assertNull(foundDeletedReader);
    }
}
