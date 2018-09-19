package es.jesmon.controller.incidencia;

import java.io.IOException;
//import org.apache.commons.io;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.*;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.jesmon.config.JesmonConstantes;
import es.jesmon.controller.JesmonController;
import es.jesmon.controller.forms.BuscadorIncidenciasForm;
import es.jesmon.controller.forms.EstadoForm;
import es.jesmon.controller.forms.GrupoForm;
import es.jesmon.controller.forms.IncidenciaForm;
import es.jesmon.controller.forms.MensajeForm;
import es.jesmon.entities.Cita;
import es.jesmon.entities.Empresa;
import es.jesmon.entities.Estado;
import es.jesmon.entities.EstadoIncidencia;
import es.jesmon.entities.Fichero;
import es.jesmon.entities.Grupo;
import es.jesmon.entities.Incidencia;
import es.jesmon.entities.JesmonEntity;
import es.jesmon.entities.Mensaje;
import es.jesmon.entities.PrioridadIncidencia;
import es.jesmon.entities.Responsable;
import es.jesmon.entities.Sede;
import es.jesmon.entities.TipoIncidencia;
import es.jesmon.entities.Tramitador;
import es.jesmon.repository.util.AliasBean;
import es.jesmon.repository.util.CriterioBeanOr;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.JesmonServices;
import es.jesmon.services.estadoIncidencia.EstadoIncidenciaService;
import es.jesmon.services.estados.EstadosService;
import es.jesmon.services.incidencias.IncidenciasService;
import es.jesmon.services.mail.MailSender;
import es.jesmon.services.mensaje.MensajesService;
import es.jesmon.services.responsable.ResponsableServices;
import es.jesmon.services.sedes.SedesServices;
import es.jesmon.services.tramitador.TramitadorServices;
import es.jesmon.services.util.Pager;
import es.jesmon.services.util.UtilDate;

@Controller
public class IncidenciasController extends JesmonController {
	
	private static Logger logger = LoggerFactory.getLogger(IncidenciasController.class);

	@Autowired
	EstadoIncidenciaService estadoIncidenciaService;
	
	@Autowired
	EstadosService estadosService;
	
	@Autowired
	JesmonServices jesmonService;
	
	@Autowired
	IncidenciasService incidenciasService;
	
	@Autowired
	SedesServices sedesServices;
	
	@Autowired
	TramitadorServices tramitadorServices;
	
	@Autowired
	ResponsableServices responsableServices;
	
	@Autowired
	MensajesService mensajesService;
	
	@Autowired
    @Qualifier("javasampleapproachMailSender")
	public MailSender mailSender;
	
/*	@PostMapping("/tramitador/DetalleGrupo")
	public String getDetalleGrupo(HttpServletRequest request, Model model, GrupoForm grupoForm) {
		return procesarViewResolver("grupos2", request);
	}
	
	*/
	
	@RequestMapping(value = "/tramitador/consultarGrupo", method = RequestMethod.GET)
	public String consultarGrupo(Map<String, Object> model,
			@RequestParam(value = "idGrupo", required = true) int idGrupo, HttpServletRequest request) {
		try {
			
		//	Integer idIncidencia = new Integer(new String(Base64.getDecoder().decode(idIndicenciaStr.getBytes())));
			
			Grupo grupo = (Grupo)jesmonService.buscarByPK(Grupo.class, "idGrupo", idGrupo);
			if(grupo == null) {
				return procesarViewResolver("error", request);
			}
			
			model.put("grupo", grupo);
			return procesarViewResolver("consultarGrupo", request);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
		}
	}
	

	@GetMapping("/tramitador/grupos")
	public String getRegistrarLogin(HttpServletRequest request, Model model, GrupoForm grupoForm) {
		return procesarViewResolver("grupos", request);
	}
		
	@GetMapping("/tramitador/planes")
	public String getPlanes(HttpServletRequest request, Model model, GrupoForm grupoForm) {
		try {
			
			/*
		CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
		List<Grupo> listaGrupos = (List<Grupo>)(List)jesmonService.getLista(criteriosBusqueda, null, Grupo.class);
		model.addAttribute("grupos", listaGrupos);
		*/
		
		
		
		CriteriosBusqueda criteriosBusqueda2 = new CriteriosBusqueda();
		// Cojo el grupo mío de sesión
		Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
		Grupo grupo = tramitador.getGrupo();
		criteriosBusqueda2.addCriterio("estado", 3,CriteriosBusqueda.DISTINTO);
		criteriosBusqueda2.addCriterio("grupob", grupo);
		criteriosBusqueda2.agregarAlias("grupob", "grupob", AliasBean.INNER_JOIN);
		List<Cita> listaCitas = (List<Cita>)(List)jesmonService.getLista(criteriosBusqueda2, null, Cita.class);
		model.addAttribute("citasSolicitadas", listaCitas);
		
		CriteriosBusqueda criteriosBusqueda3 = new CriteriosBusqueda();
		criteriosBusqueda3.addCriterio("estado", 3,CriteriosBusqueda.DISTINTO);
		criteriosBusqueda3.addCriterio("grupoa", grupo);
		criteriosBusqueda3.agregarAlias("grupoa", "grupoa", AliasBean.INNER_JOIN);
		List<Cita> listaCitasPendientes = (List<Cita>)(List)jesmonService.getLista(criteriosBusqueda3, null, Cita.class);
		model.addAttribute("citasPendientes", listaCitasPendientes);
		
		
		CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
		criteriosBusqueda.addCriterio("estado", 3);
		
		criteriosBusqueda.agregarAlias("grupoa", "grupoa", AliasBean.LEFT_OUTER_JOIN);		
		criteriosBusqueda.agregarAlias("grupob", "grupob", AliasBean.LEFT_OUTER_JOIN);
		CriterioBeanOr criterioOr = new CriterioBeanOr();
		criterioOr.addOr("grupoa", grupo);
		criterioOr.addOr("grupob", grupo);
		criteriosBusqueda.addCriterioOr(criterioOr);
		
		List<Cita> listaCitas3 = (List<Cita>)(List)jesmonService.getLista(criteriosBusqueda, null, Cita.class);
		model.addAttribute("citasAprobadas", listaCitas3);
		
		
		
		}
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
    	
		return procesarViewResolver("planes", request);
	}
	
	
	
	@GetMapping(value = "/*/VisualizarImagen",
		  	produces = MediaType.IMAGE_JPEG_VALUE
		  	)


public @ResponseBody byte[] VisualizarImagen(HttpServletRequest request, Model model, GrupoForm grupoForm,  @RequestParam(value = "idGrupo", required = false) Integer idGrupo) {
		 byte[] res = null;
	try {
		
		Grupo grupo = (Grupo)jesmonService.buscarByPK(Grupo.class, "idGrupo", idGrupo);
		res = grupo.getFoto();
	
		
	
			
	}
	catch (Exception e) {
		e.printStackTrace();
	//	logger.error(e.getMessage(), e);
	
		// TODO: handle exception
	}
	   return res;
}
	
	@PostMapping("/tramitador/grupos")
	
	public String crearGrupo (HttpServletRequest request, Model model, GrupoForm grupoForm,  @RequestParam(value = "fichero1", required = false) MultipartFile fichero1) {
	
		try {
			HttpServletRequest httpRequest = (HttpServletRequest)request;
//			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
//
//			criteriosBusqueda.addCriterio("tramitador.nombre", "rober");
//			criteriosBusqueda.agregarAlias("grupos", "grupos", AliasBean.INNER_JOIN);
//			
//				List<Tramitador> listaTramitadores = (List<Tramitador>)(List)jesmonService.getLista(criteriosBusqueda, null, Tramitador.class);
//				Tramitador tramitador = listaTramitadores.get(0);
			
			Grupo grupo = new Grupo();
			String edad = grupoForm.getEdad();
			if (edad.equals("18-25"))
					edad = "1";
			else if (edad.equals("25-30"))
				    edad = "2";
			else if (edad.equals("30-35"))
			    edad = "3";
			else if (edad.equals("35-40"))
			    edad = "4";
			else if (edad.equals("40-45"))
			    edad = "5";
			grupo.setEdad(Integer.parseInt(edad));
			
			String genero = grupoForm.getGenero();
			if (genero.equals("Femenino"))
				genero ="2";
			else
			    genero = "1";		
			grupo.setGenero(Integer.parseInt(genero));
			//grupo.setnum_personas(numpersonas);
			grupo.setnumpersonas(Integer.parseInt(grupoForm.getNumPersonas()));
//			grupo.setEdad(2);
//			grupo.setGenero(1);
//			grupo.setnum_personas(3);
			
			grupo.setFoto(fichero1.getBytes());
	
			grupo.setIntereses(grupoForm.getIntereses());

			
		//	tramitador.setPassword(tramitadorForm.getPassword());
			
			  // Insertar en la base de datos el nuevo usuario
			jesmonService.insertar(grupo);
			
			Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
			Tramitador tramitadorTmp = (Tramitador)jesmonService.buscarByPK(Tramitador.class, "idTramitador", tramitador.getIdTramitador());
			tramitadorTmp.setGrupo(grupo);
			tramitador.setGrupo(grupo);
			jesmonService.modificar(tramitadorTmp);
			
			request.setAttribute("mensaje", "Grupo modificado de forma correcta");
			jesmonService.limpiarObjetoSesion(tramitador);
			
			httpRequest.getSession().setAttribute(JesmonConstantes.USUARIO_SESION, tramitador);
		     
		
		//    	return "redirect:/tramitador/grupos2.html?mensaje=Usuario registrado de forma correcta";
	    	return procesarViewResolver("modificarGrupo", request);
	 //		return procesarViewResolver("empresas", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
		
	}
	@GetMapping("/tramitador/Actualizarcita")
	
	public String actualizarCita(HttpServletRequest request, Model model,@RequestParam(value = "idCita", required = false) int idCita) {
		 
		try {
			
			Cita citaTmp =  (Cita)jesmonService.buscarByPK(Cita.class, "id_cita", idCita);
			citaTmp.setEstado(3);
			jesmonService.modificar(citaTmp);
			return getPlanes(request, model, new GrupoForm());
			//return procesarViewResolver("planes", request);	
		
		}
		
		
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
		
	}
	
	@GetMapping("/tramitador/insertarcita")
	public String registrarCita(HttpServletRequest request, Model model) {
		 
		try {
			

			
			Cita cita = new Cita();
			cita.setEstado(1); // Solicita pero pendiente
			//jesmonService.insertar(cita);
			Integer grupoA = Integer.parseInt(request.getParameter("grupoA"));    
			Integer grupoB = Integer.parseInt(request.getParameter("grupoB"));
		
			cita.setGrupoa(new Grupo(grupoA));
			cita.setGrupob(new Grupo(grupoB));

			jesmonService.insertar(cita);			
			
			request.setAttribute("mensaje", "Cita registrada de forma correcta");
			
		     
			
		//    	return "redirect:/tramitador/grupos2.html?mensaje=Usuario registrado de forma correcta";
	    	return procesarViewResolver("empresas", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
		
	}
	
	
	@PostMapping("/tramitador/modificarGrupo")
	public String modificarGrupo(HttpServletRequest request, Model model, GrupoForm grupoForm) {
	
		try {			
			Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.agregarAlias("grupo", "grupo", AliasBean.INNER_JOIN);
			Tramitador tramitadorTmp = (Tramitador)jesmonService.buscarByPK(Tramitador.class, "idTramitador", 
					tramitador.getIdTramitador(), criteriosBusqueda);
			
			Grupo grupo = tramitadorTmp.getGrupo();
			grupo.setEdad(new Integer(grupoForm.getEdad()));
			
			jesmonService.modificar(grupo);
			request.setAttribute("mensaje", "Grupo modificado de forma correcta");
			
		     
			
		//    	return "redirect:/tramitador/grupos2.html?mensaje=Usuario registrado de forma correcta";
	    	return procesarViewResolver("modificarGrupo", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
		
	}
	
	@GetMapping("/tramitador/modificarGrupo")
	public String getRegistrarLoginModificar(HttpServletRequest request, Model model, GrupoForm grupoForm) {
        try {
        	Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.agregarAlias("grupo", "grupo", AliasBean.INNER_JOIN);
			Tramitador tramitadorTmp = (Tramitador)jesmonService.buscarByPK(Tramitador.class, "idTramitador", 
					tramitador.getIdTramitador(), criteriosBusqueda);
			
			Grupo grupo = tramitadorTmp.getGrupo();
			request.setAttribute("grupo", grupo);
        	
        	return procesarViewResolver("modificarGrupo", request);
        }
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
	}
	
	@RequestMapping(value = "/*/consultarIncidencia", method = RequestMethod.GET)
	public String consultarIncidencia(Map<String, Object> model,
			@RequestParam(value = "idIncidencia", required = true) String idIndicenciaStr, HttpServletRequest request) {
		try {
			JesmonEntity usuarioSesion = (JesmonEntity)getUsuarioSesion(request);
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.agregarAlias("sede", "sede", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("sede.direccion", "direccion", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("sede.empresa", "empresa", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("responsable", "responsable", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadoActual", "estado", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadosIncidencia", "estadosIncidencia", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadosIncidencia.ficheros", "ficheros", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("tipoIncidencia", "tipoIncidencia", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("prioridadIncidencia", "prioridadIncidencia", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("prioridadIncidencia", "prioridadIncidencia", AliasBean.LEFT_JOIN);
			
			criteriosBusqueda.addCriterio("sede.idSede", usuarioSesion.getListaIdsSedes().toArray(), CriteriosBusqueda.IN);
			Integer idIncidencia = new Integer(new String(Base64.getDecoder().decode(idIndicenciaStr.getBytes())));
			
			Incidencia incidencia = (Incidencia)jesmonService.buscarByPK(Incidencia.class, "idIncidencia", idIncidencia, criteriosBusqueda);
			if(incidencia == null) {
				return procesarViewResolver("error", request);
			}
			/*
			if(StringUtils.isNotBlank(request.getParameter("mensaje")))
				request.setAttribute("mensaje", request.getParameter("mensaje"));
			*/
			model.put("incidencia", incidencia);
			return procesarViewResolver("consultarIncidencia", request);
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
		}
	}

	@GetMapping(value={"/cliente/insertarIncidencia", "/admin/insertarIncidencia"})
    public String getMappingInsertarIncidencia(HttpServletRequest request, 
    		Model model,
    		IncidenciaForm incidenciaForm
    		) {
		try {
			//model.addAttribute("incidenciaForm", new IncidenciaForm());
			model.addAttribute("listaPrioridadesIncidencia", incidenciasService.getListaPrioridadesIncidencia());
			model.addAttribute("listaTiposIncidencia", incidenciasService.getListaTiposIncidencia());
			model.addAttribute("incidenciaForm", new IncidenciaForm());
			
	    	return procesarViewResolver("insertarIncidencia", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
    }
	
	@PostMapping("/cliente/insertarIncidencia")
    public String postMappingInsertarIncidencia(@Valid IncidenciaForm incidenciaForm,
    		HttpServletRequest request, BindingResult bindingResult, @RequestParam(value = "fichero1", required = false) MultipartFile fichero1,
    		@RequestParam(value = "fichero2", required = false) MultipartFile fichero2,
    		@RequestParam(value = "fichero3", required = false) MultipartFile fichero3) {
    	try {
			if (bindingResult.hasErrors()) 
				procesarViewResolver("insertarIncidencia", request);
			
    		Incidencia incidencia = new Incidencia();
    		incidencia.setAsunto(incidenciaForm.getAsunto());
    		incidencia.setTexto(incidenciaForm.getTexto());
    		incidencia.setFechaAlta(new Date());
    		if(incidenciaForm.getIdPrioridadIncidencia() != null)
    			incidencia.setPrioridadIncidencia((PrioridadIncidencia)jesmonService.buscarByPK(PrioridadIncidencia.class, "idPrioridadIncidencia", incidenciaForm.getIdPrioridadIncidencia()));
    		
    		if(incidenciaForm.getIdTipoIncidencia() != null)
    			incidencia.setTipoIncidencia((TipoIncidencia)jesmonService.buscarByPK(TipoIncidencia.class, "idTipoIncidencia",incidenciaForm.getIdTipoIncidencia()));
    		
    		List<Fichero> listaFicheros = new ArrayList<Fichero>();
    		addFichero(fichero1, listaFicheros);
    		addFichero(fichero2, listaFicheros);
    		addFichero(fichero3, listaFicheros);
    		
    		Responsable responsable = (Responsable)getUsuarioSesion(request);
    		incidencia.setResponsable(responsable);
    		CriteriosBusqueda criteriosBusquedaSede = new CriteriosBusqueda();
    		criteriosBusquedaSede.agregarAlias("empresa", "empresa", AliasBean.INNER_JOIN);
    		incidencia.setSede((Sede)jesmonService.buscarByPK(Sede.class, "idSede", incidenciaForm.getIdSede(), criteriosBusquedaSede));
    		incidenciasService.insertar(incidencia, listaFicheros);
    		
    		try {
    			List<Tramitador> listaTramitadores = tramitadorServices.getListaTramitadoresSede(incidenciaForm.getIdSede());
    			String enlace = getUrlAplicacion(request) + "/tramitador/consultarIncidencia?idIncidencia=" + incidencia.getIdIncidencia();
    			String subject = "Nueva incidencia " + incidencia.getIdIncidencia() + ": " + incidencia.getAsunto();
    			String body = "<div>Nueva incidencia <b>" + incidencia.getIdIncidencia() + "</b>.<br />" + 
    				"<ul>" + 
	    			"<li>Asunto: <b>" + incidencia.getAsunto() + "</b>.</li>" +
		    		"<li>Descipción: <b>" + incidencia.getTexto() + "</b>.</li>" +
		    		"<li>Usuario alta: <b>" + incidencia.getResponsable().getNombre() + "</b>.</li>" +
		    		"<li>Fecha alta: <b>" + UtilDate.dateToStringCompleto(incidencia.getFechaAlta()) + "</b>.</li>" +
		    		"<li>Sede: <b>" + incidencia.getSede().getDenominacion() + "</b>.</li>" +
		    		"<li>Empresa: <b>" + incidencia.getSede().getEmpresa().getDenominacion() + "</b>.</li>";
    			if(incidencia.getPrioridadIncidencia() != null)
    				body += "<li>Prioridad: <b>" + incidencia.getPrioridadIncidencia().getDenominacion() + "</b>.</li>";
    			
    			if(incidencia.getTipoIncidencia() != null)
    				body += "<li>Tipo incidencia: <b>" + incidencia.getTipoIncidencia().getDenominacion() + "</b>.</li>";
    			body += "</ul>";
    			enlace = "<a href='" + enlace + "' style='font-weight: bold;'>TRAMITAR</a></div>";
	    		
	    		String email = "";
	    		for(Tramitador tramitador : listaTramitadores)
	    			email += tramitador.getEmail() + ",";
	    			
	    		mailSender.sendMail(email, subject, body + enlace);
	    		
	    		enlace = StringUtils.replace(enlace, "/tramitador/", "/cliente/");
	    		enlace = StringUtils.replace(enlace, ">TRAMITAR<", ">CONSULTAR<");
	    		
	    		mailSender.sendMail(responsable.getEmail(), subject, body + enlace);
    		}
    		catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage(), e);
			}
    		
    		return "redirect:/cliente/incidencias.html?mensaje=Creada la incidencia " + incidencia.getIdIncidencia() + " de forma correcta&";
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
		}
    }
	
	@RequestMapping(value = "/*/cambiarEstadoIncidencia", method = RequestMethod.POST)
	public String consultarIncidencia(Map<String, Object> model,
			EstadoForm estadoForm, HttpServletRequest request,
			@RequestParam(value = "fichero1", required = false) MultipartFile fichero1,
    		@RequestParam(value = "fichero2", required = false) MultipartFile fichero2,
    		@RequestParam(value = "fichero3", required = false) MultipartFile fichero3) {
		try {
			
			System.out.println("observaciones: " + estadoForm.getObservaciones() + " | " + request.getParameter("observaciones"));
			System.out.println("idIncidenciaB64: " + estadoForm.getIdIncidenciaB64() + " | " + request.getParameter("idIncidenciaB64"));
			System.out.println("idEstado: " + estadoForm.getIdEstado() + " | " + request.getParameter("idEstado"));
			JesmonEntity usuarioSesion = (JesmonEntity)getUsuarioSesion(request);
			
			Tramitador tramitador = null;
			Responsable responsable = null;
			if(usuarioSesion instanceof Tramitador)
				tramitador = (Tramitador)usuarioSesion;
			else
				responsable = (Responsable)usuarioSesion;
			
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.agregarAlias("sede", "sede", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("sede.empresa", "empresa", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("responsable", "responsable", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadoActual", "estadoActual", AliasBean.INNER_JOIN);
			criteriosBusqueda.addCriterio("sede.idSede", usuarioSesion.getListaIdsSedes().toArray(), CriteriosBusqueda.IN);
			criteriosBusqueda.agregarAlias("estadosIncidencia", "estadosIncidencia", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadosIncidencia.ficheros", "ficheros", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("tipoIncidencia", "tipoIncidencia", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("prioridadIncidencia", "prioridadIncidencia", AliasBean.LEFT_JOIN);
			
			Integer idIncidencia = new Integer(new String(Base64.getDecoder().decode(estadoForm.getIdIncidenciaB64())));
			
			Incidencia incidencia = (Incidencia)jesmonService.buscarByPK(Incidencia.class, "idIncidencia", idIncidencia, criteriosBusqueda);
			if(incidencia == null) {
				return procesarViewResolver("error", request);
			}
			Estado estado = (Estado)jesmonService.buscarByPK(Estado.class, "idEstado", estadoForm.getIdEstado());
			
			EstadoIncidencia estadoIncidencia = new EstadoIncidencia();
			estadoIncidencia.setEstado(estado);
			estadoIncidencia.setFechaEstado(new Date());
			estadoIncidencia.setObservaciones(estadoForm.getObservaciones());
			estadoIncidencia.setIncidencia(incidencia);
			
			incidencia.getEstadosIncidencia().add(estadoIncidencia);
			incidencia.setEstadoActual(estado);
			if(tramitador != null)
				estadoIncidencia.setTramitador(tramitador);
			else
				estadoIncidencia.setResponsable(responsable);
			
			List<Fichero> listaFicheros = new ArrayList<Fichero>();
    		addFichero(fichero1, listaFicheros);
    		addFichero(fichero2, listaFicheros);
    		addFichero(fichero3, listaFicheros);
    		
			estadoIncidenciaService.insertarEstadoIncidencia(estadoIncidencia, listaFicheros);
			incidencia.getEstadosIncidencia().add(estadoIncidencia);
			
			if(estadoForm.getLgEmailAviso() != null && estadoForm.getLgEmailAviso().equals(1L)) {
				try {
	    			String enlace = getUrlAplicacion(request) + "/tramitador/consultarIncidencia?idIncidencia=" + incidencia.getIdIncidencia();
	    			String subject = "Cambio de estado incidencia " + incidencia.getIdIncidencia() + ". Nuevo estado " + estado.getDescripcion();
	    			String body = "<div>Cambio de estado incidencia <b>" + incidencia.getIdIncidencia() + "</b>. Nuevo estado <b>" + estado.getDescripcion() + "</b>.<br />" + 
	    				"Datos de la incidencia: <br />" +
	    				"<ul>" +
		    			"<li>Asunto: <b>" + incidencia.getAsunto() + "</b>.</li>" +
			    		"<li>Descipción: <b>" + incidencia.getTexto() + "</b>.</li>" +
			    		"<li>Usuario alta: <b>" + incidencia.getResponsable().getNombre() + "</b>.</li>" +
			    		"<li>Fecha alta: <b>" + UtilDate.dateToStringCompleto(incidencia.getFechaAlta()) + "</b>.</li>" +
			    		"<li>Sede: <b>" + incidencia.getSede().getDenominacion() + "</b>.</li>" +
			    		"<li>Empresa: <b>" + incidencia.getSede().getEmpresa().getDenominacion() + "</b>.</li>" +
		    			"</ul>";
	    			enlace = "<a href='" + enlace + "' style='font-weight: bold;'>CONSULTAR</a></div>";
		    		
		    		String email = "";
		    		if(responsable != null) {
		    			List<Tramitador> listaTramitadores = tramitadorServices.getListaTramitadoresSede(incidencia.getSede().getIdSede());
		    			for(Tramitador tramitadorTmp : listaTramitadores)
		    				email += tramitadorTmp.getEmail() + ",";
		    		}
		    		
		    		mailSender.sendMail(email, subject, body + enlace);
		    		email = incidencia.getResponsable().getEmail();
		    		enlace = StringUtils.replace(enlace, "/tramitador/", "/cliente/");
		    		mailSender.sendMail(email, subject, body + enlace);
	    		}
	    		catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}			
			}
			
			model.put("incidencia", incidencia);
			model.put("resultado", "Estado cambiado de forma correcta. Nuevo estado: " + estado.getDescripcion());
			return procesarViewResolver("consultarIncidencia", request);
		}
		catch (Exception e) {
			e.printStackTrace();
			return procesarViewResolver("error", request);
		}
	}
	
	
	@RequestMapping(value = "/*/incidencias")
	public String incidencias(Map<String, Object> model,
			@ModelAttribute("buscadorIncidenciasForm") BuscadorIncidenciasForm buscadorIncidenciasForm, 
			HttpServletRequest request,
			@RequestParam("pageSize") Optional<Integer> pageSize,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("orden") Optional<String> orden) {
		try {
			
			if(page.isPresent() || orden.isPresent()) {
				if(request.getSession().getAttribute("buscadorIncidenciasFormSesion") != null) {
					BuscadorIncidenciasForm buscadorIncidenciasFormSesion = (BuscadorIncidenciasForm)request.getSession().getAttribute("buscadorIncidenciasFormSesion");
					buscadorIncidenciasForm.setIdEstado(buscadorIncidenciasFormSesion.getIdEstado());
					BeanUtils.copyProperties(buscadorIncidenciasForm, buscadorIncidenciasFormSesion);
				}
			}
			
			
			int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
	        // Evaluate page. If requested parameter is null or less than 0 (to
	        // prevent exception), return initial size. Otherwise, return value of
	        // param. decreased by 1.
			int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;
			
			//Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
			JesmonEntity usuarioSesion = (JesmonEntity)getUsuarioSesion(request);
			CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
			criteriosBusqueda.agregarAlias("sede", "sede", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("sede.empresa", "empresa", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("responsable", "responsable", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("estadoActual", "estadoActual", AliasBean.INNER_JOIN);
			//criteriosBusqueda.agregarAlias("estadoIncidencia.estado", "estado", AliasBean.INNER_JOIN);
			criteriosBusqueda.agregarAlias("tipoIncidencia", "tipoIncidencia", AliasBean.LEFT_JOIN);
			criteriosBusqueda.agregarAlias("prioridadIncidencia", "prioridadIncidencia", AliasBean.LEFT_JOIN);
			criteriosBusqueda.addCriterio("sede.idSede", usuarioSesion.getListaIdsSedes().toArray(), CriteriosBusqueda.IN);
			
			criteriosBusqueda.setNumPaginacion(evalPage + 1);
			criteriosBusqueda.setNumRegistrosPagina(evalPageSize);
			
			if(buscadorIncidenciasForm.getIdSede() != null)
				criteriosBusqueda.addCriterio("sede", new Sede(buscadorIncidenciasForm.getIdSede()));
			
			if(buscadorIncidenciasForm.getIdEmpresa() != null) {
				Empresa empresa = new Empresa(buscadorIncidenciasForm.getIdEmpresa());
				criteriosBusqueda.addCriterio("sede.empresa", empresa);
				model.put("listaSedes", sedesServices.getListaSedes(buscadorIncidenciasForm.getIdEmpresa()));
				model.put("listaResponsables", responsableServices.getListaResponsables(buscadorIncidenciasForm.getIdEmpresa()));
			}
			
			if(buscadorIncidenciasForm.getIdEstado() != null)
				criteriosBusqueda.addCriterio("estadoActual", new Estado(buscadorIncidenciasForm.getIdEstado()));

			if(buscadorIncidenciasForm.getIdTipoIncidencia() != null)
				criteriosBusqueda.addCriterio("tipoIncidencia", new TipoIncidencia(buscadorIncidenciasForm.getIdTipoIncidencia()));
			
			if(buscadorIncidenciasForm.getIdPrioridadIncidencia() != null)
				criteriosBusqueda.addCriterio("prioridadIncidencia", new PrioridadIncidencia(buscadorIncidenciasForm.getIdPrioridadIncidencia()));
			
			Date fechaAltaDesde = UtilDate.stringToDate(buscadorIncidenciasForm.getFechaAltaDesde());
			if(fechaAltaDesde != null)
				criteriosBusqueda.addCriterio("fechaAlta", fechaAltaDesde, CriteriosBusqueda.MAYOR_IGUAL);
				
			Date fechaAltaHasta = UtilDate.stringToDate235959(buscadorIncidenciasForm.getFechaAltaHasta());
			if(fechaAltaHasta != null)
				criteriosBusqueda.addCriterio("fechaAlta", fechaAltaHasta, CriteriosBusqueda.MENOR_IGUAL);
			
			if(buscadorIncidenciasForm.getIdIncidencia() != null)
				criteriosBusqueda.addCriterio("idIncidencia", buscadorIncidenciasForm.getIdIncidencia());
			
			if(buscadorIncidenciasForm.getIdResponsable() != null)
				criteriosBusqueda.addCriterio("responsable", new Responsable(buscadorIncidenciasForm.getIdResponsable()));
				
			List<ParBean> criteriosOrdenacion = new ArrayList<>();
			if(orden.isPresent()) {
				String[] datosOrden = StringUtils.split(orden.get(), ";");
				criteriosOrdenacion.add(new ParBean(datosOrden[0], datosOrden[1]));
			}else
				criteriosOrdenacion.add(new ParBean("fechaAlta", CriteriosBusqueda.DESC));
			List<Incidencia> listaIncidencias = new ArrayList<>();
			Long numResultados = jesmonService.getNumResultados(criteriosBusqueda, Incidencia.class);
			if(numResultados > 0L)
				listaIncidencias = (List<Incidencia>)(List)jesmonService.getLista(criteriosBusqueda, criteriosOrdenacion, Incidencia.class);
			
			model.put("listaEstados", estadosService.getListaEstados());
			if(usuarioSesion instanceof Tramitador)
				model.put("listaEmpresas", usuarioSesion.getListaEmpresas());
			else
				model.put("listaSedes", usuarioSesion.getListaSedes());
		
			model.put("listaIncidencias", listaIncidencias);
			
			model.put("selectedPageSize", evalPageSize);
			if(orden.isPresent())
				model.put("orden", orden.get());
			model.put("pageSizes", PAGE_SIZES);
			model.put("numResultados", numResultados.intValue());
			Integer totalPages = (numResultados.intValue() / evalPageSize) + 1;
			Pager pager = new Pager(totalPages, evalPage, BUTTONS_TO_SHOW);
			model.put("pager", pager);
			model.put("totalPages", totalPages);
			model.put("currentPage", evalPage);
			
			model.put("listaPrioridadesIncidencia", incidenciasService.getListaPrioridadesIncidencia());
			model.put("listaTiposIncidencia", incidenciasService.getListaTiposIncidencia());
			
			request.getSession().setAttribute("buscadorIncidenciasFormSesion", buscadorIncidenciasForm);
			return procesarViewResolver("incidencias", request);
		}
		catch (Exception e) {
			e.printStackTrace();
			return procesarViewResolver("error", request);
		}
	}

	@RequestMapping(value = "/*/listaSedes")
	public @ResponseBody
	List<Sede> getListaSedes(@RequestParam("idEmpresa") Integer idEmpresa) {  
		try {
			return sedesServices.getListaSedes(idEmpresa);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/*/listaResponsables")
	public @ResponseBody  
	List<Responsable> getListaResponsables(@RequestParam("idEmpresa") Integer idEmpresa) {  
		try {
			return responsableServices.getListaResponsables(idEmpresa);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/*/insertarMensaje", method = RequestMethod.POST)
    public String postMappingInsertarMensaje(MensajeForm mensajeForm, HttpServletRequest request,
    		@RequestParam(value = "fichero1", required = false) MultipartFile fichero1,
    		@RequestParam(value = "fichero2", required = false) MultipartFile fichero2,
    		@RequestParam(value = "fichero3", required = false) MultipartFile fichero3
    		) {
    	try {
    		/*MensajeForm mensajeForm = new MensajeForm();
    		mensajeForm.setTexto(request.getParameter("texto"));
    		mensajeForm.setIdIncidenciaB64(request.getParameter("idIncidenciaB64"));
    		*/
    		Mensaje mensaje = new Mensaje();
    		mensaje.setTexto(mensajeForm.getTexto());
    		mensaje.setFecha(new Date());
    		CriteriosBusqueda criteriosBusquedaIncidencia = new CriteriosBusqueda();
    		criteriosBusquedaIncidencia.agregarAlias("sede", "sede", AliasBean.INNER_JOIN);
    		criteriosBusquedaIncidencia.agregarAlias("sede.empresa", "empresa", AliasBean.INNER_JOIN);
    		criteriosBusquedaIncidencia.agregarAlias("responsable", "responsable", AliasBean.INNER_JOIN);
    		Incidencia incidencia = (Incidencia) jesmonService.buscarByPK(Incidencia.class, "idIncidencia", mensajeForm.getIdIncidencia(), criteriosBusquedaIncidencia);
    		mensaje.setIncidencia(new Incidencia(mensajeForm.getIdIncidencia()));
    		mensaje.setLgInterno(mensajeForm.getLgInterno());
    		List<Fichero> listaFicheros = new ArrayList<Fichero>();
    		addFichero(fichero1, listaFicheros);
    		addFichero(fichero2, listaFicheros);
    		addFichero(fichero3, listaFicheros);
    		
    		JesmonEntity usuarioSesion = getUsuarioSesion(request);
    		String urlParcial = "tramitador";
    		if(usuarioSesion instanceof Tramitador)
    			mensaje.setTramitador((Tramitador)usuarioSesion);
    		else {
    			mensaje.setResponsable((Responsable)usuarioSesion);
    			urlParcial = "cliente";
    		}
    		mensajesService.insertar(mensaje, listaFicheros);
    		if(mensajeForm.getLgEnviarCorreo() != null && mensajeForm.getLgEnviarCorreo().equals(1)) 
    			try {
	    			String enlace = getUrlAplicacion(request) + "/tramitador/consultarIncidencia?idIncidencia=" + mensajeForm.getIdIncidencia();
	    			String subject = "Nuevo mensaje incidencia " + mensajeForm.getIdIncidencia();
	    			String body = "<div>Cambio de estado incidencia <b>" + mensajeForm.getIdIncidencia() + "</b>.<br />" + 
	    				"Datos del mensaje: <br />" +
	    				"<ul>" +
	    				"<li>Asunto incidencia: <b>" + incidencia.getAsunto() + "</b>.</li>" +
		    			"<li>Testo: <b>" + mensaje.getTexto() + "</b>.</li>" +
			    		"<li>Usuario alta: <b>" + usuarioSesion.getNombreCompleto() + "</b>.</li>" +
			    		"<li>Fecha alta: <b>" + UtilDate.dateToStringCompleto(mensaje.getFecha()) + "</b>.</li>" +
			    		"<li>Sede: <b>" + incidencia.getSede().getDenominacion() + "</b>.</li>" +
			    		"<li>Empresa: <b>" + incidencia.getSede().getEmpresa().getDenominacion() + "</b>.</li>" +
		    			"</ul>";
	    			enlace = "<a href='" + enlace + "' style='font-weight: bold;'>CONSULTAR</a></div>";
		    		
		    		String email = "";
		    		if(usuarioSesion instanceof Responsable) {
		    			List<Tramitador> listaTramitadores = tramitadorServices.getListaTramitadoresSede(incidencia.getSede().getIdSede());
		    			for(Tramitador tramitadorTmp : listaTramitadores)
		    				email += tramitadorTmp.getEmail() + ",";
		    		}
		    		
		    		mailSender.sendMail(email, subject, body + enlace);
		    		email = incidencia.getResponsable().getEmail();
		    		enlace = StringUtils.replace(enlace, "/tramitador/", "/cliente/");
		    		mailSender.sendMail(email, subject, body + enlace);
	    		}
	    		catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage(), e);
				}
    		
    	
    		request.setAttribute("mensaje", "Mensaje insertardo de forma correcta");
    		return "redirect:/" + urlParcial + "/consultarIncidencia.html?idIncidencia=" + Base64.getEncoder().encodeToString(mensajeForm.getIdIncidencia().toString().getBytes()) + "&mensaje=Mensaje insertardo de forma correcta&";
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
		}
    }
	
	@RequestMapping(value = "/*/consultarFichero", method = RequestMethod.GET)
	public void consultarFichero(Map<String, Object> model,
			@RequestParam(value = "idFichero", required = true) String idFicheroStr, HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer idFichero = new Integer(new String(Base64.getDecoder().decode(idFicheroStr)));
			Fichero fichero = (Fichero)jesmonService.buscarByPK(Fichero.class, "idFichero", idFichero);
			response.setContentLength(fichero.getContenidoFichero().length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fichero.getNombreFichero() + "\"");
			response.setContentType("application/x-document");
			ServletOutputStream servletOutputStream = response.getOutputStream();
			servletOutputStream.write(fichero.getContenidoFichero());
			servletOutputStream.flush();
			servletOutputStream.close();
		}
    	catch (Exception e) {
    		e.printStackTrace();
    		logger.error(e.getMessage(), e);
		}
    }
	
	private void addFichero(MultipartFile ficheroForm, List<Fichero> listaFicheros) throws IOException {
		if(ficheroForm != null && ficheroForm.getBytes() != null && StringUtils.isNotBlank(ficheroForm.getOriginalFilename())) {
			Fichero fichero = new Fichero();
			fichero.setContenidoFichero(ficheroForm.getBytes());
			fichero.setNombreFichero(ficheroForm.getOriginalFilename());
			listaFicheros.add(fichero);
		}
	}
}
