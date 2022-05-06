/*
ADD YOUR CLIENT-SIDE CODE FOR add.html HERE
*/
let titleInput = document.getElementById("title");
let dropdown = document.getElementById("genre");
let quality = document.getElementById("quality");
let button = document.getElementById("submit");
let message = document.getElementById("message");
button.addEventListener("click", function() {
	let book = {
		title: titleInput.value,
		// quality: quality.checked? "yes":"no",
		genre: dropdown.value,
	}

	// let book ={
		// name: titleInput.value
	// }
	// bookName = titleInput.value
	console.log(`http://localhost:8080/books/post`)
	fetch(`http://localhost:8080/books/post`, {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(book),
	}).then(function (response) {
		if(response.status === 200)
            message.textContent = "Success";
        else
            message.textContent = `Bad request. Response status : ${response.status}`

	}).catch(function (error) {
		console.log(error);

	});

});