/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Question;
import context.DBContext;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author quoct
 */
public class QuestionDAO extends DBContext {

    static Connection conn;
    static PreparedStatement ps;
    static ResultSet rs;

    public static String readFile(String excelFilePath, int collectionId) {
        String query = "INSERT INTO BankQuestion (Detail, AnswerA, AnswerB, AnswerC, AnswerD, TrueAnswer)\n"
                + "VALUES (?, ?, ?, ?, ?, ?);"
                + "insert into CollectionConnect (CollectionID, BankQuestionID) values (?, SCOPE_IDENTITY());";
        String errorRows = "";
        try ( FileInputStream fis = new FileInputStream(excelFilePath);  Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            for (Row row : sheet) {
                String detail = null;
                String answerA = null;
                String answerB = null;
                String answerC = null;
                String answerD = null;
                String trueAnswer = null;

                boolean isEmptyCell = false;
                boolean isValidColumnF = true;

                for (int i = 0; i <= 5; i++) { // Duyệt từ cột A đến F
                    Cell cell = row.getCell(i);
                    if (cell == null || cell.getCellTypeEnum() == CellType.BLANK) {
                        errorRows = "Row ";
                        isEmptyCell = true;
                    }

                    if (i == 5) { // Kiểm tra cột F
                        String value = cell.getStringCellValue();
                        if (!value.toUpperCase().equals("A") && !value.toUpperCase().equals("B")
                                && !value.toUpperCase().equals("C") && !value.toUpperCase().equals("D")) {
                            errorRows = "Row ";
                            isValidColumnF = false;
                        }
                    }
                }

                if (isEmptyCell || !isValidColumnF) {
                    continue; // Chuyển sang hàng tiếp theo nếu có ô trống hoặc cột F không hợp lệ
                }

                // Nếu hàng hợp lệ, đọc và xử lý dữ liệu ở đây
                for (Cell cell : row) {
                    if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue() + "\t");
                    } else if (cell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(cell.getStringCellValue() + "\t");
                    }
                    // Thêm xử lý cho các kiểu dữ liệu khác nếu cần
                }
                ps.setString(1, row.getCell(0).getStringCellValue());
                ps.setString(2, row.getCell(1).getStringCellValue());
                ps.setString(3, row.getCell(2).getStringCellValue());
                ps.setString(4, row.getCell(3).getStringCellValue());
                ps.setString(5, row.getCell(4).getStringCellValue());
                ps.setString(6, row.getCell(5).getStringCellValue());
                ps.setInt(7, collectionId);
                ps.executeUpdate();
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (errorRows != null) {
            return errorRows;
        }
        return "";
    }
    
    public static void CreateQuestion(String Detail, String AnswerA, String AnswerB, String AnswerC, String AnswerD, String TrueAnswer, int CollectionId) {
        try {
            String query = "INSERT INTO Question (Detail, AnswerA, AnswerB, AnswerC, AnswerD, TrueAnswer)\n"
                    + "VALUES (?, ?, ?, ?, ?, ?) where CollectionId = ?;";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, Detail);
            ps.setString(2, AnswerA);
            ps.setString(3, AnswerB);
            ps.setString(4, AnswerC);
            ps.setString(5, AnswerD);
            ps.setString(6, TrueAnswer);
            ps.setInt(7, CollectionId);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    public static void main(String[] args) {
        
    }
}
