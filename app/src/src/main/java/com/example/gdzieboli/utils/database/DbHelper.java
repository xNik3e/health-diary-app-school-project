package com.example.gdzieboli.utils.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gdzieboli.feature.mainwindow.addMoreWindow.model.RecordModel;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "records.db";
    private static final String TABLE_NAME = "records_table";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MAIN_ORGAN = "MainOrgan";
    private static final String COLUMN_ADDITIONAL_ORGANS = "additionalOrgans";
    private static final String COLUMN_DATE = "dateAdded";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_SYMPTHOMS = "sympthoms";
    private static final String COLUMN_RESOURCE_NUMBER = "resourceNumber";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TABLE_NAME + "(" +
                        COLUMN_ID + " integer primary key autoincrement, " +
                        COLUMN_MAIN_ORGAN + " varchar(25), " +
                        COLUMN_ADDITIONAL_ORGANS + " varchar(300), " +
                        COLUMN_DATE + " bigint, " +
                        COLUMN_DESCRIPTION + " text, " +
                        COLUMN_SYMPTHOMS + " text, " +
                        COLUMN_RESOURCE_NUMBER + " integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long insertRecord(RecordModel record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAIN_ORGAN, record.getMainOrgan());
        if (record.getAdditionalOrgans() != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : record.getAdditionalOrgans()) {
                sb.append(s).append(";");
            }
            sb.deleteCharAt(sb.length() - 1);
            values.put(COLUMN_ADDITIONAL_ORGANS, sb.toString());
        } else {
            values.put(COLUMN_ADDITIONAL_ORGANS, "");
        }
        BigInteger date_val = new BigInteger(String.valueOf(record.getDateAdded().getTime()));
        values.put(COLUMN_DATE, date_val.longValue());
        values.put(COLUMN_DESCRIPTION, record.getDesciption());

        if (record.getSympthoms() != null) {
            StringBuilder sb1 = new StringBuilder();
            for (String s : record.getSympthoms()) {
                sb1.append(s).append(";");
            }
            sb1.deleteCharAt(sb1.length() - 1);
            values.put(COLUMN_SYMPTHOMS, sb1.toString());

        } else {
            values.put(COLUMN_SYMPTHOMS, "");
        }

        values.put(COLUMN_RESOURCE_NUMBER, record.getResourceNumber());

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;
    }
    @SuppressLint("Range")
    public List<RecordModel> getRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<RecordModel> recordModels = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                RecordModel recordModel = new RecordModel();
                recordModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                recordModel.setMainOrgan(cursor.getString(cursor.getColumnIndex(COLUMN_MAIN_ORGAN)));
                Date d;
                long date_as_long = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
                d = new Date(date_as_long);
                recordModel.setDateAdded(d);
                recordModel.setDesciption(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                String sympthoms, additionaOrgans;
                sympthoms = cursor.getString(cursor.getColumnIndex(COLUMN_SYMPTHOMS));
                additionaOrgans = cursor.getString(cursor.getColumnIndex(COLUMN_ADDITIONAL_ORGANS));

                if (sympthoms.length() == 0) {
                    recordModel.setSympthoms(null);
                } else {
                    List<String> listOfSympthoms = new ArrayList<String>(Arrays.asList(sympthoms.split(";")));
                    recordModel.setSympthoms(listOfSympthoms);
                }

                if ((additionaOrgans.length() == 0)) {
                    recordModel.setAdditionalOrgans(null);
                } else {
                    List<String> listOfOrgans = new ArrayList<String>(Arrays.asList(additionaOrgans.split(";")));
                    recordModel.setAdditionalOrgans(listOfOrgans);
                }
                recordModel.setResourceNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_NUMBER)));
                recordModels.add(recordModel);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return recordModels;
    }

    @SuppressLint("Range")
    public RecordModel getRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            RecordModel recordModel = new RecordModel();
            recordModel.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            recordModel.setMainOrgan(cursor.getString(cursor.getColumnIndex(COLUMN_MAIN_ORGAN)));
            Date d;
            long date_as_long = cursor.getLong(cursor.getColumnIndex(COLUMN_DATE));
            d = new Date(date_as_long);
            recordModel.setDateAdded(d);
            recordModel.setDesciption(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            String sympthoms, additionaOrgans;
            sympthoms = cursor.getString(cursor.getColumnIndex(COLUMN_SYMPTHOMS));
            additionaOrgans = cursor.getString(cursor.getColumnIndex(COLUMN_ADDITIONAL_ORGANS));

            if (sympthoms.length() == 0) {
                recordModel.setSympthoms(null);
            } else {
                List<String> listOfSympthoms = new ArrayList<String>(Arrays.asList(sympthoms.split(";")));
                recordModel.setSympthoms(listOfSympthoms);
            }

            if ((additionaOrgans.length() == 0)) {
                recordModel.setAdditionalOrgans(null);
            } else {
                List<String> listOfOrgans = new ArrayList<String>(Arrays.asList(additionaOrgans.split(";")));
                recordModel.setAdditionalOrgans(listOfOrgans);
            }
            recordModel.setResourceNumber(cursor.getInt(cursor.getColumnIndex(COLUMN_RESOURCE_NUMBER)));
            cursor.close();
            db.close();
            return recordModel;
        } else {
            db.close();
            return null;
        }
    }

    public int updateRecord(RecordModel record) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MAIN_ORGAN, record.getMainOrgan());
        if (record.getAdditionalOrgans() != null) {
            StringBuilder sb = new StringBuilder();
            for (String s : record.getAdditionalOrgans()) {
                sb.append(s).append(";");
            }
            sb.deleteCharAt(sb.length() - 1);
            values.put(COLUMN_ADDITIONAL_ORGANS, sb.toString());
        } else {
            values.put(COLUMN_ADDITIONAL_ORGANS, "");
        }
        BigInteger date_val = new BigInteger(String.valueOf(record.getDateAdded().getTime()));
        values.put(COLUMN_DATE, date_val.longValue());
        values.put(COLUMN_DESCRIPTION, record.getDesciption());
        if (record.getSympthoms() != null) {
            StringBuilder sb1 = new StringBuilder();
            for (String s : record.getSympthoms()) {
                sb1.append(s).append(";");
            }
            sb1.deleteCharAt(sb1.length() - 1);
            values.put(COLUMN_SYMPTHOMS, sb1.toString());
        } else {
            values.put(COLUMN_SYMPTHOMS, "");
        }
        values.put(COLUMN_RESOURCE_NUMBER, record.getResourceNumber());
        int noOfRowsUpdate = db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{record.getId() + ""});
        db.close();
        return noOfRowsUpdate;
    }

    public int deleteRecord(int recordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int noOfRowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{recordId+""});
        db.close();
        return noOfRowsDeleted;
    }

    @SuppressLint("Range")
    public int getIndex(RecordModel recordModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[]{COLUMN_ID};
        String selection = COLUMN_DATE + " +?";

        BigInteger date_val = new BigInteger(String.valueOf(recordModel.getDateAdded().getTime()));

        String[] selectionArgs = new String[]{String.valueOf(date_val.longValue())};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        int index = 0;

        if(cursor != null && cursor.moveToFirst()){
            index = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return index;
    }

}
