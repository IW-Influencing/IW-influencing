document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("botonNotificacionesNav").onclick = b => cargaNotificacionesModalNav();
    document.getElementById("botonPerfilNav").onclick = b => cargaPerfilModalNav();
});

function cargaPerfilModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "edicionPerfil", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaNotificacionesModalNav(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "notificaciones", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}