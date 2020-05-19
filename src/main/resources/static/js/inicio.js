document.addEventListener("DOMContentLoaded", () => {
	lanzaAlert();
	let botonCrearPropuesta = document.getElementById("botonCrearPropuesta");
	if (botonCrearPropuesta !== null){
		botonCrearPropuesta.onclick = b => cargaPropuestaModal();
	}
	document.getElementById("verTodasPropuestas").onclick = b => location.href= config.rootUrl + "busquedaPropuesta";
	for (let p of document.getElementsByClassName("btnVerPropuesta")){
		p.onclick = c => {cargaPropuestaEspecifica(p.dataset.id);}
	}
})

function cargaPropuestaModal(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta/creacion", 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			validaCamposRegistroPropuesta();
		});
}

function cargaPropuestaEspecifica(idPropuesta){
		document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function validaCamposRegistroPropuesta(){
	let b = document.getElementById("btnPublicarPropuesta");
	b.onclick = c => {
		validaTexto(document.getElementsByName("nombre")[0]);
		validaTexto(document.getElementsByName("descripcion")[0]);
		validaTexto(document.getElementsByName("tags")[0]);
		validaTags(document.getElementsByName("tags")[0]);
		validaLongitudNombre(document.getElementsByName("nombre")[0], 30);
		validaFechas(document.getElementsByName("fechaInicio")[0], document.getElementsByName("fechaFin")[0]);
	}
}


function validaFechas(fechaInicio, fechaFin){
	let menor = true;
	if (fechaInicio.value < fechaFin.value){
		menor = false;
	}
	fechaInicio.setCustomValidity(!menor?"":"La fecha de inicio debe ser menor a la fecha de fin de la propuesta");
	fechaFin.setCustomValidity(!menor?"":"La fecha de inicio debe ser menor a la fecha de fin de la propuesta");

}

function validaTags(tags){
	let valores = tags.value;
	let valido = true;
	let largo = false;
	for (p of valores.split(",")){
		if (p !== p.trim())
			valido = false;
		else if(p.length > 30){
			largo = true;
		}
		
	}
	tags.setCustomValidity(!valido?"No se pueden introducir espacios al principio ni al final del tag":
						  (!largo)?"":"La longitud máxima de un tag es de 30 caracteres");
}

function validaLongitudNombre(nombre,longitud){
	nombre.setCustomValidity(nombre.value.length <= 30 ? "":"La longitud máxima del nombre es de 30 caracteres");
}

function validaTexto(campo){
	campo.setCustomValidity(campo.value === campo.value.trim()?"":"No se pueden introducir espacios al comienzo ni al final");
}