DROP DATABASE IF EXISTS `eiki`; 

CREATE DATABASE IF NOT EXISTS `eiki` CHARACTER 
SET
    utf8mb4; 

USE `eiki`; 

DROP TABLE IF EXISTS m_company; 

CREATE TABLE m_company( 
    company_cd INT (11) DEFAULT 0 COMMENT '事業者コード'
    , rr_cd SMALLINT (6) DEFAULT 0 NOT NULL COMMENT '鉄道コード'
    , company_name VARCHAR (256) DEFAULT '' NOT NULL COMMENT '事業者名(一般)'
    , company_name_k VARCHAR (256) DEFAULT NULL COMMENT '事業者名(一般・カナ)'
    , company_name_h VARCHAR (256) DEFAULT NULL COMMENT '事業者名(正式名称)'
    , company_name_r VARCHAR (256) DEFAULT NULL COMMENT '事業者名(略称)'
    , company_url VARCHAR (512) DEFAULT NULL COMMENT 'Webサイト'
    , company_type SMALLINT (6) DEFAULT 0 COMMENT '事業者区分'
    , e_status SMALLINT (6) DEFAULT 0 COMMENT '状態'
    , e_sort INT (11) DEFAULT 0 COMMENT '並び順'
    , PRIMARY KEY (`company_cd`)
) COMMENT = '事業者'; 

DROP TABLE IF EXISTS m_line; 

CREATE TABLE m_line( 
    line_cd INT (11) DEFAULT 0 COMMENT '路線コード'
    , company_cd INT (11) DEFAULT 0 NOT NULL COMMENT '事業者コード'
    , line_name VARCHAR (256) DEFAULT '' NOT NULL COMMENT '路線名称(一般)'
    , line_name_k VARCHAR (256) DEFAULT NULL COMMENT '路線名称(一般・カナ)'
    , line_name_h VARCHAR (256) DEFAULT NULL COMMENT '路線名称(正式名称)'
    , line_color_c VARCHAR (8) DEFAULT NULL COMMENT '路線カラー（コード）'
    , line_color_t VARCHAR (32) DEFAULT NULL COMMENT '路線カラー(名称）'
    , line_type SMALLINT (6) DEFAULT 0 COMMENT '路線区分'
    , lon double DEFAULT 0 COMMENT '路線表示時の中央経度'
    , lat double DEFAULT 0 COMMENT '路線表示時の中央緯度'
    , zoom SMALLINT (6) DEFAULT 0 COMMENT '路線表示時のGoogleMap倍率'
    , e_status SMALLINT (6) DEFAULT 0 COMMENT '状態'
    , e_sort INT (11) DEFAULT 0 COMMENT '並び順'
    , PRIMARY KEY (`line_cd`)
) COMMENT = '路線'; 

DROP TABLE IF EXISTS m_station; 

CREATE TABLE m_station( 
    station_cd INT (11) DEFAULT 0 NOT NULL COMMENT '駅コード'
    , station_g_cd INT (11) DEFAULT 0 NOT NULL COMMENT '駅グループコード'
    , station_name VARCHAR (256) DEFAULT '' NOT NULL COMMENT '駅名称'
    , station_name_k VARCHAR (256) DEFAULT NULL COMMENT '駅名称(カナ)'
    , station_name_r VARCHAR (256) DEFAULT NULL COMMENT '駅名称(ローマ字)'
    , station_name2 VARCHAR (256) DEFAULT NULL COMMENT 'station_name2'
    , station_number VARCHAR (256) DEFAULT NULL COMMENT 'station_number'
    , station_u VARCHAR (256) DEFAULT NULL COMMENT 'station_u'
    , line_cd INT (11) DEFAULT 0 NOT NULL COMMENT ''
    , pref_cd SMALLINT (6) DEFAULT 0 COMMENT ''
    , post VARCHAR (32) DEFAULT NULL COMMENT '駅郵便番号'
    , address VARCHAR (1024) DEFAULT NULL COMMENT '住所'
    , lon DOUBLE DEFAULT 0 COMMENT '経度'
    , lat DOUBLE DEFAULT 0 COMMENT '緯度'
    , open_ymd date DEFAULT NULL COMMENT '開業年月日'
    , close_ymd date DEFAULT NULL COMMENT '廃止年月日'
    , e_status SMALLINT (6) DEFAULT 0 COMMENT '状態'
    , e_sort INT (11) DEFAULT 0 COMMENT '並び順'
    , PRIMARY KEY (`station_cd`)
) COMMENT = '駅'; 

DROP TABLE IF EXISTS m_station_join; 

CREATE TABLE m_station_join( 
    line_cd INT (11) DEFAULT 0 COMMENT '路線コード'
    , station_cd1 INT (11) DEFAULT 0 COMMENT '駅コード１'
    , station_cd2 INT (11) DEFAULT 0 COMMENT '駅コード２'
    , PRIMARY KEY (line_cd, station_cd1, station_cd2)
) COMMENT = '接続駅'; 

DROP TABLE IF EXISTS m_station_group; 

CREATE TABLE m_station_group( 
    station_cd VARCHAR (7) COMMENT '駅コード'
    , station_g_cd INT (11) COMMENT '駅グループ'
    , rr_name INT (11) DEFAULT 0 COMMENT '鉄道名'
    , line_name INT (11) DEFAULT 0 COMMENT '鉄道線路名'
    , station_name INT (11) DEFAULT 0 COMMENT '駅名'
    , PRIMARY KEY (station_cd, station_g_cd)
) COMMENT = '駅グループコード仕様'; 

DROP TABLE IF EXISTS m_todofuken; 

CREATE TABLE m_todofuken( 
    pref_cd INT (2) DEFAULT 0 COMMENT '都道府県コード'
    , pref_name VARCHAR (32) DEFAULT 0 COMMENT '都道府県名'
    , PRIMARY KEY (pref_cd)
) COMMENT = '都道府県マスタ';
