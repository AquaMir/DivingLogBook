package aquamir.DivingLogBook;

import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class Setting  extends Fragment {
    private View v;
    private DBManager mDB;
    private ContentValues data;
    private String mode = "add";
    private long result;
    private EditText userName, userLicence, userLicenceNo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_setting, container, false);

        // DB 연결
        mDB = new DBManager(v.getContext());

        v.findViewById(R.id.btnSave).setOnClickListener(saveListener);
        v.findViewById(R.id.btnCancel).setOnClickListener(cancelListener);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // save views as variables in this method
        // "view" is the one returned from onCreateView
        userName = (EditText) view.findViewById(R.id.userName);
        userLicence = (EditText) view.findViewById(R.id.userLicence);
        userLicenceNo = (EditText) view.findViewById(R.id.userLicenceNo);

        // 수정모드인 경우 id 셋팅 처리
        data = Main.user;
        if (data != null) {
            mode = "mod";
            this.setData(data);
        } else {
            data = new ContentValues();
        }
    }

    private  View.OnClickListener cancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final android.support.v4.app.FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.details,  new LogList());
            transaction.commit();
        }
    };

    private void setData(ContentValues data) {
        userName.setText(data.get("name").toString());
        userLicence.setText(data.get("licence").toString());
        userLicenceNo.setText(data.get("licenceNo").toString());
    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            data.put("name", userName.getText().toString());
            data.put("licence", userLicence.getText().toString());
            data.put("licenceNo", userLicenceNo.getText().toString());

            Main.user = data;
            Main.setData(data);

            if (mode == "mod") {
                result = mDB.updateUser(data, new String[]{data.get("id").toString()});
                if(result > 0) {
                    Toast.makeText(v.getContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                result = mDB.insertUser(data);
                if(result > 0) {
                    Toast.makeText(v.getContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            final android.support.v4.app.FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.details, new LogList());
            transaction.commit();
        }
    };
}
