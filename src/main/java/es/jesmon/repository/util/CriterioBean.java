/*
 * Created on 21-sep-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package es.jesmon.repository.util;


import java.util.Date;
import java.util.List;

import es.jesmon.services.util.UtilDate;


/**
 * @author ovejeror
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CriterioBean {

	private String nombreCriterio;
    private Object valorCriterio;
    private Object valorCriterio2;
    private String tipoComparacion;

	public CriterioBean() {
    }

    public CriterioBean(String nombre, Object valor, String comparacion)
    {
        nombreCriterio = nombre;
        valorCriterio = valor;
        tipoComparacion = comparacion;
    }
    
    public CriterioBean(String nombre, Object valor1,Object valor2, String comparacion)
    {
        nombreCriterio = nombre;
        valorCriterio = valor1;
        valorCriterio2 = valor2;        
        tipoComparacion = comparacion;
    }

    public CriterioBean(String nombreCriterio, Object valorCriterio)
    {
        this.nombreCriterio = nombreCriterio;
        this.valorCriterio = valorCriterio;
        this.tipoComparacion = CriteriosBusqueda.IGUAL;
    }

    public String getNombreCriterio()
    {
        return nombreCriterio;
    }

    public void setNombreCriterio(String nombreCriterio)
    {
        this.nombreCriterio = nombreCriterio;
    }

    public Object getValorCriterio()
    {
        return valorCriterio;
    }

    public void setValorCriterio(Object valorCriterio)
    {
        this.valorCriterio = valorCriterio;
    }

    public String getTipoComparacion()
    {
        return tipoComparacion;
    }

    public void setTipoComparacion(String tipoComparacion)
    {
        this.tipoComparacion = tipoComparacion;
    }

    public String getValorSQL()
    {
        String result = "";
        if(valorCriterio == null)
            result = "null";
        else if(valorCriterio.getClass().equals(java.lang.String.class))
        	if(tipoComparacion.equals(CriteriosBusqueda.LIKE) || tipoComparacion.equals(CriteriosBusqueda.ILIKE))
        		result = "'%" + valorCriterio + "%'";
        	else	
        		result = "'" + valorCriterio + "'";
        else if(valorCriterio.getClass().equals(Integer.class) ||
        		valorCriterio.getClass().equals(Float.class) ||
        		valorCriterio.getClass().equals(Double.class))
            result = valorCriterio.toString();
        else if(valorCriterio.getClass().equals(java.util.Date.class))
            result = "'" + UtilDate.dateToStringSQL((Date)valorCriterio) + "'";
        else if(valorCriterio.getClass().equals(java.sql.Date.class))
            result = "'"+valorCriterio.toString() + "'";
        else if(valorCriterio.getClass().equals(java.util.ArrayList.class))
        {
            result = "(";
            List listaValores = (List)valorCriterio;
            for(int i = 0; i < listaValores.size(); i++)
            {
                if(i > 0)
                    result = result + ",";
                if(listaValores.get(i).getClass().equals(java.lang.String.class))
                    result = result + "'" + listaValores.get(i) + "'";
                else
                    result = result + listaValores.get(i).toString();
            }

            result = result + ")";
        } else
        {
            result = valorCriterio.toString();
        }
        return result;
    }

    public String getValorSQL2()
    {
        String result = "";
        if(valorCriterio2 == null)
            result = "null";
        else
        if(valorCriterio2.getClass().equals(java.lang.String.class))
            result = "'" + valorCriterio2 + "'";
        else
        if(valorCriterio2.getClass().equals(Boolean.class)){
        	if( valorCriterio2.toString().equals("true"))
        		result = "1";
        	else
        		result = "0";
        }                
        else
        if(valorCriterio2.getClass().equals(java.util.Date.class))
            result = "'" + UtilDate.dateToStringSQL((Date)valorCriterio2) + "'";
        else
        if(valorCriterio2.getClass().equals(java.util.ArrayList.class))
        {
            result = "(";
            List listaValores = (List)valorCriterio2;
            for(int i = 0; i < listaValores.size(); i++)
            {
                if(i > 0)
                    result = result + ",";
                if(listaValores.get(i).getClass().equals(java.lang.String.class))
                    result = result + "'" + listaValores.get(i) + "'";
                else
                    result = result + listaValores.get(i).toString();
            }

            result = result + ")";
        } else
        {
            result = valorCriterio2.toString();
        }
        return result;
    }

    public String getComparacionSQL(String as)
    {
    	if(!as.equals("")){
    		as += ".";
    	}
        String result = "";
        if(tipoComparacion == null)
        {
            if(valorCriterio == null)
                result = as + nombreCriterio + " IS " + getValorSQL();
            else
                result = as + nombreCriterio + CriteriosBusqueda.IGUAL + getValorSQL();
        } else
        if(tipoComparacion.equals(CriteriosBusqueda.IN)) {
            if(valorCriterio == null)
                result = as + nombreCriterio + " IS " + getValorSQL();
            else
                result = as + nombreCriterio + " " + tipoComparacion + " " + getValorSQL();
        } 
        else if(tipoComparacion.equals(CriteriosBusqueda.BETWEEN))
            result = "(" + as + nombreCriterio + " BETWEEN " + getValorSQL() + " AND " + getValorSQL2() + ")";
        else if(valorCriterio == null) {
            if(tipoComparacion.equals(CriteriosBusqueda.IGUAL))
                result = as + nombreCriterio + " IS " + getValorSQL();
            else
            if(tipoComparacion.equals(CriteriosBusqueda.DISTINTO))
                result = as + nombreCriterio + " IS NOT " + getValorSQL();
            else
                result = as + nombreCriterio + tipoComparacion + getValorSQL();
        } else {
            result = as + nombreCriterio + " " + tipoComparacion + " " + getValorSQL();
        }
        return result;
    }

    public Object getValorCriterio2()
    {
        return valorCriterio2;
    }

    public void setValorCriterio2(Object valorCriterio2)
    {
        this.valorCriterio2 = valorCriterio2;
    }
    
    public  static String dateToString(java.util.Date d){
    	return "'" + UtilDate.dateToStringSQL(d) + "'";
	}
    
}
