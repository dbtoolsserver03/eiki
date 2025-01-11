package jp.co.eiki;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import jp.co.eiki.common.MyDbConnection;

public class CsvToMStation {

	public static void main(String[] args) {
		String csvFilePath = "res/csv/input/20250111/station20240426free.csv";

		// SQL
		String insertSQL = "INSERT INTO m_station (" +
				"station_cd, station_g_cd, station_name, station_name_k, station_name_r, " +
				" line_cd, pref_cd, " +
				"post, address, lon, lat, open_ymd, close_ymd, e_status, e_sort) " +
				"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (
				Connection conn = MyDbConnection.getConnection();
				FileReader reader = new FileReader(csvFilePath);
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
				PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {
			// CSVファイルをループする
			for (CSVRecord record : csvParser) {
				preparedStatement.setInt(1, Integer.parseInt(record.get("station_cd")));
				preparedStatement.setInt(2, Integer.parseInt(record.get("station_g_cd")));
				preparedStatement.setString(3, record.get("station_name"));
				preparedStatement.setString(4, record.get("station_name_k"));
				preparedStatement.setString(5, record.get("station_name_r"));
				preparedStatement.setInt(6, Integer.parseInt(record.get("line_cd")));
				preparedStatement.setShort(7, Short.parseShort(record.get("pref_cd")));
				preparedStatement.setString(8, record.get("post"));
				preparedStatement.setString(9, record.get("address"));
				preparedStatement.setDouble(10, Double.parseDouble(record.get("lon")));
				preparedStatement.setDouble(11, Double.parseDouble(record.get("lat")));
				preparedStatement.setDate(12,
						record.get("open_ymd") == null || "0000-00-00".equals(record.get("open_ymd")) ? null
								: java.sql.Date.valueOf(record.get("open_ymd")));
				preparedStatement.setDate(13,
						record.get("close_ymd") == null || "0000-00-00".equals(record.get("close_ymd")) ? null
								: java.sql.Date.valueOf(record.get("close_ymd")));
				preparedStatement.setShort(14, Short.parseShort(record.get("e_status")));
				preparedStatement.setInt(15, Integer.parseInt(record.get("e_sort")));

				preparedStatement.addBatch();
			}

			// 执行批处理
			preparedStatement.executeBatch();
			System.out.println("CSV m_station OK");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
