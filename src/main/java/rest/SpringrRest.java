package rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import es.jesmon.entities.Grupo;
//@RestController

@Controller
public class SpringrRest {
	@RequestMapping(value = "/servicio", method = RequestMethod.GET)
//	@ResponseStatus(HttpStatus.OK)
//	@Transactional(value = "jpaTransactionManager")
//	@ResponseBody
	 public @ResponseBody  Grupo getGrupo(){
	      Grupo result = new Grupo();
	      result.setEdad(3);
	      result.setGenero(2);
	      result.setIntereses("Intereses");
	      
	      return result;
	   }
	
}


/*
@RequestMapping(value = "/resturl", method = RequestMethod.GET, produces = {"application/json"})
@ResponseStatus(HttpStatus.OK)
@Transactional(value = "jpaTransactionManager")
public @ResponseBody List<DomainObject> findByResourceID(@PathParam("resourceID") String resourceID) {

*/
