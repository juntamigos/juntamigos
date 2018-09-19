package es.jesmon.repository.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.LockMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;

/**
 * @author ovejeror
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CriteriosBusqueda {
  
	public static String	IGUAL		=	"=";
	public static String	MAYOR		=	">";
	public static String	MENOR		=	"<";
	public static String 	MAYOR_IGUAL	=	">=";
	public static String  	MENOR_IGUAL	=	"<=";
	public static String  	DISTINTO	=	"<>";
	public static String	IN 			=	"IN";
	public static String	NOT_IN 		=	"NOT IN";
	public static String	BETWEEN		=	"BETWEEN";
	public static String	LIKE		=	"LIKE";
	public static String	NOT_LIKE	=	"NOT_LIKE";
	public static String	ILIKE		=	"ILIKE";
	public static String	ILIKE2		=	"ILIKE2";
	public static String 	IS_NOT_NULL	= "IS NOT NULL";
	public static String 	IS_NULL		= "IS NULL";
	public static String 	EXPRESION_REGULAR	=	"EXPRESION_REGULAR";
	public static String 	EXPRESION_REGULAR2	=	"EXPRESION_REGULAR2";
	public static String	NORMA_LIKE			=	"NORMA_LIKE";
	
	public static String 	ASC		= "ASC";
	public static String 	DESC	= "DESC";
	
	public static String	IGUAL_OTRA_PROP		=	"=2";
	public static String	MAYOR_OTRA_PROP		=	">2";
	public static String	MENOR_OTRA_PROP		=	"<2";
	public static String 	MAYOR_IGUAL_OTRA_PROP	=	">=2";
	public static String  	MENOR_IGUAL_OTRA_PROP	=	"<=2";
	public static String  	DISTINTO_OTRA_PROP	=	"<>2";
	
	public static String	SQL_RESTRICTION	=	"SQL_RESTRICTION"; //Restrictions.sqlRestriction
	
	public static String	FUNC_DISTINCT			=	"DISTINCT";
	public static String	FUNC_AGREGADA_SUM		=	"SUM";
	public static String	FUNC_AGREGADA_AVG		=	"AVG";
	public static String	FUNC_AGREGADA_MIN		=	"MIN";
	public static String	FUNC_AGREGADA_MAX		=	"MAX";
	public static String	FUNC_AGREGADA_COUNT		=	"COUNT";
	public static String	FUNC_AGREGADA_ROW_COUNT	=	"ROWCOUNT";
	public static String	FUNC_AGREGADA_COUNT_DISTINCT = "COUNTDISTINCT";
	
	private final int LIMITE_IN = 1000;
	
	private boolean unicoResultado = false;
	
	private String nombreCriteria;
	
	private List<CriterioBean> listaCriterios;
	
	private List<CriterioBeanOr> listaCriteriosOR;
	
	private List<ParBean> listaFuncionesAgregadas;
	
	private List<String> listaGroupBy;
	
	private List<String> listaProyecciones = new ArrayList<String> ();
	
	private HashMap<String, String> hashMapAliasProyecciones = new HashMap<String, String> ();
	
	private String as;
	
	private Integer numPaginacion;
	
	private Integer numRegistrosPagina;
	
	private Integer numMaxRegistros;
	
	private HashMap<String, AliasBean> hashAlias;
	
	private HashMap<String, Integer> hashContadorAlias;
	
	private Class classResultTransformer;
	
	private List<LockMode> listaBloqueos = new ArrayList<LockMode> ();
	
	/*
	 * Ejemplo criteriosBusqueda.agregarAlias("expediente.beneficiario", "beneficiario", AliasBean.INNER_JOIN); 
	 */
	
	public void agregarAlias(String nombreAlias, String valorAlias ,String tipoJoin){
		if(hashAlias == null) {
			hashAlias = new HashMap <String, AliasBean>();
			hashContadorAlias = new HashMap <String, Integer>();
		}
		if(!hashAlias.containsKey(nombreAlias)){
			hashAlias.put(nombreAlias, new AliasBean (nombreAlias, valorAlias, tipoJoin));
			hashContadorAlias.put(nombreAlias, 1);
		}
		else {
			Integer contador = new Integer (hashContadorAlias.get(nombreAlias).toString());
			hashContadorAlias.put(nombreAlias, new Integer(contador.intValue()+1));
		}
	}
	
	public void eliminarAlias (String nombreAlias){
		if(hashAlias == null){
			hashAlias = new HashMap <String, AliasBean>();
			hashContadorAlias = new HashMap <String, Integer>();
		}
		if(hashAlias.containsKey(nombreAlias)){
			Integer contador = new Integer (hashContadorAlias.get(nombreAlias).toString());
			if(contador.intValue() == 1){			
				hashAlias.remove(nombreAlias);
				hashContadorAlias.remove(nombreAlias);
			}
			else {
				hashContadorAlias.put(nombreAlias, new Integer(contador.intValue()-1));
			}
		}
	}
	
	public void eliminarAlias (){		
		hashAlias = new HashMap <String, AliasBean>();
		hashContadorAlias = new HashMap <String, Integer>();		
	}
	
    public Integer getNumMaxRegistros() {
		return numMaxRegistros;
	}

	public void setNumMaxRegistros(Integer numMaxRegistros) {
		this.numMaxRegistros = numMaxRegistros;
	}

	public Integer getNumPaginacion() {
		return numPaginacion;
	}

	public void setNumPaginacion(Integer numPaginacion) {
		this.numPaginacion = numPaginacion;
	}

	public Integer getNumRegistrosPagina() {
		/*if(numRegistrosPagina == null)
			return new Integer(0);*/
		return numRegistrosPagina;
	}

	public void setNumRegistrosPagina(Integer numRegistrosPagina) {
		this.numRegistrosPagina = numRegistrosPagina;
	}

	public CriteriosBusqueda(){
    	listaCriterios = new ArrayList <CriterioBean>();
    	listaCriteriosOR = new ArrayList <CriterioBeanOr>();
    	listaGroupBy = new ArrayList<String>();
    	listaFuncionesAgregadas = new ArrayList<ParBean>();
    	//numRegistrosPagina = new Integer (0);
    	as = "";
    }
       
    public List<CriterioBean> getlistaCriterios(){
        return listaCriterios;
    }
	
	public CriterioBean getCriterio(String nombreCriterio) {
		CriterioBean criterio = null;
		boolean seguir = true;
		int i = 0;
		while (seguir && i < listaCriterios.size()){
			if(((CriterioBean)listaCriterios.get(i)).getNombreCriterio().equals(nombreCriterio)){
				seguir = false;
				criterio = ((CriterioBean)listaCriterios.get(i));
			}
			else
				i++;
		}
		return criterio;
	}
	
	public void addCriterio(CriterioBean criterio) {
		listaCriterios.add(criterio);
	}
	
	public void addCriterio(String nombre, Object valor) {
		listaCriterios.add(new CriterioBean (nombre, valor));
	}
	
	public void addCriterio(String nombre, Object valor, String comparacion) {
		listaCriterios.add(new CriterioBean (nombre, valor, comparacion));
	}
	
	
	public void addCriterioOr(CriterioBeanOr criterio) {
		listaCriteriosOR.add(criterio);
	}
	
	public String getWhere(){	
		String where = "";		
		CriterioBean criterio;
		for (int i=0;i<this.getlistaCriterios().size();i++){
		// Obtenemos cada uno de los criterios de busqueda...
			criterio = getlistaCriterios().get(i);				
			String comparacion = "(" + criterio.getComparacionSQL(as) + ")";
			if (where.length() == 0)
				where = " where " + comparacion;
			else
				where += " AND " + comparacion;
		}
		return where;
	}

	public String getAs() {
		return as;
	}
	
	public void setAs(String as) {
		this.as = as;
	}
	
	public List<CriterioBean> getListaCriterios() {
		return listaCriterios;
	}
	
	public void setListaCriterios(Vector<CriterioBean> listaCriterios) {
		this.listaCriterios = listaCriterios;
	}

	public void actualizarCriteria(Criteria criteria){
		CriterioBean criterio;
		for (int i=0; i<this.getlistaCriterios().size();i++){
			criterio= getlistaCriterios().get(i);
			if(criterio.getTipoComparacion().equals(IGUAL))
				criteria.add(Restrictions.eq(criterio.getNombreCriterio() , criterio.getValorCriterio()));
			else if (criterio.getTipoComparacion().equals(MAYOR))
				criteria.add(Restrictions.gt(criterio.getNombreCriterio(), criterio.getValorCriterio()));
			else if (criterio.getTipoComparacion().equals(MENOR))
				criteria.add(Restrictions.lt(criterio.getNombreCriterio(),(criterio.getValorCriterio())));
			else if (criterio.getTipoComparacion().equals(MAYOR_IGUAL))
				criteria.add(Restrictions.ge(criterio.getNombreCriterio(),(criterio.getValorCriterio())));
			else if (criterio.getTipoComparacion().equals(MENOR_IGUAL))
				criteria.add(Restrictions.le(criterio.getNombreCriterio(),(criterio.getValorCriterio())));
			else if(criterio.getTipoComparacion().equals(DISTINTO))
				criteria.add(Restrictions.ne(criterio.getNombreCriterio(),(criterio.getValorCriterio())));
			else if(criterio.getTipoComparacion().equals(IN) || criterio.getTipoComparacion().equals(NOT_IN)){
				Object [] listTmp =(Object []) criterio.getValorCriterio();
				if(listTmp.length >0 && listTmp.length <= LIMITE_IN){
					if(criterio.getTipoComparacion().equals(IN))
						criteria.add(Restrictions.in(criterio.getNombreCriterio(), listTmp));
					else
						criteria.add(Restrictions.not(Restrictions.in(criterio.getNombreCriterio(), listTmp)));
				}
				else if(listTmp.length >0){
					Junction junction = null; 
					if(criterio.getTipoComparacion().equals(IN))
						junction = Restrictions.disjunction();
					else
						junction = Restrictions.conjunction();
					int posInic = 0;
					while (posInic < listTmp.length){
						int numElem = LIMITE_IN;
						if(posInic + LIMITE_IN >listTmp.length)
							numElem = listTmp.length - posInic;
						Object [] listTmp2 = new Object[numElem];
						System.arraycopy(listTmp, posInic, listTmp2, 0, numElem);
						posInic += numElem;
						if(criterio.getTipoComparacion().equals(IN))
							junction.add(Restrictions.in(criterio.getNombreCriterio(),listTmp2));
						else
							junction.add(Restrictions.not(Restrictions.in(criterio.getNombreCriterio(), listTmp2)));
					}
					criteria.add(junction);
				}
			}
			//else if(criterio.getTipoComparacion().equals(NOT_IN))
				//criteria.add(Restrictions.not(Restrictions.in(criterio.getNombreCriterio(),((Object []) criterio.getValorCriterio()))));			
			else if(criterio.getTipoComparacion().equals(BETWEEN))
				criteria.add(Restrictions.between (criterio.getNombreCriterio(), criterio.getValorCriterio(), criterio.getValorCriterio2()));
			else if(criterio.getTipoComparacion().equals(LIKE))
				criteria.add(Restrictions.like(criterio.getNombreCriterio(), "%" + criterio.getValorCriterio() + "%"));
			else if(criterio.getTipoComparacion().equals(ILIKE))
				criteria.add(Restrictions.ilike(criterio.getNombreCriterio(), "%" + criterio.getValorCriterio() + "%"));
			else if(criterio.getTipoComparacion().equals(ILIKE2))
				criteria.add(Restrictions.ilike(criterio.getNombreCriterio(), criterio.getValorCriterio()));
			else if(criterio.getTipoComparacion().equals(NOT_LIKE))
				criteria.add(Restrictions.not (Restrictions.ilike(criterio.getNombreCriterio(), "%" + criterio.getValorCriterio() + "%")));
			else if(criterio.getTipoComparacion().equals(IS_NOT_NULL))
				criteria.add(Restrictions.isNotNull(criterio.getNombreCriterio()));
			else if(criterio.getTipoComparacion().equals(IS_NULL))
				criteria.add(Restrictions.isNull(criterio.getNombreCriterio()));
			else if(criterio.getTipoComparacion().equals(EXPRESION_REGULAR))
				actualizarCriteriaExpresionRegular (criterio.getValorCriterio().toString(), criterio.getNombreCriterio(), criteria);
			else if(criterio.getTipoComparacion().equals(EXPRESION_REGULAR2))
				actualizarCriteriaExpresionRegular2(criterio.getValorCriterio().toString(), criterio.getNombreCriterio(), criteria);
			else if(criterio.getTipoComparacion().equals(SQL_RESTRICTION))
				criteria.add(Restrictions.sqlRestriction(criterio.getValorCriterio().toString()));
			else if(criterio.getTipoComparacion().equals(IGUAL_OTRA_PROP))
				criteria.add(Restrictions.eqProperty (criterio.getNombreCriterio() , criterio.getValorCriterio().toString()));
			else if (criterio.getTipoComparacion().equals(MAYOR_OTRA_PROP))
				criteria.add(Restrictions.gtProperty(criterio.getNombreCriterio(), criterio.getValorCriterio().toString()));
			else if (criterio.getTipoComparacion().equals(MENOR_OTRA_PROP))
				criteria.add(Restrictions.ltProperty(criterio.getNombreCriterio(), (criterio.getValorCriterio().toString())));
			else if (criterio.getTipoComparacion().equals(MAYOR_IGUAL_OTRA_PROP))
				criteria.add(Restrictions.geProperty(criterio.getNombreCriterio(), (criterio.getValorCriterio().toString())));				
			else if (criterio.getTipoComparacion().equals(MENOR_IGUAL_OTRA_PROP))
				criteria.add(Restrictions.leProperty(criterio.getNombreCriterio(), (criterio.getValorCriterio().toString())));							
			else if(criterio.getTipoComparacion().equals(DISTINTO_OTRA_PROP))
				criteria.add(Restrictions.neProperty(criterio.getNombreCriterio(), (criterio.getValorCriterio().toString())));
			else if(criterio.getTipoComparacion().equals(NORMA_LIKE))
				criteria.add(new NormaLikeExpression(criterio.getNombreCriterio(), criterio.getValorCriterio().toString()));
		}
		
		for (int i=0; i<listaCriteriosOR.size(); i++) {
			CriterioBeanOr criterioOR = listaCriteriosOR.get(i);
			if (criterioOR.getListasOr().isEmpty())
				continue;
			Disjunction disjunction = Restrictions.disjunction();
			List<CriterioBean> listaCriterioBeanOr =  criterioOR.getListasOr();
			for (int j=0; j< listaCriterioBeanOr.size(); j++) {
				CriterioBean criterioBeanOR = listaCriterioBeanOr.get(j);
				if(criterioBeanOR.getTipoComparacion().equals(IGUAL))
					disjunction.add(Restrictions.eq(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));				
				else if (criterioBeanOR.getTipoComparacion().equals(MAYOR))
					disjunction.add(Restrictions.gt(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));				
				else if (criterioBeanOR.getTipoComparacion().equals(MENOR))
					disjunction.add(Restrictions.lt(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));				
				else if (criterioBeanOR.getTipoComparacion().equals(MAYOR_IGUAL))
					disjunction.add(Restrictions.ge(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));				
				else if (criterioBeanOR.getTipoComparacion().equals(MENOR_IGUAL))
					disjunction.add(Restrictions.le(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));							
				else if(criterioBeanOR.getTipoComparacion().equals(DISTINTO))
					disjunction.add(Restrictions.ne(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));							
				else if(criterioBeanOR.getTipoComparacion().equals(IN) || criterioBeanOR.getTipoComparacion().equals(NOT_IN)){
					Object [] listTmp =(Object []) criterioBeanOR.getValorCriterio();
					if(listTmp.length > 0 && listTmp.length <= LIMITE_IN){
						if(criterioBeanOR.getTipoComparacion().equals(IN))
							disjunction.add(Restrictions.in(criterioBeanOR.getNombreCriterio(), ((Object []) criterioBeanOR.getValorCriterio())));
						else
							disjunction.add(Restrictions.not(Restrictions.in(criterioBeanOR.getNombreCriterio(), ((Object []) criterioBeanOR.getValorCriterio()))));
					}
					else if(listTmp.length > 0){						
						int posInic = 0;
						Junction junction = null; 
						if(criterioBeanOR.getTipoComparacion().equals(IN))
							junction = Restrictions.disjunction();
						else
							junction = Restrictions.conjunction();
						while (posInic < listTmp.length){
							int numElem = LIMITE_IN;
							if(posInic + LIMITE_IN >listTmp.length)
								numElem = listTmp.length - posInic;
							Object [] listTmp2 = new Object[numElem];
							System.arraycopy(listTmp, posInic, listTmp2, 0, numElem);
							posInic += numElem;
							if(criterioBeanOR.getTipoComparacion().equals(IN))
								junction.add(Restrictions.in(criterioBeanOR.getNombreCriterio(), listTmp2));
							else
								junction.add(Restrictions.not(Restrictions.in(criterioBeanOR.getNombreCriterio(), listTmp2)));
						}
						disjunction.add(junction);
					}
				}
				//else if(criterioBeanOR.getTipoComparacion().equals(NOT_IN))
					//disjunction.add(Restrictions.not(Restrictions.in(criterioBeanOR.getNombreCriterio(), ((Object []) criterioBeanOR.getValorCriterio()))));							
				else if(criterioBeanOR.getTipoComparacion().equals(BETWEEN))
					disjunction.add(Restrictions.between (criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio(), criterioBeanOR.getValorCriterio2()));				
				else if(criterioBeanOR.getTipoComparacion().equals(LIKE))
					disjunction.add(Restrictions.like(criterioBeanOR.getNombreCriterio(), "%" + criterioBeanOR.getValorCriterio() + "%"));
				else if(criterioBeanOR.getTipoComparacion().equals(ILIKE))
					disjunction.add(Restrictions.ilike(criterioBeanOR.getNombreCriterio(), "%" + criterioBeanOR.getValorCriterio() + "%"));
				else if(criterioBeanOR.getTipoComparacion().equals(ILIKE2))
					disjunction.add(Restrictions.ilike(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio()));
				else if(criterioBeanOR.getTipoComparacion().equals(IS_NOT_NULL))
					disjunction.add(Restrictions.isNotNull(criterioBeanOR.getNombreCriterio()));
				else if(criterioBeanOR.getTipoComparacion().equals(IS_NULL))
					disjunction.add(Restrictions.isNull(criterioBeanOR.getNombreCriterio()));
				else if(criterioBeanOR.getTipoComparacion().equals(SQL_RESTRICTION))
					disjunction.add(Restrictions.sqlRestriction(criterioBeanOR.getValorCriterio().toString()));
				else if(criterioBeanOR.getTipoComparacion().equals(EXPRESION_REGULAR))
					actualizarCriteriaExpresionRegular (criterioBeanOR.getValorCriterio().toString(), criterioBeanOR.getNombreCriterio(), disjunction);
				else if(criterioBeanOR.getTipoComparacion().equals(NORMA_LIKE))
					disjunction.add(new NormaLikeExpression(criterioBeanOR.getNombreCriterio(), criterioBeanOR.getValorCriterio().toString()));
			}
			criteria.add(disjunction);
		}
		
		for (CriterioBeanOr criterioOR : listaCriteriosOR) {
			if (criterioOR.getListasAnd().isEmpty())
				continue;
			Disjunction disjunction = Restrictions.disjunction();
			for (List<CriterioBean> listaAnds : criterioOR.getListasAnd()){				
				Conjunction conjunction = Restrictions.conjunction();
				for (CriterioBean criterioAnd : listaAnds){
					if(criterioAnd.getTipoComparacion().equals(IGUAL))
						conjunction.add(Restrictions.eq(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));				
					else if (criterioAnd.getTipoComparacion().equals(MAYOR))
						conjunction.add(Restrictions.gt(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));				
					else if (criterioAnd.getTipoComparacion().equals(MENOR))
						conjunction.add(Restrictions.lt(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));				
					else if (criterioAnd.getTipoComparacion().equals(MAYOR_IGUAL))
						conjunction.add(Restrictions.ge(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));				
					else if (criterioAnd.getTipoComparacion().equals(MENOR_IGUAL))
						conjunction.add(Restrictions.le(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));							
					else if(criterioAnd.getTipoComparacion().equals(DISTINTO))
						conjunction.add(Restrictions.ne(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));							
					else if(criterioAnd.getTipoComparacion().equals(IN)){
						Object [] listTmp =(Object []) criterioAnd.getValorCriterio();
						if(listTmp.length >0 && listTmp.length<= LIMITE_IN)
							conjunction.add(
									Restrictions.in(criterioAnd.getNombreCriterio(), ((Object []) criterioAnd.getValorCriterio())));
						else if(listTmp.length >0){						
							int posInic = 0;
							while (posInic < listTmp.length){
								int numElem = LIMITE_IN;
								if(posInic + LIMITE_IN > listTmp.length)
									numElem = listTmp.length - posInic;
								Object [] listTmp2 = new Object[numElem];
								System.arraycopy(listTmp, posInic, listTmp2, 0, numElem);
								posInic += numElem;
								conjunction.add(Restrictions.in(criterioAnd.getNombreCriterio(), listTmp2));
							}
						}
					}
					else if(criterioAnd.getTipoComparacion().equals(NOT_IN))
						conjunction.add(Restrictions.not(Restrictions.in(criterioAnd.getNombreCriterio(), ((Object []) criterioAnd.getValorCriterio()))));							
					else if(criterioAnd.getTipoComparacion().equals(BETWEEN))
						conjunction.add(Restrictions.between (criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio(), criterioAnd.getValorCriterio2()));				
					else if(criterioAnd.getTipoComparacion().equals(LIKE))
						conjunction.add(Restrictions.like(criterioAnd.getNombreCriterio(), "%" + criterioAnd.getValorCriterio() + "%"));
					else if(criterioAnd.getTipoComparacion().equals(ILIKE))
						conjunction.add(Restrictions.ilike(criterioAnd.getNombreCriterio(), "%" + criterioAnd.getValorCriterio() + "%"));
					else if(criterioAnd.getTipoComparacion().equals(ILIKE2))
						conjunction.add(Restrictions.ilike(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio()));
					else if(criterioAnd.getTipoComparacion().equals(IS_NOT_NULL))
						conjunction.add(Restrictions.isNotNull(criterioAnd.getNombreCriterio()));
					else if(criterioAnd.getTipoComparacion().equals(IS_NULL))
						conjunction.add(Restrictions.isNull(criterioAnd.getNombreCriterio()));
					else if(criterioAnd.getTipoComparacion().equals(SQL_RESTRICTION))
						conjunction.add(Restrictions.sqlRestriction(criterioAnd.getValorCriterio().toString()));
					else if(criterioAnd.getTipoComparacion().equals(EXPRESION_REGULAR))
						actualizarCriteriaExpresionRegular (criterioAnd.getValorCriterio().toString(), criterioAnd.getNombreCriterio(), conjunction);
					else if(criterioAnd.getTipoComparacion().equals(NORMA_LIKE))
						conjunction.add(new NormaLikeExpression(criterioAnd.getNombreCriterio(), criterioAnd.getValorCriterio().toString()));
				}
				disjunction.add(conjunction);
			}
			criteria.add(disjunction);
		}
		
		//Restrictions.disjunction()
		if(hashAlias!= null){
			Iterator<AliasBean> it = hashAlias.values().iterator();
			while (it.hasNext()) {
				AliasBean alias = (AliasBean) it.next();
				if(alias.getTipoJoin().equals(AliasBean.INNER_JOIN))
					criteria.createAlias(alias.getNombreAlias(), alias.getValorAlias());
				else if(alias.getTipoJoin().equals(AliasBean.LEFT_JOIN))
					criteria.setFetchMode(alias.getNombreAlias(), FetchMode.JOIN);
				else if(alias.getTipoJoin().equals(AliasBean.LEFT_OUTER_JOIN))
					criteria.createAlias(alias.getNombreAlias(), alias.getValorAlias(), JoinType.LEFT_OUTER_JOIN);
			}		
		}
		
		if(this.getNumMaxRegistros() != null){
			criteria.setMaxResults(getNumMaxRegistros().intValue());
		}
		else if(this.getNumPaginacion() != null && this.getNumRegistrosPagina() != null) {
			criteria.setFirstResult((getNumPaginacion().intValue()-1) * getNumRegistrosPagina().intValue()) ;
			criteria.setMaxResults(getNumRegistrosPagina().intValue());
		}
		ProjectionList listaProjection = Projections.projectionList();
		
		for (int i=0; i<listaGroupBy.size(); i++){
			String groupBy = listaGroupBy.get(i);
			listaProjection.add(Projections.groupProperty(groupBy));
			//criteria.setProjection(Projections.projectionList().add(Projections.groupProperty(groupBy)));
		}
		
		for (int i=0; i<listaFuncionesAgregadas.size(); i++){
			ParBean par = listaFuncionesAgregadas.get(i);
			String nombreFuncion = par.getNombre();

			if(nombreFuncion.equals(FUNC_DISTINCT)){
				listaProjection.add(Projections.distinct(Projections.property(par.getValor())));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_SUM)){
				listaProjection.add(Projections.sum(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_AVG)){
				listaProjection.add(Projections.avg(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_MIN)){
				listaProjection.add(Projections.min(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_MAX)){
				listaProjection.add(Projections.max(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_COUNT)){
				listaProjection.add(Projections.count(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_COUNT_DISTINCT)){
				listaProjection.add(Projections.countDistinct(par.getValor()));
			}
			else if(nombreFuncion.equals(FUNC_AGREGADA_ROW_COUNT)){
				listaProjection.add(Projections.rowCount());
			}
		}
		
		for (int i=0; i<listaProyecciones.size(); i++){
			String proyeccion = listaProyecciones.get(i).toString ();
			String aliasProyeccion = hashMapAliasProyecciones.get(proyeccion);
			listaProjection.add(Projections.property(proyeccion), aliasProyeccion);
		}
			
		if(listaProjection.getLength() > 0){
			if(unicoResultado)
				criteria.setProjection(Projections.distinct(listaProjection));
			else				
				criteria.setProjection(listaProjection);
		}
		if(unicoResultado)
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);						
		
		if(classResultTransformer != null)			
			criteria.setResultTransformer(Transformers.aliasToBean(classResultTransformer));
				
		for(LockMode lock : listaBloqueos)
			criteria.setLockMode("prueba", lock);
		
		/*
		DetachedCriteria subQuery = DetachedCriteria.forClass(Expediente.class);
		subQuery.setProjection(Projections.distinct( Projections.projectionList().add(Projections.alias(Projections.property("idExpediente"), "idExpediente"))));
		criteria.add(Subqueries.propertyIn("idExpediente", subQuery));
		*/
	}


	public HashMap<String, AliasBean> getListaAlias() {
		return hashAlias;
	}


	public void setListaAlias(HashMap<String, AliasBean> hashAlias) {
		this.hashAlias = hashAlias;
	}

	public void addGroupBy(String groupBy){
		listaGroupBy.add(groupBy);
	}
	
	public void addFuncionAgregada(String nombreFuncion, String nombreColumna){
		listaFuncionesAgregadas.add(new ParBean(nombreFuncion, nombreColumna));
	}

	public boolean isUnicoResultado() {
		return unicoResultado;
	}

	public void setUnicoResultado(boolean unicoResultado) {
		this.unicoResultado = unicoResultado;
	}
	
	public static void actualizarCriteriaExpresionRegular (String expresion, String nombreCampo, Criteria criteria){
		Disjunction disjunction = Restrictions.disjunction(); 
		if( StringUtils.isNotBlank(expresion)){
			String listaValores[] = StringUtils.split(expresion, ", ");
			if(listaValores != null)
				for(int i=0; i<listaValores.length; i++){
					String valor = listaValores[i];
					if( StringUtils.contains(valor, "-")){
						String[] aux = StringUtils.split(valor, "-");
						if(aux != null && aux.length == 2){
							String inico = aux [0];
							String fin = aux [1];
							if( StringUtils.isNotBlank (inico) && StringUtils.isNumeric(inico) &&
								StringUtils.isNotBlank (fin) && StringUtils.isNumeric(fin)){
								Conjunction conjunction = Restrictions.conjunction();
								conjunction.add(Restrictions.ge(nombreCampo, new Long(inico.trim())));
								conjunction.add(Restrictions.le(nombreCampo, new Long(fin.trim())));
								disjunction.add(conjunction);
							}
						}
					}
					else{
						if(StringUtils.isNotBlank (valor) && StringUtils.isNumeric(valor))
							disjunction.add(Restrictions.eq(nombreCampo, new Long(valor.trim())));
				}
			}
		}
		criteria.add(disjunction);
	}
	
	public static void actualizarCriteriaExpresionRegular (String expresion, String nombreCampo, Junction criteria){
		Disjunction disjunction = Restrictions.disjunction(); 
		if( StringUtils.isNotBlank(expresion)){
			String listaValores[] = StringUtils.split(expresion, ", ");
			if(listaValores != null)
				for(int i=0; i<listaValores.length; i++){
					String valor = listaValores[i];
					if( StringUtils.contains(valor, "-")){
						String[] aux = StringUtils.split(valor, "-");
						if(aux!= null && aux.length == 2){
							String inico = aux [0];
							String fin = aux [1];
							if( StringUtils.isNotBlank (inico) && StringUtils.isNumeric(inico) &&
								StringUtils.isNotBlank (fin) && StringUtils.isNumeric(fin)){
								Conjunction conjunction = Restrictions.conjunction();
								conjunction.add(Restrictions.ge(nombreCampo, new Long(inico.trim())));
								conjunction.add(Restrictions.le(nombreCampo, new Long(fin.trim())));
								disjunction.add(conjunction);
							}
						}
					}
					else{
						if( StringUtils.isNotBlank (valor) && StringUtils.isNumeric(valor))
							disjunction.add(Restrictions.eq(nombreCampo, new Long(valor.trim())));
				}
			}
		}
		criteria.add(disjunction);
	}
	
	public static void actualizarCriteriaExpresionRegular2 (String expresion, String nombreCampo, Criteria criteria){
		Disjunction disjunction = Restrictions.disjunction(); 
		if( StringUtils.isNotBlank(expresion)){
			String listaValores[] = StringUtils.split(expresion, ", ");
			if(listaValores != null)
				for(int i=0; i<listaValores.length; i++){
					String valor = listaValores[i];
					if( StringUtils.contains(valor, "-")){
						String[] aux = StringUtils.split(valor, "-");
						if(aux!= null && aux.length == 2){
							String inico = aux [0];
							String fin = aux [1];
							if( StringUtils.isNotBlank (inico) && StringUtils.isNotBlank (fin)){
								Conjunction conjunction = Restrictions.conjunction();
								conjunction.add(Restrictions.ge(nombreCampo, inico.trim()));
								conjunction.add(Restrictions.le(nombreCampo, fin.trim()));
								disjunction.add(conjunction);
							}
						}
					}
					else{
						if(StringUtils.isNotBlank (valor) && StringUtils.isNumeric(valor))
							disjunction.add(Restrictions.eq(nombreCampo, valor.trim()));
				}
			}
		}
		criteria.add(disjunction);
	}
	
	public static String getSQLExpresionRegular (String expresion, String nombreCampo){
		String result = "";
		if( StringUtils.isNotBlank(expresion)){
			result = " 1=1 AND (";
			String listaValores[] = StringUtils.split(expresion, ", ");
			if(listaValores != null)
				for(int i=0; i<listaValores.length; i++){
					String valor = listaValores[i];
					if( StringUtils.contains(valor, "-")){
						String[] aux = StringUtils.split(valor, "-");
						if(aux!= null && aux.length == 2){
							String inico = aux [0];
							String fin = aux [1];
							if( StringUtils.isNotBlank (inico) && StringUtils.isNumeric(inico) &&
								StringUtils.isNotBlank (fin) && StringUtils.isNumeric(fin)){
								result += " (" + nombreCampo + " >= " + inico  + " AND " + nombreCampo + " <= " + fin + ") ";
								if(i<listaValores.length-1)
									result += " OR ";
							}
						}
					}
					else{
						if( StringUtils.isNotBlank (valor) && StringUtils.isNumeric(valor))
							result += " " + nombreCampo + " = " + valor;
						if(i<listaValores.length-1)
							result += " OR ";
				}
			}
			result += ") ";
		}
		return result;
	}
	
	public void addProyeccion (String proyeccion){
		listaProyecciones.add(proyeccion);
		hashMapAliasProyecciones.put(proyeccion, proyeccion);
	}
	
	public void addProyeccion (String proyeccion, String aliasProyeccion){
		listaProyecciones.add(proyeccion);
		hashMapAliasProyecciones.put(proyeccion, aliasProyeccion);
	}
	
	public void removeProyeccion (String proyeccion){
		listaProyecciones.remove(proyeccion);
		hashMapAliasProyecciones.remove(proyeccion);
	}
	
	public List<String> getListaProyecciones (){
		return listaProyecciones;
	}
	
	public void setListaProyecciones (List<String> listaProyecciones){
		this.listaProyecciones = listaProyecciones;
	}
	
	/*
	public void addDistinct (String propiedad){
		listaDistinct.add(propiedad);		
	}
	public void dellDistinct (String propiedad){
		listaDistinct.remove(propiedad);		
	}
	*/
	
	public boolean contieneCountDistinct(){
		for (int i = 0; i < listaFuncionesAgregadas.size(); i++){
			if(listaFuncionesAgregadas.get(i).getNombre().equals(FUNC_AGREGADA_COUNT_DISTINCT))
				return true;
		}
		return false;
	}	

	public void eliminarCountDistinct(){
		for (int i = 0; i < listaFuncionesAgregadas.size(); i++){
			if(listaFuncionesAgregadas.get(i).getNombre().equals(FUNC_AGREGADA_COUNT_DISTINCT)){
				listaFuncionesAgregadas.remove(i);
				break;
			}
		}		
	}

	public List<CriterioBeanOr> getListaCriteriosOR() {
		return listaCriteriosOR;
	}

	public void setListaCriteriosOR(List<CriterioBeanOr> listaCriteriosOR) {
		this.listaCriteriosOR = listaCriteriosOR;
	}

	public Class getClassResultTransformer() {
		return classResultTransformer;
	}

	public void setClassResultTransformer(Class classResultTransformer) {
		this.classResultTransformer = classResultTransformer;
	}

	public List<LockMode> getListaBloqueos() {
		return listaBloqueos;
	}

	public void setListaBloqueos(List<LockMode> listaBloqueos) {
		this.listaBloqueos = listaBloqueos;
	}

	public HashMap<String, AliasBean> getHashAlias() {
		return hashAlias;
	}

	public void setHashAlias(HashMap<String, AliasBean> hashAlias) {
		this.hashAlias = hashAlias;
	}
	
	public boolean contieneAlisas (String nombreAlias) {
		if(hashAlias == null)
			return false;
		return hashAlias.containsKey(nombreAlias);
	}

	public String getNombreCriteria() {
		return nombreCriteria;
	}

	public void setNombreCriteria(String nombreCriteria) {
		this.nombreCriteria = nombreCriteria;
	}
}
