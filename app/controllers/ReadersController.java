package controllers;

import models.LogBook;
import models.Reader;
import play.data.validation.Error;
import play.data.validation.Required;
import play.data.validation.Valid;

import play.data.validation.Validation;
import play.mvc.Controller;

import java.util.List;

/**
 * Класс-контроллер модели Reader(читатель)
 * <p>
 * Содержит функции:
 * <p>
 * вывода всех читателей,
 * добавления читателя,
 * удаления читателя,
 * редактирования читателя,
 * поиска читателя по имени и фамилии,
 * отображения прочитанных книг читателем
 * возващающей список прочитанных книг
 */

public class ReadersController extends Controller {

    /**
     * Вывод всех читателей
     */
    public static void list() {
        List<Reader> readers = Reader.find("order by surname, name").fetch();
        render(readers);
    }


    /**
     * Функция добавления читателя
     * <p>
     * Сохраняет в таблицу Reader нового читателя
     *
     * @param reader Reader читатель
     */
    public static void addReader(@Valid Reader reader) {
        if (Validation.hasErrors()) {
            for (Error error : Validation.errors()) {
                System.out.println(error.message());
            }
        } else reader.save();
        list();

    }


    /**
     * Функция удаления читателя из библиотеки
     * <p>
     * Удаляет из таблицы Reader читателя
     *
     * @param id Long номер читателя, которого необходимо удалить
     */
    public static void deleteReader(Long id) {
        Reader reader = Reader.findById(id);
        reader.delete();
        list();
    }


    /**
     * Функция редактирования читателя
     *
     * @param id Long номер читателя, которого нужно отредактировать
     */
    public static void form(Long id) {
        if (id == null) {
            render();
        }
        Reader reader = Reader.findById(id);
        render(reader);
    }


    /**
     * Поиск читателя по имени и фамилии.
     * Если читатель найден - возвращает объект класса Reader,
     * иначе null
     *
     * @param name    String имя читателя
     * @param surname String фамилия читателя
     * @return если читатель найден - возвращает объект класса Reader,
     * иначе null
     */
    public static Reader searchByNameAndSurname(@Required String name, @Required String surname) {
        if (!Validation.hasErrors() && !name.trim().equals("") && !surname.trim().equals("")) {
            return Reader.find("(lower(name) like ?1 " +
                            "AND lower(surname) like ?2)",
                    "%" + name.toLowerCase() + "%", "%" + surname.toLowerCase() + "%").first();
        }
        return null;

    }


    /**
     * Функция отображает список прочитанных кинг читателем
     *
     * @param searchReaderName    имя читателя
     * @param searchReaderSurname фамилия читателя
     */
    public static void showHistoryReader(String searchReaderName, String searchReaderSurname) {
        if ((searchReaderName == null || searchReaderName.trim().equals("")) &&
                (searchReaderSurname == null || searchReaderSurname.trim().equals(""))) {
            list();
        }
        List logReaders = ReadersController.booksBySearchReader(searchReaderName, searchReaderSurname);
        render(logReaders);
    }


    /**
     * Функция возвращает список книг, которые брал искомый читатель
     *
     * @param searchReaderName    String имя читателя
     * @param searchReaderSurname String фамилия читателя
     * @return List<Reader> список книг
     */
    public static List<LogBook> booksBySearchReader(@Required String searchReaderName, @Required String searchReaderSurname) {
        if (!Validation.hasErrors() && !searchReaderName.equals("") && !searchReaderSurname.equals("")) {
            return LogBook.find("(lower(reader.name) like ?1 AND lower(reader.surname) like ?2)" +
                            "OR (lower(reader.name) like ?1 AND lower(reader.surname) like ?3)" +
                            "OR (lower(reader.name) like ?3 AND lower(reader.surname) like ?2)",
                    "%" + searchReaderName.toLowerCase() + "%", "%" + searchReaderSurname.toLowerCase() + "%", "").fetch();

        } return null;
    }
}
