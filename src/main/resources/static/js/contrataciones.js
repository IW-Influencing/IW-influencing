document.addEventListener("DOMContentLoaded", () => {
	let inputBusqueda =  document.getElementById("inputBusquedaContrataciones")
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
	return go(config.rootUrl + "contrataciones/busca", 'POST', {patron:patron})
	.then(d => {serverToken = d.token; updateState(d);return d;})
		.catch(e => console.log(e))
}