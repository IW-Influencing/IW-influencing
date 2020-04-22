document.addEventListener("DOMContentLoaded", () => {
	let inputBusqueda =  document.getElementById("cuadroBusquedaTagBar")
	inputBusqueda.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
		    // Cancel the default action, if needed
		    event.preventDefault();
		    // Trigger the button element with a click
			if (inputBusqueda.value != "")
		    	cargaBusquedas(inputBusqueda.value);
		 }
	}); 
		for (let boton of document.getElementsByClassName("botonPaginacion")) {
			boton.onclick = p => botonLista(boton.dataset.propPatron,boton.dataset.propIndice);
	}

	
})


function cargaBusquedas(patron){
	return go2(config.rootUrl + "busquedaPerfil/busca?patron=" + patron, 'GET')
	.then(html => { 
var  div = document.getElementById("divPerfiles");
div.innerHTML = html;
})
		.catch(e => console.log(e))

}


function botonLista(patron="", indice){
	return go2(config.rootUrl + "busquedaPerfil/busca?patron=" + patron+"&indicePagina="+indice, 'GET')
	.then(html => { var  div = document.getElementById("divPerfiles");
	div.innerHTML = html;
})
		.catch(e => console.log(e))
}