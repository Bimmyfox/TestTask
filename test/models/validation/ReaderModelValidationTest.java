package models.validation;

import models.Reader;
import org.junit.Before;
import org.junit.Test;
import play.data.validation.Validation;
import play.test.Fixtures;
import play.test.UnitTest;

public class ReaderModelValidationTest extends UnitTest {

    @Before
    public void setup() {
        Fixtures.deleteDatabase();
        Fixtures.loadModels("test-data.yml");
    }

    //--------models.validation-------------
    @Test
    public void validReader() {
        Reader reader = new Reader("Никишин", "Никита", "Никитович", "464646",
                "Пенза главная 1", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertTrue(valid.ok);

        reader = new Reader("Мамин-Сибиряк", "Никита", "Никитович", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertTrue(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertTrue(valid.ok);
    }


    @Test
    public void notValidSurnameReader() {
        Reader reader = new Reader("", "Никита", "", "464646",
                "Пенза главная 1", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("123", "Никита", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("лорлkhkhk", "Никита", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("fafafa", "Никита", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);
    }


    @Test
    public void notValidNameReader() {
        Reader reader = new Reader("Никишин", "", "", "464646",
                "Пенза главная 1", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "123", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "лорлkhkhk", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "fafafa", "", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);
    }


    @Test
    public void notValidPatronymicReader() {

        Reader reader = new Reader("Никишин", "Никита", "123", "464646",
                "Пенза главная 1", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "лорлkhkhk", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "fafafa", "464646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

    }

    @Test
    public void notValidPhoneNumberReader() {
        Reader reader = new Reader("Никишин", "Никита", "", "",
                "Пенза главная 1", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "gdf",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "ыв",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "4646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "645646546546465464646654654654646",
                "Пенза главная 1", "0000 000000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);
    }

    @Test
    public void notValidAddressReader() {
        Reader reader = new Reader("Никишин", "Никита", "", "454545",
                "", "0000 000000");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

    }


    @Test
    public void notValidPassportDataReader() {
        Reader reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "");
        Validation.ValidationResult valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "sdf");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "ыва");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "ываesdf");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "0000");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "0000234234");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);

        reader = new Reader("Никишин", "Никита", "", "454545",
                "Пенза главная 1", "00002222222222222222222");
        valid = Validation.current().valid(reader);
        assertFalse(valid.ok);
    }
}
