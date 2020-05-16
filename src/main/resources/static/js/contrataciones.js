function prepareListeners(tipo) {
	let inputBusqueda =  document.getElementById("cuadroBusquedaTagBar")
	inputBusqueda.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
		    // Cancel the default action, if needed
		    event.preventDefault();
		    // Trigger the button element with a click
			cargaBusquedas(inputBusqueda.value);
		 }
	});
	if (tipo == "tipo"){
		for (let boton of document.getElementsByClassName("botonPaginacion")) {
			boton.onclick = p => botonListaEstados(boton.dataset.propPatron,boton.dataset.propIndice);			
		}
	}
	else{
		for (let boton of document.getElementsByClassName("botonPaginacion")) {
				boton.onclick = p => botonLista(boton.dataset.propPatron,boton.dataset.propIndice);
		}
	}
	for (let p of document.getElementsByClassName("btnValorar")) {
		p.onclick = c => cargaModalValorar(p.dataset.id, p.dataset.idPropuesta);
	}
	
	for (let p of document.getElementsByClassName("btnDetalles")) {
			p.onclick = c => cargaModalPropuesta(p.dataset.id)
	}
	for (let p of document.getElementsByClassName("tagFilter")) {
		p.onclick = c => cargaBusquedasPorEstado(p.dataset.id)
	}
	
	
}


document.addEventListener("DOMContentLoaded", () => {
	prepareListeners();
	lanzaAlert();

})


function cargaModalValorar(idCandidatura, idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "contrataciones/valorar?idCandidatura="+idCandidatura+"&idPropuesta=" + idPropuesta, 'GET')
		.then(html => {
			document.getElementById("contenidoModal").innerHTML=html;
			document.getElementsByName("puntuacion")[0].addEventListener('change', pintaEstrellas);
		});
}

function pintaEstrellas(puntuacion){
	let parteEntera = puntuacion.target.value[0];
	let parteDecimal = puntuacion.target.value[2];
	
	tablinks = document.getElementsByClassName("estrella");
	for (i = 0; i < parteEntera; i++) {
		tablinks[i].className = tablinks[i].className = "estrella fa fa-star";
	}
	if (parteDecimal === '5'){
		tablinks[parteEntera].className = tablinks[parteEntera].className = "estrella fa fa-star-half-alt";
		for (i = parteEntera+1; i < 5; i++){
		tablinks[i].className = tablinks[i].className = "estrella far fa-star";
	}
	}
	else{
		for (i = parteEntera; i < 5; i++){
		tablinks[i].className = tablinks[i].className = "estrella far fa-star";
	}
	}
		
}

function cargaModalPropuesta(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "contrataciones/vista?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaBusquedas(patron){
	return go2(config.rootUrl + "contrataciones/busca?patron=" + patron, 'GET')
	.then(html => { var  div = document.getElementById("divContrataciones");
	div.innerHTML = html;
})
		.catch(e => console.log(e))

}

function cargaBusquedasPorEstado(estado){
	return go2(config.rootUrl + "contrataciones/estado?estado=" + estado, 'GET')
	.then(html => { 
	var  div = document.getElementById("divContrataciones");
		div.innerHTML = html;
		prepareListeners("tipo");
	})
		.catch(e => console.log(e))

}

function botonLista(patron="", indice){
	return go2(config.rootUrl + "contrataciones/busca?patron=" + patron+"&indicePagina="+indice, 'GET')
		.then(html => { 
			var  div = document.getElementById("divContrataciones");
			div.innerHTML = html;
			prepareListeners();
		})
		.catch(e => console.log(e))
}

function botonListaEstados(estado="", indice){
	return go2(config.rootUrl + "contrataciones/estado?estado=" + estado+"&indicePagina="+indice, 'GET')
		.then(html => { 
			var  div = document.getElementById("divContrataciones");
			div.innerHTML = html;
			prepareListeners("tipo");
		})
		.catch(e => console.log(e))
}
