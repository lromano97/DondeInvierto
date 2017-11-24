var inputIndicador = document.getElementById("inputExpresion");

function indicadorToInput(button){
	inputIndicador.value =  inputIndicador.value + "#" + button.innerHTML;
}

function cuentaToInput(button){
	inputIndicador.value =  inputIndicador.value + "&" +button.innerHTML;
}