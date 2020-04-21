document.addEventListener("DOMContentLoaded", () => {
	let inputBusqueda =  document.getElementById("cuadroBusquedaTagBar")
	inputBusqueda.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
		    // Cancel the default action, if needed
		    event.preventDefault();
		    // Trigger the button element with a click
		    cargaBusquedas(inputBusqueda.value);
		 }
	}); 
	
})


function cargaBusquedas(patron){
	return go2(config.rootUrl + "busquedaPropuesta/busca?patron=" + patron, 'GET')
	.then(html => { 
	var  div = document.getElementById("divPropuestas");
	div.innerHTML = html;
})
		.catch(e => console.log(e))

}