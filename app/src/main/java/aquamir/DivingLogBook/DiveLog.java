package aquamir.DivingLogBook;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by aquamir on 2015-10-28.
 */
public class DiveLog {
    private ContentValues cv = new ContentValues();

    public ContentValues getData(){
        return cv;
    }
    
    public void setData(Cursor result) {
        setId(result.getInt(result.getColumnIndex("id")));
        setPoint(result.getString(result.getColumnIndex("point")));
        setCountry(result.getString(result.getColumnIndex("country")));
        setLat(result.getString(result.getColumnIndex("lat")));
        setLng(result.getString(result.getColumnIndex("lng")));
        setDate(result.getString(result.getColumnIndex("date")));
        setStartTime(result.getString(result.getColumnIndex("startTime")));
        setBuddy(result.getString(result.getColumnIndex("buddy")));
        setGuide(result.getString(result.getColumnIndex("guide")));
        setInstructor(result.getString(result.getColumnIndex("instructor")));
        setStartAir(result.getInt(result.getColumnIndex("startAir")));
        setEndAir(result.getInt(result.getColumnIndex("endAir")));
        setVisibility(result.getString(result.getColumnIndex("visibility")));
        setAirTemp(result.getString(result.getColumnIndex("airTemp")));
        setWaterTemp(result.getString(result.getColumnIndex("waterTemp")));
        setSurfTime(result.getString(result.getColumnIndex("surfTime")));
        setStartPG(result.getString(result.getColumnIndex("startPG")));
        setEndPG(result.getString(result.getColumnIndex("endPG")));
        setDiveTime(result.getString(result.getColumnIndex("diveTime")));
        setMaxDepth(result.getString(result.getColumnIndex("maxDepth")));
        setAvgDepth(result.getString(result.getColumnIndex("avgDepth")));
        setSafeDepth(result.getInt(result.getColumnIndex("safeDepth")));
        setSafeTime(result.getInt(result.getColumnIndex("safeTime")));
        setWeight(result.getString(result.getColumnIndex("weight")));
        setComment(result.getString(result.getColumnIndex("comment")));
        setDiveType(result.getString(result.getColumnIndex("diveType")));
        setEquipment(result.getString(result.getColumnIndex("equipment")));
    }

    public Integer getId() {
        return (int)cv.get("id");
    }

    public void setId(int id) {
        cv.put("id",id);
    }

    public String getPoint() {
        return cv.get("point").toString();
    }

    public void setPoint(String point) {
        cv.put("point",point);
    }

    public String getCountry() {
        return cv.get("country").toString();
    }

    public void setCountry(String country) {
        cv.put("country",country);
    }

    public String getLat() {
        return cv.get("lat").toString();
    }

    public void setLat(String lat) {
        cv.put("lat",lat);
    }

    public String getLng() {
        return cv.get("lng").toString();
    }

    public void setLng(String lng) {
        cv.put("lng",lng);
    }

    public String getDate() {
        return cv.get("date").toString();
    }

    public void setDate(String date) {
        cv.put("date",date);
    }

    public String getStartTime() {
        return cv.get("startTime").toString();
    }

    public void setStartTime(String startTime) {
        cv.put("startTime",startTime);
    }

    public String getBuddy() {
        return cv.get("buddy").toString();
    }

    public void setBuddy(String buddy) {
        cv.put("buddy",buddy);
    }

    public String getGuide() {
        return cv.get("guide").toString();
    }

    public void setGuide(String guide) {
        cv.put("guide",guide);
    }

    public String getInstructor() {
        return cv.get("instructor").toString();
    }

    public void setInstructor(String instructor) {
        cv.put("instructor",instructor);
    }

    public Integer getStartAir() {
        return (int)cv.get("startAir");
    }

    public void setStartAir(int startAir) {
        cv.put("startAir",startAir);
    }

    public Integer getEndAir() {
        return (int)cv.get("endAir");
    }

    public void setEndAir(int endAir) {
        cv.put("endAir",endAir);
    }

    public String getVisibility() {
        return cv.get("visibility").toString();
    }

    public void setVisibility(String visibility) {
        cv.put("visibility",visibility);
    }

    public String getAirTemp() {
        return cv.get("airTemp").toString();
    }

    public void setAirTemp(String airTemp) {
        cv.put("airTemp",airTemp);
    }

    public String getWaterTemp() {
        return cv.get("waterTemp").toString();
    }

    public void setWaterTemp(String waterTemp) {
        cv.put("waterTemp",waterTemp);
    }

    public String getSurfTime() {
        return cv.get("surfTime").toString();
    }

    public void setSurfTime(String surfTime) {
        cv.put("surfTime",surfTime);
    }

    public String getStartPG() {
        return cv.get("startPG").toString();
    }

    public void setStartPG(String startPG) {
        cv.put("startPG",startPG);
    }

    public String getEndPG() {
        return cv.get("endPG").toString();
    }

    public void setEndPG(String endPG) {
        cv.put("endPG",endPG);
    }

    public String getDiveTime() {
        return cv.get("diveTime").toString();
    }

    public void setDiveTime(String diveTime) {
        cv.put("diveTime",diveTime);
    }

    public String getMaxDepth() {
        return cv.get("maxDepth").toString();
    }

    public void setMaxDepth(String maxDepth) {
        cv.put("maxDepth",maxDepth);
    }

    public String getAvgDepth() {
        return cv.get("avgDepth").toString();
    }

    public void setAvgDepth(String avgDepth) {
        cv.put("avgDepth",avgDepth);
    }

    public Integer getSafeDepth() {
        return (int)cv.get("safeDepth");
    }

    public void setSafeDepth(int safeDepth) {
        cv.put("safeDepth",safeDepth);
    }

    public Integer getSafeTime() {
        return (int)cv.get("safeTime");
    }

    public void setSafeTime(int safeTime) {
        cv.put("safeTime",safeTime);
    }

    public String  getWeight() {
        return cv.get("weight").toString();
    }

    public void setWeight(String weight) {
        cv.put("weight",weight);
    }

    public String getComment() {
        return cv.get("comment").toString();
    }

    public void setComment(String comment) {
        cv.put("comment",comment);
    }

    public String getDiveType() {
        return cv.get("diveType").toString();
    }

    public void setDiveType(String divetype) {
        cv.put("diveType",divetype);
    }

    public String getEquipment() {
        return cv.get("equipment").toString();
    }

    public void setEquipment(String equipment) {
        cv.put("equipment",equipment);
    }
}
