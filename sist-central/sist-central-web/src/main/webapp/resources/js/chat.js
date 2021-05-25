if (window.WebSocket) {
	console.log('abriendo socket');
    var ws = new WebSocket("ws://localhost:8080/push");
    ws.onmessage = function(event) {
        var text = event.data;
        console.log(text);
    };
}
else {
	console.warn("no se pudo inicializar socket");
}

function sendMensaje(texto, vacunadorCi, vacunadorNombre, fechaHora){
	console.log('mandando mensaje');
	let jsonMensaje = {texto,vacunadorCi,vacunadorNombre,fechaHora};
	ws.send(JSON.stringify(jsonMensaje));
}