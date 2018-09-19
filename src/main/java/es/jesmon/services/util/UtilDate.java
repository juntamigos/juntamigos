package es.jesmon.services.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UtilDate {

	//private static Logger logger 	=	LogginFactory.getLogger(UtilDate.class);
	private static String SEPARADOR = "/";
	
	private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yyyy");

	private static SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
	
	private static SimpleDateFormat formatoFechaInteco = new SimpleDateFormat("yyyyMMdd");
	
	private static SimpleDateFormat formatoFechaBoePeticion = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat formatoFechaBoeRespuesta = new SimpleDateFormat("yyyy-MM-dd");
	
	private static SimpleDateFormat formatoFecha2 = new SimpleDateFormat("dd-MM-yyyy");
	
	private static SimpleDateFormat formatoFechaRegistro = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static SimpleDateFormat formatoFechaComplejo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static SimpleDateFormat formatoFechaComplejo2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	private static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
	
	private static SimpleDateFormat formatoFechaHora = new SimpleDateFormat("HH:mm:ss");
	
	private static SimpleDateFormat formatoTimeLine = new SimpleDateFormat("yyyy,MM,dd");
			
	// a partir de un date genera un String
	public static String dateToString (Date date) {
		String fechaTrans = "";
		try{
			fechaTrans = formatoFecha.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	public static String dateToString2 (Date date) {
		String fechaTrans = new String();

		try{
			fechaTrans = formatoFecha2.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	
	public static String dateToStringCompleto (Date date) {
		String fechaTrans = new String();
		try{
			fechaTrans = formatoFechaComplejo.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	public static String dateToStringFichero (Date date) {
		String fechaTrans             = new String();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd_HHmmss");

		try{
			fechaTrans = formatoFecha.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}

	// a partir de un calendar genera un String
	public static String calendarToString (Calendar calendar) {
		String fechaTrans             = new String();
		Date date = calendar.getTime();		
		try{
			fechaTrans = formatoFecha.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}

	// a partir de un calendar genera un String Hora
	public static String calendarToStringTime (Calendar calendar) {
		String fechaTrans             = new String();
		Date date = calendar.getTime();

		try{
			fechaTrans = formatoFechaHora.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	
	// a partir de un String genera un Date
	public static Date stringToDate (String fecha) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		if (StringUtils.isBlank(fecha))
			return null;
		Date fechaTrans  = null;		

		try{
			fechaTrans = formatoFecha.parse(fecha);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		return fechaTrans;
	}
	
	
	public static Date stringToDateRegistro (String fecha) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date fechaTrans = null;
		
		try{
			fechaTrans = formatoFechaRegistro.parse(fecha);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//
		return fechaTrans;
	}

	public static String dateToStringRegistro (Date fecha) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		String fechaTrans = null;
		try{
			fechaTrans = formatoFechaRegistro.format(fecha);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		//
		return fechaTrans;
	}
	
	public static Date stringToDate235959 (String fecha) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date fechaTrans = null;
		
		try{
			fechaTrans = formatoFechaComplejo.parse(fecha + " 23:59:59");
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		return fechaTrans;
	}
	
	public static Date dateToDate235959 (Date fecha) {
		
		Date fechaTrans = null;
		
		try{
			fechaTrans = formatoFechaComplejo.parse(dateToString(fecha) + " 23:59:59");
		}catch (ParseException e){
		}
		return fechaTrans;
	}
	
	public static Date stringToDate (String fecha, String hora) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date fechaTrans = null;		

		try{
			fechaTrans = formatoFechaComplejo.parse(fecha + " " + hora);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		return fechaTrans;
	}
	
	public static Date stringToDateCompleto (String fecha) {
		Date fechaTrans = null;		
		try{
			fechaTrans = formatoFechaComplejo.parse(fecha);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		return fechaTrans;
	}

	// a partir de un String genera un Calendar
	public static Calendar stringToCalendar (String fecha) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date 		fechaTrans  = new Date();
		Calendar 	cal 		= Calendar.getInstance(); 		
		try{
			fechaTrans = formatoFecha.parse(fecha);
			cal.setTime(fechaTrans);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		return cal;
	}

	public static Calendar stringToCalendar (String fecha, String hora) {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date 		fechaTrans  = new Date();
		Calendar 	cal 		= Calendar.getInstance(); 
				
		try{
			fechaTrans = formatoFechaComplejo.parse(fecha+" "+hora);
			cal.setTime(fechaTrans);
		}catch (ParseException e){
			//Pendiente tratar exception
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		String atributo="";
		if( atributo.endsWith("_Hidden"))
			atributo=atributo.substring(0,atributo.lastIndexOf("_Hidden"));
		return cal;
	}

	public static Calendar stringToCalendarWS (String fecha)throws ParseException {
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (String) ->" + fecha);
		Date 		fechaTrans  = new Date();
		Calendar 	cal 		= Calendar.getInstance(); 
						
		try{
			fechaTrans = formatoFechaRegistro.parse(fecha);
			cal.setTime(fechaTrans);
		}catch (ParseException e){
			throw e;
		}
		//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA (Date) ->" + fechaTrans);
		String atributo="";
		if(atributo.endsWith("_Hidden"))
			atributo = atributo.substring(0,atributo.lastIndexOf("_Hidden"));
		return cal;
	}
	
	public static Date inicializar (Date fecha) {
		String fechaStr = dateToString(fecha);
		Date devolver = stringToDate (fechaStr);
		return devolver;
	}
	
	public static Calendar inicializar (Calendar fecha) {
		String fechaStr = calendarToString(fecha);
		Calendar devolver = stringToCalendar (fechaStr);
		return devolver;
	}
	
	// compara las dos fechas
	// 0: si son iguales
	// <0: fecha1 < fecha2
	// >0: fecha1 > fecha2
	public static int compare (Date fecha1, Date fecha2) {
		fecha1 = inicializar (fecha1);
		fecha2 = inicializar (fecha2);
		
		//logger.debug("compare >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA 1 ->" + fecha1);
		//logger.debug("compare >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>FECHA 2 ->" + fecha2);
		
		int comparar = 0; 
		comparar = fecha1.compareTo(fecha2); 
		//logger.debug("compare >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ->" + comparar);
		
		return comparar;
	}
	
	public static Date sumarDia (Date fecha) {
		fecha = inicializar (fecha);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, 1);
		fecha = cal.getTime();
		return fecha;
	}
	
	public static Date sumarDias (Date fecha, int dias) {
		if(fecha == null)
			return null;
		//fecha = inicializar (fecha);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.DATE, dias);
		fecha = cal.getTime();
		return fecha;
	}
	
	public static Date sumarMeses (Date fecha, int meses) {
		fecha = inicializar (fecha);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		cal.add(Calendar.MONTH, meses);
		fecha = cal.getTime();
		return fecha;
	}
	
	// comprueba si la fecha tiene el formato correcto y es correcta
	// devuelve:
	// - true: es correcta
	// - false: no es correcta
	public static boolean esFormatoCorrecto(String fecha)  
	{   
		if (fecha == null) return false;
		else{
			try{
				formatoFecha.parse(fecha);
				return true;
			}catch (Exception e){
				return false;
		 }
	   }		 
	}	
	
	// comprueba si la fecha es correcta
	public static boolean fechaCorrecta(String fechaStr) {
		boolean result = true;
		
		if (fechaStr.length() > 10 || fechaStr.length() < 8) return false;
		StringTokenizer tokens = new StringTokenizer(fechaStr, UtilDate.SEPARADOR);
		
		// Comprobamos la longitud de las cadenas...
		
		if (tokens.countTokens() != 3) return false;
		String dia = tokens.nextToken();
		if (dia.length() > 2 || dia.length() < 1) return false;
		String mes = tokens.nextToken();
		if (mes.length() > 2 || mes.length() < 1) return false;
		String anio = tokens.nextToken();
		if (anio.length() != 4) return false;
		
		// Comprobamos el tipo numerico de las cadenas...
		
		try {
			
			int diaAux = Integer.parseInt(dia);
			int mesAux = Integer.parseInt(mes);
			int anioAux = Integer.parseInt(anio);
			
			//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fechaCorrecta -> dia:" + diaAux +"|mes:"+mesAux+"|anioAux:"+anioAux);

			if ((diaAux < 1) || (diaAux > 31)) 	return false;
			if ((mesAux < 1) || (mesAux > 12)) 	return false;
			if (anioAux < 0) 					return false;

			//logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fechaCorrecta -> correcta");
			
		} catch (NumberFormatException e) {
			return false;
		}
		
		return result;
	}
	
	public static String dateToStringSQL (Date date) {
		String fechaTrans = new String();
		try{
			fechaTrans = formatoFecha.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	public static String dateToStringInteco (Date date) {
		String fechaTrans = new String();		
		try{
			fechaTrans = formatoFechaInteco.format(date);
		}catch (NullPointerException e){
			//Pendiente tratar exception
		}
		return fechaTrans;
	}
	
	public static DateTime stringToDateTime (String fecha){
		return dateTimeFormatter.parseDateTime(fecha);		
	}
	
	public static boolean equals(Date fecha1, Date fecha2){
		if(fecha1 == null && fecha2 == null)
			return true;
		
		if((fecha1 != null && fecha2 == null)|| (fecha1 == null && fecha2 != null))
			return false;
		
		String fecha1Str = dateToString(fecha1);
		String fecha2Str = dateToString(fecha2);
		
		if(fecha1Str == null || fecha2Str == null)							
			return false;
		return fecha1Str.equals(fecha2Str);
		
	}
	
	public static Date calendarToDate(Calendar fecha){
		String fechaStr = calendarToString(fecha);
		return stringToDate(fechaStr);
	}
	
	
	public static void main(String[] args)
	{
		//Para probar los m�todos
		/*String fechaRegistroStr = "20090722123546";
		Date fechaRegistro = UtilDate.stringToDateRegistro(fechaRegistroStr);*/
		System.out.println("Inicio");
		
		System.out.println(UtilDate.isViernes("3/03/2017"));
		
		System.out.println("Fin");
	}
	
	public static String getHora (Date fecha){
		try {
			return formatoHora.format(fecha);
		}
		catch (Exception e) {
			return "";
		}
	}
	
	public static int getUltimoDiaMes (int anio, int mes){
		Calendar calCurr = GregorianCalendar.getInstance();
		calCurr.set(anio, mes -1, 1); // Months are 0 to 11
		return calCurr.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);		
	}
	
	public static int getAnnio (Date fecha) {
		//fecha = inicializar (fecha);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.YEAR);		
	}
	
	public static int getMes (Date fecha) {
		//fecha = inicializar (fecha);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.MONTH) + 1;		
	}
	
	public static Date eliminarHora (Date fecha){
		return stringToDate(dateToString(fecha));		
	}
	
	public static String dateToStringTimeLine (Date fecha){
		try {
			return formatoTimeLine.format (fecha);
		}
		catch (Exception e){
			return null;
		}
	}
	
	public static Date stringToDateComplejo (String fecha){
		try {
			return formatoFechaComplejo2.parse(fecha);
		}
		catch (Exception e){
			return null;
		}
	}
	
	public static String dateToStringComplejo (Date fecha){
		try {
			String str = formatoFechaComplejo2.format (fecha);							
			return StringUtils.removeEnd(str, " 00:00"); 
		}
		catch (Exception e){
			return null;
		}
	}
	
	public static int getNuDiasAnio (Date fecha){
		if (fecha == null)
			return 0;
		int anio = getAnnio (fecha);
		if ((anio % 4 == 0) && ((anio % 100 != 0) || (anio % 400 == 0)))
			return 366;
		else
			return 365;
	}
	
	public static int getDiasEntreFechas (Date fechaIncio, Date fechaFin){		
		return Days.daysBetween(new DateTime(fechaIncio), new DateTime(fechaFin)).getDays(); 		
	}
	
	public static Date calcularFechaMaxima (Date fecha1, Date fecha2){
		if (fecha1 == null || fecha2 == null)
			return null;
		if (fecha1 == null)
			return fecha2;
		if (fecha2 == null)
			return fecha1;
		if(fecha1.getTime() > fecha2.getTime())
			return fecha1;
		else
			return fecha2;
	}
	
	public static Date calcularFechaMaxima (List<Date> listaFechas){
		if(listaFechas == null || listaFechas.isEmpty())
			return null;
		return Collections.max(listaFechas);
	}
	
	public static Date calcularFechaMinima (Date fecha1, Date fecha2){
		if (fecha1 == null || fecha2 == null)
			return null;
		if (fecha1 == null)
			return fecha2;
		if (fecha2 == null)
			return fecha1;
		if(fecha1.getTime() > fecha2.getTime())
			return fecha2;
		else
			return fecha1;
	}
	
	public static String dateToStringBOEPeticion (Date fecha){
		try {
			return formatoFechaBoePeticion.format(fecha);
		}
		catch (Exception e){
			return null;
		}
	}
	
	public static Date stringToDateBOERespuesta (String fecha){
		try {
			return formatoFechaBoeRespuesta.parse(fecha);
		}
		catch (Exception e){
			return null;
		}
	}
	
	
	public static boolean isViernes(String fechaStr){
		Date fecha = stringToDate(fechaStr);
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		return cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
	}
	
	
}
