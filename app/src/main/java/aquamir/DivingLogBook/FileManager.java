package aquamir.DivingLogBook;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

/**
 * Created by aquamir on 2015-11-06.
 *
 * 파일 경로 정리
 * File cashDir    = Context().getCacheDir(); // 내부 > 캐시영역
 * File dbDir      = Context().getDatabasePath(DBManager.dbName); // 내부 > db 파일
 * File fileDir    = Context().getFilesDir(); // 내부 > 일반 파일 저장영역
 * File rootDir    = Environment.getExternalStorageDirectory(); // 외부 > 최상위 루트
 */
public class FileManager extends Activity {
    private String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private String dirName = "DivingLogBook";
    File dir;
    public FileManager() {
        dir = new File(sdPath,  dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public boolean WriteTextFile(String strFileName, String strBuf) {
        try {
            File file = new File(dir, strFileName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(strBuf.getBytes());
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public ArrayList<String[]> ReadCSVFile(String fn) {
        ArrayList<String[]> datas = new ArrayList<String[]>();
        FileInputStream is = null;
        String strFileName = sdPath + "/" + dirName + "/" + fn;
        try {
            is = new FileInputStream(strFileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] RowData = line.split(",");
                datas.add(RowData);
            }
        }
        catch (IOException ex) {
            // handle exception
            return null;
        }
        finally {
            try {
                if(is != null) {
                    is.close();
                }
            }
            catch (IOException e) {
                // handle exception
                return null;
            }
        }
        return datas;
    }

    public String ReadTextFile(String fn) {
        String text = null;
        String strFileName = sdPath + "/" + dirName + "/" + fn;
        try {
            FileInputStream fis = new FileInputStream(dir + strFileName);
            Reader in = new InputStreamReader(fis);
            int size = fis.available();
            char[] buffer = new char[size];
            in.read(buffer);
            in.close();

            text = new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }

    public String ReadTextAssets(String strFileName) {
        String text = null;
        try {
            InputStream is = getAssets().open(strFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            text = new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return text;
    }
}
