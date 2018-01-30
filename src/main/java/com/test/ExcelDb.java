package com.test;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author moksh
 *
 */
public class ExcelDb
{

   private String fileName = null;

   private final String XLS = "xls";

   private final String XLSX = "xlsx";

   public static void main(String[] args)
   {
      String fileName = "Book.xls";
      ExcelDb.getInstance(fileName).get2DArray();
   }

   private ExcelDb(String fileName)
   {
      this.fileName = fileName;
   }

   public static ExcelDb getInstance(String fileName)
   {
      return new ExcelDb(fileName);
   }

   public String[][] get2DArray()
   {
      Row row;
      Cell cell;
      String[][] value = null;
      try
      {
         FileInputStream inputStream = new FileInputStream(fileName);

         Workbook workbook = getWorkBookInstance(inputStream);

         // get sheet number
         int sheetCn = workbook.getNumberOfSheets();

         for (int cn = 0; cn < sheetCn; cn++)
         {

            // get 0th sheet data
            Sheet sheet = workbook.getSheetAt(0);

            // get number of rows from sheet
            int rows = sheet.getPhysicalNumberOfRows();

            // get number of cell from row
            int cells = sheet.getRow(cn).getPhysicalNumberOfCells();

            value = new String[rows][cells];

            for (int r = 0; r < rows; r++)
            {
               row = sheet.getRow(r); // bring row
               if (row != null)
               {
                  for (int c = 0; c < cells; c++)
                  {
                     cell = row.getCell(c);
                     if (cell != null)
                     {

                        switch (cell.getCellType())
                        {

                        case Cell.CELL_TYPE_FORMULA:
                           // do nothing
                           System.out.println("WARNING: Function Cell Found.");
                           break;

                        case Cell.CELL_TYPE_NUMERIC:
                           value[r][c] = "" + cell.getNumericCellValue();
                           break;

                        case Cell.CELL_TYPE_STRING:
                           value[r][c] = "" + cell.getStringCellValue();
                           break;

                        case Cell.CELL_TYPE_BLANK:
                           value[r][c] = "[BLANK]";
                           System.out.println("WARNING: Blank Cell Found.");
                           break;

                        case Cell.CELL_TYPE_ERROR:
                           // do nothing
                           break;
                        default:
                        }
                        // System.out.print(value[r][c]);

                     }
                     else
                     {
                        System.out.print("[null]\t");
                     }
                  } // for(c)
                  System.out.print("\n");
               }
            } // for(r)
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return value;
   }

   private Workbook getWorkBookInstance(FileInputStream inputStream) throws IOException
   {
      String ext = FilenameUtils.getExtension(fileName);
      Workbook workbook = null;
      if (XLS.equals(ext))
      {
         workbook = new HSSFWorkbook(inputStream);
      }
      else if (XLSX.equals(ext))
      {
         workbook = new XSSFWorkbook(inputStream);
      }
      return workbook;
   }

}
