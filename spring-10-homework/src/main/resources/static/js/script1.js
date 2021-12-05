// const header = document.getElementById('title');
// const content = document.getElementById('content')
//
// getAllBooks();
//
// function getAllBooks() {
//     const tbodyOld = document.getElementsByTagName('tbody')[0];
//     const tbodyNew = document.createElement('tbody');
//     fetch('/rest/book')
//         .then(response => response.json())
//         .then(books => books.forEach(function (book) {
//             const tr = document.createElement('tr');
//
//             const id = document.createElement('td');
//             id.innerHTML = book.id;
//             const title = document.createElement('td');
//             title.innerHTML = book.title;
//             const authors = document.createElement('td');
//             authors.innerHTML = book.authors;
//             const genres = document.createElement('td');
//             genres.innerHTML = book.genres;
//
//             const openButton = document.createElement('button');
//             openButton.className = 'action-button';
//             openButton.id = 'open-button';
//             openButton.value = book.id;
//             openButton.innerText = 'Открыть';
//
//             const editButton = document.createElement('button');
//             editButton.className = 'action-button';
//             editButton.id = 'edit-button';
//             editButton.value = book.id;
//             editButton.innerText = 'Изменить';
//
//             const deleteButton = document.createElement('button');
//             deleteButton.className = 'action-button';
//             deleteButton.id = 'delete-button';
//             deleteButton.value = book.id;
//             deleteButton.innerText = 'Удалить';
//
//             tr.append(id, title, authors, genres, openButton, editButton, deleteButton);
//             tbodyNew.append(tr);
//         }));
//     document.getElementsByTagName('table')[0].replaceChild(tbodyNew, tbodyOld);
// }
//
//
// document.addEventListener('click',async e => {
//     switch (e.target.id) {
//         case 'open-button':
//             fetch('/rest/book/' + e.target.value)
//                 .then(response => response.json())
//                 .then(book => {
//                     header.innerHTML = `Информация о книге`;
//                     content.innerHTML =
//                         `<table class="book-info">
//                             <tr class="row">
//                                 <td class="title"><b>ID:</b></td>
//                                 <td>${book.id}</td>
//                             </tr>
//                             <tr class="row">
//                                 <td class="title"><b>Название:</b></td>
//                                 <td class="value">${book.title}</td>
//                             </tr>
//                             <tr class="row">
//                                 <td class="title"><b>Автор(ы):</b></td>
//                                 <td class="value">${book.authors}</td>
//                             </tr>
//                             <tr class="row">
//                                 <td class="title"><b>Жанр(ы):</b></td>
//                                 <td class="value">${book.genres}</td>
//                             </tr>
//                         </table>`;
//                 });
//             break;
//         case 'edit-button':
//             await fetch('/rest/book/' + e.target.value)
//                 .then(response => response.json())
//                 .then(book => {
//                     header.innerHTML = `Редактировать информацию о книге`;
//                     const bookInfo = document.createElement('div');
//                     bookInfo.className = "bookInfo";
//
//                     const row = document.createElement('div');
//                     row.className = "row";
//
//                     const label = document.createElement('label');
//                     label.className = "book-label";
//                     label.for = "id-input";
//                     label.innerText = "ID:";
//                     const input = document.createElement('input');
//                     input.value = book.id;
//                     input.className = "book-edit-input";
//                     input.id = "id-input";
//                     row.append(label, input);
//                     bookInfo.append(row);
//                 });
//             break;
//         case 'delete-button':
//             await fetch('/rest/book/' + e.target.value, {method: 'DELETE'});
//             getAllBooks();
//             break;
//     }
//
// });
//
// // <tr th:each="book : ${books}">
// //     <td th:text="${book.id}">1</td>
// //     <td th:text="${book.title}">Book Title</td>
// //     <td th:text="${book.authors}">Authors</td>
// //     <td th:text="${book.genres}">Genres</td>
// //     <td>
// //         <form action="#" th:action="@{/book/{id}(id=${book.id})}" th:method="get">
// //             <button type="submit" className="action-button">
// //                 Открыть
// //             </button>
// //         </form>
// //     </td>
// //     <td>
// //         <form action="#" th:action="@{/book/{id}/edit(id=${book.id})}" th:method="get">
// //             <button type="submit" className="action-button">
// //                 Изменить
// //             </button>
// //         </form>
// //     </td>
// //     <td>
// //         <form action="#" th:action="@{/book/{id}(id=${book.id})}" th:method="delete">
// //             <button type="submit" className="action-button">
// //                 Удалить
// //             </button>
// //         </form>
// //     </td>
// // </tr>