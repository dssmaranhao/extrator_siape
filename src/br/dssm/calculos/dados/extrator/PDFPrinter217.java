package br.dssm.calculos.dados.extrator;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class PDFPrinter217 {

	private static final String arquivoExecutavel = "exe/OfficeToPDF.exe"; // executável
	private static final String diretorioOrigem = "calculos/xlsx/";
	private static final String diretorioDestino = "calculos/pdf/";
	
	public static void imprimir() throws IOException, InvalidFormatException {
		
		File destinationDirectory = new File(diretorioOrigem);
		if (!destinationDirectory.isDirectory()) {
			System.out.println("O diretório de fichas financeiras não foi localizado.");
			destinationDirectory.mkdirs();
		}
		
		File[] arquivos = destinationDirectory.listFiles();
		
		for (File arquivoOrigem : arquivos) {
	
			if (arquivoOrigem.isFile()) {

				long tempoInicial = System.currentTimeMillis();
			    FileInputStream fis = new FileInputStream(arquivoOrigem);
			    
			    Workbook wb = WorkbookFactory.create(fis);
			    wb.setSheetHidden(0, Workbook.SHEET_STATE_VERY_HIDDEN);
			    wb.setSheetHidden(1, Workbook.SHEET_STATE_VISIBLE);
			    wb.setSheetHidden(2, Workbook.SHEET_STATE_VERY_HIDDEN);
			    wb.setSheetHidden(3, Workbook.SHEET_STATE_VERY_HIDDEN);
			    
			    FileOutputStream fos =new FileOutputStream(arquivoOrigem);  
			    wb.write(fos);
			    wb = null;
			    fis.close();
			    fos.close();
			    
				System.out.println(arquivoOrigem.getName());
				
				File arquivoDestino = new File(diretorioDestino + "/" + stripExtension(arquivoOrigem.getName()) + ".pdf");
				
				if (!arquivoDestino.isFile()) {
					
					ArrayList<String> command = new ArrayList<String>();
					command.add(arquivoExecutavel);
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
				
			    fis = new FileInputStream(arquivoOrigem);
			    
			    wb = WorkbookFactory.create(fis);
			    wb.setSheetHidden(0, Workbook.SHEET_STATE_VISIBLE);
			    wb.setSheetHidden(1, Workbook.SHEET_STATE_VISIBLE);
			    wb.setSheetHidden(2, Workbook.SHEET_STATE_VISIBLE);
			    wb.setSheetHidden(3, Workbook.SHEET_STATE_VISIBLE);
			    
			    fos = new FileOutputStream(arquivoOrigem);  
			    wb.write(fos);
			    wb = null;
			    fis.close();
			    fos.close();
			    long tempoFinal = System.currentTimeMillis();
			    System.out.printf("%.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
			} else {
				System.out.println("O arquivo original não existe no diretório de destino.");
			}
		}
	}

    static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }
    
	public static void main(String[] args) {
		try {
			PDFPrinter217.imprimir();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
