const header = document.getElementById('title');

const bookEditForm =
    `<form id="edit-form" action = "/rest/book" method="post">
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
    const bookCreateButton = createButton('page-button', null,'Добавить книгу', getCreatePage);
    createDiv.append(bookCreateButton);

    const listDiv = document.createElement('div');
    const bookListButton = createButton('page-button', null, 'Список книг', getAllBooks);
    listDiv.append(bookListButton);

    const newContent = document.createElement('div');
    newContent.id = 'content';

    newContent.append(createDiv, listDiv);

    header.innerHTML = 'Библиотека';
    setNewContent(newContent);
}

function getCreatePage() {
    const newContent = createNewContentWithBackButton(getStartPage);

    const formDiv = document.createElement('div');
    formDiv.innerHTML = bookEditForm;
    newContent.append(formDiv);

    header.innerText = 'Ввести информацию о книге';
    setNewContent(newContent);

    document.getElementById('row-id').remove();
    document.getElementById('edit-form').addEventListener('submit', handleCreateBookSubmit);
}

function createNewContentWithBackButton(action) {
    const newContent = document.createElement('div');
    newContent.id = 'content';

    const backDiv = document.createElement('div');
    backDiv.className = 'back-button';
    const backButton = createButton('page-button', null, 'Назад', action);
    backDiv.append(backButton);
    newContent.append(backDiv);

    return newContent;
}

async function getBookPage(event) {
    const newContent = createNewContentWithBackButton(getAllBooks);
    const tableDiv = await createBookPageTable(event.target.value);

    const comments = document.createElement('div');
    comments.className = 'comments';

    const commentHeader = document.createElement('h2');
    commentHeader.innerText = 'Комментарии';

    const commentAdd = document.createElement('div');
    commentAdd.className = 'add-comment';

    const commentForm = createCommentForm(event.target.value);

    commentAdd.append(commentForm);

    const commentList = document.createElement('div');
    commentList.className = 'comment-list';

    comments.append(commentHeader, commentAdd, commentList);

    newContent.append(tableDiv, comments);

    header.innerText = 'Информация о книге';
    setNewContent(newContent);
    await getCommentList(event.target.value);
    document.getElementById('add-comment').addEventListener('submit', handleAddCommentSubmit)
}

function createCommentForm(bookId) {
    const commentForm = document.createElement('form');
    commentForm.id = 'add-comment';
    commentForm.method = 'post';
    commentForm.action = '/rest/book/' + bookId + '/comment';

    const label = document.createElement('label');
    label.for = 'text-input';
    label.innerText = 'Добавить комментарий';

    const input = document.createElement('input');
    input.className = 'add-comment-input';
    input.id = 'text-input';
    input.name = 'text';
    input.type = 'text';

    const commentButton = document.createElement('button');
    commentButton.className = 'comment-button';
    commentButton.type = 'submit';
    commentButton.innerText = 'Добавить';

    commentForm.append(label, input, commentButton);

    return commentForm;
}

async function createBookPageTable(bookId) {
    const tableDiv = document.createElement('div')

    await fetch('/rest/book/' + bookId)
        .then(response => {
            if (!response.ok) {
                throw new Error('Could not get data for book with id=' + bookId);
            }
            return response.json();
        })
        .then(book => {
            tableDiv.innerHTML =
                `<table class="book-info">
                    <tr class="row">
                        <td class="title"><b>ID:</b></td>
                        <td id="book-id">${book.id}</td>
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
        })
        .catch(err => console.log(err))
    ;

    return tableDiv;
}

async function handleAddCommentSubmit(event) {
    await handleFormSubmit(event);
    await getCommentList();
}

async function handleFormSubmit(event) {
    event.preventDefault();
    const form = event.currentTarget;
    const url = form.action;

    try {
        const formData = new FormData(form);
        const responseData = await saveData({ url, formData });
        console.log({ responseData });
    } catch (error) {
        console.error(error);
    }
    form.reset();
}

async function getCommentList() {
    const commentListNew = document.createElement('div');
    commentListNew.className = 'comment-list';
    const bookId = document.getElementById('book-id').innerText;

    await fetch('rest/book/' + bookId + '/comment')
        .then(response => {
            if (!response.ok) {
                throw new Error('Could not get comments for book with id=' + bookId);
            }
            return response.json();
        })
        .then(comments => comments.forEach(function (comment) {
                const commentRow = document.createElement('div');
                commentRow.className = 'comment';

                const name = document.createElement('p');
                name.innerHTML = '<strong>Аноним:</strong>';

                const text = document.createElement('p');
                text.innerText = comment.text;

                commentRow.append(name, text);
                commentListNew.append(commentRow);
            })
        ).catch(err => console.error(err))
    ;
    document.getElementsByClassName('comments')[0].replaceChild(commentListNew, document.getElementsByClassName('comment-list')[0])
}

async function getEditPage(event) {
    const newContent = createNewContentWithBackButton(getAllBooks);

    const formDiv = document.createElement('div');
    formDiv.innerHTML = bookEditForm;
    newContent.append(formDiv);

    header.innerText = 'Редактировать информацию о книге';
    setNewContent(newContent);
    await getBookInfo(event.target.value);
    document.getElementById('edit-form').addEventListener('submit', handleEditBookSubmit);
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
        .then(response => {
            if (!response.ok) {
                throw new Error('Could not get book data');
            }
            return response.json();
        })
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

            const openButton = createButton('action-button', book.id, 'Открыть', getBookPage);
            const editButton = createButton('action-button', book.id, 'Изменить', getEditPage);
            const deleteButton = createButton('action-button', book.id, 'Удалить', deleteBook);

            tr.append(id, title, authors, genres, openButton, editButton, deleteButton);
            tbody.append(tr);
        })).catch(err => console.error(err))
    ;
    table.append(tbody);
    newContent.append(table);

    header.innerText = 'Список книг';
    setNewContent(newContent);
}

async function handleCreateBookSubmit(event) {
    await handleFormSubmit(event);
    getStartPage();
}

async function handleEditBookSubmit(event) {
    await handleFormSubmit(event);
    await getAllBooks();
}

async function saveData({ url, formData }) {
    const plainFormData = Object.fromEntries(formData.entries());
    const formDataJsonString = JSON.stringify(plainFormData);

    const fetchOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
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

function createButton(className, value, innerText, eventOption) {
    const button = document.createElement('button');
    button.className = className;
    button.value = value;
    button.innerText = innerText;
    button.addEventListener('click', eventOption);
    return button;
}

async function deleteBook(event) {
    await fetch('/rest/book/' + event.target.value, {method: 'DELETE'});
    await getAllBooks();
}

async function getBookInfo(bookId) {
    await fetch('/rest/book/' + bookId)
        .then(response => response.json())
        .then(book => {
            document.getElementById('id-input').value = book.id;
            document.getElementById('title-input').value = book.title;
            document.getElementById('author-input').value = book.authors;
            document.getElementById('genre-input').value = book.genres;
        })
    ;
}

function setNewContent(newContent) {
    document.getElementById('container')
        .replaceChild(newContent, document.getElementById('content'));
}