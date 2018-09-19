package es.jesmon.repository.util;

import java.sql.Connection;
import java.util.List;

import es.jesmon.repository.exception.DAOException;

public interface IDAO {

	public  void modificar(Object objeto) throws DAOException;
	
	public  void modificarTx(Object objeto) throws DAOException;
	
	public  void insertar(Object objeto) throws DAOException;
	
	public  void insertarTx(Object objeto) throws DAOException;
	
	public  void eliminar(Object objeto) throws DAOException;
	
	public  void eliminarTx(Object objeto) throws DAOException;
	
	public List getLista(CriteriosBusqueda criteriosBusqueda, List<ParBean> criteriosOrdenacion, Class clase) throws DAOException;
	
	public Integer getNumRegistrosLista(CriteriosBusqueda criteriosBusqueda, Class clase) throws DAOException;
	
	public void limpiarObjeto (Object object) throws DAOException;
	
	public void refresh(Object object) throws DAOException;
	
	public void flush () throws DAOException;
	
	public void modficarCampos (List<Long> listaIds,  List<List<ParBeanObject>> listaCampos, String tabla, String columaPK, Connection con) throws DAOException;
	
	public List<Long> insertarObjetosCampos ( List<List<ParBeanObject>> listaCampos, String tabla,  String columaPK, String nombreSecuencia, Connection con) throws DAOException;
	
	public List<Long> borrarObjetosCampos (String tabla,  String columaPK,List<ParBeanObject> listaCamposWhere , Connection con) throws DAOException;

	public String getNombreColumnaBD (String nombrePropiedad, Class clase) throws DAOException;
	
	public void borrarObjetosCamposIds (String tabla,  String columaPK,List<Long> listaIds , Connection con) throws DAOException;
	
	public byte[] consultarBLOB(String nombreTabla, String nombreColumnaBLOB, CriteriosBusqueda criteriosBusqueda)throws DAOException;
	
	public String consultarCLOB(String nombreTabla, String nombreColumnaCLOB, CriteriosBusqueda criteriosBusqueda)throws DAOException;
}
