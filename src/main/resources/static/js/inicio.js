document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("botonCrearPropuesta").onclick = b => cargaPropuestaModal();
})

function cargaPropuestaModal(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "creacionPropuesta", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}