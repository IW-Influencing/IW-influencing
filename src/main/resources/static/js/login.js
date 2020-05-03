document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("registroLogin").onclick = c => cargaModalPerfil()
})


function cargaModalPerfil(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil/creacion", 'GET')
		.then(html => {document.getElementById("contenidoModal").innerHTML=html;
			asignaFuncionBotonRegistro()});
		
}


function asignaFuncionBotonRegistro(){
	let b = document.getElementById("botonRegistrarseUsuario");
			b.onclick = c => {
				var nombreCuenta = document.getElementsByName("nombreCuenta")[0].value;
				var nombre = document.getElementsByName("nombre")[0].value;
				var edad = document.getElementsByName("edad")[0].value;	
				var pass1 = document.getElementsByName("pass")[0].value;
				var pass2 = document.getElementsByName("pass")[1].value;
				validaPassword(pass1, pass2);
				var perfilTwitter = devuelveDatosPerfil("Twitter");
				var perfilFacebook = devuelveDatosPerfil("Facebook");
				var perfilInstagram = devuelveDatosPerfil("Instagram");
				var perfilYoutube = devuelveDatosPerfil("Youtube");
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
					.then(console.log("he vuelto"))
					.catch()
				registraUsuario(nombreCuenta, nombre, edad, pass1, pass2, perfilTwitter, perfilFacebook, perfilInstagram, perfilYoutube);
			}
	}

function devuelveDatosPerfil(perfil){
	let nombre = document.getElementsByName("nombre"+perfil)[0];
	let seguidores = document.getElementsByName("seguidores"+perfil)[0];
	if (nombre.value !== ""  || seguidores.value !== ""){
		console.log(nombre.value)
		console.log(seguidores.value)
		seguidores.required = true;
		nombre.required = true;
	}
	
	return {nombre:nombre.value, seguidores:seguidores.value};
}

function validaPassword(pass1, pass2){
	document.getElementsByName("pass")[0].setCustomValidity(pass1 === pass2 ?"" : "Las contraseñas no coinciden")
	document.getElementsByName("pass")[1].setCustomValidity(pass1 === pass2 ?"" : "Las contraseñas no coinciden")
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
		.then(console.log("he vuelto"))
		.catch()	
}



