package models.validation;

import models.Book;
import models.LogBook;
import models.Reader;
import org.joda.time.DateTime;
import org.junit.Test;
import play.data.validation.Validation;
import play.mvc.Before;
import play.test.Fixtures;
import play.test.UnitTest;

import java.util.Calendar;
import java.util.Date;

public class LogBookValidation extends UnitTest {


    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }

    private final Date dateToday = Calendar.getInstance().getTime();
    private final DateTime dateMustReturned = new DateTime(dateToday).plusMonths(1);
    private final Book book = new Book("Ноунейм", "Что-то", "Печать", 2017);
    private final Reader reader = new Reader("Никишин", "Никита", "", "454545",
            "Пенза главная 1", "");


    @Test
    public void validLogBook() {
        LogBook logBook = new LogBook(reader, book, dateToday, dateMustReturned.toDate(), false);
        Validation.ValidationResult valid = Validation.current().valid(logBook);
        assertTrue(valid.ok);

    }


    @Test
    public void notValidReaderLogBook() {
        LogBook logBook = new LogBook(null, book, dateToday, dateMustReturned.toDate(), false);
        Validation.ValidationResult valid = Validation.current().valid(logBook);
        assertFalse(valid.ok);
    }


    @Test
    public void notValidBookLogBook() {
        LogBook logBook = new LogBook(reader, null, dateToday, dateMustReturned.toDate(), false);
        Validation.ValidationResult valid = Validation.current().valid(logBook);
        assertFalse(valid.ok);

    }


    @Test
    public void notValidDateOfBookWasTakenLogBook() {
        LogBook logBook = new LogBook(reader, book, null, dateMustReturned.toDate(), false);
        Validation.ValidationResult valid = Validation.current().valid(logBook);
        assertFalse(valid.ok);

    }


    @Test
    public void notValidBookMustBeReturnedLogBook() {
        LogBook logBook = new LogBook(reader, book, dateToday, null, false);
        Validation.ValidationResult valid = Validation.current().valid(logBook);
        assertFalse(valid.ok);

    }
}
