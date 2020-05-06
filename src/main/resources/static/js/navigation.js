document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("botonNotificacionesNav").onclick = b => cargaNotificacionesModalNav();
    document.getElementById("botonPerfilNav").onclick = b => cargaPerfilModalNav();
});

function cargaPerfilModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil/edicion", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaNotificacionesModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "notificaciones", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
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