//let tbody = document.getElementsByTagName('tbody');
const header = document.getElementById('title');
const content = document.getElementById('content')

getAllBooks();

function getAllBooks() {
    fetch('/rest/book')
        .then(response => response.json())
        .then(books => books.forEach(function (book) {
            $('tbody').append(`
                    <tr>
                        <td>${book.id}</td>
                        <td>${book.title}</td>
                        <td>${book.authors}</td>
                        <td>${book.genres}</td>
                        <td>
                            <button type="button" class = "action-button" id="open-button" value=${book.id}>
                                Открыть
                            </button>
                        </td>
                        <td>
                            <button type="button" class = "action-button" id="edit-button" value=${book.id}>
                                Изменить
                            </button>
                        </td>
                        <td>
                            <button type="button" class = "action-button" id="delete-button" value=${book.id}>
                                Удалить
                            </button>
                        </td>
                    </tr>
                `)
        }));
}


document.addEventListener('click',e => {
    switch (e) {
        case (e.target && e.target.id === 'open-button'):
            fetch('/rest/book/' + e.target.value)
                .then(response => response.json())
                .then(book => {
                    header.innerHTML = `Информация о книге`;
                    content.innerHTML =
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
            break;
        case (e.target && e.target.id === 'delete-button'):
            fetch('/rest/book', {method: 'DELETE'});
            getAllBooks();
            break;
    }

});

// <tr th:each="book : ${books}">
//     <td th:text="${book.id}">1</td>
//     <td th:text="${book.title}">Book Title</td>
//     <td th:text="${book.authors}">Authors</td>
//     <td th:text="${book.genres}">Genres</td>
//     <td>
//         <form action="#" th:action="@{/book/{id}(id=${book.id})}" th:method="get">
//             <button type="submit" className="action-button">
//                 Открыть
//             </button>
//         </form>
//     </td>
//     <td>
//         <form action="#" th:action="@{/book/{id}/edit(id=${book.id})}" th:method="get">
//             <button type="submit" className="action-button">
//                 Изменить
//             </button>
//         </form>
//     </td>
//     <td>
//         <form action="#" th:action="@{/book/{id}(id=${book.id})}" th:method="delete">
//             <button type="submit" className="action-button">
//                 Удалить
//             </button>
//         </form>
//     </td>
// </tr>