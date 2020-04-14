function update(a) {

	
	let c = document.getElementById("c");
	a.innerHTML = "<i>hola mundo </i>";
	a.style = "color: " + c.value + ";"

}


function insertaEnDiv(json, contenido, usuario, nombrePropuesta){
	let html = ""
	
	json.forEach(msg => {
		let htmlAux
		if (msg.propio){			
			htmlAux = "<p class='mensaje enviado'> " + msg.descripcion + "</p> "			
		}
		
		else{
			htmlAux = "<p class='mensaje recibido'> " + msg.descripcion + "</p> "
		}
		html += htmlAux 
	})
	
	contenido.innerHTML = html;
	usuario.innerHTML = json[0].nombreUsuario;
	nombrePropuesta.innerHTML = json[0].nombrePropuesta;	
}

function cargaChat(idCandidatura){
	let usuario = document.getElementsByClassName("perfil")
	let nombrePropuesta = document.getElementsByClasName("nombre")
	let contenido = document.getElementById("contenidoChat")
	
	return go(serverApiUrl + "messages/getChat", 'GET', idCandidatura)
	.then(response => response.json())
	.then(json => insertaEnDiv(json, contenido, usuario, nombrePropuesta))

	
}

window.onload = () => {
	let botonEnviar = document.getElementById("b");
	
	
	let divChat = document.getElementById("a");
	b.onclick = e => update(a);
	
	let propuestas = document.getElementsByClassName("propuesta");
	propuestas.forEach(p.onclick = c => cargaChat(p));
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