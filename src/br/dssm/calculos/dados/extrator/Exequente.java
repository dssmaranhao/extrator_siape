package br.dssm.calculos.dados.extrator;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;


public class Exequente implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String nome;
	private String cpf;
	private ArrayList<String> matriculas;
	private VerbasAno verbas;
	private HashMap<String, MatriculaPeriodo> periodos;
	
	public Exequente(String nome, String cpf) {
		this.setNome(nome);
		this.setCPF(cpf); 
		verbas = new VerbasAno();
		periodos = new HashMap<String, MatriculaPeriodo>(); 
	}
		@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Nome: " + this.getNome() + "\n");
    	sb.append("CPF: " + this.getCPF() + "\n");
    	sb.append(verbas.toString() + "\n");
		return sb.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) return false;
	    if (!(obj instanceof Exequente)) return false;
	    if (obj == this) return true;
	    return (((Exequente)obj).getCPF().equals(this.getCPF()));
	}
	
	@Override
	public int hashCode() {
	   int prime = 31;
	   return prime + Objects.hash(this.getCPF()); 
	}
	
	public HashMap<String, MatriculaPeriodo> getPeriodos() {
		return periodos;
	}
	
	public void atualizarMapaPeriodos (String matricula, String admissao, String saida) {
		if(!periodos.containsKey(matricula)) {
			periodos.put(matricula, new MatriculaPeriodo(admissao, saida));
		}
	}
	
	public String getMatriculasStr() {
		String matriculas = "";
	    Collections.sort(this.getMatriculas());

	    for (int i = 0; i < this.getMatriculas().size(); i++) {
			matriculas += this.getMatriculas().get(i);
			if(i != this.getMatriculas().size() - 1) {
				matriculas += ", ";
			}
		}
	    return matriculas;
	}
	public ArrayList<String> getMatriculas() {
		return matriculas;
	}
	public void setMatriculas(ArrayList<String> matriculas) {
		this.matriculas = matriculas;
	}
	
	public void atualizarMapaVerbasPrimeiroSemestre(String matricula, String cod, int ano, double jan, double fev, double mar, double abr, double mai, double jun) {
		this.verbas.atualizarPrimeiroSemestre(matricula, cod, ano, jan, fev, mar, abr, mai, jun);
	}

	public void atualizarMapaVerbasSegundoSemestre(String matricula, String cod, int ano, double jul, double ago, double set, double out, double nov, double dez) {
		this.verbas.atualizarSegundoSemestre(matricula, cod, ano, jul, ago, set, out, nov, dez);
	}
	
	public static void salvar(String diretorioDestino, Exequente exequente) {
		
		String nomeArquivo = String.format("%s_%s.ser", exequente.getNome().replace(" ", "_"), exequente.getCPF());//, exequente.getMatricula());
		
        try {
           FileOutputStream fileOut = new FileOutputStream(diretorioDestino + nomeArquivo);
           ObjectOutputStream oos = new ObjectOutputStream(fileOut);
           oos.writeObject(exequente);
           oos.close();
           fileOut.close();
           System.out.printf("O arquivo %s foi salvo em %s.\n", nomeArquivo, diretorioDestino);
        } catch(IOException i){
            i.printStackTrace();
        }

	}
	
	public static Exequente carregar(String caminhoArquivo) {
		
        Exequente exequente = null;
        try{
           FileInputStream fileIn = new FileInputStream(caminhoArquivo);
           ObjectInputStream in = new ObjectInputStream(fileIn);
           exequente = (Exequente) in.readObject();
           in.close();
           fileIn.close();
        }catch(IOException ioExcepction) {
           ioExcepction.printStackTrace();
           return null;
        }catch(ClassNotFoundException classNotFoundException)
        {
           System.out.println("Employee class not found");
           classNotFoundException.printStackTrace();
           return null;
        }
        return exequente;
	}
	
	public VerbasAno getVerbas() {
		return verbas;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCPF() {
		return cpf;
	}
	public void setCPF(String cpf) {
		this.cpf = cpf;
	}
}
