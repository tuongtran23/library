/*
ADD YOUR CLIENT-SIDE CODE FOR search.html HERE
*/
let dropdown = document.getElementById("genre");
let table = document.getElementById("books");
let button = document.getElementById("submit");

function removeChildren(element) {
	while (element.hasChildNodes()) {
		element.lastChild.remove();
	}
}

button.addEventListener("click", function() {
	let genre = dropdown.value;
	fetch(`http://localhost:8080/books/list?genre=${genre}`).then(function (response) {
		console.log(response);
		if (response.status === 200) {
			return response.json();
		} else {
			throw Error(response.status);
		}
	}).then(function (response) {
		removeChildren(table);
		for (let row of response) {
			let tableRow = document.createElement("tr");
			for (let key of ["name", "genre"]) {
				let cell = document.createElement("td");
				cell.textContent = row[key];
				tableRow.append(cell);
			}
			table.append(tableRow);
		}
	}).catch(function (error) {
		console.log(error);
	});

});