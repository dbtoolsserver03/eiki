package jp.co.eiki;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import jp.co.eiki.common.MyDbConnection;

public class CSVToMCompany {

	public static void main(String[] args) {
		String csvFilePath = "res/csv/input/20250111/company20240328.csv";

		// SQL
        String insertSQL = "INSERT INTO m_company (" +
                "company_cd, rr_cd, company_name, company_name_k, company_name_h, " +
                "company_name_r, company_url, company_type, e_status, e_sort) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
        		
                Connection conn = MyDbConnection.getConnection();
                FileReader reader = new FileReader(csvFilePath);
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
                PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)
        ) {
            // 遍历CSV文件记录
            for (CSVRecord record : csvParser) {
                preparedStatement.setInt(1, Integer.parseInt(record.get("company_cd")));
                preparedStatement.setShort(2, Short.parseShort(record.get("rr_cd")));
                preparedStatement.setString(3, record.get("company_name"));
                preparedStatement.setString(4, record.get("company_name_k"));
                preparedStatement.setString(5, record.get("company_name_h"));
                preparedStatement.setString(6, record.get("company_name_r"));
                preparedStatement.setString(7, record.get("company_url"));
                preparedStatement.setShort(8, Short.parseShort(record.get("company_type")));
                preparedStatement.setShort(9, Short.parseShort(record.get("e_status")));
                preparedStatement.setInt(10, Integer.parseInt(record.get("e_sort")));

                // 添加到批处理
                preparedStatement.addBatch();
            }

            // 执行批处理
            preparedStatement.executeBatch();
			System.out.println("m_company　CSV OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
