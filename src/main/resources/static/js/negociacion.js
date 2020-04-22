document.addEventListener("DOMContentLoaded", () => {
	for (let p of document.getElementsByClassName("propuesta")) {
		p.onclick = c => cargaChat(p.dataset.id, p.dataset.propId, p.dataset.otroNombre, p.dataset.otroId, p.dataset.propNombre)
	}
})

function insertaEnDiv(json, contenido, idPropuesta){
	let html = []
	
	json.forEach(msg => {
		let clase = msg.propio ? 'mensaje enviado' : 'mensaje recibido';
		html.push("<p class='" + clase + "'> " + msg.sent + " - " + msg.text + "</p>");
	})
	
	contenido.innerHTML = html.join("\n");

	document.getElementById("botonUltimatum").onclick = b => cargaUltimatumModal(idPropuesta);
}

function cargaUltimatumModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "ultimatum?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("propuesta-ultimatum").innerHTML=html);
}

function cargaPerfilModal(idUsuario){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil?idUsuario=" + idUsuario, 'GET')
		.then(html => document.getElementById("propuesta-ultimatum").innerHTML=html);
}


function cargaPropuestaModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("propuesta-ultimatum").innerHTML=html);
}

function cargaChat(idCandidatura, idPropuesta, nombreUsuario, idUsuario, nombrePropuesta) {
	document.getElementsByClassName("perfil")[0].innerHTML = nombreUsuario;
	document.getElementsByClassName("perfil")[0].onclick = b => cargaPerfilModal(idUsuario);
	document.getElementsByClassName("nombre")[0].innerHTML = nombrePropuesta;
	document.getElementsByClassName("nombre")[0].onclick = b => cargaPropuestaModal(idPropuesta);
	let contenido = document.getElementById("contenidoChat");
	console.log(idPropuesta);

	return go(config.rootUrl + "message/getChat?idCandidatura=" + idCandidatura, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta));
}



