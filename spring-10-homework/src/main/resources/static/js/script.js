const header = document.getElementById('title');

const bookEditForm =
    `<form id="edit-form" action = "/rest/book/save">
            <div class="book-info">
                
                <div class="row" id="row-id">
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
                <button class="page-button" type="submit">Сохранить</button>
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
    setNewContent(newContent);
}

function getCreatePage() {
    const newContent = createNewContentWithBackButton(getStartPage);

    const formDiv = document.createElement('div');
    formDiv.innerHTML = bookEditForm;
    newContent.append(formDiv);

    header.innerText = "Ввести информацию о книге";
    setNewContent(newContent);
    document.getElementById('row-id').remove();
    document.getElementById('edit-form').addEventListener("submit", handleSaveBookSubmit);
}

function createNewContentWithBackButton(action) {
    const newContent = document.createElement('div');
    newContent.id = 'content';

    const backDiv = document.createElement('div');
    backDiv.className = 'back-button';
    const backButton = createButton('page-button', 'back-button', null, 'Назад', action);
    backDiv.append(backButton);
    newContent.append(backDiv);

    return newContent;
}

async function getBookPage(event) {
    const newContent = createNewContentWithBackButton(getAllBooks);
    const tableDiv = document.createElement('div');
    const commentDiv = document.createElement('div');
    const commentHeader = document.createComment('h2');
    commentHeader.innerText = 'Комментарии';
    const commentForm = document.createComment('form');

    var f = document.createElement("form");
    f.setAttribute('method',"post");
    f.setAttribute('action',"submit.php");

    var i = document.createElement("input"); //input element, text
    i.setAttribute('type',"text");
    i.setAttribute('name',"username");

    var s = document.createElement("input"); //input element, Submit button
    s.setAttribute('type',"submit");
    s.setAttribute('value',"Submit");

    f.appendChild(i);
    f.appendChild(s);

//and some more input elements here
//and dont forget to add a submit button

    document.getElementsByTagName('body')[0].appendChild(f);
    commentDiv.append(commentForm);
    commentDiv.append(commentHeader);
    document.getE

    newContent.append(tableDiv);

    await fetch('/rest/book/' + event.target.value)
        .then(response => response.json())
        .then(book => {
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

    header.innerHTML = `Информация о книге`;
    setNewContent(newContent);
}

async function getEditPage(event) {
    const newContent = createNewContentWithBackButton(getAllBooks);

    const formDiv = document.createElement('div');
    formDiv.innerHTML = bookEditForm;
    newContent.append(formDiv);

    header.innerHTML = `Редактировать информацию о книге`;
    setNewContent(newContent);
    await getBookInfo(event);
    document.getElementById('edit-form').addEventListener("submit", handleSaveBookSubmit);
}

async function getAllBooks() {
    const newContent = createNewContentWithBackButton(getStartPage);

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

    await fetch('/rest/book')
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
            const editButton = createButton('action-button', 'edit-button', book.id, 'Изменить', getEditPage);
            const deleteButton = createButton('action-button', 'delete-button', book.id, 'Удалить', deleteBook);

            tr.append(id, title, authors, genres, openButton, editButton, deleteButton);
            tbody.append(tr);
        }));
    table.appendChild(tbody);
    newContent.append(table);

    header.innerText = 'Список книг';
    setNewContent(newContent);
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

function createButton(className, id, value, innerText, eventOption) {
    const button = document.createElement('button');
    button.className = className;
    button.id = id;
    button.value = value;
    button.innerText = innerText;
    button.addEventListener('click', eventOption);
    return button;
}

async function deleteBook(event) {
    await fetch('/rest/book/' + event.target.value, {method: 'DELETE'});
    await getAllBooks();
}

async function getBookInfo(event) {
    await fetch('/rest/book/' + event.target.value)
        .then(response => response.json())
        .then(book => {
            document.getElementById('id-input').value = book.id;
            document.getElementById('title-input').value = book.title;
            document.getElementById('author-input').value = book.authors;
            document.getElementById('genre-input').value = book.genres;
        });
}

function setNewContent(newContent) {
    document.getElementById('container')
        .replaceChild(newContent, document.getElementById('content'));
}