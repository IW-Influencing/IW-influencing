// function update(a) {
// 	let c = document.getElementById("c");
// 	a.innerHTML = "<i>hola mundo </i>";
// 	a.style = "color: " + c.value + ";"
// }


function insertaEnDiv(json, contenido, idPropuesta){
	let html = []
	
	json.forEach(msg => {
		let clase = msg.propio ? 'mensaje enviado' : 'mensaje recibido';
		html.push("<p class='" + clase + "'> " + msg.text + "</p>");
	})
	
	contenido.innerHTML = html.join("\n");

	document.getElementById("botonUltimatum").onclick = b => cargaModal(idPropuesta);
}

function cargaModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "ultimatum?idPropuesta=" + idPropuesta, 'GET')
		.then(json => document.getElementById("propuesta-ultimatum").innerHTML=json);
}

function cargaChat(idCandidatura, idPropuesta, nombreUsuario, nombrePropuesta) {
	document.getElementsByClassName("perfil")[0].innerHTML = nombreUsuario;
	document.getElementsByClassName("nombre")[0].innerHTML = nombrePropuesta;
	let contenido = document.getElementById("contenidoChat");
	console.log(idPropuesta);

	return go(config.rootUrl + "message/getChat?idCandidatura=" + idCandidatura, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta));
}

document.addEventListener("DOMContentLoaded", () => {
	for (let p of document.getElementsByClassName("propuesta")) {
		p.onclick = c => cargaChat(p.dataset.id, p.dataset.propId, p.dataset.otro, p.dataset.propNombre)
	}
})


// envía json, espera json de vuelta; lanza error si status != 200
function go2(url, method, data = {}) {
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
			return data = response.text();
		} else {
			response.text().then(t => { throw new Error(t + ", at " + url) });
		}
	})
}