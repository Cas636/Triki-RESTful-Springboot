<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Triki</title>
<link rel="stylesheet" href="styles.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
var currentPlayer = "X"; // Inicia con el jugador X

$(document).ready(function(){
    $(".cell").click(function(){
        // Si la celda ya tiene una X o O, no permitir clics
        if ($(this).text() !== "") {
            return;
        }

        var cellIndex = $(this).data("index");
        
        $.ajax({
            url: "http://localhost:8081/triki/"+cellIndex,  // URL del servidor
            type: "PUT",                           // Método HTTP
            contentType: "application/json",       // Asegurarse de que es JSON
            data: JSON.stringify({
                cell: cellIndex,
                status: currentPlayer
            }),
            success: function(response) {

                // Colocar la X o la O en la celda
                $(".cell[data-index='" + cellIndex + "']").text(currentPlayer);

                // Actualizar el estado del turno
                currentPlayer = (currentPlayer === "X") ? "O" : "X"; // Cambiar turno

                // Actualizar el mensaje de turno en la interfaz
                $("#status").text("Turno del jugador " + currentPlayer);
                console.log("Respuesta del servidor: ", response);
            },
            error: function(xhr, status, error) {
                console.error("Error:", error);
            }
        });
    });
    $("#reset-button").click(function() {
    	 // Reiniciar el estado del juego en el cliente
        $(".cell").text(""); // Limpiar todas las celdas
        currentPlayer = "X"; // Reiniciar el jugador a X
        $("#status").text("Turno del jugador X"); // Actualizar el mensaje de turno
        var gameState = [];
        for (var i = 0; i < 9; i++) {
            gameState.push({
                cell: i,
                status: ""
            });
        }

        $.ajax({
            url: "http://localhost:8081/triki/reset",
            type: "PUT",
            contentType: "application/json",
            data: JSON.stringify(gameState),
            success: function(response) {
                console.log("Juego reiniciado en el servidor.");
            },
            error: function(xhr, status, error) {
                console.error("Error al reiniciar el juego en el servidor:", error);
            }
        });
    });
});
</script>
</head>
<body>
	<h1>Tres en Raya</h1>
	<div class="game-board">
		<div class="cell" data-index="0"></div>
		<div class="cell" data-index="1"></div>
		<div class="cell" data-index="2"></div>
		<div class="cell" data-index="3"></div>
		<div class="cell" data-index="4"></div>
		<div class="cell" data-index="5"></div>
		<div class="cell" data-index="6"></div>
		<div class="cell" data-index="7"></div>
		<div class="cell" data-index="8"></div>
	</div>
	<div class="game-info">
		<h2 id="status">Turno del jugador X</h2>
		<button id="reset-button">Reiniciar Juego</button>
	</div>
</body>
</html>