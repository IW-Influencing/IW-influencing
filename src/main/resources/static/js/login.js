document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("registroLogin").onclick = c => cargaModalPerfil();
	lanzaAlert();

})


function cargaModalPerfil(){
	document.getElementById('modal').style.display='block';
	return go2(config.rootUrl + "perfil/creacion", 'GET')
		.then(html => {document.getElementById("contenidoModal").innerHTML=html;
			asignaFuncionBotonRegistro()
			document.getElementById("tipoCuenta").addEventListener('change', compruebaPerfiles);});		
}


function compruebaPerfiles(){
		if (this.value === 'Empresa'){
			document.getElementById("divPerfilesCreacion").style.display = "none";
		}
		else{
			document.getElementById("divPerfilesCreacion").style.display = "block";

		}
}

function asignaFuncionBotonRegistro(){
	let b = document.getElementById("botonRegistrarseUsuario");
			b.onclick = c => {
				validaPassword(document.getElementsByName("pass1")[0], document.getElementsByName("pass2")[0]);
				validaTexto(document.getElementsByName("nombre")[0]);
				validaTexto(document.getElementsByName("nombreCuenta")[0]);
				validaDatosPerfil("Twitter");
				validaDatosPerfil("Facebook");
				validaDatosPerfil("Instagram");
				validaDatosPerfil("Youtube");
				if (document.getElementsByName("tipoCuenta")[0].value == 'Influencer')
					document.getElementsByName("edad")[0].required=true;
			}
	}

function validaDatosPerfil(perfil){
	let nombre = document.getElementsByName("nombre"+perfil)[0];
	let seguidores = document.getElementsByName("seguidores"+perfil)[0];
	if (nombre.value !== ""  || seguidores.value !== ""){
		seguidores.required = true;
		nombre.required = true;
	}
	
}

function validaTexto(campo){
	campo.setCustomValidity(campo.value === campo.value.trim()?"":"No se pueden introducir espacios al comienzo ni al final");
}

function validaPassword(pass1, pass2){
	pass1.setCustomValidity(pass1.value === pass2.value ?"" : "Las contraseñas no coinciden")
	pass2.setCustomValidity(pass1.value === pass2.value ?"" : "Las contraseñas no coinciden")
}



