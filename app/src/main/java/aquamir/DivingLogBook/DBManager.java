package aquamir.DivingLogBook;

/**
 * Created by aquamir on 2015-10-28.
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.CursorAdapter;

public class DBManager {
    // DB관련 상수 선언
    public static final String dbName = "DivingLogBook.db";
    private static final String userTable = "diver";
    private static final String logTable = "dive_log";
    public static final int dbVersion = 2;

    // DB관련 객체 선언
    private OpenHelper opener; // DB opener
    private SQLiteDatabase db; // DB controller

    // 부가적인 객체들
    private Context context;

    // 생성자
    public DBManager(Context context) {
        this.context = context;
        this.opener = new OpenHelper(context, dbName, null, dbVersion);
        db = opener.getWritableDatabase();
    }

    // Opener of DB and Table
    private class OpenHelper extends SQLiteOpenHelper {
        public OpenHelper(Context context, String dbName, CursorFactory factory,
                          int version) {
            super(context, dbName, factory, version);
        }

        // 생성된 DB가 없을 경우에 한번만 호출됨
        @Override
        public void onCreate(SQLiteDatabase arg0) {
            createUserDB(arg0);
            createLogDB(arg0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
            createUserDB(arg0);
            createLogDB(arg0);
        }
    }

    // Table 생성
    public void createLogDB() {
        // 외부에서 호출하는 경우 랩핑 처리
        createLogDB(db);
    }
    public void createLogDB(SQLiteDatabase db){
        String dropSql = "drop table if exists " + logTable;
        db.execSQL(dropSql);

        String createSql = "create table " + logTable + " ("
                + "id integer primary key autoincrement, "
                + "point text, "
                + "country text, "
                + "lat text, "
                + "lng text, "
                + "date text, "
                + "startTime text, "
                + "buddy text, "
                + "guide text, "
                + "instructor text, "
                + "startAir integer, "
                + "endAir integer, "
                + "visibility text, "
                + "airTemp text, "
                + "waterTemp text, "
                + "surfTime text, "
                + "startPG text, "
                + "endPG text, "
                + "diveTime text, "
                + "maxDepth text, "
                + "avgDepth text, "
                + "safeDepth integer, "
                + "safeTime integer, "
                + "weight text, "
                + "comment text, "
                + "diveType text, "
                + "equipment text)";
        db.execSQL(createSql);
    }
    public void createUserDB(SQLiteDatabase db){
        String dropSql = "drop table if exists " + userTable;
        db.execSQL(dropSql);

        String createSql = "create table " + userTable + " ("
                + "id integer primary key autoincrement, "
                + "name text, "
                + "licence text,"
                + "licenceNo text)";
        db.execSQL(createSql);
    }

    // 사용자정보 수정
    public Integer updateUser(ContentValues data, String[] params) {
        Integer result = db.update(userTable, data, "id = ?", params);
        return result;
    }
    public long insertUser(ContentValues data) {
        long result = db.insert(userTable, null, data);
        return result;
    }

    // 사용자정보 가져오기
    public ContentValues getUser() {
        // 만약 추가 사용자가 있다면 마지막 사용자 정보만 유효
        Cursor result = db.query(userTable, null, null, null, null, null, "id DESC");
        ContentValues info = new ContentValues();
        if (result.moveToFirst()) {
            info.put("id", result.getString(result.getColumnIndex("id")));
            info.put("name", result.getString(result.getColumnIndex("name")));
            info.put("licence", result.getString(result.getColumnIndex("licence")));
            info.put("licenceNo", result.getString(result.getColumnIndex("licenceNo")));
            return info;
        }
        result.close();
        return null;
    }

    // 다이빙로그 추가
    public long insertLog(ContentValues data) {
        long result = db.insert(logTable, null, data);
        return result;
    }

    // 다이빙로그 수정
    public Integer updateLog(ContentValues data, String[] params) {
        Integer result = db.update(logTable, data, "id = ?", params);
        return result;
    }

    // 다이빙로그 삭제
    public Integer removeLog(String index) {
        String[] params = new String[] {index};
        Integer result = db.delete(logTable, "id = ?", params);
        return result;
    }

    // 다이빙로그 검색
    public DiveLog getLog(String index) {
        String[] params = new String[] {index};
        Cursor result = db.query(logTable, null, "id = ?", params, null, null, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            DiveLog info = new DiveLog();
            info.setData(result);
            result.close();
            return info;
        }
        result.close();
        return null;
    }

    // 다이빙로그 목록
    public ArrayList<DiveLog> getLogList() {
        Cursor result = db.query(logTable, new String[]{"id", "point", "country"}, null, null, null, null, "id DESC");

        result.moveToFirst();
        ArrayList<DiveLog> infos = new ArrayList<DiveLog>();

        while (!result.isAfterLast()) {
            DiveLog info = new DiveLog();
            info.setId(result.getInt(result.getColumnIndex("id")));
            info.setPoint(result.getString(result.getColumnIndex("point")));
            info.setCountry(result.getString(result.getColumnIndex("country")));

            infos.add(info);
            result.moveToNext();
        }
        result.close();
        return infos;
    }

    // 다이빙로그 목록
    public String getLogData() {
        String data = "";
        Cursor result = db.query(logTable, null, null, null, null, null, "id DESC");

        result.moveToFirst();
        while (!result.isAfterLast()) {
            data += makeCSV(result);
            result.moveToNext();
        }
        result.close();
        return data;
    }

    private  String makeCSV(Cursor result) {
        String csvData = "";
        csvData += result.getString(result.getColumnIndex("id")) + "\t";
        csvData += result.getString(result.getColumnIndex("point")) + "\t";
        csvData += result.getString(result.getColumnIndex("country")) + "\t";
        csvData += result.getString(result.getColumnIndex("lat")) + "\t";
        csvData += result.getString(result.getColumnIndex("lng")) + "\t";
        csvData += result.getString(result.getColumnIndex("date")) + "\t";
        csvData += result.getString(result.getColumnIndex("startTime")) + "\t";
        csvData += result.getString(result.getColumnIndex("buddy")) + "\t";
        csvData += result.getString(result.getColumnIndex("guide")) + "\t";
        csvData += result.getString(result.getColumnIndex("instructor")) + "\t";
        csvData += result.getString(result.getColumnIndex("startAir")) + "\t";
        csvData += result.getString(result.getColumnIndex("endAir")) + "\t";
        csvData += result.getString(result.getColumnIndex("visibility")) + "\t";
        csvData += result.getString(result.getColumnIndex("airTemp")) + "\t";
        csvData += result.getString(result.getColumnIndex("waterTemp")) + "\t";
        csvData += result.getString(result.getColumnIndex("surfTime")) + "\t";
        csvData += result.getString(result.getColumnIndex("startPG")) + "\t";
        csvData += result.getString(result.getColumnIndex("endPG")) + "\t";
        csvData += result.getString(result.getColumnIndex("diveTime")) + "\t";
        csvData += result.getString(result.getColumnIndex("maxDepth")) + "\t";
        csvData += result.getString(result.getColumnIndex("avgDepth")) + "\t";
        csvData += result.getString(result.getColumnIndex("safeDepth")) + "\t";
        csvData += result.getString(result.getColumnIndex("safeTime")) + "\t";
        csvData += result.getString(result.getColumnIndex("weight")) + "\t";
        csvData +=  result.getString(result.getColumnIndex("comment")).replace("\n", ". ") + "\t";
        csvData += result.getString(result.getColumnIndex("diveType")) + "\t";
        csvData += result.getString(result.getColumnIndex("equipment")) + "\n";
        return csvData;
    }
}
