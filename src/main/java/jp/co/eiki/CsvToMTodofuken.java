package jp.co.eiki;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import jp.co.eiki.common.MyDbConnection;

public class CsvToMTodofuken {

	public static void main(String[] args) {
		String csvFilePath = "res/csv/input/20250111/todofuken.csv";

		// SQL
        String insertSQL = "INSERT INTO m_todofuken (pref_cd, pref_name) VALUES (?, ?)";

		try (
				Connection conn = MyDbConnection.getConnection();
				FileReader reader = new FileReader(csvFilePath);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
				PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			// CSVファイルをループする
			for (CSVRecord record : csvParser) {
	             int prefCd = Integer.parseInt(record.get("pref_cd"));
	                String prefName = record.get("pref_name");

	                preparedStatement.setInt(1, prefCd);
	                preparedStatement.setString(2, prefName);

				preparedStatement.addBatch();
			}

			// 执行批处理
			preparedStatement.executeBatch();
			System.out.println("CSV m_todofuken OK");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
