
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


