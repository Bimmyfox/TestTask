package models;

import play.data.validation.Match;
import play.data.validation.MaxSize;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.*;
import java.util.List;

/**
 * Класс-модель читателей
 * <p>
 * <p>
 * Содердит поля:
 * <p>
 * surname - фамилия читателя
 * name - имя  читателя
 * patronymic - отчество читателя
 * phoneNumber - номер телефона читателя
 * address - адрес читателя
 * passport - серия и номер паспорта читателя
 * <p>
 * Связанна с моделью LogBook один-ко-многим
 */
@Entity
public class Reader extends Model {

    @Required(message = "Введите фамилию")
    @MaxSize(value = 100, message = "не больше 100 символов")
    @Match(value = "^[А-Яа-яЁё\\s-]+$",
            message = "Фамилия может содержать русские буквы, пробел и дефис")
    public String surname;


    @Required(message = "Введите имя")
    @MaxSize(value = 100, message = "не больше 100 символов")
    @Match(value = "^[А-Яа-яЁё\\s-]+$",
            message = "Имя может содержать русские буквы, пробел и дефис")
    public String name;


    @MaxSize(value = 100, message = "не больше 100 символов")
    @Match(value = "^[А-Яа-яЁё\\s-]+$",
            message = "Отчество может содержать русские буквы, пробел и дефис")
    public String patronymic;


    @Required(message = "Введите номер телефона")
    @Match(value = "[0-9]{6,11}", message = "В номере должно быть от 6 до 11 цифр")
    public String phoneNumber;


    @Required(message = "Введите адрес")
    @Match(value = "^[А-Яа-яЁёa-zA-Z\\s\\d,.-]+$",
            message = "Адресс может содержать русские буквы, пробел, точку, запятую и дефис")
    public String address;


    @Required(message = "Введите паспортные данные")
    @Match(value = "\\d{4}\\s\\d{6}", message = "Паспортные данные в формате: 0000 000000")
    public String passport;


    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    public List<LogBook> logBooks;


    public Reader(String surname, String name,
                  String patronymic,
                  String phoneNumber,
                  String address,
                  String passport) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.passport = passport;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public List<LogBook> getLogBooks() {
        return logBooks;
    }

    public void setLogBooks(List<LogBook> logBooks) {
        this.logBooks = logBooks;
    }
}

