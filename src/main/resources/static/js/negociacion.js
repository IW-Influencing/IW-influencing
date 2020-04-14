function update(a) {

	
	let c = document.getElementById("c");
	a.innerHTML = "<i>hola mundo </i>";
	a.style = "color: " + c.value + ";"

}

window.onload = () => {
	let botonEnviar = document.getElementById("b");
	let divChat = document.getElementById("a");
	b.onclick = e => update(a);
}



// envía json, espera json de vuelta; lanza error si status != 200
function go(url, method, data = {}) {
	let params = {
		method: method, // POST, GET, POST, PUT, DELETE, etc.
		headers: {
			"Content-Type": "application/json; charset=utf-8",
		},
		body: JSON.stringify(data)
	};
	if (method === "GET") {
		// GET requests cannot have body
		delete params.body;
	}
	console.log("sending", url, params)
	return fetch(url, params).then(response => {
		if (response.ok) {
			return data = response.json();
		} else {
			response.text().then(t => { throw new Error(t + ", at " + url) });
		}
	})
}