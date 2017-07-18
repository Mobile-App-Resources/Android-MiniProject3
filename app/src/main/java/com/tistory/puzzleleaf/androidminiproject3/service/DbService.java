package com.tistory.puzzleleaf.androidminiproject3.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.tistory.puzzleleaf.androidminiproject3.db.Db;

/**
 * Created by cmtyx on 2017-07-18.
 */

public class DbService extends IntentService {

    public DbService() {
        super("DbService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //@TODO 작업 내용을 구현한다.
        Db.markerDatas.clear();
        Db.markerDatas = Db.dbHelper.getResult();
        sendBroadcast(new Intent("dbRefresh"));
    }
}
