/* utils.js v2.0.0: Libreria de funciones con distintas utilidades front-end
 * Copyright Carlos Giovanni Molina Ronceros
 * */

if (!String.prototype.trim) {
    /**
     * Elimina todos los caracteres espacio en blanco tanto del principio 
     * como del final de la cadena
     * 
     * @returns {String}
     */
    String.prototype.trim=function(){return this.replace(/^\s+|\s+$/g, '');};
}

if (!String.prototype.ltrim) {
    /**
     * Elimina todos los caracteres espacio en blanco del principio de la cadena
     * 
     * @returns {String}
     */
    String.prototype.ltrim=function(){return this.replace(/^\s+/,'');};
}

if (!String.prototype.rtrim) {
    /**
     * Elimina todos los caracteres espacio en blanco del final de la cadena
     * 
     * @returns {String}
     */
    String.prototype.rtrim=function(){return this.replace(/\s+$/,'');};
}

if (!String.prototype.fulltrim) {
    /**
     * Elimina todos los caracteres espacio en blanco tanto del principio como 
     * del final de la cadena, y reempleza los espacios en blanco múltiples por 
     * una sola instancia
     * 
     * @returns {String}
     */
    String.prototype.fulltrim=function(){return this.replace(/(?:(?:^|\n)\s+|\s+(?:$|\n))/g,'').replace(/\s+/g,' ');};
}

if (!String.prototype.escapeSpecialChars) {
    /**
     * Escapa los caracteres especiales dentro de una cadena
     * 
     * @returns {String}
     */
    String.prototype.escapeSpecialChars = function() {
        return this.replace(/\\n/g, "\\n")
                   .replace(/\\'/g, "\\'")
                   .replace(/\\"/g, '\\"')
                   .replace(/\\&/g, "\\&")
                   .replace(/\\r/g, "\\r")
                   .replace(/\\t/g, "\\t")
                   .replace(/\\b/g, "\\b")
                   .replace(/\\f/g, "\\f");
    };
}

if (!String.prototype.removeSpecialChars) {
    /**
     * Elimina los caracteres especiales dentro de una cadena
     * 
     * @returns {String}
     */
    String.prototype.removeSpecialChars = function() {
        return this.replace(/\n/g, " ")
                   .replace(/('|")/g, "");
    };
}

/**
 * Resetea todos los elementos tipo formulario y limpia el html internos de los 
 * contenedores con el atributo data-clear="true"
 * 
 * @param {String} id ID del contenedor
 * @returns {void}
 */
function resetContainer(id) {
    if (id!=='' && $('#'+id).length>0) {
        $('#'+id+' input').each(function(i, element) {
            if (!$(element).data('ignore')) {
                if (
                        $(element).attr('type')==='text' || 
                        $(element).attr('type')==='hidden' || 
                        $(element).attr('type')==='number' || 
                        $(element).attr('type')==='email'
                ) {
                    $(element).val(!isNullOrEmpty($(element).data('defaultvalue')) ? $(element).data('defaultvalue') : '');
                } else if (
                        $(element).attr('type')==='checkbox' || 
                        $(element).attr('type')==='radio'
                ) {
                    $(element).removeAttr('checked');
                }    
            }
            
            $(element).closest('.form-group').removeClass('has-error');
            $(element).next('label[for="'+element.id+'"]').remove();
        });

        $('#'+id+' textarea').each(function(i, element) {
            if (!$(element).data('ignore')) {
                $(element).val(!isNullOrEmpty($(element).data('defaultvalue')) ? $(element).data('defaultvalue') : '');
            }
            
            $(element).closest('.form-group').removeClass('has-error');
            $(element).next('label[for="'+element.id+'"]').remove();
        });

        $('#'+id+' select').each(function(i, element) {
            if (!$(element).data('ignore')) {
                if (isNullOrEmpty($(element).data('defaultvalue'))) {
                    $(element)[0].selectedIndex = 0;
                } else {
                    $(element).val($(element).data('defaultvalue'));
                }
            }

            $(element).closest('.form-group').removeClass('has-error');
            $(element).next('label[for="'+element.id+'"]').remove();
        });
        
        cleanContainer(id);
   }
}

/**
 * Elimina el html interno de todos los elementos con el atributo data-clear="true"
 * @param {string} id ID del contenedor
 * @returns {void}
 */
function cleanContainer(id) {
    if ($('#'+id).length>0) {
        $('#'+id+' *[data-clear="true"]').each(function(i, element) {
            $(element).html('');
        });
    }
}

/**
 * Obtiene el mensaje de error de un objeto jqXHR obtenido mediante 
 * una llamada Ajax
 * 
 * @param {Object} xhr
 * @returns {String}
 */
function getErrorMessage(xhr) {
    var apiProblem, error;

    try {
        apiProblem = $.parseJSON(xhr.responseText);
        error = 'Error ' + apiProblem.httpStatus + ': ' + apiProblem.title;
        
        if (apiProblem.detail !== null && !$.isArray(apiProblem.detail)) {
            error += '. ' + apiProblem.detail;
        } else if ($.isArray(apiProblem.detail)) {
            for (var i=0; i<apiProblem.detail.length; i++) {
                error += '<br/>' + apiProblem.detail[i];
            }
        }
        
    } catch (ex) {
        error = ex.message;
    }
    
    return error;
}

/**
 * Renderiza una imagen dentro del elemento html con el id indicado
 * 
 * @param {String} id
 * @returns {undefined}
 */
function loading(id) {
    if ($('#'+id).length>0) {
        $('#'+id).html(getLoadingImg());
    }
}

/**
 * Obtiene el código html de loading.gif
 * 
 * @returns {String}
 */
function getLoadingImg() {
    return '<img src="/img/loading.gif" alt="Cargando..." title="Cargando..." style="width:100px;height:100px;display:block;margin:0 auto;" />';
}

var BS_PRIMARY = 'primary';
var BS_SUCCESS = 'success';
var BS_INFO = 'info';
var BS_WARNING = 'warning';
var BS_DANGER = 'danger';

/**
 * Renderiza una lista de mensajes
 * 
 * @param {String} type
 * @param {String|Array} msg
 * @param {String} title
 * @param {bool} closeButton
 * @returns {String}
 */
function bsAlert(type, msg, title, closeButton) {
    var html = '';
    var icon = null;
    
    title = typeof title !== 'undefined' ? title : null;
    closeButton = typeof closeButton !== 'undefined' ? closeButton : false;
    
    if (!$.isArray(msg)) {
        msg = [msg];
    }
    
    if (msg.length === 0) {
        return html;
    }
    
    if (type===BS_PRIMARY) {
        icon = 'hand-right';
    } else if(type===BS_SUCCESS) {
        icon = 'ok-sign';
    } else if(type===BS_INFO) {
        icon = 'info-sign';
    } else if(type===BS_WARNING) {
        icon = 'exclamation-sign';
    } else if(type===BS_DANGER) {
        icon = 'remove-sign';
    }
    
    var alertClass = 'alert alert-' + type;
    var htmlCloseButton = '';

    if (closeButton) {
        alertClass += ' alert-dismissible fade in';
        htmlCloseButton += '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">x</span></button>';
    }
        
    html += '<div class="' + alertClass + '" role="alert">';
    html += htmlCloseButton;

    if (title !== null && title !== '') {
        html += '<h4>' + title + '</h4>';
    }

    html += '<ul>';
    
    for (var i=0;i<msg.length; i++) {
        var message = msg[i];
        html += '<li class="text-' + type + '">';

        if (icon !== null) {
            html += '<span class="glyphicon glyphicon-' + icon + '" aria-hidden="true"></span>&nbsp;';
        }

        html += message + '</li>';
    }

    html += '</ul></div>';
    return html;
}

/**
 * 
 * @param {String} type
 * @param {String} body
 * @param {String} title
 * @returns {String}
 */
function bsPanel(type, body, title) {
    var html = '';
    title = typeof title !== 'undefined' ? title : null;
    
    html += '<div class="panel panel-msg panel-' + type + '">';
    
    if (title !== null && title !== '') {
        html += '<div class="panel-heading">' + 
                    '<h3 class="panel-title">' + title + '</h3>' + 
                '</div>';
    }
    
    html += '<div class="panel-body">' + 
                body + 
            '</div>' + 
        '</div>';
    
    return html;
}

/**
 * Aplica un efecto de scroll hacia el elemento html con el id especificado
 * 
 * @param {String} id
 * @param {int} offset
 * @returns {JQuery|undefined}
 */
function scrollToElement(id, offset) {
    offset = typeof offset !== 'undefined' ? offset : 60;
            
    if ($('#'+id).length>0) {
        $('html, body').animate({scrollTop:$('#'+id).offset().top - offset},250);
        return $('#'+id);
    }
    
    return undefined;
}

/**
 * Comprueba si value existe y es distinto de null, undefined o vacío
 * 
 * @param {mixed} value
 * @returns {bool}
 */
function isNullOrEmpty(value) {
    return ((typeof value === 'undefined') || value===null || (''+value).trim()==='');
}

/**
 * Elimina la parte decimal si no tiene valor
 * 
 * @param {String} str
 * @returns {String}
 */
function toStrInt(str) {
    str = str.replace(/(\.[0-9]*?)0+$/, '$1');
    return str.replace(/\.$/, '');
}

/**
 * 
 * @param {boolean} value
 * @returns {String}
 */
function boolToStr(value) {
    var result = '';
        
    if (true === value) {
        result = 'Si';
    } else if (false === value) {
        result = 'No';
    }

    return result;
}

/**
 * 
 * @param {Object} elements
 * @param {Object} attrs
 * @param {String} selected
 * @returns {String}
 */
function renderSelect(elements, attrs, selected) {
    var html = '';
    attrs = typeof attrs !== 'undefined' ? attrs : [];
    selected = typeof selected !== 'undefined' ? selected : null;
    
    html += '<select';
    
    for (var k in attrs){
        if (attrs.hasOwnProperty(k)) {
            html += ' ' + k + '="' + attrs[k] + '"';
        }
    }
    
    html += '>';
    html += '<option value=""></option>';
    
    for (var k in elements){
        if (elements.hasOwnProperty(k)) {
            html += '<option value="' + k + '"' + (k===selected ? ' selected="selected"' : '') + '>' + elements[k] + '</option>';
        }
    }
    
    html += '</select>';
    return html;
}

function strToDate(str) {
    
    var re = /^\d{4}\-\d{2}\-\d{2}\s\d{2}\:\d{2}:\d{2}/;
    
    if (!isNullOrEmpty(str) && re.test(str)) {
        var sdatetime = str.substring(0, 16);
        sdatetime = str.split(' ');
        var sdate = sdatetime[0];
        var stime = sdatetime[1];
        var adate = sdate.split('-');
        var atime = stime.split(':');
        
        var aaaa = parseInt(adate[0],10);
        var mm = parseInt(adate[1],10);
        var dd = parseInt(adate[2],10);
        
        var hh = parseInt(atime[0],10);
        var ii = parseInt(atime[1],10);
        var ss = parseInt(atime[2],10);
        
        return new Date(aaaa, mm-1, dd, hh, ii, ss);
        
    } else {
        return null;
    }
}

/**
 * Retorna la representación string en formato dd-mm-yyyy de un objeto del tipo Date
 * 
 * @param {Date} date
 * @returns {String}
 */
function getDateString(date) 
{
    if (date instanceof Date) {
        var dia = date.getDate();
        var mes = date.getMonth() + 1;
        var anyo = date.getFullYear();
        
        return (dia < 10 ? '0' : '') + dia + '-' + (mes < 10 ? '0' : '') + mes + '-' + anyo;
    }
    
    return '';
}

/**
 * Retorna la representación string en formato hh:mm:ss de un objeto del tipo Date
 * 
 * @param {Date} date
 * @param {bool} withSeconds
 * @returns {String}
 */
function getTimeString(date, withSeconds) 
{
    withSeconds = typeof withSeconds !== 'undefined' ? withSeconds : false;
    
    if (date instanceof Date) {
        var hora = date.getHours();
        var minutos = date.getMinutes();
        var segundos = date.getSeconds();
        
        return (hora < 10 ? '0' : '') + hora + ':' + (minutos < 10 ? '0' : '') + minutos + (withSeconds ? '-' + (segundos < 10 ? '0' : '') + segundos : '');
    }
    
    return '';
}

/**
 * 
 * @param {Object} obj
 * @returns {int}
 */
function objectSize(obj) {
    var count = 0;
    var i;

    for (i in obj) {
        if (obj.hasOwnProperty(i)) {
            count++;
        }
    }
    
    return count;
}

/**
 * Elimina los saltos de línea y retornos de carro de un string
 * 
 * @param {String} str
 * @returns {String}
 */
function cleanString(str) {
    return (str+'').replace(/[\r\n]/g, ' ').replace(/[\\"]/g, '\\$&').replace(/\u0000/g, '\\0');
}

/**
 * Convierte los caracteres especiales html a su correspondiente código
 * 
 * @param {String} str
 * @returns {String}
 */
function htmlEscape(str) {
    return String(str)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
}

/**
 * Aplica tasa de impuesto
 * 
 * @param {float} value
 * @param {float} tax
 * @param {int} precision
 * @returns {String}
 */
function applyTax(value, tax, precision) {
    precision = typeof precision !== 'undefined' ? precision : 2;
    return Math.round(value*(1 + (tax / 100)) * (Math.pow(10, precision))) / (Math.pow(10, precision));
}

/**
 * Busca el indice del elemento con el valor dato para una propiedad concreta
 * 
 * @param {Array} array
 * @param {String} attr
 * @param {Object} value
 * @param {boolean} isKey
 * @returns {Number}
 */
function indexByAttrValue(array, attr, value, isKey) {
    isKey = typeof isKey !== 'undefined' ? isKey : true;
    
    if (isKey && isNullOrEmpty(value)) {
        return -1;
    }
    
    for (var i=0; i<array.length; i++) {
        if (array[i].hasOwnProperty(attr) && array[i][attr] === value) {
            return i;
        }
    }
    
    return -1;
}