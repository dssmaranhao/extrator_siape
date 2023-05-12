package br.dssm.calculos.dados.extrator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class VerbasAno implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private HashMap<String, HashMap<Integer, HashMap<String, Double>>> mapaCodigos;
	private HashMap<String, HashMap<String, HashMap<String, Double>>> mapaMatriculas;
	private ArrayList<Integer> anos;
	private ArrayList<String> codigos;
	private ArrayList<String> matriculas;
	
	public VerbasAno() {
		mapaMatriculas = new HashMap<String, HashMap<String, HashMap<String, Double>>>();
		anos = new ArrayList<Integer>();
		codigos = new ArrayList<String>();
		matriculas = new ArrayList<String>();
	}

	public ArrayList<String> listarMatriculasPorListaCodigos(List<String> listaCodigos) {
		
		HashMap<String, HashMap<String, Double>> mapaCodigos = null;
		HashMap<String, Double> mapaValores = null;
		ArrayList<String> listaMatriculas = new ArrayList<String>();

		for( String matricula: matriculas ){
			mapaCodigos = mapaMatriculas.get(matricula);
			for (String codigo : listaCodigos) {
				mapaValores = mapaCodigos.get(codigo);
				if( mapaValores != null) {
					if (!listaMatriculas.contains(matricula))
						listaMatriculas.add(matricula);
				}
			}
		}
		
		return listaMatriculas;
	}
	
	public HashMap<String, Double> totalizarListaCodigos(List<String> listaCodigos) {
		
		HashMap<String, HashMap<String, Double>> mapaCodigos = null;
		HashMap<String, Double> mapaValores = null;
		HashMap<String, Double> mapaResumo = new HashMap<String, Double>();
		List<String> meses = Arrays.asList("jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez", "13º");
		
		for( String matricula: matriculas ){
			mapaCodigos = mapaMatriculas.get(matricula);
			for (String codigo : listaCodigos) {
				mapaValores = mapaCodigos.get(codigo);
				if( mapaValores != null) {
					for (Integer ano : anos) {
						for (String mes : meses) {
							if(mapaValores.containsKey(mes + "/" + ano))
								if( mapaResumo.containsKey(mes + "/" + ano) )
									mapaResumo.put(mes + "/" + ano, mapaResumo.get(mes + "/" + ano) + mapaValores.get(mes + "/" + ano));
								else 
									mapaResumo.put(mes + "/" + ano, mapaValores.get(mes + "/" + ano));					
						}
					}
				}
			}
		}
		
		return mapaResumo;
	}
	
	public void atualizarPrimeiroSemestre(String matricula, String cod, int ano, double jan, double fev, double mar, double abr, double mai, double jun) {
		
		if(!codigos.contains(cod))
			codigos.add(cod);
		
		if(!anos.contains(ano))
			anos.add(ano);

		if(!matriculas.contains(matricula))
			matriculas.add(matricula);

		if(!mapaMatriculas.containsKey(matricula))
			mapaMatriculas.put(matricula, new HashMap<String, HashMap<String, Double>>());
		
		HashMap<String, HashMap<String, Double>> mapaCodigos = mapaMatriculas.get(matricula);
		
		if(!mapaCodigos.containsKey(cod))
			mapaCodigos.put(cod, new HashMap<String, Double>());
		
		HashMap<String, Double> mapaValores = mapaCodigos.get(cod);
		
		if(mapaValores.containsKey("jan/"+ano))
			mapaValores.put("jan/"+ano, mapaValores.get("jan/"+ano) + jan);
		else
			mapaValores.put("jan/"+ano, jan);
		
		if(mapaValores.containsKey("fev/"+ano))
			mapaValores.put("fev/"+ano, mapaValores.get("fev/"+ano) + fev);
		else
			mapaValores.put("fev/"+ano, fev);
		
		if(mapaValores.containsKey("mar/"+ano))
			mapaValores.put("mar/"+ano, mapaValores.get("mar/"+ano) + mar);
		else
			mapaValores.put("mar/"+ano, mar);
		
		if(mapaValores.containsKey("abr/"+ano))
			mapaValores.put("abr/"+ano, mapaValores.get("abr/"+ano) + abr);
		else
			mapaValores.put("abr/"+ano, abr);
		
		if(mapaValores.containsKey("mai/"+ano))
			mapaValores.put("mai/"+ano, mapaValores.get("mai/"+ano) + mai);
		else
			mapaValores.put("mai/"+ano, mai);
		
		if(mapaValores.containsKey("jun/"+ano))
			mapaValores.put("jun/"+ano, mapaValores.get("jun/"+ano) + jun);
		else
			mapaValores.put("jun/"+ano, jun);
	
		mapaCodigos.put(cod, mapaValores);
		mapaMatriculas.put(matricula, mapaCodigos);
	}
	
	public void atualizarSegundoSemestre(String matricula, String cod, int ano, double jul, double ago, double set, double out, double nov, double dez) {
		
		if(!codigos.contains(cod))
			codigos.add(cod);
		
		if(!anos.contains(ano))
			anos.add(ano);

		if(!matriculas.contains(matricula))
			matriculas.add(matricula);

		if(!mapaMatriculas.containsKey(matricula))
			mapaMatriculas.put(matricula, new HashMap<String, HashMap<String, Double>>());
		
		HashMap<String, HashMap<String, Double>> mapaCodigos = mapaMatriculas.get(matricula);
		
		if(!mapaCodigos.containsKey(cod))
			mapaCodigos.put(cod, new HashMap<String, Double>());
		
		HashMap<String, Double> mapaValores = mapaCodigos.get(cod);
	
		if(mapaValores.containsKey("jul/"+ano))
			mapaValores.put("jul/"+ano, mapaValores.get("jul/"+ano) + jul);
		else
			mapaValores.put("jul/"+ano, jul);
		
		if(mapaValores.containsKey("ago/"+ano))
			mapaValores.put("ago/"+ano, mapaValores.get("ago/"+ano) + ago);
		else
			mapaValores.put("ago/"+ano, ago);
		
		if(mapaValores.containsKey("set/"+ano))
			mapaValores.put("set/"+ano, mapaValores.get("set/"+ano) + set);
		else
			mapaValores.put("set/"+ano, set);
		
		if(mapaValores.containsKey("out/"+ano))
			mapaValores.put("out/"+ano, mapaValores.get("out/"+ano) + out);
		else
			mapaValores.put("out/"+ano, out);
		
		if(mapaValores.containsKey("nov/"+ano))
			mapaValores.put("nov/"+ano, mapaValores.get("nov/"+ano) + nov);
		else
			mapaValores.put("nov/"+ano, nov);
		
		if(mapaValores.containsKey("dez/"+ano))
			mapaValores.put("dez/"+ano, mapaValores.get("dez/"+ano) + dez);
		else
			mapaValores.put("dez/"+ano, dez);
		
		mapaCodigos.put(cod, mapaValores);
		mapaMatriculas.put(matricula, mapaCodigos);
	}
//	public HashMap<String, Double> getValoresPorCodigo(int cod){
//		for
//		if(!mapaCodigos.containsKey(cod))
//			return null;
//		
//		return mapaCodigos.get(cod);
//	}
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	
    	Collections.sort(matriculas);
		Collections.sort(codigos);
		Collections.sort(anos);
		HashMap<String, Double> mapaValores = null;
		HashMap<String, HashMap<String, Double>> mapaCodigos = null;
		Double valor = null;
		
		for (String matricula : matriculas) {
			
			mapaCodigos = mapaMatriculas.get(matricula);

			sb.append("Matrícula: " + matricula + "\n");
			
			for (String cod : codigos) {
			
				mapaValores = mapaCodigos.get(cod);
				
				if (mapaValores != null) {
					
					sb.append("Código: " + cod + "\n");
					sb.append(String.format("%10s",""));
					for (Integer ano : anos) {
						sb.append(String.format("%10d",ano));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","jan"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("jan/"+ano)){
							valor = mapaValores.get("jan/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","fev"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("fev/"+ano)){
							valor = mapaValores.get("fev/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","mar"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("mar/"+ano)){
							valor = mapaValores.get("mar/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","abr"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("abr/"+ano)){
							valor = mapaValores.get("abr/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","mai"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("mai/"+ano)){
							valor = mapaValores.get("mai/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
		
					sb.append(String.format("%10s","jun"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("jun/"+ano)){
							valor = mapaValores.get("jun/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","jul"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("jul/"+ano)){
							valor = mapaValores.get("jul/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","ago"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("ago/"+ano)){
							valor = mapaValores.get("ago/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","set"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("set/"+ano)){
							valor = mapaValores.get("set/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","out"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("out/"+ano)){
							valor = mapaValores.get("out/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","nov"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("nov/"+ano)){
							valor = mapaValores.get("nov/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
					
					sb.append(String.format("%10s","dez"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("dez/"+ano)){
							valor = mapaValores.get("dez/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n");
		
					sb.append(String.format("%10s","13º"));
					for (Integer ano : anos) {
						if(mapaValores.containsKey("13º/"+ano)){
							valor = mapaValores.get("13º/"+ano);
						} else {
							valor = Double.valueOf(0f);
						}
						sb.append(String.format("%10.2f",valor.doubleValue()));
					}
					sb.append("\n\n");
				}
			}
		}
		
		return sb.toString();
	}
}
