document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("botonNotificacionesNav").onclick = b => cargaNotificacionesModalNav();
	document.getElementById("botonPerfilNav").onclick = b => cargaPerfilModalNav();
	if (document.getElementById("btnNumeroNotificaciones").innerText == "0")
		document.getElementById("indiceNumNotificaciones").style.display = "none";
	
});

function cargaPerfilModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil/edicion", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaNotificacionesModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "notificaciones", 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			configuraCierreNotificaciones();
		});
}

function configuraCierreNotificaciones(){
	 for (let p of document.getElementsByClassName("eliminaNotificacion")){
        p.onclick =  c => { 
			marcaNotificacionLeida(p.dataset.id);
		}
    }
}

function marcaNotificacionLeida(idNotificacion){
	return go2(config.rootUrl + "notificaciones/elimina?idNotificacion="+idNotificacion, 'GET')
	.then(numNotificaciones => {
		if (numNotificaciones > 0){
			numNotificaciones = numNotificaciones;
			document.getElementById("btnNumeroNotificaciones").innerText = numNotificaciones;
		}
		else{
			document.getElementById("indiceNumNotificaciones").style.display = "none";
		}
		cargaNotificacionesModalNav();
	});
}

// Desplegable RRSS
function abreEmpresa(evt, nombreEmpresa) {
	let i, x, tablinks;
	x = document.getElementsByClassName("empresa");
	for (i = 0; i < x.length; i++) {
		x[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablink");
	for (i = 0; i < x.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" w3-border-red", "");
	}
	document.getElementById(nombreEmpresa).style.display = "block";
	evt.currentTarget.firstElementChild.className += " w3-border-red";
}

function lanzaAlert() {
	if (document.getElementById("mensajeInfo").textContent !== "")
	  alert(document.getElementById("mensajeInfo").textContent);
}

function cargaModalDenuncia(idDenunciado){
	let url = window.location.href.toString().split(window.location.host)[1];
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "denuncia?idDenunciado=" + idDenunciado+"&ruta="+url, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}