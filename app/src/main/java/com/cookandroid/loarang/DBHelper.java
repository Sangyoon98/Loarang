package com.cookandroid.loarang;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;  // 데이터베이스 스키마를 변경하는 경우 데이터베이스 버전을 증가시켜야 합니다.
    public static final String DATABASE_NAME = "CharacterList.db"; // 데이터베이스 이름

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(FeedEntry.SQL_CREATE_ENTRIES); //테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(FeedEntry.SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "character"; //테이블 명
        public static final String COLUMN_NAME_IMAGE = "image"; //컬럼 명
        public static final String COLUMN_NAME_NAME = "name"; //컬럼 명
        public static final String COLUMN_NAME_CHARACTER_LEVEL = "character_level"; //컬럼 명
        public static final String COLUMN_NAME_CLASS = "class"; //컬럼 명
        public static final String COLUMN_NAME_ITEM_LEVEL = "item_level"; //컬럼 명
        public static final String COLUMN_NAME_SERVER = "server"; //컬럼 명

        //테이블 생성 쿼리
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        FeedEntry.COLUMN_NAME_IMAGE + " TEXT," +
                        FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                        FeedEntry.COLUMN_NAME_CHARACTER_LEVEL + " TEXT," +
                        FeedEntry.COLUMN_NAME_CLASS + " TEXT," +
                        FeedEntry.COLUMN_NAME_ITEM_LEVEL + " TEXT," +
                        FeedEntry.COLUMN_NAME_SERVER + " TEXT)";
        //DROP TABLE 테이블을 삭제 쿼리
        //IF EXISTS 절을 사용하면 삭제하려는 데이터베이스나 테이블이 존재하지 않아서 발생하는 에러를 미리 방지.
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }
}
