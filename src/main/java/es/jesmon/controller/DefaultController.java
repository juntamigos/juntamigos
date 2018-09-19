package es.jesmon.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.jesmon.config.JesmonConstantes;
import es.jesmon.controller.forms.CambiarPasswordForm;
import es.jesmon.controller.forms.TramitadorForm;
import es.jesmon.entities.Grupo;
import es.jesmon.entities.JesmonEntity;
import es.jesmon.entities.Responsable;
import es.jesmon.entities.Tramitador;
import es.jesmon.repository.ResponsableRepository;
import es.jesmon.repository.TramitadorRepository;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.services.JesmonServices;
import es.jesmon.services.mail.MailSender;
import es.jesmon.services.tramitador.TramitadorServices;

@Controller
public class DefaultController extends JesmonController{

	@Autowired
	JesmonServices jesmonServices;
	
	@Autowired
	TramitadorServices tramitadorServices;
	
	@Autowired
	ResponsableRepository responsableRepository;
	
	@Autowired
	TramitadorRepository tramitadorRepository;
	
	@Autowired
    @Qualifier("javasampleapproachMailSender")
	public MailSender mailSender;
	
	@GetMapping("/login/login")
	public String login(HttpServletRequest request) {
	    return procesarViewResolver("login", request);
	}
	
	@GetMapping("/login/registro")
	public String registro(HttpServletRequest request) {
	    return procesarViewResolver("RegistrarLogin", request);
	}
	
	
	@PostMapping("/login/Registro")
	public String registrarLogin(HttpServletRequest request, Model model, TramitadorForm tramitadorForm) {
	
		try {
			Tramitador tramitador = new Tramitador();
			
			tramitador.setNombre("rober");
			tramitador.setNif("12345678Z");
			tramitador.setLogin(tramitadorForm.getUser());
			tramitador.setEmail(tramitadorForm.getEmail());
			tramitador.setActivo(1);
           BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    	
	    	
	    		tramitador.setPassword(passwordEncoder.encode(tramitadorForm.getPassword()).getBytes(StandardCharsets.UTF_8));
		//	tramitador.setPassword(tramitadorForm.getPassword());
			
			
			
			request.setAttribute("mensaje", "Usuario registrado de forma correcta");
	    //	return postEmpresa(request, model, Tramitador.getId().toString());
        // Insertar en la base de datos el nuevo usuario
	     	jesmonServices.insertar(tramitador);
		
	    	return "redirect:/login/login.html?mensaje=Usuario registrado de forma correcta";
	    }
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			return procesarViewResolver("error", request);
			// TODO: handle exception
		}
		
	}
	
	
	
	
	  @GetMapping("/dani")
	    public  @ResponseBody  Grupo getGrupo(){
		  Grupo result = new Grupo();
		try {  
		
	      result.setEdad(10);
	      result.setGenero(10);
	      result.setIntereses("Intereses");
	      // INserto el grupo
	      jesmonServices.insertar(result);
	      
		}
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			
			// TODO: handle exception
		}
		return result;
	    }
		
	
	  @GetMapping("/dani3")
	    public  @ResponseBody  Grupo getGrupo3(HttpServletRequest request, Model model,@RequestParam(value = "numPersonas", required = false) int numPersonas,
	    @RequestParam(value = "edad", required = false) int edad,
	    @RequestParam(value = "intereses", required = false) String intereses,
	    @RequestParam(value = "genero", required = false) int genero,
	    @RequestParam(value = "foto", required = false) MultipartFile fichero1)

	    
	               {
		  Grupo result = new Grupo();
		try {  
		
	      result.setEdad(edad);
	      result.setnumpersonas(numPersonas);
	      
	      result.setGenero(genero);
	      result.setIntereses(intereses);
	      result.setFoto(fichero1.getBytes());
	      // INserto el grupo
	      jesmonServices.insertar(result);
	      
		}
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			
			// TODO: handle exception
		}
		return result;
	    }
	    
	  
	  
	  @GetMapping("/dani2")
	    public  @ResponseBody  Grupo getGrupo2(HttpServletRequest request, Model model,@RequestParam(value = "numPersonas", required = false) int numPersonas,
	    @RequestParam(value = "edad", required = false) int edad,
	    @RequestParam(value = "intereses", required = false) String intereses,
	    @RequestParam(value = "genero", required = false) int genero)
	    
	    
	               {
		  Grupo result = new Grupo();
		try {  
		
	      result.setEdad(edad);
	      result.setnumpersonas(numPersonas);
	      
	      result.setGenero(genero);
	      result.setIntereses(intereses);
	      // INserto el grupo
	      jesmonServices.insertar(result);
	      
		}
		catch (Exception e) {
			e.printStackTrace();
		//	logger.error(e.getMessage(), e);
			
			// TODO: handle exception
		}
		return result;
	    }
	    
	  
	  
    @GetMapping("/")
    public String raiz(HttpServletRequest request) {
    	Tramitador tramitador = (Tramitador)getUsuarioSesion(request);
   
    	if(request.isUserInRole(JesmonConstantes.ROLE_TRAMITADOR) && tramitador.getGrupo() == null)
    		return "redirect:/tramitador/grupos.html";
    	else if(request.isUserInRole(JesmonConstantes.ROLE_TRAMITADOR)  && tramitador.getGrupo() != null)
    		return "redirect:/tramitador/empresas.html";
    	else if(request.isUserInRole(JesmonConstantes.ROLE_CLIENTE))
    		return "redirect:/cliente/grupos.html";
    	else if(request.isUserInRole(JesmonConstantes.ROLE_ADMIN))
    		return "redirect:/admin/grupos.html";
    	return "redirect:/login/login.html";
    }
    
    
    
    @GetMapping("/error")
    public String error(HttpServletRequest request) {
    	return "redirect:/error/error.html";
	}
    
    @GetMapping("/error/error")
    public String errorError(HttpServletRequest request) {
	    return procesarViewResolver("error", request);
	}
    
    @GetMapping("/error/403")
    public String error403(HttpServletRequest request) {
        return procesarViewResolver("/error/403", request);
    }

    @RequestMapping(value = "/login/solicitarCambioPassword")
    public String solicitarCambioPassword(@RequestParam (value="email", required=true)String email, 
    		@RequestParam (value="repeatEmail", required=true)String repeatEmail,
    		HttpServletRequest request)
    {
    	try {
    		if(StringUtils.isBlank(email) || !StringUtils.equals(email, repeatEmail)) {
    			return "redirect:/login/login?errorLogin=" + URLEncoder.encode("Error. El campo email es vacio o no coinciden los valores de los campos email", "UTF-8") + "&";
    		}
    		
	    	CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
	    	criteriosBusqueda.addCriterio("email", email);
	    	criteriosBusqueda.addCriterio("activo", 1);
	    	
	    	Long numResultados = jesmonServices.getNumResultados(criteriosBusqueda, Responsable.class);
	    	if(numResultados.equals(0L)) {
	    		numResultados = jesmonServices.getNumResultados(criteriosBusqueda, Tramitador.class);
	    	}
	    	
	    	if(numResultados > 0) {
	    		//String from = "rovejero@gmail.com";
	    		String subject = "Reestablecer contraseña en Jesmon Seguridad";
	    		String token = DigestUtils.sha512Hex(email + JesmonConstantes.CLAVE_FIRMA) + "|" + email;
	    		
	    		String enlace = getUrlAplicacion(request) + "/login/resetearPassword?token=" + new String(Base64.getEncoder().encode(token.getBytes()));
	    		String body = "<div>Puede reestablecer su contraseña pinchando <a href='" + enlace + "'>aquí</a></div>";
	    		
	    		mailSender.sendMail(email, subject, body);
	    		return "redirect:/login/login.html?mensaje=" + URLEncoder.encode("Correo para reestablecer la contraseña enviado de forma correcta", "UTF-8") + "&";
	    	}
	    	return "redirect:/login/login?errorLogin=" + URLEncoder.encode("Error. No se ha encontrado ningún usuario con el email indicado: " + email, "UTF-8") + "&";
    	}
    	catch (Exception e) {
			e.printStackTrace();
			return procesarViewResolver("error", request);
		}
    }
    
    @GetMapping("/login/resetearPassword")
    public String getResetearPassword(HttpServletRequest request) {
    	String token = request.getParameter("token");
    	token = new String(Base64.getDecoder().decode(token.getBytes()));

    	String[] partes = StringUtils.split(token, "|");
    	String email = partes[1];
    	String firmaRequest = partes[0];
    	
    	String firma = DigestUtils.sha512Hex(email + JesmonConstantes.CLAVE_FIRMA);
    	if(StringUtils.equals(firmaRequest, firma)){
    		request.getSession().setAttribute("email", email);
    		return procesarViewResolver("resetearPassword", request);
    	}
    	return procesarViewResolver("error", request);
    }
    
    @PostMapping("/login/resetearPassword")
    public String postResetearPassword(
    	@RequestParam (value="password", required=true)String password, 
		@RequestParam (value="repeatPassword", required=true)String repeatPassword,
		HttpServletRequest request) {
		try {
			if(StringUtils.isBlank(password) || !StringUtils.equals(password, repeatPassword)) {
				request.setAttribute("error", "El campo contraseña es vacio o no coinciden las dos contraseñas introducidas");
				return procesarViewResolver("resetearPassword", request);
    		}
			
			String email = request.getSession().getAttribute("email").toString();
	    	CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
	    	criteriosBusqueda.addCriterio("email", email);
	    	criteriosBusqueda.addCriterio("activo", 1);
	    	
	    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    	
	    	List<Responsable> listaResponsables = (List<Responsable>)(List) jesmonServices.getLista(criteriosBusqueda, null, Responsable.class);
	    	if(!listaResponsables.isEmpty()) {
	    		Responsable responsable = listaResponsables.get(0);
	    		responsable.setPassword(passwordEncoder.encode(password).getBytes(StandardCharsets.UTF_8));
	    		responsableRepository.save(responsable);
	    		//return procesarViewResolver("login", request);
	    		return "redirect:/login/login.html?mensaje=" + URLEncoder.encode("Contraseña reestablecida de forma correcta", "UTF-8") + "&";
	    	}
	    	else {
	    		List<Tramitador> listaTramitadores = (List<Tramitador>)(List) jesmonServices.getLista(criteriosBusqueda, null, Tramitador.class);
		    	if(!listaTramitadores.isEmpty()) {
		    		Tramitador tramitador = listaTramitadores.get(0);
		    		tramitador.setPassword(passwordEncoder.encode(password).getBytes(StandardCharsets.UTF_8));
		    		tramitadorRepository.save(tramitador);
		    		//return procesarViewResolver("login", request);
		    		return "redirect:/login/login.html?mensaje=" + URLEncoder.encode("Contraseña reestablecida de forma correcta", "UTF-8") + "&";
		    	}
	    	}
	    	return procesarViewResolver("error", request);
	    }
		catch (Exception e) {
			e.printStackTrace();
			return procesarViewResolver("error", request);
		}
    }
    
    @PostMapping("/login/cambiarPasswordPerfil")
    public String cambiarPassword(@Valid CambiarPasswordForm cambiarPasswordForm, BindingResult bindingResult, HttpServletRequest request) {
    	try {
	        if (bindingResult.hasErrors()) {
	            return procesarViewResolver("consultarPerfil", request);
	        }
	        
	        String error = null;
	        if(StringUtils.isBlank(cambiarPasswordForm.getNuevoPassword()))
	        	error = "El campo nueva contraseña no puede ser vacío";
	        
	        if(StringUtils.isBlank(cambiarPasswordForm.getRepitaNuevoPassword()))
	        	error = "El campo repita nueva contraseña no puede ser vacío";
	        
	        if(!StringUtils.equals(cambiarPasswordForm.getNuevoPassword(), cambiarPasswordForm.getRepitaNuevoPassword()))
	        	error = "El campo nueva contraseña y el de confirmación no coinciden";
	        
	        if(StringUtils.isNotBlank(error)) {
	        	request.setAttribute("error", error);
	        	return procesarViewResolver("consultarPerfil", request);
	        }
	        
	        
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        
	        //byte[] passwordActual = passwordEncoder.encode(cambiarPasswordForm.getPasswordActual()).getBytes(StandardCharsets.UTF_8);
	        
	        //responsable.setPassword(passwordEncoder.encode(password).getBytes(StandardCharsets.UTF_8));
	        JesmonEntity usuarioSesion = getUsuarioSesion(request);
	        
	       // System.out.println(new String(passwordActual));
	        //System.out.println(new String(usuarioSesion.getPassword()));
	        
	        //System.out.println(passwordEncoder.matches(new String(passwordActual, StandardCharsets.UTF_8), 
	        		//new String(usuarioSesion.getPassword(), StandardCharsets.UTF_8)));
	        
	        System.out.println(passwordEncoder.matches( 
	        		cambiarPasswordForm.getPasswordActual(),
	        		new String(usuarioSesion.getPassword(), StandardCharsets.UTF_8)));
	        
	        if(!passwordEncoder.matches( 
	        		cambiarPasswordForm.getPasswordActual(),
	        		new String(usuarioSesion.getPassword(), StandardCharsets.UTF_8))) {
	        	request.setAttribute("error", "La contraseña actual no es correcta");
	        	return procesarViewResolver("consultarPerfil", request);
	        }
	        
	        
	        JesmonEntity usuario = (JesmonEntity)jesmonServices.buscarByPK(usuarioSesion.getClass(), usuarioSesion.getNombreCampoId(), usuarioSesion.getId());
	        usuario.setPassword(passwordEncoder.encode(cambiarPasswordForm.getNuevoPassword()).getBytes(StandardCharsets.UTF_8));
	        //responsable.setPassword(responsable.setPassword(passwordEncoder.encode(password).getBytes(StandardCharsets.UTF_8));/
	        jesmonServices.modificar(usuario);
	        request.setAttribute("mensaje", "Contraseña cambiada de forma correcta");
	        return procesarViewResolver("consultarPerfil", request);
    	}
		catch (Exception e) {
			e.printStackTrace();
			return procesarViewResolver("error", request);
		}
    }
    
    
    @GetMapping("/*/contacto")
    public String contacto(HttpServletRequest request) {
    	return procesarViewResolver("contacto", request);
    }
    
    public static void main(String[] args) {
    	try {
    		String email = "rovejero@gmail.com"; 
    		String token = DigestUtils.sha512Hex(email + JesmonConstantes.CLAVE_FIRMA) + "|" + email;
    		token =  new String(Base64.getEncoder().encode(token.getBytes()));
    		System.out.println(token);
    		token =  new String(Base64.getDecoder().decode(token.getBytes()));
    		System.out.println(token);
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
}
