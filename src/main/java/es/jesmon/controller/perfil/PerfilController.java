package es.jesmon.controller.perfil;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import es.jesmon.controller.JesmonController;
import es.jesmon.controller.forms.CambiarPasswordForm;

@Controller
public class PerfilController extends JesmonController{
	
	@GetMapping("/*/consultarPerfil")
	public String consultarPerfil(CambiarPasswordForm cambiarPasswordForm, HttpServletRequest request) {
	    return procesarViewResolver("consultarPerfil", request);
	}

}
