package br.dssm.calculos.dados.analisador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Competencia {

	String mesStr;
	String anoStr;
	int mes;
	int ano;
	Date dt;
	
	public Competencia(String mesStr, String anoStr, int mes, int ano, Date dt) {
		this.mesStr = mesStr;
		this.anoStr = anoStr;
		this.mes = mes;
		this.ano = ano;
		this.dt = dt;
	}
	
	public String getMesAno() {
		return mesStr + "/" + anoStr;
	}
	
	public Date getDate() {
		return dt;
	}
	
	public static ArrayList<Competencia> getListaCompetencias (String dataInicial, String dataFinal) throws ParseException {
		
		DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        Date dt1 = df.parse(dataInicial);
        Date dt2 = df.parse(dataFinal);
        Competencia c = null;
        ArrayList<Competencia> lista = new ArrayList<Competencia>();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime (dt1);
        for (Date dt = dt1; dt.compareTo(dt2) <= 0; ) {
        	String mesStr = new SimpleDateFormat ("MMM", new Locale ("pt", "BR")).format(dt).toLowerCase();
        	String anoStr = new SimpleDateFormat ("yyyy", new Locale ("pt", "BR")).format(dt).toLowerCase();
        	int mes = Integer.parseInt(new SimpleDateFormat ("MM", new Locale ("pt", "BR")).format(dt));
        	int ano = Integer.parseInt(new SimpleDateFormat ("yyyy", new Locale ("pt", "BR")).format(dt));
        	c = new Competencia(mesStr, anoStr, mes, ano, dt);
        	lista.add(c);
        	
        	if(mes == 12) {
        		lista.add(new Competencia("13º", anoStr, 12, ano, dt));
        	}
        	
        	cal.add (Calendar.MONTH, +1);
            dt = cal.getTime();
        }
        
        return lista;
	}
	
}
