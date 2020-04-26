document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("registroLogin").onclick = c => cargaModalPerfil()
})


function cargaModalPerfil(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "registro", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}
