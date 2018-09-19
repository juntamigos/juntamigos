function getValidationObject(rules, submitHandler) {
    var mySubmitHandler;
    
    if (typeof submitHandler !== 'undefined') {
        mySubmitHandler = submitHandler;
    } else {
        mySubmitHandler = function(form) {
            $(form).find('.btn').attr('disabled','disabled');
            //alert($(form).find('input[type=submit]').length);
            form.submit();
        }
    }
    
    return {
        'submitHandler': mySubmitHandler,
        'rules': rules,
        'errorClass': 'text-danger control-label-msg',
        'invalidHandler': function(form, validator) {
            if (!validator.numberOfInvalids()) {
                return;
            }
            scrollToElement($(validator.errorList[0].element).attr('id'));
        },
        'highlight': function(element) {
            $(element).closest('.form-group').addClass('has-error');
        },            
        'unhighlight': function(element) {
            $(element).closest('.form-group').removeClass('has-error');
        }
    };
}

/*
var loginFormRules = {
	    usuario: {
	        required:true,
	        maxlength:64
	    },
	    password: {
	        required:true,
	        maxlength:64
	    }
	};
*/
var solicitarCambioPasswordFormRules = {
    email: {
      required: true,
      email: true
    },
	repeatEmail: {
  		equalTo: "#email"
     }
};

var loginFormRules = {
		username: {
			required: true
		},
		password: {
			required: true
		}
};

var insertarIncidenciaFormRules = {
		idSede: {
			required: true
		},
		asunto: {
			required: true
		},
		texto: {
			required: true
		}
};


var estadoFormRules = {
		idEstado: {
			required: true
		},
		idIncidenciaEstado: {
			required: true
		}
};

var mensajeFormRules = {
		idIncidenciaMensaje: {
			required: true
		},
		texto: {
			required: true
		}
};

var cambiarPasswordFomRules = {
		passwordActual: {
	      required: true,
	      minlength: 6
	    },
	    nuevoPassword: {
	      required: true,
	      minlength: 6
	    },
	    repitaNuevoPassword: {
	    	required: true,
	    	minlength: 6,
	  		equalTo: "#nuevoPassword"
	    }
};

if (!String.prototype.startsWith) {
	  String.prototype.startsWith = function(stringBuscada, posicion) {
	    posicion = posicion || 0;
	    return this.indexOf(stringBuscada, posicion) === posicion;
	  };
	}


if (!String.prototype.trim) {
  (function() {
    // Make sure we trim BOM and NBSP
    var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
    String.prototype.trim = function() {
      return this.replace(rtrim, '');
    };
  })();
}

if (!String.prototype.endsWith) {
  String.prototype.endsWith = function(searchString, position) {
      var subjectString = this.toString();
      if (typeof position !== 'number' || !isFinite(position) || Math.floor(position) !== position || position > subjectString.length) {
        position = subjectString.length;
      }
      position -= searchString.length;
      var lastIndex = subjectString.indexOf(searchString, position);
      return lastIndex !== -1 && lastIndex === position;
  };
}

function validarEmail(valor) {
	if ( /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/.test(valor))
		return true;
	else
		return false;
}