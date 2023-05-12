package br.dssm.calculos.dados.analisador;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import javax.swing.text.MaskFormatter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellReference;

import br.dssm.calculos.dados.extrator.Exequente;
import br.dssm.calculos.dados.extrator.MatriculaPeriodo;
import br.dssm.calculos.dados.extrator.PDFPrinter217;
import br.dssm.calculos.dados.extrator.Totalizador217;

public class Analisador217 {

	//GDPGTAS
	private static String diretorioOrigem = "fichas_financeiras/ser/";
	private static String diretorioDestino = "calculos/xlsx/";
	private static String planilhasZeradas = "calculos/planilhas_vazias/relatorio.txt";	
	private static String arquivoModelo = "./modelos/modelo.xlsx";
	private static String dataInicial = "01/07/2006";
	private static String dataFinal = "31/12/2008";
	private static String dataCiencia = "17/08/2012";
	//private static String numeroProcesso = "0025886-28.2012.8.10.0001";
	private static ArrayList<String> bancoCPFs;
	private static HashMap<String,ArrayList<String>> bancoArquivos;
	
	public static void analisar2() throws Exception {
		 
		File originDirectory = new File(diretorioOrigem);
		if (!originDirectory.isDirectory()) {
			originDirectory.mkdirs();
		}
		
		File[] arquivosOrigem = originDirectory.listFiles();
		
		Exequente exequente = null;

	    double tempoTotal = 0f;
	    double tempoMedio = 0f;
	    int n = 1;
	    int totalArquivos = arquivosOrigem.length;  
	    
		for (File arquivoOrigem : arquivosOrigem) {
			
			if(arquivoOrigem.getName().contains(".ser")) {

			    long tempoInicial = System.currentTimeMillis();
			    
				exequente = Exequente.carregar(arquivoOrigem.getCanonicalPath());
				
				gerar(exequente);
				
			    long tempoFinal = System.currentTimeMillis();
			    tempoTotal += (tempoFinal - tempoInicial)/1000f;
		    
			    tempoMedio = tempoTotal/n;
			    double tempoEstimado = tempoMedio * (totalArquivos - n);
			    n++;
			    System.out.printf("%5.2f\n", tempoEstimado/3600);

			}
		}
	}
	
	public static void gerar(Exequente exequente) throws Exception {
	    InputStream inp = new FileInputStream(arquivoModelo);

	    Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = wb.getSheetAt(1);
	    
	    List<String> codigos = Arrays.asList("PROVENTO BASICO");
	    
	    ArrayList<String> listaMatriculas = exequente.getVerbas().listarMatriculasPorListaCodigos(codigos);
	    HashMap<String, Double> mapa_resumo = exequente.getVerbas().totalizarListaCodigos(codigos);
	    
	    String matriculas = "";
	    Collections.sort(listaMatriculas);

	    for (int i = 0; i < listaMatriculas.size(); i++) {
			matriculas += listaMatriculas.get(i);
			if(i != listaMatriculas.size() - 1) {
				matriculas += ", ";
			}
		}
	    
	    Row row = null;
	    Cell cell = null;
	    CellStyle style = null;
	    Font font = null;
	    Font font1 = null;
	    
	    int i = 25;

		font1 = wb.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(Font.BOLDWEIGHT_NORMAL);
	    
		DateFormat df = new SimpleDateFormat ("dd/MM/yyyy");
        Date dt3 = df.parse(dataCiencia);
        
        ArrayList<Competencia> competencias = Competencia.getListaCompetencias(dataInicial, dataFinal);

        for (Competencia competencia : competencias) {

        	row = sheet.getRow(i);
   		
    	    cell = row.getCell(0);
    	    if (cell == null)
    	        cell = row.createCell(0);
    	    cell.setCellValue(competencia.getMesAno());
    	    style = wb.createCellStyle();
    	    style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);
    	    
    		Double valor = mapa_resumo.get(competencia.getMesAno());
    		
    		if (valor == null) valor = Double.valueOf(0f);
    		
    		cell = row.getCell(1);
    	    if (cell == null)
    	        cell = row.createCell(1);
    	    cell.setCellValue(valor);
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
    	    style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);

    		cell = row.getCell(2);
    	    if (cell == null)
    	        cell = row.createCell(2);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
    	    cell.setCellFormula("B"+(i+1)+"*6.1%");
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
    	    style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);

    	    cell = row.getCell(3);
    	    if (cell == null)
    	        cell = row.createCell(3);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
    	    cell.setCellFormula("VLOOKUP(A"+(i+1)+",'Índices Atualização'!A:B,2,FALSE)");
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("0.00000000"));
    		style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);

    	    cell = row.getCell(4);
    	    if (cell == null)
    	        cell = row.createCell(4);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
    	    cell.setCellFormula("C"+(i+1)+"*D"+(i+1)); 
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
    		style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);
        	    
    	    cell = row.getCell(5);
    	    if (cell == null)
    	        cell = row.createCell(5);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
 
    	    if( competencia.getDate().after(dt3) ){
        	    cell.setCellFormula("VLOOKUP(A"+(i+1)+",'Índices Poupança'!A:D,4,FALSE)");
    	    	//cell.setCellFormula("IF(LEFT(A"+(i+1)+",3)=\"13º\","+"F"+i+",IF(F"+i+"-0.5%>0,F"+i+"-0.5%,0))");
    	    } else {
    	    	cell.setCellFormula("$G$5");
    	    }
    	    
    	    style = wb.createCellStyle();
    		style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setDataFormat(wb.createDataFormat().getFormat("0.00%"));
    	    style.setFont(font1);
    	    cell.setCellStyle(style);
    	    
    	    cell = row.getCell(6);
    	    if (cell == null)
    	        cell = row.createCell(6);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
    	    cell.setCellFormula("E"+(i+1)+"*F"+(i+1)); 
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
    		style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);
    		cell.setCellStyle(style);
    	    
    	    cell = row.getCell(7);
    	    if (cell == null)
    	        cell = row.createCell(7);
    	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
    	    cell.setCellFormula("E"+(i+1)+"+G"+(i+1));
    	    style = wb.createCellStyle();
    	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
    		style.setAlignment(CellStyle.ALIGN_CENTER);
    		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    		style.setBorderBottom(CellStyle.BORDER_THIN);
    		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
    	    style.setFont(font1);    		
    	    cell.setCellStyle(style);

    	    i++;
		}

		row = sheet.getRow(i);
	
		font = wb.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);

	    cell = row.getCell(0);
	    if (cell == null)
	        cell = row.createCell(0);
	    cell.setCellValue("TOTAL");
	    style = wb.createCellStyle();
	    style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);

	    cell = row.getCell(1);
	    if (cell == null)
	        cell = row.createCell(1);
	    cell.setCellValue("");
	    style = wb.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);
	    
		cell = row.getCell(2);
	    if (cell == null)
	        cell = row.createCell(2);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("SUM(C"+23+":C"+i+")");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
	    style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);
	    
	    cell = row.getCell(3);
	    if (cell == null)
	        cell = row.createCell(3);
	    cell.setCellValue("");
	    style = wb.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);


	    cell = row.getCell(4);
	    if (cell == null)
	        cell = row.createCell(4);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("SUM(E"+26+":E"+i+")");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);
	    
	    cell = row.getCell(5);
	    if (cell == null)
	        cell = row.createCell(5);
	    cell.setCellValue("");
	    style = wb.createCellStyle();
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    cell.setCellStyle(style);

	    
	    cell = row.getCell(6);
	    if (cell == null)
	        cell = row.createCell(6);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("SUM(G"+26+":G"+i+")");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cell.setCellStyle(style);
	    
	    cell = row.getCell(7);
	    if (cell == null)
	        cell = row.createCell(7);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("SUM(H"+26+":H"+i+")");
	    style = wb.createCellStyle();
	    style.setFont(font);
	    style.setDataFormat(wb.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cell.setCellStyle(style);

		row = sheet.getRow(5);
	    cell = row.getCell(1);
	    if (cell == null)
	        cell = row.createCell(1);
	    cell.setCellValue(exequente.getNome());
	    
		row = sheet.getRow(6);
	    cell = row.getCell(1);
	    if (cell == null)
	        cell = row.createCell(1);
	    cell.setCellValue(matriculas);
		
	    row = sheet.getRow(7);
	    cell = row.getCell(1);
	    if (cell == null)
	        cell = row.createCell(1);
	    cell.setCellValue(formatString(exequente.getCPF(), "###.###.###-##"));   
		
		row = sheet.getRow(16);
	    cell = row.getCell(7);
	    if (cell == null)
	        cell = row.createCell(7);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("E"+(i+1));

		row = sheet.getRow(17);
	    cell = row.getCell(7);
	    if (cell == null)
	        cell = row.createCell(7);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("G"+(i+1));
	    
		row = sheet.getRow(18);
	    cell = row.getCell(7);
	    if (cell == null)
	        cell = row.createCell(7);
	    cell.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell.setCellFormula("H"+(i+1));

	    wb.setForceFormulaRecalculation(true);

	    FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
	    
	    CellReference cellReference = new CellReference("$H$19"); 
	    row = sheet.getRow(cellReference.getRow());
	    cell = row.getCell(cellReference.getCol()); 
	    CellValue cellValue = evaluator.evaluate(cell);
	    
	    if( cellValue.getNumberValue() > 0.001 ) {
		    FileOutputStream fileOut = new FileOutputStream("./"+diretorioDestino+"/"+exequente.getNome().replace(" ", "_")+"_"+exequente.getCPF()+".xlsx");
		    wb.write(fileOut);
		    fileOut.close();
	    } else {
	    	FileWriter fw = new FileWriter(planilhasZeradas, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter out = new PrintWriter(bw);
	        out.println(exequente.getNome() + "\t" + exequente.getCPF());
	        out.close();
	        bw.close();
	        fw.close();
	    }
	}
	
    static String stripExtension (String str) {
        if (str == null) return null;

        int pos = str.lastIndexOf(".");

        if (pos == -1) return str;

        return str.substring(0, pos);
    }
    
    public static String formatString(String value, String pattern) {
        MaskFormatter mf;
        try {
            mf = new MaskFormatter(pattern);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(value);
        } catch (ParseException ex) {
            return value;
        }
    }
    
	public static void main(String[] args) throws Exception {
		try {
			Analisador217.analisar2();
			//Totalizador217.excluir();
			//PDFPrinter217.imprimir();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//  catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	}
}
