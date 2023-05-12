package br.dssm.calculos.dados.extrator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BuiltinFormats;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;

public class Totalizador217 {

	private static final String diretorioOrigem = "calculos_implantacoes/xlsx/";
	private static final String diretorioDestino = "calculos_implantacoes/";
	private static String arquivoModeloResumoGeral = "./modelos/modelo_resumo_geral.xlsx";
	
	public static void excluir() throws IOException, InvalidFormatException {
		
		File destinationDirectory = new File(diretorioOrigem);
		if (!destinationDirectory.isDirectory()) {
			System.out.println("O diretório de fichas financeiras não foi localizado.");
			destinationDirectory.mkdirs();
		}
		
		File[] arquivos = destinationDirectory.listFiles();

	    FileInputStream fis2 = new FileInputStream(arquivoModeloResumoGeral);
	    Workbook wb2 = WorkbookFactory.create(fis2);
	    Sheet sheet2 = wb2.getSheetAt(0);
	    Row row2 = null;
	    Cell cell2 = null;

	    Font font1 = null;
		font1 = wb2.createFont();
		font1.setFontHeightInPoints((short) 10);
		font1.setBoldweight(Font.BOLDWEIGHT_BOLD);

	    Font font2 = null;
		font2 = wb2.createFont();
		font2.setFontHeightInPoints((short) 10);
		font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);

	    CellStyle style2 = null;
	    style2 = wb2.createCellStyle();
	    style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	    style2.setFont(font2);
	    style2.setWrapText(true);
	    
	    DecimalFormat df = new DecimalFormat("####0,00");
	    
	    int indice2 = 12;

		for (File arquivoOrigem : arquivos) {
	
			if (arquivoOrigem.isFile()) {

				if(arquivoOrigem.getCanonicalPath().contains("~$")) {
					arquivoOrigem.delete();
					continue;
				}

			    FileInputStream fis1 = new FileInputStream(arquivoOrigem);
			    
			    Workbook wb1 = WorkbookFactory.create(fis1);
			    Sheet sheet1 = wb1.getSheetAt(1);
			    
			    FormulaEvaluator evaluator1 = null;
			    
			    CellReference cellReference1 = null; 
			    Row row1 = null;
			    Cell cell1 = null; 
			    CellValue cellValue1 = null;

			    cellReference1 = new CellReference("$B$8"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    String s1 = cell1.getStringCellValue();

			    cellReference1 = new CellReference("$B$7"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    String s2 = cell1.getStringCellValue();

			    cellReference1 = new CellReference("$B$6"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    String s3 = cell1.getStringCellValue();
			    
			    evaluator1 = wb1.getCreationHelper().createFormulaEvaluator();
			    cellReference1 = new CellReference("$H$19"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    cellValue1 = evaluator1.evaluate(cell1);
			    double v1 = cellValue1.getNumberValue();
			    
			    evaluator1 = wb1.getCreationHelper().createFormulaEvaluator();
			    cellReference1 = new CellReference("$H$22"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    cellValue1 = evaluator1.evaluate(cell1);
			    double v2 = cellValue1.getNumberValue();
			    
			    evaluator1 = wb1.getCreationHelper().createFormulaEvaluator();
			    cellReference1 = new CellReference("$H$20"); 
			    row1 = sheet1.getRow(cellReference1.getRow());
			    cell1 = row1.getCell(cellReference1.getCol()); 
			    cellValue1 = evaluator1.evaluate(cell1);
			    double v3 = cellValue1.getNumberValue();
			    
	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(0);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(0);
	    	    cell2.setCellValue(s1);
	    		cell2.setCellStyle(style2);

	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(1);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(1);
	    	    cell2.setCellValue(s2);
	    		cell2.setCellStyle(style2);
	    		
	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(2);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(2);
	    	    cell2.setCellValue(s3);
	    		cell2.setCellStyle(style2);

	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(3);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(3);
	    	    cell2.setCellValue(round(v1,2));
	    	    style2.setDataFormat(wb2.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
	    		cell2.setCellStyle(style2);
	    	    
	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(4);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(4);
	    	    cell2.setCellValue(round(v2,2));
	    	    style2.setDataFormat(wb2.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
	    	    cell2.setCellStyle(style2);
	    	    
	    		row2 = sheet2.getRow(indice2);
	    		if(row2 == null)
	    			sheet2.createRow(indice2);
	    			row2 = sheet2.getRow(indice2);
	    	    cell2 = row2.getCell(5);
	    	    if (cell2 == null)
	    	        cell2 = row2.createCell(5);
	    	    cell2.setCellValue(round(v3,2));
	    	    style2.setDataFormat(wb2.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
	    	    cell2.setCellStyle(style2);
	    	    
	    	    row2.setHeight((short)-1);
	    	    
	    		indice2++;
	    		

			    System.out.println(indice2);
			} else {
				System.out.println("O arquivo original não existe no diretório de destino.");
			}
		}

		row2 = sheet2.getRow(indice2);
		if(row2 == null)
			sheet2.createRow(indice2);
			sheet2.addMergedRegion(CellRangeAddress.valueOf(String.format("A%d:C%d", indice2+1, indice2+1)));		
			row2 = sheet2.getRow(indice2);

		    cell2 = row2.getCell(0);
		    if (cell2 == null)
		        cell2 = row2.createCell(0);
		    cell2.setCellType(Cell.CELL_TYPE_NUMERIC);
		    cell2.setCellValue("TOTAL");
		    style2 = wb2.createCellStyle();
		    style2.setFont(font1);
		    style2.setAlignment(CellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style2.setBorderTop(CellStyle.BORDER_MEDIUM);
			style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
			style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    cell2.setCellStyle(style2);

		    cell2 = row2.getCell(1);
		    if (cell2 == null)
		        cell2 = row2.createCell(1);
		    style2 = wb2.createCellStyle();
			style2.setBorderTop(CellStyle.BORDER_MEDIUM);
			style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
			style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    cell2.setCellStyle(style2);
		    
		    cell2 = row2.getCell(2);
		    if (cell2 == null)
		        cell2 = row2.createCell(2);
		    style2 = wb2.createCellStyle();
			style2.setBorderTop(CellStyle.BORDER_MEDIUM);
			style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
			style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
			style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		    cell2.setCellStyle(style2);

	    cell2 = row2.getCell(3);
	    if (cell2 == null)
	        cell2 = row2.createCell(3);
	    cell2.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell2.setCellFormula("SUM(D"+13+":D"+indice2+")");
	    style2 = wb2.createCellStyle();
	    style2.setFont(font1);
	    style2.setDataFormat(wb2.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
		style2.setBorderTop(CellStyle.BORDER_MEDIUM);
		style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cell2.setCellStyle(style2);
	    
	    cell2 = row2.getCell(4);
	    if (cell2 == null)
	        cell2 = row2.createCell(4);
	    cell2.setCellType(Cell.CELL_TYPE_FORMULA);
	    cell2.setCellFormula("SUM(E"+13+":E"+indice2+")");
	    style2 = wb2.createCellStyle();
	    style2.setFont(font1);
	    style2.setDataFormat(wb2.createDataFormat().getFormat("_-\"R$\"* #,##0.00_-;\\-\"R$\"* #,##0.00_-;_-\"R$\"* \"-\"??_-;_-@_-"));
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);        	    
		style2.setBorderTop(CellStyle.BORDER_MEDIUM);
		style2.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style2.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style2.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		cell2.setCellStyle(style2);

	    wb2.setForceFormulaRecalculation(true);

	    FileOutputStream fileOut = new FileOutputStream(diretorioDestino + "resumo_geral.xlsx");
	    wb2.write(fileOut);
	    fileOut.close();
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
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
			Totalizador217.excluir();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
