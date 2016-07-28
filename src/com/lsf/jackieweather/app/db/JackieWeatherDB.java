package com.lsf.jackieweather.app.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lsf.jackieweather.app.model.City;
import com.lsf.jackieweather.app.model.County;
import com.lsf.jackieweather.app.model.Province;

public class JackieWeatherDB {

	public static final String DB_NAME = "jackie_weather";

	public static final int version = 1;

	private static JackieWeatherDB jackieWeatherDB;

	private SQLiteDatabase db;

	private JackieWeatherDB(Context context) {
		JackieWeatherOpenHelper dbHelper = new JackieWeatherOpenHelper(context,
				DB_NAME, null, version);
		db = dbHelper.getWritableDatabase();
	}

	public synchronized static JackieWeatherDB getInstance(Context context) {
		if (jackieWeatherDB == null) {
			jackieWeatherDB = new JackieWeatherDB(context);
		}
		return jackieWeatherDB;
	}

	public void saveProvince(Province province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert("Province", null, values);
		}
	}

	public List<Province> loadProvinces(){
		List<Province> list = new ArrayList<Province>();
		Cursor cursor = db.query("Province", null, null, null, null, null, null); 
		if(cursor.moveToFirst()){
			do{
				Province province = new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while (cursor.moveToNext());
		}
		return list;
	}
	
	public void saveCity(City city) {
		if (city != null) {
			ContentValues values = new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert("City", null, values);
		}
	}

	public List<City> loadCitys(int provinceId){
		List<City> list = new ArrayList<City>();
		Cursor cursor = db.query("City", null, "province_id = ?", 
				new String[]{String.valueOf(provinceId) }, null, null, null); 
		if(cursor.moveToFirst()){
			do{
				City city = new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(provinceId);
				list.add(city);
			}while (cursor.moveToNext());
		}
		return list;
	}
	
	public void saveCounty(County county) {
		if (county != null) {
			ContentValues values = new ContentValues();
			values.put("county_name", county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert("County", null, values);
		}
	}

	public List<County> loadCountys(int cityId){
		List<County> list = new ArrayList<County>();
		Cursor cursor = db.query("City", null, "county_id = ?", 
				new String[]{String.valueOf(cityId) }, null, null, null); 
		if(cursor.moveToFirst()){
			do{
				County county = new County();
				county.setId(cursor.getInt(cursor.getColumnIndex("id")));
				county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
				county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
				county.setCityId(cityId);
				list.add(county);
			}while (cursor.moveToNext());
		}
		return list;
	}
}
