package aquamir.DivingLogBook;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogMap extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    MapView mapView;
    private View v;
    private DBManager mDB;
    private ArrayList<DiveLog> logDataArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.activity_log_map, container, false);

        mapView = (MapView) v.findViewById(R.id.logMap);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this); //this is important

//        mDB = new DBManager(v.getContext());
//        logDataArray = mDB.getLogList();
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng test = new LatLng(37.4814300,126.8826370);
        mMap.addMarker(new MarkerOptions().position(test));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(test, 10));
    }

    /**
     * 주소로부터 위치정보 취득
     * @param address 주소
     */
    private LatLng findGeoPoint(String address) {
        Geocoder geocoder = new Geocoder(v.getContext(), Locale.KOREA);
        LatLng latLng;
        Address addr;
        try {
            List<Address> listAddress = geocoder.getFromLocationName(address, 1);
            if (listAddress.size() > 0) { // 주소값이 존재 하면
                addr = listAddress.get(0); // Address형태로
                latLng = new LatLng((double) (addr.getLatitude() * 1E6), (double) (addr.getLongitude() * 1E6));
                return latLng;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 위도,경도로 주소취득
     * @param lat
     * @param lng
     * @return 주소
     */
    private String findAddress(double lat, double lng) {
        StringBuffer bf = new StringBuffer();
        Geocoder geocoder = new Geocoder(v.getContext(), Locale.KOREA);
        List<Address> address;
        String currentLocationAddress;
        try {
            if (geocoder != null) {
                // 세번째 인수는 최대결과값인데 하나만 리턴받도록 설정했다
                address = geocoder.getFromLocation(lat, lng, 1);
                // 설정한 데이터로 주소가 리턴된 데이터가 있으면
                if (address != null && address.size() > 0) {
                    // 주소
                    currentLocationAddress = address.get(0).getAddressLine(0).toString();

                    // 전송할 주소 데이터 (위도/경도 포함 편집)
                    bf.append(currentLocationAddress).append("#");
                    bf.append(lat).append("#");
                    bf.append(lng);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bf.toString();
    }
}
