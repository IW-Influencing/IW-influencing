document.addEventListener("DOMContentLoaded", () => {
	for (let p of document.getElementsByClassName("propuesta")) {
		p.onclick = c => {
			var contenido = document.getElementById("contenidoChat");
			var idCandidatura = p.dataset.id, idPropuesta=p.dataset.propId, nombreUsuario=p.dataset.otroNombre,
			idEmisor = p.dataset.propioId, idReceptor = p.dataset.otroId, nombrePropuesta=p.dataset.propNombre;
			config.propId = p.dataset.propId;	
			cargaChat(idCandidatura, idPropuesta, nombreUsuario,idEmisor, idReceptor, nombrePropuesta,contenido);
			document.getElementById("botonEnviar").addEventListener("keyup", function(event, idCandidatura, idReceptor, contenido, idPropuesta) {
					if (event.keyCode === 13) {
					    // Cancel the default action, if needed
					    event.preventDefault();
					    // Trigger the button element with a click
				//Comprueba que el mensaje no esté vacío ni que solo contenga espacios en blanco
				if (this.value.length !== 0 && this.value.trim())
					    enviarMensajeChatNegociacion(this.value, p.dataset.id,p.dataset.otroId, document.getElementById("contenidoChat"), p.dataset.propId);
						this.value = "";
						
					 }
				});
			}
	}

	ws.receive = json => {
		let contenido = document.getElementById("contenidoChat");
		insertaEnDiv(json, contenido, config.propId);
	}
})

function insertaEnDiv(json, contenido, idPropuesta){
	let html = []
	
	json.forEach(msg => {
		let clase = msg.propio ? 'mensaje enviado' : 'mensaje recibido';
		html.push("<p class='" + clase + " msg'> " + msg.sent + " - " + msg.text + "</p>");
	})
	
	contenido.innerHTML = html.join("\n");

	document.getElementById("botonUltimatum").onclick = b => cargaUltimatumModal(idPropuesta);
}

function cargaUltimatumModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta/ultimatum?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaPerfilModal(idUsuario){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil?idUsuario=" + idUsuario, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}


function cargaPropuestaModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaChat(idCandidatura, idPropuesta, nombreUsuario,idEmisor, idReceptor, nombrePropuesta, contenido) {
	document.getElementsByClassName("perfil")[0].innerHTML = nombreUsuario;
	document.getElementsByClassName("perfil")[0].onclick = b => cargaPerfilModal(idReceptor);
	document.getElementsByClassName("nombre")[0].innerHTML = nombrePropuesta;
	document.getElementsByClassName("nombre")[0].onclick = b => cargaPropuestaModal(idPropuesta);
	return go(config.rootUrl + "message/getChat?idCandidatura=" + idCandidatura+"&idUsuario="+idEmisor, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta));
}

function enviarMensajeChatNegociacion(mensaje, idCandidatura,idReceptor, contenido, idPropuesta){
	return go(config.rootUrl + "message/insertaMsg?idCandidatura=" + idCandidatura + "&msg=" + mensaje +"&idReceptor="+idReceptor, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta));
		
}




