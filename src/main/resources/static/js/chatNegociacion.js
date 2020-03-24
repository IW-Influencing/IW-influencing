document.addEventListener("DOMContentLoaded", () => {
	let propuesta = document.querySelectorAll('.propuesta')	
	propuesta.onclick = (e) => {
		e.preventDefault();
		go("/devuelveChatNegociacion", 'POST', {nombre: propuesta.find("span").value})
		.then()
		.catch()
		}	
	});