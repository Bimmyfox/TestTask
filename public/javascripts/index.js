$(window).ready(function () {

    document.getElementById('last-name').oninput = function() {
        var a = $(".add-book-form > td > .add-last-name");
        for (var i = 0; i < a.length; i ++) {
            a[i].value = this.value;
        }
    };

    document.getElementById('first-name').oninput = function() {
        var a = $(".add-book-form > td > .add-first-name");
        for (var i = 0; i < a.length; i ++) {
            a[i].value = this.value;
        }
    };
});

function checkValidation() {

    var flag = true;

    document.querySelectorAll('.add-book-form > td > input')
        .forEach(function (item) {
            if (item.checkValidity()) {
                item.style.boxShadow = 'none';
            } else {
                item.style.boxShadow = "0 0 3px #ff0000";
                item.reportValidity();

                flag = false;
            }
        });

    if (!flag)
        document.getElementById('failMessage').style.display = 'block';

    return flag;
}


function addForm() {
    $( "tbody:last" ).append( "<tr class=\"add-book-form\">\n" +
        "            <td><input class='add-last-name' disabled type=\"text\" name=\"readerSurname\" value=\""
        + document.getElementById('last-name').value + "\" placeholder=\"Фамилия\"\n" +
        "                       required pattern=\"^[А-Яа-яЁё\\s\\-]+$\"></td>\n" +
        "            <td><input class='add-first-name' disabled type=\"text\" name=\"readerName\" value=\""
        + document.getElementById('first-name').value + "\" placeholder=\"Имя\"\n" +
        "                       required pattern=\"^[А-Яа-яЁё\\s\\-]+$\"></td>\n" +
        "            <td><input type=\"text\" name=\"bookTitle\" placeholder=\"Название книги\"\n" +
        "                       required pattern=\"^[А-Яа-яЁёa-zA-Z\\s-,.]+$\"></td>\n" +
        "            <td><input type=\"text\" name=\"bookAuthor\" placeholder=\"Автор\"\n" +
        "                       required pattern=\"^[А-Яа-яЁёa-zA-Z\\s-,.]+$\"></td>\n" +
        "            <td><i class=\"fa fa-trash remove-btn\" aria-hidden=\"true\" onclick=\"removeForm(this)\"></i></td>\n" +
        "        </tr>");
}



function submitForm() {
    if (!checkValidation())
        return 0;

    var forms = $('.add-book-form');
    var result = {
        'readerSurname': [],
        'readerName': [],
        "bookTitle": [],
        "bookAuthor": []
    };
    forms.each(function (count, item) {
        var ch = [];
        $(item).children().each(function (c, i) {
            ch.push($(i).children()[0]);
        });
        result['readerSurname'].push( ch[0].value ? ch[0].value : " " );
        result['readerName'].push( ch[1].value ? ch[1].value : " " );
        result['bookTitle'].push( ch[2].value ? ch[2].value : " " );
        result['bookAuthor'].push( ch[3].value ? ch[3].value : " " );
    });


    $.ajax({
        type: "POST",
        data: {
            'readerSurname': result['readerSurname'].join(','),
            'readerName': result['readerName'].join(','),
            'bookTitle': result['bookTitle'].join(','),
            'bookAuthor': result['bookAuthor'].join(',')
        },
        url: "takeTheBooks"
    }).done(function() {
        window.location.reload();
    }).error(function (resp) {
        $('#error-message').html(`<h3 style="color: #ff0000;">${resp.responseText}</h3>`)
    });
}