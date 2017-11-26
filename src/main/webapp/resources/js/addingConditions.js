
var index = 0;
$('#condicionesForm')
	.on('click', '.addButton', function() {
		index++;
		var $template = $('#condicionTemplate'),
		$clone = $template
						.clone()
						.removeClass('hide')
						.removeAttr('id')
						.attr('data-condicion-index', index)
						.insertBefore($template);
		$clone 
			.find('[name=parametro1]').attr('name', 'parametro1[' + index + ']').end()
			.find('[name=comparador]').attr('name', 'comparador[' + index + ']').end()
			.find('[name=parametro2]').attr('name', 'parametro2[' + index + ']').end();	
		console.log(index);
	})
	
	.on('click', '.removeButton', function() {
		var $row = $(this).parents('.form-group'),
			index = $row.attr('data-condicion-index');
		
//		$('#condicionesForm')
//			.formValidation('removeField', $row.find('[name="parametro1[' + index + '"]'))
//			.formValidation('removeField', $row.find('[name="comparador[' + index + '"]'))
//			.formValidation('removeField', $row.find('[name="parametro2[' + index + '"]'));
		
		$row.remove();
		console.log(index);
	});


$(document).ready(function(){
	$("select.miComparador").change(function(){
		var selectedComparador = $(".miComparador option:selected").val();
//		alert("Elegiste algo");
		var comparadorName = $(".miComparador").attr('name');
		var length = comparadorName.length;
		var index = comparadorName.substring(11, length-1);
		var parameterName = "parametro2{" + index + "}";
		var firstPart = "input[name=";
		var secondPart = "]";
		var inputToChange = firstPart.concat("'", parameterName, "'" , secondPart);
		if(selectedComparador == 4){
			$(inputToChange).hide();
		}else{
			$(inputToChange).show();
		}
		
	});
});


//GENERO EL JSON

//$(".metodologiaCreate").onClick(function(){
//	
//	var metodologia = {}
//	metodologia["parametro[0]"] = $("input[parametro[0]]").val();
//	alert(metodologia["parametro[0"]);
//	
//	$ajax({
//		type : "POST",
//		contentType : "application/json",
//		url : "xd"/*hay que ver */,
//		data : JSON.stringify(metodologia),
//		dataType : 'json',
//		timeout : 100000,
//		success : function(){
//			console.log("SUCCESS");
//		},
//		error: function(){
//			console.log("ERROR");
//		},
//		
//	})
//});

