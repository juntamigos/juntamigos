package es.jesmon.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import es.jesmon.entities.Responsable;
import es.jesmon.entities.Sede;
import es.jesmon.entities.Tramitador;
import es.jesmon.repository.util.AliasBean;
import es.jesmon.repository.util.CriteriosBusqueda;
import es.jesmon.repository.util.ParBean;
import es.jesmon.services.JesmonServices;
import es.jesmon.services.empresa.EmpresaService;
import es.jesmon.services.grupo.GrupoService;

@Component
public class AfterLoginFilter extends GenericFilterBean {

	private static Logger logger = LoggerFactory.getLogger(AfterLoginFilter.class);
	
	@Autowired
	JesmonServices jesmonServices;
	
	@Autowired
	EmpresaService empresaServices;
//	
//	@Autowired
//	GrupoService grupoServices;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
 		Authentication auth = SecurityContextHolder.getContext().getAuthentication();


		if(auth != null) {
			if(auth.isAuthenticated() && auth.getPrincipal() != null && auth.getPrincipal().getClass().equals(User.class) && httpRequest.getSession().getAttribute(JesmonConstantes.USUARIO_SESION) == null) {
				User user = (User)auth.getPrincipal();
				/*String role = "";
				for (GrantedAuthority granted : auth.getAuthorities())
					role = granted.getAuthority();*/
				if(httpRequest.isUserInRole(JesmonConstantes.ROLE_TRAMITADOR)) {
					CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();


					criteriosBusqueda.addCriterio("login", user.getUsername());
					List<ParBean> criteriosOrdenacion = new ArrayList<>();
					criteriosOrdenacion.add(new ParBean("login", CriteriosBusqueda.ASC));
			
				//	if (user.)
					criteriosBusqueda.agregarAlias("grupo", "grupo", AliasBean.LEFT_OUTER_JOIN);
					try {
						List<Tramitador> listaTramitadores = (List<Tramitador>)(List)jesmonServices.getLista(criteriosBusqueda, criteriosOrdenacion, Tramitador.class);
						
						Tramitador tramitador = listaTramitadores.get(0);


						jesmonServices.limpiarObjetoSesion(tramitador);
					
						httpRequest.getSession().setAttribute(JesmonConstantes.USUARIO_SESION, tramitador);
						//request.getRequestDispatcher("/tramitador/incidencias").forward(request, response);
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				}
				else if(httpRequest.isUserInRole(JesmonConstantes.ROLE_CLIENTE)) {
					CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
					criteriosBusqueda.setNombreCriteria("responsable");
					criteriosBusqueda.agregarAlias("grupos", "grupos", AliasBean.INNER_JOIN);
					//criteriosBusqueda.agregarAlias("sede.empresa", "empresa", AliasBean.INNER_JOIN);
					//criteriosBusqueda.agregarAlias("empresa.direccion", "direccion", AliasBean.LEFT_JOIN);
					//criteriosBusqueda.addCriterio("responsable.nif", user.getUsername());
					try {
						List<Responsable> listaResponsables = (List<Responsable>)(List)jesmonServices.getLista(criteriosBusqueda, null, Responsable.class);
						Responsable responsable = listaResponsables.get(0);
						for (Sede sede : responsable.getSedes()) {
							//jesmonServices.limpiarObjetoSesion(sede);
							//jesmonServices.refresh(sede.getEmpresa());
							responsable.getListaSedes().add(sede);
							responsable.getListaIdsSedes().add(sede.getIdSede());
						}
						jesmonServices.limpiarObjetoSesion(responsable);
						httpRequest.getSession().setAttribute(JesmonConstantes.USUARIO_SESION, responsable);
						//request.getRequestDispatcher("/cliente/incidencias").forward(request, response);
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				}
				else if(httpRequest.isUserInRole(JesmonConstantes.ROLE_ADMIN)) {
					try {
						Admin admin = new Admin();
//						CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
//						
//						criteriosBusqueda.agregarAlias("grupos", "grupos", AliasBean.INNER_JOIN);
//						List<Responsable> listaResponsables = (List<Responsable>)(List)jesmonServices.getLista(criteriosBusqueda, null, Responsable.class);
						
//						CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
//						criteriosBusqueda.setNombreCriteria("responsable");
//						criteriosBusqueda.agregarAlias("grupos", "grupos", AliasBean.INNER_JOIN);
						/*
						CriteriosBusqueda criteriosBusqueda = new CriteriosBusqueda();
						criteriosBusqueda.addCriterio("sedes", "sedes", AliasBean.LEFT_JOIN);
						criteriosBusqueda.addCriterio("responsables", "responsables", AliasBean.LEFT_JOIN);
						List<ParBean> criteriosOrdenacion = new ArrayList<ParBean>();
						List<Empresa> listaEmpresas = (List<Empresa>) (List)jesmonServices.getLista(criteriosBusqueda, criteriosOrdenacion, Empresa.class);
						admin.setListaEmpresas(listaEmpresas);
						*/
					//	empresaServices.setEmpresasUsuario(admin);
					//	grupoServices.setGrupoUsuario (admin);
						
						httpRequest.getSession().setAttribute(JesmonConstantes.USUARIO_SESION, admin);
					}
					catch (Exception e) {
						e.printStackTrace();
						logger.error(e.getMessage(), e);
					}
				}
			}
			//else
				//System.out.println(auth.getPrincipal());
		}
		chain.doFilter(request, response);
    }
}

