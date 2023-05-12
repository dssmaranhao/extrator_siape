package br.dssm.calculos.dados.extrator;

public class Periodo {
	private int ano;
	private int mes;

	public Periodo(int ano, int mes) {
		this.setAno(ano);
		this.setMes(mes);
	}
	
	public int getAno() {
		return ano;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}

	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	switch (this.getMes()) {
		case 1:
	    	sb.append("jan/" + this.getAno());			
			break;
		case 2:
	    	sb.append("fev/" + this.getAno());			
			break;
		case 3:
	    	sb.append("mar/" + this.getAno());			
			break;
		case 4:
	    	sb.append("abr/" + this.getAno());			
			break;
		case 5:
	    	sb.append("mai/" + this.getAno());			
			break;
		case 6:
	    	sb.append("jun/" + this.getAno());			
			break;
		case 7:
	    	sb.append("jul/" + this.getAno());			
			break;
		case 8:
	    	sb.append("ago/" + this.getAno());			
			break;
		case 9:
	    	sb.append("set/" + this.getAno());			
			break;
		case 10:
	    	sb.append("out/" + this.getAno());			
			break;
		case 11:
	    	sb.append("nov/" + this.getAno());			
			break;
		case 12:
	    	sb.append("dez/" + this.getAno());			
			break;
		case 13:
	    	sb.append("13º/" + this.getAno());			
			break;
		}

		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Exequente)) return false;
	    if (obj == this) return true;

	    return (((Periodo)obj).getAno() == this.getAno()) && (((Periodo)obj).getMes() == this.getMes());
	}
	
	@Override
	public int hashCode() {
		int result = 0;
		int prime = 31;
		result += prime * this.getAno();
		
	   return prime + this.getAno(); 
	}
	
	
}
