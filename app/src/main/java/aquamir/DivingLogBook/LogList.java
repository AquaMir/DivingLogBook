package aquamir.DivingLogBook;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LogList extends Fragment {
    private View v;
    private DBManager mDB;
    private ArrayList<DiveLog> logDataArray;
    private ListView logList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_log_list, container, false);

        mDB = new DBManager(v.getContext());
        logDataArray = mDB.getLogList();
        logList = (ListView)v.findViewById(R.id.logList);
        LogAdapter adapter = new LogAdapter(v.getContext(), R.layout.list_layout, logDataArray);
        logList.setAdapter(adapter);
        logList.setOnItemClickListener(onClickLog);

        v.findViewById(R.id.btnExport).setOnClickListener(onClickExport);
        v.findViewById(R.id.btnImport).setOnClickListener(onClickImport);

        return v;
    }

    View.OnClickListener onClickExport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Back Up Database
            FileManager fm = new FileManager();
            String strBuf = mDB.getLogData();
            fm.WriteTextFile("DivingLogBook.csv", strBuf);
            Toast.makeText(v.getContext(), "파일이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        }
    };
    View.OnClickListener onClickImport = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            alertDialog.setTitle("로그 가져오기");

            // Setting Dialog Message
            alertDialog.setMessage("기존 데이터가 모두 교체됩니다. \n 진행하시겠습니까?");

            // Setting Icon to Dialog
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);

            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES", importData);

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // 취소
                }
            });
            alertDialog.show();
        }
    };

    DialogInterface.OnClickListener importData = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog,int which) {
            long result;
            FileManager fm = new FileManager();
            ArrayList<String[]> datas = fm.ReadCSVFile("DivingLogBook.csv");
            if (datas != null){
                // DB 재 생성
                mDB.createLogDB();

                // 데이터 삽입
                for(String[] row : datas) {
                    DiveLog log = new DiveLog();
                    log.setId(Integer.parseInt(row[0]));
                    log.setPoint(row[1]);
                    log.setCountry(row[2]);
                    log.setLat(row[3]);
                    log.setLng(row[4]);
                    log.setDate(row[5]);
                    log.setStartTime(row[6]);
                    log.setBuddy(row[7]);
                    log.setGuide(row[8]);
                    log.setInstructor(row[9]);
                    log.setStartAir(Integer.parseInt(row[10]));
                    log.setEndAir(Integer.parseInt(row[11]));
                    log.setVisibility(row[12]);
                    log.setAirTemp(row[13]);
                    log.setWaterTemp(row[14]);
                    log.setSurfTime(row[15]);
                    log.setStartPG(row[16]);
                    log.setEndPG(row[17]);
                    log.setDiveTime(row[18]);
                    log.setMaxDepth(row[19]);
                    log.setAvgDepth(row[20]);
                    log.setSafeDepth(Integer.parseInt(row[21]));
                    log.setSafeTime(Integer.parseInt(row[22]));
                    log.setWeight(row[23]);
                    log.setComment(row[24]);
                    log.setDiveType(row[25]);
                    log.setEquipment(row[26]);
                    result = mDB.insertLog(log.getData());
                }//for

                logDataArray = mDB.getLogList();
                logList = (ListView)v.findViewById(R.id.logList);
                LogAdapter adapter = new LogAdapter(v.getContext(), R.layout.list_layout, logDataArray);
                logList.setAdapter(adapter);
                Toast.makeText(v.getContext(), "로그 가져오기가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(v.getContext(), "로그 파일에 오류가 있습니다.", Toast.LENGTH_SHORT).show();
            }//if-data

            fm = null;
        }//click
    };

    ListView.OnItemClickListener onClickLog = new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            DiveLog data = logDataArray.get(position);
            if (data != null) {
                final android.support.v4.app.FragmentTransaction transaction = getFragmentManager()
                        .beginTransaction();

                Fragment fragAdd = new LogAdd();
                transaction.replace(R.id.details, fragAdd);
                Bundle bundle = new Bundle();
                bundle.putInt("id", data.getId());
                fragAdd.setArguments(bundle);
                transaction.commit();
            }
        }
    };
}
