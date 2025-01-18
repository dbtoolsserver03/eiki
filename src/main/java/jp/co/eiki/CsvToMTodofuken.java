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

		// SQL文を準備する
		String insertSQL = "INSERT INTO m_todofuken (pref_cd, pref_name) VALUES (?, ?)";

		try (
				// データベース接続を取得
				Connection conn = MyDbConnection.getConnection();
				// CSVファイルを読み込むためのFileReaderを作成
				FileReader reader = new FileReader(csvFilePath);
				// CSVParserを初期化し、ヘッダー行をスキップする設定を適用
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader());
				// SQL文を実行するためのPreparedStatementを準備
				PreparedStatement preparedStatement = conn.prepareStatement(insertSQL)) {

			// CSVファイルの各レコードをループ処理
			for (CSVRecord record : csvParser) {
				// CSVの"pref_cd"列から値を取得し、int型に変換
				int prefCd = Integer.parseInt(record.get("pref_cd"));
				// CSVの"pref_name"列から値を取得
				String prefName = record.get("pref_name");

				// SQL文の1番目のプレースホルダーにprefCdを設定
				preparedStatement.setInt(1, prefCd);
				// SQL文の2番目のプレースホルダーにprefNameを設定
				preparedStatement.setString(2, prefName);

				// バッチ処理にSQLを追加
				preparedStatement.addBatch();
			}

			// バッチ処理を実行し、データベースに反映
			preparedStatement.executeBatch();
			System.out.println("CSVからm_todofukenへのデータ登録が完了しました。");

		} catch (Exception e) {
			// 例外が発生した場合にスタックトレースを出力
			e.printStackTrace();
		}
	}
}
