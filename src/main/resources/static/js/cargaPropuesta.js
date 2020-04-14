function cargaModal(){
    document.getElementById('id01').style.display='none';
    let modalPropuesta = document.getElementById("modalPropuesta");
    return go(serverApiUrl + "propuesta", 'GET', idPropuesta)
	.then(response => response.json())
	.then(json => insertaEnDiv(json, contenido, usuario, nombrePropuesta));
}

window.onload = () => {
    let modalPropuesta = document.getElementById("modalPropuesta");
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