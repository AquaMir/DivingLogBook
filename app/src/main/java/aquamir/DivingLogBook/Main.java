package aquamir.DivingLogBook;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class Main extends FragmentActivity implements View.OnClickListener {
    private Button btnList, btnSet, btnAdd;
    private DBManager mDB;
    public static ContentValues user;
    private static TextView userName, userLicence, userLicenceNo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        btnList     = (Button) findViewById(R.id.nav_log);
        btnSet      = (Button) findViewById(R.id.nav_set);
        btnAdd      = (Button) findViewById(R.id.nav_add);

        btnList.setOnClickListener(this);
        btnSet.setOnClickListener(this);
        btnAdd.setOnClickListener(this);

        mDB = new DBManager(this);
        user = mDB.getUser();

        userName = (TextView) findViewById(R.id.name);
        userLicence = (TextView) findViewById(R.id.licence);
        userLicenceNo = (TextView) findViewById(R.id.licence_no);
        if(user != null) {
            setData(user);
        }

        fragmentReplace(R.id.nav_log);
    }

    public static void setData(ContentValues data) {
        userName.setText(data.get("name").toString());
        userLicence.setText(data.get("licence").toString());
        userLicenceNo.setText(data.get("licenceNo").toString());
    }

    public void fragmentReplace(int reqNewFragmentIndex) {

        Fragment newFragment = null;
        newFragment = getFragment(reqNewFragmentIndex);

        // replace fragment
        final android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.details, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();

    }

    public Fragment getFragment(int id) {
        Fragment frag = null;
        switch (id) {
            case R.id.nav_add:
                frag =  new LogAdd();
                break;
            case R.id.nav_set:
                frag =  new Setting();
                break;

            default:
                frag = new LogList();
                break;
        }
        return frag;
    }

    @Override
    public void onClick(View v) {
        fragmentReplace(v.getId());
    }
}
