function updateMapa( jsonMap){
    let map = JSON.parse(jsonMap);
    $("#UY-AR").text(map.Artigas);
    $("#UY-CA").text(map.Canelones);
    $("#UY-CL").text(map.CerroLargo);
    $("#UY-CO").text(map.Colonia);
    $("#UY-DU").text(map.Durazno);
    $("#UY-FS").text(map.Flores);
    $("#UY-FD").text(map.Florida);
    $("#UY-LA").text(map.Lavalleja);
    $("#UY-MA").text(map.Maldonado);
    $("#UY-MO").text(map.Montevideo);
    $("#UY-PA").text(map.Paysandu);
    $("#UY-RN").text(map.FrayBentos);
    $("#UY-RV").text(map.Rivera);
    $("#UY-RO").text(map.Rocha);
    $("#UY-SA").text(map.Salto);
    $("#UY-SJ").text(map.SanJose);
    $("#UY-SO").text(map.Soriano);
    $("#UY-TA").text(map.Tacuarembo);
    $("#UY-TT").text(map.TreintayTres);
}

function updateVacunadosTotales(numero){
    $("#vacunadosTotales").text(numero);
}
function updateVacunadosHoy(numero){
    $("#vacunadosHoy").text(numero);
}
function updateDosisAdministradas(numero){
    $("#dosisAdministradas").text(numero);
}
function updateAgendadosProximos(numero){
    $("#agendadosProximos").text(numero);
}

function count(){
    $('.count').each(function () {
        $(this).prop('Counter', 0).animate({
            Counter: $(this).text()
        }, {
            duration: 800,
            easing: 'swing',
            step: function (now) {
                $(this).text(Math.ceil(now).toLocaleString());
            }
        });
    });
}
