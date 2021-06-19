
var ctx;
var myBarChart;

function dosisChart(datosMapJson){
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
                    '#3289a8',
                ],
                borderColor: [
                    '#184657',
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
}

function updateDosisHoy(dosisAdministradas){
    $('#dosisActuales').text(dosisAdministradas);
}