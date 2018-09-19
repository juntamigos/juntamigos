package es.jesmon.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import es.jesmon.config.JesmonConstantes;
import es.jesmon.entities.JesmonEntity;

public abstract class JesmonController {
	
	protected static final int BUTTONS_TO_SHOW = 5;
	protected static final int INITIAL_PAGE = 0;
	protected static final int INITIAL_PAGE_SIZE = 5;
	protected static final int[] PAGE_SIZES = {5, 10, 20};
	
	private static String urlAplicacion;
	
	public JesmonEntity getUsuarioSesion(HttpServletRequest request) {
		return (JesmonEntity)request.getSession().getAttribute(JesmonConstantes.USUARIO_SESION);
	}

	public String procesarViewResolver(String view, HttpServletRequest request) {
		if(StringUtils.endsWith(request.getRequestURI(), ".do"))
			view += ".jsp";
		else
			view += ".html";
		
		return view;
	}
	
	public static String getUrlAplicacion(HttpServletRequest request) {
		if(urlAplicacion == null) {
			urlAplicacion = request.getRequestURL().toString();
			String contexto = request.getContextPath();
			urlAplicacion = urlAplicacion.substring(0, urlAplicacion.indexOf(contexto) + contexto.length());
		}
		return urlAplicacion;
	}
	
}
