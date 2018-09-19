package rest;


//import java.util.LinkedList;
import java.util.List;

//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.WebApplicationException;
//import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import es.jesmon.entities.Grupo;
import es.jesmon.services.JesmonServices;

//@Path("/service/")
public class Gruporest {
}

//	@Autowired
//	JesmonServices jesmonService;
//
//	@POST
//	@Path("/createGrupo")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void save(Grupo grupo){
//		try {
//
//		jesmonService.insertar(grupo);
//		}
//	    catch (Exception e) {
//	    	e.printStackTrace();
//	    }
//	}
//	
//	
//	
//	 @GET
////	   @Produces({MediaType.APPLICATION_JSON})
//	   @Produces
//	   @Path("/obtenerGrupo")
//	   public Grupo getGrupo(){
//	      Grupo result = new Grupo();
//	      result.setEdad(3);
//	      result.setGenero(2);
//	      result.setIntereses("Intereses");
//	      
//	      return result;
//	   }
//	
// // la llamada debería ser así:
//	
//	
////en el Header de la petición, poner "Content-Type:application/json", para que el servidor sepa que los datos le llegan en formato JSON, y en el cuerpo de la petición poner los datos en formato JSON, algo como esto
//	
//    //URL para nuestro servicio sea http://localhost:8080/jesmon/resources/service/obtenerGrupo
//	/*	
//	{
//		"edad": 2,
//		"genero":1,
//		"num_personas":4,
//		"intereses": "intereses del grupo"
//				
//	}
//	
	
