package aquamir.DivingLogBook;

import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogAdd extends Fragment {
    private View v;
    private Integer year, month, day, hour, minute, id;
    private DBManager mDB;
    private DiveLog data;
    private String mode = "add";
    private long result;
    private EditText point, country, txtDate, txtTime, buddy, guide, instructor, startAir, endAir, weight, visibility, airTemp, waterTemp, surfTime, diveTime, maxDepth, avgDepth, safeDepth, safeTime, comment, thick;
    private CheckBox boat, beach, wall, cave, ship, night;
    private RadioButton dry, wet, none;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_log_add, container, false);

        // DB 연결
        mDB = new DBManager(v.getContext());

        // 달력, 시간 처리
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day= calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        v.findViewById(R.id.btnDate).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), dateSetListener, year, month, day).show();
            }
        });
        v.findViewById(R.id.btnTime).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new TimePickerDialog(v.getContext(), timeSetListener, hour, minute, false).show();
            }
        });

        v.findViewById(R.id.btnSave).setOnClickListener(saveListener);
        v.findViewById(R.id.btnCancel).setOnClickListener(cancelListener);

        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        point = (EditText) v.findViewById(R.id.point);
        country = (EditText) v.findViewById(R.id.country);
        txtDate = (EditText) v.findViewById(R.id.txtDate);
        txtTime = (EditText) v.findViewById(R.id.txtTime);
        buddy = (EditText) v.findViewById(R.id.buddy);
        guide = (EditText) v.findViewById(R.id.guide);
        instructor = (EditText) v.findViewById(R.id.instructor);
        startAir = (EditText) v.findViewById(R.id.startAir);
        endAir = (EditText) v.findViewById(R.id.endAir);
        weight = (EditText) v.findViewById(R.id.weight);
        visibility = (EditText) v.findViewById(R.id.visibility);
        airTemp = (EditText) v.findViewById(R.id.airTemp);
        waterTemp = (EditText) v.findViewById(R.id.waterTemp);
        surfTime = (EditText) v.findViewById(R.id.surfTime);
        diveTime = (EditText) v.findViewById(R.id.diveTime);
        maxDepth = (EditText) v.findViewById(R.id.maxDepth);
        avgDepth = (EditText) v.findViewById(R.id.avgDepth);
        safeDepth = (EditText) v.findViewById(R.id.safeDepth);
        safeTime = (EditText) v.findViewById(R.id.safeTime);
        comment = (EditText) v.findViewById(R.id.comment);
        boat = (CheckBox) v.findViewById(R.id.boat);
        beach = (CheckBox) v.findViewById(R.id.beach);
        wall = (CheckBox) v.findViewById(R.id.wall);
        cave = (CheckBox) v.findViewById(R.id.cave);
        ship = (CheckBox) v.findViewById(R.id.ship);
        night = (CheckBox) v.findViewById(R.id.night);
        dry = (RadioButton) v.findViewById(R.id.dry);
        wet = (RadioButton) v.findViewById(R.id.wet);
        none = (RadioButton) v.findViewById(R.id.none);
        thick = (EditText) v.findViewById(R.id.thick);

        // 수정모드인 경우 id 셋팅 처리
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        } else {
            id = 0;
        }

        if (id > 0) {
            data = mDB.getLog(id.toString());
            if (data != null) {
                mode = "mod";
                this.setData(data);
            }
        } else {
            data = new DiveLog();
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

    private void setData(DiveLog data) {
        point.setText(data.getPoint());
        country.setText(data.getCountry());
        txtDate.setText(data.getDate());
        txtTime.setText(data.getStartTime());
        buddy.setText(data.getBuddy());
        guide.setText(data.getGuide());
        instructor.setText(data.getInstructor());
        startAir.setText(data.getStartAir().toString());
        endAir.setText(data.getEndAir().toString());
        weight.setText(data.getWeight());
        visibility.setText(data.getVisibility());
        airTemp.setText(data.getAirTemp());
        waterTemp.setText(data.getWaterTemp());
        surfTime.setText(data.getSurfTime());
        diveTime.setText(data.getDiveTime());
        maxDepth.setText(data.getMaxDepth());
        avgDepth.setText(data.getAvgDepth());
        safeDepth.setText(data.getSafeDepth().toString());
        safeTime.setText(data.getSafeTime().toString());
        comment.setText(data.getComment());
        String[] divetype = data.getDiveType().split("\\|");
        boat.setChecked(Boolean.valueOf(divetype[0]));
        beach.setChecked(Boolean.valueOf(divetype[1]));
        wall.setChecked(Boolean.valueOf(divetype[2]));
        cave.setChecked(Boolean.valueOf(divetype[3]));
        ship.setChecked(Boolean.valueOf(divetype[4]));
        night.setChecked(Boolean.valueOf(divetype[5]));
        String[] equip = data.getEquipment().split("\\|");

        if (equip[0] == "dry") {
            dry.setChecked(true);
        } else if(equip[0] == "wet") {
            wet.setChecked(true);
        } else {
            none.setChecked(true);
        }
        thick.setText(equip[1]);

    }

    private View.OnClickListener saveListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            data.setPoint(point.getText().toString());
            data.setCountry(country.getText().toString());

            // TODO : 위도,경도 입력 처리
            data.setLng("37.4814300");
            data.setLat("126.8826370");

            data.setDate(txtDate.getText().toString());
            data.setStartTime(txtTime.getText().toString());
            data.setBuddy(buddy.getText().toString());
            data.setGuide(guide.getText().toString());
            data.setInstructor(instructor.getText().toString());
            data.setStartAir(Integer.parseInt(startAir.getText().toString()));
            data.setEndAir(Integer.parseInt(endAir.getText().toString()));
            data.setWeight(weight.getText().toString());
            data.setVisibility(visibility.getText().toString());
            data.setAirTemp(airTemp.getText().toString());
            data.setWaterTemp(waterTemp.getText().toString());
            data.setSurfTime(surfTime.getText().toString());
            data.setDiveTime(diveTime.getText().toString());
            data.setMaxDepth(maxDepth.getText().toString());
            data.setAvgDepth(avgDepth.getText().toString());

            // TODO : 압력그룹 자동계산 처리
            data.setStartPG("A");
            data.setEndPG("D");

            data.setSafeDepth(Integer.parseInt(safeDepth.getText().toString()));
            data.setSafeTime(Integer.parseInt(safeTime.getText().toString()));
            data.setComment(comment.getText().toString());

            // 다이빙 type 처리
            String divetype = "";
            divetype += boat.isChecked() + "|";
            divetype += beach.isChecked() + "|";
            divetype += wall.isChecked() + "|";
            divetype += cave.isChecked() + "|";
            divetype += ship.isChecked() + "|";
            divetype += night.isChecked();
            data.setDiveType(divetype);

            // 다이빙 장비
            String equip = "";
            if (dry.isChecked()) {
                equip = "dry|";
            } else if(wet.isChecked()) {
                equip = "wet|";
            } else {
                equip = "none|";
            }
            equip += thick.getText().toString();
            data.setEquipment(equip);

            if (mode == "mod") {
                result = mDB.updateLog(data.getData(), new String[]{data.getId().toString()});
                if(result > 0) {
                    Toast.makeText(v.getContext(), "수정되었습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                result = mDB.insertLog(data.getData());
                if(result > 0) {
                    Toast.makeText(v.getContext(), "등록되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            final android.support.v4.app.FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();

            transaction.replace(R.id.details,  new LogList());
            transaction.commit();
        }
    };

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String date = String.format("%d-%02d-%02d", year,monthOfYear+1, dayOfMonth);
            TextView txt = (TextView) v.findViewById(R.id.txtDate);
            txt.setText(date);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.format("%02d:%02d", hourOfDay, minute);
            TextView txt = (TextView) v.findViewById(R.id.txtTime);
            txt.setText(time);
        }
    };
}
