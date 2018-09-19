package es.jesmon.services.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("unchecked")
public class SingletonJesmon {
	
	private final static int NUM_DIAS_CADUCIDAD = 1; 

	private static HashMap listaHashMap;
	
	private static HashMap listaFechaCaducidadHashMap;
	
	private static HashMap<String, List<Object>> listaDeListasDeObjetos;
	
	private static HashMap<String, Date> listaFechaCaducidadListas;
	
	public static String LISTA_ESTADOS = "listaEstados";
	
	public static String LISTA_TIPOS_INCIDENCIA = "listaTiposIncidencia";
	
	public static String LISTA_PRIORIDADES_INCIDENCIA = "listaPrioridadesIncidencia";
	
	public static void putHashMap(String nombreHashMap, Object nombreObjeto, Object objeto){
		if(listaHashMap == null){
			listaHashMap = new HashMap();
			listaFechaCaducidadHashMap = new HashMap();
		} 
		if( ! listaHashMap.containsKey(nombreHashMap)){
			listaHashMap.put(nombreHashMap, new HashMap());
			listaFechaCaducidadHashMap.put(nombreHashMap, getNuevaFechaCaducidad());
		}
		else {
			Date fechaCaducidad = (Date) listaFechaCaducidadHashMap.get(nombreHashMap);			
			if( isFechaCaducada(fechaCaducidad)){
				listaHashMap.put(nombreHashMap,new HashMap());
				listaFechaCaducidadHashMap.put(nombreHashMap, getNuevaFechaCaducidad());
			}
		}		
		((HashMap)listaHashMap.get(nombreHashMap)).put(nombreObjeto.toString(), objeto);		
	}
		
	public static Object getObjetoHashMap(String nombreHashMap, Object nombreObjeto){
		if(listaHashMap == null)
			return null;
		if(! listaHashMap.containsKey(nombreHashMap))
			return null;
		HashMap hashMap = (HashMap) listaHashMap.get(nombreHashMap);
		if(! hashMap.containsKey(nombreObjeto.toString()))
			return null;
		else {
			Date fechaCaducidad = (Date) listaFechaCaducidadHashMap.get(nombreHashMap);			
			if( isFechaCaducada(fechaCaducidad))
				return null;
			else
				return hashMap.get(nombreObjeto.toString());
		}
	}

	public static void addLista(String nombreLista, List lista){
		if(listaDeListasDeObjetos == null){
			listaDeListasDeObjetos = new HashMap <String, List<Object>>();
			listaFechaCaducidadListas = new HashMap <String, Date>();
		}
		listaDeListasDeObjetos.put(nombreLista, lista);
		listaFechaCaducidadListas.put(nombreLista, getNuevaFechaCaducidad());
	}
	
	public static void addLista(String nombreLista, List lista, Date fechaCaducidad){
		if(listaDeListasDeObjetos == null){
			listaDeListasDeObjetos = new HashMap <String, List<Object>>();
			listaFechaCaducidadListas = new HashMap <String, Date>();
		}
		listaDeListasDeObjetos.put(nombreLista, lista);
		listaFechaCaducidadListas.put(nombreLista, fechaCaducidad);
	}
	
	public static List getLista(String nombreLista){
		if(listaDeListasDeObjetos == null || listaDeListasDeObjetos.get(nombreLista) == null)
			return null;
		else{
			Date fechaCaducidad = (Date) listaFechaCaducidadListas.get(nombreLista);			
			if(isFechaCaducada(fechaCaducidad))
				return null;
			else
				return (List)listaDeListasDeObjetos.get(nombreLista);
		}
	}
	
	private static boolean isFechaCaducada(Date fechaCaducidad){
		Date fechaActual = new Date();
		if(fechaActual.getTime() > fechaCaducidad.getTime())
			return true;
		else
			return false;
	}
	
	private static Date getNuevaFechaCaducidad(){
		Calendar calendario = Calendar.getInstance();
		calendario.add(Calendar.DAY_OF_YEAR, NUM_DIAS_CADUCIDAD);
		return calendario.getTime();
	}
	
	public static void resetarLista (String nombreLista){
		if(listaDeListasDeObjetos != null)
			if(listaDeListasDeObjetos.get(nombreLista) != null)
				listaDeListasDeObjetos.put(nombreLista, null);
	}
	
	public static void reset(){
		listaHashMap = new HashMap();
		listaFechaCaducidadHashMap = new HashMap();
		listaDeListasDeObjetos = new HashMap();
		listaFechaCaducidadListas = new HashMap();
	}
	
}
