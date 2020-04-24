document.addEventListener("DOMContentLoaded", () => {
	let inputBusqueda =  document.getElementById("inputBusquedaContrataciones")
	inputBusqueda.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
		    // Cancel the default action, if needed
		    event.preventDefault();
		    // Trigger the button element with a click
			if (inputBusqueda.value != "")
			    cargaBusquedas(inputBusqueda.value);
		 }
	});
	
	for (let p of document.getElementsByClassName("btnValorar")) {
		p.onclick = c => cargaModalValorar(p.dataset.id)
	}
	
		for (let p of document.getElementsByClassName("btnDetalles")) {
		p.onclick = c => cargaModalPropuesta(p.dataset.id)
	}
		
})


function cargaModalValorar(idContratacion){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "valoracion?idContratacion=" + idContratacion, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaModalPropuesta(idPropuesta){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "propuesta?idPropuesta=" + idPropuesta, 'GET')
		.then(html => document.getElementById("contenidoModal").innerHTML=html);
}

function cargaBusquedas(patron){
	return go2(config.rootUrl + "contrataciones/busca?patron=" + patron, 'GET')
	.then(html => { var  div = document.getElementById("divContrataciones");
	div.innerHTML = html;
})
		.catch(e => console.log(e))

}
