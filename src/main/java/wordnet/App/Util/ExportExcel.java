package wordnet.App.Util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import wordnet.App.Model.Output;
import wordnet.App.Model.Result;
import wordnet.Util.PathFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by chien on 02/04/2018.
 */
public class ExportExcel {

    private static final String[] title = {"IdSynset", "WordForm", "Translate", "Case", "Gloss"};

    public static void exportExcel(Output output) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cellStyle.setFont(font);
        createTile(sheet, cellStyle);
        int rowCount = 1;
        for (Map.Entry<String, List<Result>> stringListEntry : output.getMap().entrySet()) {
            for (Result result : stringListEntry.getValue()) {
                Row row = sheet.createRow(rowCount++);
                String[] strings = {
                        result.getIdSynset(),
                        result.getListWordEn().toString(),
                        result.getListWordVn().toString(),
                        result.getCaseName(),
                        result.getGloss()
                };
                for (int i = 0; i < strings.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(strings[i]);
                }
            }
        }
        exportToExcel(workbook);
    }

    private static void createTile(XSSFSheet sheet, CellStyle cellStyle) {
        Row row = sheet.createRow(0);
        int cellCount = 0;
        for (int i = 0; i < title.length; i++) {
            Cell cell = row.createCell(cellCount++);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(title[i]);
        }
    }

    private static void exportToExcel(Workbook workbook) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(PathFile.exportExcel);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
