function MasCampos() {
    var checkBox = document.getElementById("Check");
    var input = document.getElementById("camposExtra");
    if (checkBox.checked){
        input.style.display = "block";
    } else {
        input.style.display = "none";
    }
}

function mostrarDosis() {
    document.getElementById("info").style.display = "none";
    document.getElementById("puestos").style.display = "none";
    document.getElementById("turnos").style.display = "none";
    document.getElementById("lotes").style.display = "none";
    document.getElementById("dosis").style.display = "block";
    document.getElementById("botonInfo").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonPuestos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonTurnos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonLotes").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonDosis").style = "padding: 12px 60px; background-color:#222938; color:white; border-color:#222938";

}

function mostrarLotes() {
    document.getElementById("info").style.display = "none";
    document.getElementById("puestos").style.display = "none";
    document.getElementById("turnos").style.display = "none";
    document.getElementById("lotes").style.display = "block";
    document.getElementById("dosis").style.display = "none";
    document.getElementById("botonInfo").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonPuestos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonTurnos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonLotes").style = "padding: 12px 60px; background-color:#222938; color:white; border-color:#222938";
    document.getElementById("botonDosis").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";

}

function mostrarTurnos() {
    document.getElementById("info").style.display = "none";
    document.getElementById("puestos").style.display = "none";
    document.getElementById("turnos").style.display = "block";
    document.getElementById("lotes").style.display = "none";
    document.getElementById("dosis").style.display = "none";
    document.getElementById("botonInfo").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonPuestos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonTurnos").style = "padding: 12px 60px; background-color:#222938; color:white; border-color:#222938";
    document.getElementById("botonLotes").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonDosis").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";

}

function mostrarInfo() {
    document.getElementById("info").style.display = "block";
    document.getElementById("puestos").style.display = "none";
    document.getElementById("turnos").style.display = "none";
    document.getElementById("lotes").style.display = "none";
    document.getElementById("dosis").style.display = "none";
    document.getElementById("botonInfo").style = "padding: 12px 60px; background-color:#222938; color:white; border-color:#222938";
    document.getElementById("botonPuestos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonTurnos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonLotes").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonDosis").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";

}

function mostrarPuestos() {
    document.getElementById("info").style.display = "none";
    document.getElementById("puestos").style.display = "block";
    document.getElementById("turnos").style.display = "none";
    document.getElementById("lotes").style.display = "none";
    document.getElementById("dosis").style.display = "none";
    document.getElementById("botonInfo").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonPuestos").style = "padding: 12px 60px; background-color:#222938; color:white; border-color:#222938";
    document.getElementById("botonTurnos").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonLotes").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";
    document.getElementById("botonDosis").style = "padding: 12px 60px; background-color:white; color:#222938; border-color:#222938";

}