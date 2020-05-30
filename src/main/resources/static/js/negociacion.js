document.addEventListener("DOMContentLoaded", () => {
	for (let p of document.getElementsByClassName("propuesta")) {
		p.onclick = c => {
			setConfigs(p);
			cargaChat(p.dataset.id, p.dataset.propId, p.dataset.otroNombre, p.dataset.propioId, p.dataset.otroId, p.dataset.propNombre,document.getElementById("contenidoChat"), p.dataset.estadoCandidatura);
		}
	}
	asignaListenerBarraEnvio();
	ws.receive = json => {
		if (pertenecePropuestaSeleccionada(json[0].nombrePropuesta,document.getElementsByClassName("nombre")[0].innerHTML)){
			let contenido = document.getElementById("contenidoChat");
			insertaEnDiv(json, contenido, config.propId, config.candidaturaId);
		}
	}
});

function pertenecePropuestaSeleccionada(propuestaMensajes, propuestaSeleccionada){
	return propuestaMensajes === propuestaSeleccionada;
}

function setConfigs(propuesta){
			config.propId = propuesta.dataset.propId;	
			config.receptorId = propuesta.dataset.otroId;
			config.emisorId = propuesta.dataset.propioId;
			config.candidaturaId = propuesta.dataset.id;
}

function asignaListenerBarraEnvio(){
	document.getElementById("botonEnviar").addEventListener("keyup", function(event) {
					if (event.keyCode === 13) {
					    // Cancel the default action, if needed
					    event.preventDefault();
					    // Trigger the button element with a click
				//Comprueba que el mensaje no esté vacío ni que solo contenga espacios en blanco
				if (this.value.length !== 0 && this.value.trim())
					    enviarMensajeChatNegociacion(this.value, config.candidaturaId,config.receptorId, config.emisorId, document.getElementById("contenidoChat"), config.propId);
						this.value = "";
						
					 }
				});
}


function parseaFecha(fecha){
	let array = fecha.split("T");
	let dias = array[0].split("-");
	let minutos = array[1].split(":");
	return dias[2] + "/" + dias[1] + "/" + dias[0] + "  " + minutos[0] + ":" + minutos[1];
}

function insertaEnDiv(json, contenido, idPropuesta, idCandidatura, estadoCandidatura){
	let html = []
	
	json.forEach(msg => {
		let clase = msg.propio ? 'mensaje enviado' : 'mensaje recibido';
		html.push("<p class='" + clase + " msg'> " + parseaFecha(msg.sent) + " - " + msg.text + "</p>");
	})
	
	contenido.innerHTML = html.join("\n");
	if (estadoCandidatura == "NEGOCIANDO"){
		document.getElementById("botonUltimatum").innerText = "Enviar Ultimatum";
		document.getElementById("botonUltimatum").onclick = b => cargaUltimatumModal(idPropuesta, idCandidatura);
		document.getElementById("botonEnviar").style.display = "block";

	}
	else{
		document.getElementById("botonUltimatum").innerText = "Visualizar Ultimatum";
		document.getElementById("botonUltimatum").onclick = b => cargaUltimatumVisualizacionModal(idPropuesta);
		document.getElementById("botonEnviar").style.display = "none";
	}

}

function cargaUltimatumModal(idPropuesta, idCandidatura){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta/ultimatum?idPropuesta=" + idPropuesta + "&idCandidatura=" + idCandidatura, 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			document.getElementById("btnEnviarUltimatum").onclick = b => enviaUltimatum();
		});
}

function cargaUltimatumVisualizacionModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta/vistaUltimatum?idPropuesta=" + idPropuesta, 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			document.getElementById("btnEnviarUltimatum").onclick = b => enviaUltimatum();
		});
}

function enviaUltimatum(){
		let edades=document.getElementsByName("edades")[0].value;
		let sueldo=document.getElementsByName("sueldo")[0].value;
		let fechaInicio=document.getElementsByName("fechaInicio")[0].value;
		let fechaFin=document.getElementsByName("fechaFin")[0].value;
		let idPropuesta=document.getElementsByName("idPropuesta")[0].value;
		let idCandidatura=document.getElementsByName("idCandidatura")[0].value;
		
	
	return go(config.rootUrl + "propuesta/enviaUltimatum?edades="+edades+"&sueldo="+sueldo+"&fechaInicio="+fechaInicio+"&fechaFin="+fechaFin
	+"&idPropuesta="+idPropuesta+"&idCandidatura="+idCandidatura, 'GET')
		.then(json => {
			insertaMensaje(json);
			document.getElementById("botonUltimatum").innerText = "Visualizar Ultimatum";
			document.getElementById("botonUltimatum").onclick = b => cargaUltimatumVisualizacionModal(idPropuesta);
			document.getElementById("botonEnviar").style.display = "none";
			enviarMensajeChatNegociacion("Se ha enviado un ultimatum",config.candidaturaId,config.receptorId, config.emisorId, document.getElementById("contenidoChat"), config.propId);
			document.getElementById('modal').style.display='none';
		});
}

function insertaMensaje(json){
	let clase = json[0].propio ? 'mensaje enviado' : 'mensaje recibido';
	document.getElementById("contenidoChat").innerHTML.push("\n <p class='" + clase + " msg'> " + parseaFecha(msg.sent) + " - " + msg.text + "</p>")
	
}

function cargaPerfilModal(idUsuario){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil?idUsuario=" + idUsuario, 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			document.getElementById("btnDenunciar").onclick = b => cargaModalDenuncia(document.getElementById("btnDenunciar").dataset.id);
		})
}


function cargaPropuestaModal(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			document.getElementById("btnDenunciar").onclick = b => cargaModalDenuncia(document.getElementById("btnDenunciar").dataset.id);
		})
}

function cargaChat(idCandidatura, idPropuesta, nombreUsuario,idEmisor, idReceptor, nombrePropuesta, contenido, estadoCandidatura) {
	document.getElementsByClassName("perfil")[0].innerHTML = nombreUsuario;
	document.getElementsByClassName("perfil")[0].onclick = b => cargaPerfilModal(idReceptor);
	document.getElementsByClassName("nombre")[0].innerHTML = nombrePropuesta;
	document.getElementsByClassName("nombre")[0].onclick = b => cargaPropuestaModal(idPropuesta);
	return go(config.rootUrl + "message/getChat?idCandidatura=" + idCandidatura+"&idUsuario="+idEmisor, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta, idCandidatura, estadoCandidatura));
}

function enviarMensajeChatNegociacion(mensaje, idCandidatura, idReceptor, idEmisor, contenido, idPropuesta){
	return go(config.rootUrl + "message/insertaMsg?idCandidatura=" + idCandidatura + "&msg=" + mensaje +"&idEmisor="+idEmisor+"&idReceptor="+idReceptor, 'GET')
		.then(json => insertaEnDiv(json, contenido, idPropuesta, idCandidatura));
		
}




