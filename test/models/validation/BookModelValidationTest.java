package models.validation;

import models.Book;
import org.junit.Before;
import org.junit.Test;
import play.data.validation.Validation;
import play.test.Fixtures;
import play.test.UnitTest;

public class BookModelValidationTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }

    //--------models.validation-------------
    @Test
    public void validBook() {
        Book book = new Book("Ноунейм", "Что-то", "Печать", 2017);
        Validation.ValidationResult valid = Validation.current().valid(book);
        assertTrue(valid.ok);

        book = new Book("Ноунейм", "123123", "Печать", 2017);
        valid = Validation.current().valid(book);
        assertTrue(valid.ok);

        book = new Book("Ноунейм", "12-31-23", "Печать", 2017);
        valid = Validation.current().valid(book);
        assertTrue(valid.ok);
    }

    @Test
    public void notValidTitleBook() {
        Book book = new Book("Ноунейм", " ", "Печать", 2017);
        Validation.ValidationResult valid = Validation.current().valid(book);
        assertFalse(valid.ok);

        book = new Book("Ноунейм", "дпваsdшщk234фыфвфв", "Печать", 2017);
        valid = Validation.current().valid(book);
        assertTrue(valid.ok);
    }

    @Test
    public void notValidAuthorBook() {
        Book book = new Book("", "Что-то", "Печать", 2017);
        Validation.ValidationResult valid = Validation.current().valid(book);
        assertFalse(valid.ok);

        book = new Book("двапвапsdf234фыфвфв", "123123", "Печать", 2017);
        valid = Validation.current().valid(book);
        assertFalse(valid.ok);
    }

    @Test
    public void notValidYearOfIssueBook() {
        Book book = new Book("Ноунейм", "Что-то", "Печать", 1000);
        Validation.ValidationResult valid = Validation.current().valid(book);
        assertFalse(valid.ok);

        book = new Book("Ноунейм", "123123", "Печать", 200000);
        valid = Validation.current().valid(book);
        assertFalse(valid.ok);
    }

    @Test
    public void notValidPublisherBook() {
        Book book = new Book("Ноунейм", "Что-то", "", 2017);
        Validation.ValidationResult valid = Validation.current().valid(book);
        assertFalse(valid.ok);

        book = new Book("Ноунейм", "123123", "двапвапsdf234фыфвфв", 2017);
        valid = Validation.current().valid(book);
        assertFalse(valid.ok);
    }


}
