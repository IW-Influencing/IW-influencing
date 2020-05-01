document.addEventListener("DOMContentLoaded", () => {
	
	let b = document.getElementById("botonRegistrarseUsuario");
		b.onclick = c => {
			validaCamposRegistro();
			var nombreCuenta = document.getElementsByName("nombreCuenta").value;
			var nombre = document.getElementsByName("nombre").value;
			var edad = document.getElementsByName("edad").value;	
			var pass1 = document.getElementsByName("pass1").value;
			var pass2 = document.getElementByName("pass2").value;
			var perfilTwitter = devuelveDatosPerfil("Twitter");
			var perfilFacebook = devuelveDatosPerfil("Facebook")
			var perfilInstagram = devuelveDatosPerfil("Instagram")
			var perfilYoutube = devuelveDatosPerfil("Youtube")
			registraUsuario(nombreCuenta, nombre, edad, pass1, pass2, perfilTwitter, perfilFacebook, perfilInstagram, perfilYoutube);
		}

})

function devuelveDatosPerfil(perfil){
	let campoNombre = "nombre"+perfil;
	let campoSeguidores = "seguidores"+perfil;
	return {nombre:document.getElementsByName(campoNombre).value, seguidores = document.getElementsByName(campoSeguidores).value};
}

function registraUsuario(nombreCuenta, nombre, edad, pass1, pass2, perfilTwitter, perfilFacebook, perfilInstagram, perfilYoutube){
	go(config.rootUrl + "perfil/registraUsuario", 'POST', 
	{nombreCuenta: nombreCuenta,
	 nombre: nombre,
	 edad: edad,
	 pass1: pass1,
	 pass2: pass2,
	 perfilTwitter: perfilTwitter,
	 perfilFacebook: perfilFacebook,
	 perfilInstagram: perfilInstagram,
	 perfilYoutube: perfilYoutube})
		.then()
		.catch()
		
	
}
