const header = document.getElementById('title');

const bookEditForm =
    `<form id="edit-form" action = "/rest/book/save">
            <div class="book-info">
                
                <div class="row">
                    <label class="book-label" for="id-input"><b>ID:</b></label>
                    <input class="book-edit-input" id="id-input" readonly name="id" type="text"/>
                </div>
                
                <div class="row">
                    <label class="book-label" for="title-input"><b>Название:</b></label>
                    <input class="book-edit-input" id="title-input" name="title" type="text"/>
                </div>
    
                <div class="row">
                    <label class="book-label" for="author-input"><b>Автор(ы):</b></label>
                    <input class="book-edit-input" id="author-input" name="authors" type="text"/>
                </div>
    
                <div class="row">
                    <label class="book-label" for="genre-input"><b>Жанр(ы):</b></label>
                    <input class="book-edit-input" id="genre-input" name="genres" type="text"/>
                </div>
            </div>
            <div>
                <button class="edit-button" type="submit">Сохранить</button>
            </div>
    </form>`;

getStartPage();

function getStartPage() {
    const createDiv = document.createElement('div');
    const bookCreateButton = createButton('page-button', 'create-button', null,'Добавить книгу', getCreatePage);
    createDiv.append(bookCreateButton);

    const listDiv = document.createElement('div');
    const bookListButton = createButton('page-button', 'list-button', null, 'Список книг', getAllBooks);
    listDiv.append(bookListButton);

    const newContent = document.createElement('div');
    newContent.id = "content";

    newContent.append(createDiv);
    newContent.append(listDiv);

    header.innerHTML = "Библиотека";
    document.getElementsByClassName("container")[0].replaceChild(newContent, content);
}

function getCreatePage() {

    header.innerHTML = "Ввести информацию о книге";
    document.getElementById('content').innerHTML = bookEditForm;
    document.getElementById('edit-form').addEventListener("submit", handleSaveBookSubmit);
}

async function handleSaveBookSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await saveBook({ url, formData });

        console.log({ responseData });
    } catch (error) {
        console.error(error);
    }
    getStartPage();
}

async function saveBook({ url, formData }) {
    const plainFormData = Object.fromEntries(formData.entries());
    const formDataJsonString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: formDataJsonString,
    };

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }

    return response.json();
}

function getAllBooks() {
    const oldContent = document.getElementById('content');
    const newContent = document.createElement('div');
    newContent.id = 'content';

    const backDiv = document.createElement('div');
    backDiv.className = 'back-button';
    const backButton = createButton('page-button', 'back-button', null,'Назад', getStartPage);
    backDiv.append(backButton);
    newContent.append(backDiv);

    const table = document.createElement('table');
    table.className = 'books';
    const head = document.createElement('thead');
    head.innerHTML =
        `<tr>
            <th>ID</th>
            <th>Название</th>
            <th>Автор(ы)</th>
            <th>Жанр(ы)</th>
        </tr>`;
    const tbody = document.createElement('tbody');

    table.append(head);

    fetch('/rest/book')
        .then(response => response.json())
        .then(books => books.forEach(function (book) {
            const tr = document.createElement('tr');

            const id = document.createElement('td');
            id.innerHTML = book.id;
            const title = document.createElement('td');
            title.innerHTML = book.title;
            const authors = document.createElement('td');
            authors.innerHTML = book.authors;
            const genres = document.createElement('td');
            genres.innerHTML = book.genres;

            const openButton = createButton('action-button', 'open-button', book.id, 'Открыть', getBookPage);
            const editButton = createButton('action-button', 'edit-button', book.id, 'Изменить', editBook);
            const deleteButton = createButton('action-button', 'delete-button', book.id, 'Удалить', deleteBook);

            tr.append(id, title, authors, genres, openButton, editButton, deleteButton);
            tbody.append(tr);
        }));
    table.appendChild(tbody);
    newContent.append(table);

    header.innerText = 'Список книг';
    document.getElementsByClassName('container')[0].replaceChild(newContent, oldContent);
}

function createButton(className, id, value, innerText, eventOption) {
    const button = document.createElement('button');
    button.className = className;
    button.id = id;
    button.value = value;
    button.innerText = innerText;
    button.addEventListener('click', eventOption);
    return button;
}

async function getBookPage(event) {
    const oldContent = document.getElementById('content');
    const newContent = document.createElement('div');
    newContent.id = 'content';

    const backDiv = document.createElement('div');
    backDiv.className = 'back-button';
    const backButton = document.createElement('button');
    backButton.className = "page-button";
    backButton.id = "back-button";
    backButton.innerText = "Назад";
    backButton.addEventListener('click', getAllBooks);
    backDiv.append(backButton);
    newContent.append(backDiv);

    const tableDiv = document.createElement('div');
    newContent.append(tableDiv);

    await fetch('/rest/book/' + event.target.value)
        .then(response => response.json())
        .then(book => {
            header.innerHTML = `Информация о книге`;
            tableDiv.innerHTML =
                `<table class="book-info">
                            <tr class="row">
                                <td class="title"><b>ID:</b></td>
                                <td>${book.id}</td>
                            </tr>
                            <tr class="row">
                                <td class="title"><b>Название:</b></td>
                                <td class="value">${book.title}</td>
                            </tr>
                            <tr class="row">
                                <td class="title"><b>Автор(ы):</b></td>
                                <td class="value">${book.authors}</td>
                            </tr>
                            <tr class="row">
                                <td class="title"><b>Жанр(ы):</b></td>
                                <td class="value">${book.genres}</td>
                            </tr>
                        </table>`;
        });
    document.getElementsByClassName('container')[0].replaceChild(newContent, oldContent);
}

async function deleteBook(event) {
    await fetch('/rest/book/' + event.target.value, {method: 'DELETE'});
    getAllBooks();
}

async function editBook(event) {
    await fetch('/rest/book/' + event.target.value)
        .then(response => response.json())
        .then(book => {
            header.innerHTML = `Редактировать информацию о книге`;
            document.getElementById('content').innerHTML = bookEditForm;

            document.getElementById('id-input').value = book.id;
            document.getElementById('title-input').value = book.title;
            document.getElementById('author-input').value = book.authors;
            document.getElementById('genre-input').value = book.genres;

            document.getElementById('edit-form').addEventListener("submit", handleSaveBookSubmit);
        });
}