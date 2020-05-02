document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("botonCrearPropuesta").onclick = b => cargaPropuestaModal();
	document.getElementById("verTodasPropuestas").onclick = b => location.href= config.rootUrl + "busquedaPropuesta";
	for (let p of document.getElementsByClassName("btnVerPropuesta")){
		p.onclick = c => {cargaPropuestaEspecifica(p.dataset.id);}
	}
})

function cargaPropuestaModal(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta/creacion", 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaPropuestaEspecifica(idPropuesta){
		document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}