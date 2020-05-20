document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("influencerAdmin").onclick = b => cargaResultadosInfluencer();
    document.getElementById("propuestasAdmin").onclick = b => cargaResultadosPropuestas(); 
    document.getElementById("denunciasAdmin").onclick = b => cargaResultadosDenuncias();
    document.getElementById("empresasAdmin").onclick = b => cargaResultadosEmpresas();
});


function cargaResultadosInfluencer(){
        return go2(config.rootUrl + "admin/influencers", 'GET')
        .then(html => { 
            var  div = document.getElementById("tablaAdministracion");
            div.innerHTML = html;
        })
            .catch(e => console.log(e))
    
    
}

function cargaResultadosEmpresas(){
    return go2(config.rootUrl + "admin/empresas", 'GET')
    .then(html => { 
        var  div = document.getElementById("tablaAdministracion");
        div.innerHTML = html;
    })
        .catch(e => console.log(e))
}

function cargaResultadosDenuncias(){
    return go2(config.rootUrl + "admin/denuncias", 'GET')
    .then(html => { 
        var  div = document.getElementById("tablaAdministracion");
        div.innerHTML = html;
    })
        .catch(e => console.log(e))
}

function cargaResultadosPropuestas(){
    return go2(config.rootUrl + "admin/propuestas", 'GET')
    .then(html => { 
        var  div = document.getElementById("tablaAdministracion");
        div.innerHTML = html;
    })
        .catch(e => console.log(e))
}
