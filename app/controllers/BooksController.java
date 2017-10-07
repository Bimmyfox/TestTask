package controllers;

import models.Book;
import models.LogBook;
import play.data.validation.Error;
import play.data.validation.Required;
import play.data.validation.Valid;
import play.data.validation.Validation;
import play.mvc.Controller;

import java.util.List;

/**
 * Класс-контроллер модели Book(книга)
 *
 * Содержит функции:
 *
 * вывода всех книг,
 * добавления книги,
 * удаления книги,
 * редактирования книги,
 * поиска книги по автору и названию,
 * поиска читателей определённой книги,
 * отображения читателей определённой книги,
 * проверки возврата читателем книги
 */

public class BooksController extends Controller {

    /**
     * Функция вывода всех книг, отсорированных по автору и названию
     */
    public static void list() {
        List<Book> books = Book.find("order by author, title").fetch();
        render(books);
    }


    /**
     * Функция добавления книги
     *
     * Сохраняет в таблицу Book новую книгу
     * @param book новая книга
     */
    public static void addBook(@Valid Book book) {
        if (Validation.hasErrors()) {
            for (Error error : Validation.errors()) {
                System.out.println(error.message());
            }
        } else book.save();
        list();
    }


    /**
     * Функция удаления книги из библиотеки
     *
     * Удаляет из таблицы Book книгу
     * @param id книги, которую необходимо удалить
     */
    public static void deleteBook(Long id) {
        Book book = Book.findById(id);
        book.delete();
        list();
    }


    /**
     * Функция редактирования книги
     *
     * @param id Long номер редактируемой книги
     */
    public static void form(Long id) {
        if (id == null) {
            render();
        }
        Book book = Book.findById(id);
        render(book);
    }


    /**
     * Функция поиска книги по переданным названию и автору
     *
     * @param title String название книги
     * @param author String автор книги
     * @return book, если книга найдена,
     *          null, если нет
     */
    public static Book searchBookByTitleAndAuthor(@Required String title, @Required String author) {
        if (!Validation.hasErrors() && !title.trim().equals("") && !title.trim().equals("")) {
            Book book = Book.find("(lower(title) like ?1 " +
                            "AND lower(author) like ?2)",
                    "%" + title.toLowerCase() + "%", "%" + author.toLowerCase() + "%").first();
            return book;
        }
        return null;
    }


    /**
     * Функция отображает всех читатателей, которые брали книгу
     * с искомыми названием searchBookTitle и автором searchBookAuthor
     *
     * @param searchBookTitle String  название книги
     * @param searchBookAuthor String автор книги
     */
    public static void showHistoryBook(@Required String searchBookTitle, @Required String searchBookAuthor) {
        if (!Validation.hasErrors() && !searchBookTitle.trim().equals("") && !searchBookAuthor.trim().equals("")) {
            List<LogBook> logBooks = readersBySearchBook(searchBookTitle.toLowerCase(), searchBookAuthor.toLowerCase());
            render(logBooks);
        }
        list();
    }


    /**
     * Функция возвращает список читателей, которые брали книгу
     * с названием searchBookTitle и автором searchBookAuthor
     *
     * @param searchBookTitle  String название книги
     * @param searchBookAuthor String автор книги
     * @return список читателей, если книга найдена, null в противном случае
     */
    public static List<LogBook> readersBySearchBook(@Required String searchBookTitle, @Required String searchBookAuthor) {
        if (!Validation.hasErrors() && !searchBookAuthor.trim().equals("") && !searchBookAuthor.trim().equals("")) {
            return LogBook.find("(lower(book.title) like ?1 AND lower(book.author) like ?2)" +
                            "OR (lower(book.title) like ?1 AND lower(book.author) like ?3)" +
                            "OR (lower(book.title) like ?3 AND lower(book.author) like ?2)",
                    "%" + searchBookTitle + "%", "%" + searchBookAuthor + "%", "").fetch();
        }
        return null;
    }


    /**
     * Функция проверяет: возвращена ли в библиотеку книга
     *
     * @param book Book книга, наличие в библиотеке которой надо проверить
     * @return true, если книга в библиотеке,
     * false - в противном случае
     */
    public static boolean checkReturnedBook(@Required Book book) {
        if (!Validation.hasErrors()) {
            List<LogBook> logBooks = readersBySearchBook(book.title.toLowerCase(), book.author.toLowerCase());
            for (LogBook logBook : logBooks)
                if (!logBook.isReturned()) {
                    return false;
                }
            return true;
        }
        return false;
    }
}
