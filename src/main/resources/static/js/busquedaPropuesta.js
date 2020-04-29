function prepareListeners() {
let inputBusqueda =  document.getElementById("cuadroBusquedaTagBar")
	inputBusqueda.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
		    // Cancel the default action, if needed
		    event.preventDefault();
		    // Trigger the button element with a click
		    cargaBusquedas(inputBusqueda.value);
		 }
	}); 
	
		for (let boton of document.getElementsByClassName("botonPaginacion")) {
			boton.onclick = p => botonLista(boton.dataset.propPatron,boton.dataset.propIndice);
	}
	
	for (let p of document.getElementsByClassName("imagen")) {
		p.onclick = c => cargaModalPropuesta(p.dataset.id)
	}
	
	for (let p of document.getElementsByClassName("bNombreEmpresa")) {
		p.onclick = c => cargaModalPerfil(p.dataset.id)
	}
	
	for (let p of document.getElementsByClassName("btnDetalles")) {
		p.onclick = c => cargaModalPropuesta(p.dataset.id)
	}
}

document.addEventListener("DOMContentLoaded", () => {
	prepareListeners();
})


function cargaModalPropuesta(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}


function cargaModalPerfil(idPerfil){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil?idUsuario=" + idPerfil, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}


function cargaBusquedas(patron){
	return go2(config.rootUrl + "busquedaPropuesta/busca?patron=" + patron, 'GET')
	.then(html => { 
	var  div = document.getElementById("divPropuestas");
	div.innerHTML = html;
				prepareListeners();

})
		.catch(e => console.log(e))

}

function botonLista(patron="", indice){
	console.log("botonLista", patron, indice);
	return go2(config.rootUrl + "busquedaPropuesta/busca?patron=" + patron+"&indicePagina="+indice, 'GET')
		.then(html => { 
			var  div = document.getElementById("divPropuestas");
			div.innerHTML = html;
			prepareListeners();
		})
		.catch(e => console.log(e))
}
