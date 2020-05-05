document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("registroLogin").onclick = c => cargaModalPerfil();
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
				validaPassword(document.getElementsByName("pass1")[0].value, document.getElementsByName("pass2")[0].value);
				validaDatosPerfil("Twitter");
				validaDatosPerfil("Facebook");
				validaDatosPerfil("Instagram");
				validaDatosPerfil("Youtube");
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

function validaPassword(pass1, pass2){
	document.getElementsByName("pass")[0].setCustomValidity(pass1 === pass2 ?"" : "Las contraseñas no coinciden")
	document.getElementsByName("pass")[1].setCustomValidity(pass1 === pass2 ?"" : "Las contraseñas no coinciden")
}



