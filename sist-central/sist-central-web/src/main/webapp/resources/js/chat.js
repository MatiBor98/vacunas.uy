if (window.WebSocket) {
	console.log('abriendo socket');
    var ws = new WebSocket("wss://vacunas.web.elasticloud.uy/push");
    ws.onmessage = function(event) {
        let jsonMensajes = JSON.parse(event.data);
        console.log(jsonMensajes);
        
        //Es primera vez que vienen mensajes
        if (Array.isArray(jsonMensajes)) {
        
        	//Primefaces demora para poner borrarmsg() disponible
        	var checkExist = setInterval(function() {
            if (borrarmsg !== "undefined") {
              console.log("Exists!");
              borrarmsg();
        
        	  jsonMensajes.map(mensaje => addMensaje(
        	  	[{name: 'param1', value: JSON.stringify(mensaje)}]
        	  ));
              
              clearInterval(checkExist);
            }
          }, 100);
        	
        }
        else {
        	addMensaje( [{name: 'param1', value: JSON.stringify(jsonMensajes)}]);
        }
        
    };
}
else {
	console.warn("no se pudo inicializar socket");
}

function sendMensaje(texto, vacunadorCi, vacunadorNombre, fechaHora){

	console.log(texto);
	texto = JSON.parse(texto);
	fechaHora = fechaHora.replace('Z', '');
	console.log('mandando mensaje');
	let jsonMensaje = {texto,vacunadorCi,vacunadorNombre,fechaHora};
	ws.send(JSON.stringify(jsonMensaje));
}