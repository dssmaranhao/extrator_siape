package br.dssm.calculos.dados.extrator;

import java.io.Serializable;
import java.util.Objects;

public class MatriculaPeriodo implements Serializable{

	private static final long serialVersionUID = -2984703482342403997L;
	private String admissao;
	private String saida;
	
	public MatriculaPeriodo(String admissao, String saida) {
		this.setAdmissao(admissao);
		this.setSaida(saida);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Admissão: " + this.getAdmissao());
		sb.append(" Saída: " + this.getSaida());
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof MatriculaPeriodo)) return false;
	    if (obj == this) return true;
	    if( ((MatriculaPeriodo)obj).getAdmissao().equals(this.getAdmissao()) &&
	    	((MatriculaPeriodo)obj).getSaida().equals(this.getSaida()) ) {
	    	return true;
	    } else {
	    	return false;
	    }
	}
	
	@Override
	public int hashCode() {
	   int prime = 31;
	   return prime + 37*Objects.hash(this.getAdmissao()) + 43*Objects.hash(this.getSaida()); 
	}
	
	public String getAdmissao() {
		return admissao;
	}
	public void setAdmissao(String admissao) {
		this.admissao = admissao;
	}
	public String getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		this.saida = saida;
	}
}
