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


var ctx;
var myBarChart;

function evolucionChart(datosMapJson){
    let datosMap = JSON.parse(datosMapJson);
    console.log(datosMap);
    ctx = document.getElementById('myChart');

    if (myBarChart != null){
        myBarChart.destroy();
    }

    myBarChart = new Chart(ctx, {
        type: 'bar',
        data: {
            datasets: [{
                label: '# de Dosis',
                data: datosMap,
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                ],
                borderColor: [
                    'rgba(255, 99, 132, 1)',
                ],
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        beginAtZero: true,
                        callback: function(value) {if (value % 1 === 0) {return value;}}
                    }
                }
            }
        }
    });

    /*let chart = new Chart(ctx, {
        type: 'bar',
        data: datosMap,
        options: {
            //scales: {
            //    xAxis: {
                    // The axis for this scale is determined from the first letter of the id as `'x'`
                    // It is recommended to specify `position` and / or `axis` explicitly.
            //        type: 'time',
            //    }
            //}
        }
    });*/

}
