package es.jesmon.controller.tramitador;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.jesmon.controller.JesmonController;
import es.jesmon.entities.Empresa;
import es.jesmon.entities.Tramitador;
import es.jesmon.repository.util.AliasBean;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.services.JesmonServices;
import es.jesmon.services.estadoIncidencia.EstadoIncidenciaService;
import es.jesmon.services.estados.EstadosService;
import es.jesmon.services.incidencias.IncidenciasService;
import es.jesmon.services.mail.MailSender;
import es.jesmon.services.mensaje.MensajesService;
import es.jesmon.services.responsable.ResponsableServices;
import es.jesmon.services.sedes.SedesServices;
import es.jesmon.services.tramitador.TramitadorServices;

@Controller
public class TramitadorController extends JesmonController {
	
	private static Logger logger = LoggerFactory.getLogger(TramitadorController.class);

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
	
	@GetMapping("/admin/tramitadores")
    public String getTramitadores(HttpServletRequest request) {
		try {
			//model.addAttribute("incidenciaForm", new IncidenciaForm());
	    	return procesarViewResolver("tramitadores", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
    }
	
	@PostMapping("/admin/tramitadores")
	public String postTramitadores(HttpServletRequest request, Model model,
		@RequestParam(value = "idTramitador", required = true) String idTramitadorStr) {
		try {
			if(StringUtils.isNotBlank(idTramitadorStr)) {
				Integer idTramitador = new Integer(idTramitadorStr);
				CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
				criteriosBusqueda.addCriterio("idTramitador", idTramitador);
				criteriosBusqueda.addCriterio("sedes", "sedes", AliasBean.LEFT_JOIN);
				Tramitador tramitador = (Tramitador)jesmonService.buscarByPK(Empresa.class, "idTramitador", idTramitador, criteriosBusqueda);
				model.addAttribute("tramitador", tramitador);
			}
	    	return procesarViewResolver("tramitadores", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
    }
}