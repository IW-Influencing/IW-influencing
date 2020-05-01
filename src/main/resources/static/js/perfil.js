document.addEventListener("DOMContentLoaded", () => {
	
	
	for (let p of document.getElementsByClassName("propuesta")) {
		p.onclick = c => {
			config.propId = p.dataset.propId;
			cargaChat(p.dataset.id, p.dataset.propId, p.dataset.otroNombre, p.dataset.otroId, p.dataset.propNombre);
		}
	}

	ws.receive = json => {
		let contenido = document.getElementById("contenidoChat");
		insertaEnDiv(json, contenido, config.propId);
	}
})
