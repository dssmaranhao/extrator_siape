package br.dssm.calculos.dados.extrator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Extrator {

	private static final String diretorioExecutavel = "exe/pdftotext.exe"; // executável
	private static final String diretorioFichasFinanceirasPDF = "fichas_financeiras/pdf/";
	private static final String diretorioFichasFinanceirasTXT = "fichas_financeiras/txt/";
	private static final String diretorioFichasFinanceirasSER = "fichas_financeiras/ser/";
	private static ArrayList<Exequente> exequentes = new ArrayList<Exequente>();
	
	public static String unaccent(String src) {
		return Normalizer
				.normalize(src, Normalizer.Form.NFD)
				.replaceAll("[^\\p{ASCII}]", "");
	}
	
	public static void extrairTexto() throws IOException {
		
		File destinationDirectory = new File(diretorioFichasFinanceirasPDF);
		if (!destinationDirectory.isDirectory()) {
			System.out.println("O diretório de fichas financeiras não foi localizado.");
			destinationDirectory.mkdirs();
		}
		
		File[] arquivos = destinationDirectory.listFiles();
		
		for (File arquivoOrigem : arquivos) {
	
			if (arquivoOrigem.isFile()) {

				System.out.println(arquivoOrigem.getName());
				
				File arquivoDestino = new File(diretorioFichasFinanceirasTXT + arquivoOrigem.getName() + ".txt");
				
				if (!arquivoDestino.isFile()) {
					
					ArrayList<String> command = new ArrayList<String>();
					command.add(diretorioExecutavel);
					command.add("-layout");
					command.add(arquivoOrigem.getPath());
					command.add(arquivoDestino.getPath());
					
					ProcessBuilder builder = new ProcessBuilder(command);
					Process process = builder.start();
					InputStream is = process.getErrorStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader input = new BufferedReader(isr);

					String linha = input.readLine();
					while (linha != null && !linha.equals("")) {
						linha = input.readLine();
					}
				}
			} else {
				System.out.println("O arquivo original não existe no diretório de destino.");
			}
		}
	}

	
	public static void serializar() throws Exception {
		
		File originDirectory = new File(diretorioFichasFinanceirasTXT);
		if (!originDirectory.isDirectory()) {
			originDirectory.mkdirs();
		}
		
	    long tempoInicial = System.currentTimeMillis();
	    
		File[] arquivosOrigem = originDirectory.listFiles();
		
		//BufferedWriter writer = new BufferedWriter(new FileWriter("bd_cpfs2.txt", true));
		
		//HashSet<String> listaCPFs = new HashSet<String>();
		
		for (File arquivoOrigem : arquivosOrigem) {
			
			if(arquivoOrigem.getName().contains(".txt")) {

				System.out.println(arquivoOrigem.getName());
				BufferedReader br = new BufferedReader(new FileReader(arquivoOrigem));
				try {
				    String line = br.readLine();

				    String matriculaSIAPE = "";
		        	String nomeServidor = "";
		        	String cpf = "";
		        	String cargoEmprego = "";
		        	String classe = "";
		        	String referencia = "";
		        	String funcao = "";
		        	String depSF = "";
		        	String dependenteIR = "";
		        	String ats = "";
		        	String banco = "";
		        	String agencia = "";
		        	String conta = "";
		        	int anoReferencia = -1;
        			
			        int idcNomeServidor = -1;
			        int idcMatriculaSIAPE = -1;
			        int idcCPF = -1;
			        int idcCargoEmprego = -1; 
			        int idcClasse = -1; 
			        int idcReferencia = -1;
			        int idcFuncao = -1;
			        int idcDEPSF =  -1; 
			        int idcDependente = -1; 
			        int idcATS = -1; 
			        int idcBanco = -1; 
			        int idcAgencia = -1; 
			        int idcConta = -1; 
			        int idcAnoReferencia = -1; 
		        	
			        boolean extrair = false;
			        
				    while (line != null) {
				        line = br.readLine();

				        if(line != null && line.contains("NOME DO SERVIDOR")) {
				        	idcNomeServidor = line.indexOf("NOME DO SERVIDOR");
				        	idcMatriculaSIAPE = line.indexOf("MAT. SIAPE");
				        	idcCPF = line.indexOf("CPF");
				        	line = br.readLine();
				        	nomeServidor = line.substring(idcNomeServidor, idcMatriculaSIAPE).trim();
				        	matriculaSIAPE = line.substring(idcMatriculaSIAPE, idcCPF).trim();
				        	cpf = line.substring(idcCPF).trim();
				        	
				        	System.out.println(nomeServidor + " " + matriculaSIAPE + " " + cpf );
				        }


				        if(line != null && line.contains("CARGO/EMPREGO")) {
				        	idcCargoEmprego = line.indexOf("CARGO/EMPREGO");
				        	idcClasse = line.indexOf("CLASSE");
				        	idcReferencia = line.indexOf("REF/PADRÃO/NÍVEL");
				        	idcFuncao = line.indexOf("FUNÇÃO");
				        	line = br.readLine();
				        	cargoEmprego = line.substring(idcCargoEmprego, idcClasse).trim();
				        	classe = line.substring(idcClasse, idcReferencia).trim();
				        	referencia = line.substring(idcReferencia, idcFuncao).trim();
				        	funcao = line.substring(idcFuncao).trim();
				        	
				        	//System.out.println(cargoEmprego + " " + classe + " " + referencia + " " + funcao);
				        }
				        
				        if(line != null && line.contains("DEP. S.F.")) {
				        	idcDEPSF = line.indexOf("DEP. S.F.");
				        	idcDependente = line.indexOf("DEPENDENTE IR");
				        	idcATS = line.indexOf("A.T.S.(%)");
				        	idcBanco = line.indexOf("BANCO");
				        	idcAgencia = line.indexOf("AGÊNCIA");
				        	idcConta = line.indexOf("CONTA");
				        	idcAnoReferencia = line.indexOf("ANO REFERÊNCIA");				        	
				        	line = br.readLine();
				        	depSF = line.substring(idcDEPSF, idcDependente).trim();
				        	dependenteIR = line.substring(idcDependente, idcATS).trim();
				        	ats = line.substring(idcATS, idcBanco).trim();
				        	banco = line.substring(idcBanco,idcAgencia).trim();
				        	agencia = line.substring(idcAgencia,idcConta).trim();
				        	conta = line.substring(idcConta,idcAnoReferencia).trim();
				        	anoReferencia = Integer.parseInt(line.substring(idcAnoReferencia).trim());
				        	
				        	//System.out.println(depSF + " " + dependenteIR + " " + ats + " " + banco + " " + agencia + " " + conta + " " + anoReferencia);
				        }
				        
				        if(line != null && line.contains("DISCRIMINAÇÃO") && line.contains("JAN")) {
				        	while(true) {
				        		line = br.readLine();
				        		if(line != null && line.contains("Ministério da Economia")) {
				        			break;
				        		}
				        		if(line != null && !line.trim().isEmpty() && !line.contains("Data de emissão")) {
				        			String discriminacao = line.substring(13,67).trim();
						        	String temp = "";

						        	double jan = 0;
						        	double fev = 0;
						        	double mar = 0;
						        	double abr = 0;
						        	double mai = 0;
						        	double jun = 0;
						        	
						        	temp = line.substring(67,78).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		jan = Double.parseDouble(temp);
						        	
						        	temp = line.substring(78,91).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		fev = Double.parseDouble(temp);
						        	
						        	temp = line.substring(91,103).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		mar = Double.parseDouble(temp);
						        	
						        	temp = line.substring(103,115).trim().replace(".", "").replace(",", ".");    	
						        	if(!temp.isEmpty())
						        		abr = Double.parseDouble(temp);
						        	
						        	temp = line.substring(115,128).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		mai = Double.parseDouble(temp);
						        	
						        	temp = line.substring(128,141).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		jun = Double.parseDouble(temp);

						        	System.out.println(discriminacao + " " + jan + " " + fev + " " + mar + " " + abr + " " + mai + " " + jun );
						        	
							        Exequente exequente = new Exequente(nomeServidor, cpf);
									
							        if(!exequentes.contains(exequente)) {
							        	exequentes.add(exequente);
							        	System.out.println(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath());
							        	//writer.append(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath()+"\n");
							        }
									
									int indice = exequentes.indexOf(exequente);
							        exequente = exequentes.get(indice);
					        		exequente.setNome(nomeServidor);
							        exequente.atualizarMapaVerbasPrimeiroSemestre(matriculaSIAPE, discriminacao, anoReferencia, jan, fev, mar, abr, mai, jun);
							        //exequente.atualizarMapaPeriodos(matriculaSIAPE, admissao, saida);
							        exequentes.set(indice, exequente);
				        		}
				        	}
				        }

				        
				        if(line != null && line.contains("DISCRIMINAÇÃO") && line.contains("JUL")) {
				        	ArrayList<String> buffer = new ArrayList<String>();
				        	int maxSize = 0;
				        	while(true) {
				        		line = br.readLine();
				        		if(line != null && line.contains("Ministério da Economia")) {
				        			break;
				        		}
				        		if(line != null && !line.trim().isEmpty() && !line.contains("Data de emissão")) {
				        	/*		buffer.add(line);
				        			if(line.length() > maxSize) {
				        				maxSize = line.length();
				        			}
				        		}
				        	}
				        	
				        	int[] temp = new int[maxSize];
			        		for (int i = 0; i < maxSize; i++) {
		        				temp[i] = 0;
							}
			        		
				        	for(String s : buffer) {
				        		for (int i = 0; i < s.length(); i++) {
				        			if(s.charAt(i) == ' ') {
				        				temp[i] += 0;
				        			} else {
				        				temp[i] += 1;				        				
				        			}
								}
				        	}
				        	
			        		for (int i = 0; i < maxSize - 3; i++) {
			        			if(temp[i] == 0 && temp[i+1] == 0 && temp[i+2] == 0 && temp[i+3] != 0) {
			        				System.out.print(i+2 + " ");
			        			}
			        		//	while(temp[i] == 0) {
			        		//		i++;
			        		//	}
		        			//	System.out.print(temp[i]);
		        			//	indices
							}
			        		System.out.println();
			        		*/
			        		

				        			String discriminacao = line.substring(13,63).trim();
						        	String temp = "";

						        	double jul = 0;
						        	double ago = 0;
						        	double set = 0;
						        	double out = 0;
						        	double nov = 0;
						        	double dez = 0;
						        	
						        	temp = line.substring(67,78).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		jul = Double.parseDouble(temp);
						        	
						        	temp = line.substring(78,91).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		ago = Double.parseDouble(temp);
						        	
						        	temp = line.substring(91,103).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		set = Double.parseDouble(temp);
						        	
						        	temp = line.substring(103,115).trim().replace(".", "").replace(",", ".");    	
						        	if(!temp.isEmpty())
						        		out = Double.parseDouble(temp);
						        	
						        	temp = line.substring(115,128).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		nov = Double.parseDouble(temp);
						        	
						        	temp = line.substring(128,141).trim().replace(".", "").replace(",", ".");
						        	if(!temp.isEmpty())
						        		dez = Double.parseDouble(temp);

						        	System.out.println(discriminacao + " " + jul + " " + ago + " " + set + " " + out + " " + nov + " " + dez );
						        	
							        Exequente exequente = new Exequente(nomeServidor, cpf);
									
							        if(!exequentes.contains(exequente)) {
							        	exequentes.add(exequente);
							        	System.out.println(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath());
							        	//writer.append(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath()+"\n");
							        }
									
									int indice = exequentes.indexOf(exequente);
							        exequente = exequentes.get(indice);
					        		exequente.setNome(nomeServidor);
							        exequente.atualizarMapaVerbasSegundoSemestre(matriculaSIAPE, discriminacao, anoReferencia, jul, ago, set, out, nov, dez);
							        //exequente.atualizarMapaPeriodos(matriculaSIAPE, admissao, saida);
							        exequentes.set(indice, exequente);
				        		}
				        	}
				        }
				    }

				} finally {
				    br.close();
				}
			}
		}
//		writer.close();
		
//		System.out.println(listaCPFs.size());
		System.out.println(exequentes.size());
	    long tempoFinal = System.currentTimeMillis();
	    double tempoTotal = tempoFinal - tempoInicial;
	    System.out.println(tempoTotal/60);

        for (Exequente exequente : exequentes) {
        	System.out.println(exequente.getVerbas());
			Exequente.salvar(diretorioFichasFinanceirasSER, exequente);
		}
	}
	
	/*
	public static void gerarBancoArquivos() throws Exception {
		
		File originDirectory = new File("D:\\Facilita\\FICHAS FINANCEIRAS_2009a2022maio\\Fichas\\txt\\");
		if (!originDirectory.isDirectory()) {
			originDirectory.mkdirs();
		}
		
	    long tempoInicial = System.currentTimeMillis();
	    
		File[] arquivosOrigem = originDirectory.listFiles();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("./fichas_financeiras/bd_arquivos.txt", true));
		
		HashSet<String> listaCPFs = new HashSet<String>();
		
		for (File arquivoOrigem : arquivosOrigem) {
			
			if(arquivoOrigem.getName().contains(".txt")) {

				
				BufferedReader br = new BufferedReader(new FileReader(arquivoOrigem));
				try {
				    String line = br.readLine();

				    String matricula = "";
		        	String nome = "";
		        	String cpf = "";
		        	String admissao = "";
        			String saida = "";
        			int ano = -1;
        			
			        int idcCPF = -1;
			        int idcMatricula = -1;
			        int idcNome = -1; 
			        int idcAdmissao = -1; 
			        int idcSaida = -1;
			        int idcAno = -1;
			        
			        boolean extrair = false;
			        
				    while (line != null) {
				        line = br.readLine();

				        if(line != null && line.contains("ANO:")) {
				        	idcAno = line.indexOf("ANO:");
				        	ano = Integer.parseInt(line.substring(idcAno+4).trim());
				        }

				        if(line != null && line.contains("NOME:") && line.contains("CPF:")) {
				        	idcNome = line.indexOf("NOME:");
				        	idcCPF = line.indexOf("CPF:");
				        	nome = line.substring(idcNome+5, idcCPF).trim();
				        	cpf = line.substring(idcCPF+4).trim();
				        }

				        if(line != null && line.contains("SAÍDA:")) {
				        	idcMatricula = line.indexOf("MATRÍCULA:");
				        	idcAdmissao = line.indexOf("ADMISSÃO:");
				        	idcSaida = line.indexOf("SAÍDA:");
				        	matricula = line.substring(idcMatricula+10, idcAdmissao).trim();
				        	admissao = line.substring(idcAdmissao+9, idcSaida).trim();
				        	saida = line.substring(idcSaida+6).trim();
				        }
				        
				        
				        if(line != null && line.contains("FOLHA:")) {
				        	while(true) {
						        line = br.readLine();
						       
						        if(line.isEmpty()) {
						        	continue;
						        }
						        if(line.contains("Impresso em")) {
						        	break;
						        }
						        
						        int cod = Integer.parseInt(line.substring(0, 7).trim());
						        String verba = line.substring(8, 69).trim();
						        String valores = line.substring(69);
						        StringTokenizer tokenizer = new StringTokenizer(valores, " ");
						        double jan = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double fev = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double mar = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double abr = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double mai = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double jun = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double jul = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double ago = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double set = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double out = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double nov = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double dez = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        double dec = Double.parseDouble(tokenizer.nextToken().replace(".", "").replace(",", "."));
						        
						        Exequente exequente = new Exequente(nome, cpf);
								
						        if(!exequentes.contains(exequente)) {
						        	exequentes.add(exequente);
						        	//System.out.println(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath());
						        	writer.append(exequente.getCPF()+"\t"+arquivoOrigem.getAbsolutePath()+"\n");
						        }
								
								int indice = exequentes.indexOf(exequente);
						        exequente = exequentes.get(indice);
				        		exequente.setNome(nome);
						        exequente.atualizarMapaVerbas(matricula, cod, ano, jan, fev, mar, abr, mai, jun, jul, ago, set, out, nov, dez, dec);
						        exequente.atualizarMapaPeriodos(matricula, admissao, saida);
						        exequentes.set(indice, exequente);
				        	}
				        	
				        }

			        }

				} finally {
				    br.close();
				}
			}
		}

		writer.close();

	}
	*/
	public static void main(String[] args) {
		
//		Exequente e = Exequente.carregar("fichas_financeiras\\ser\\ABELARDO_TEIXEIRA_BALLUZ_27263207320.ser");
//		System.out.println(e.getPeriodos());
		
		System.out.println("oi");
		
/*		try {
			extrairTexto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/		
		try {
			serializar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
