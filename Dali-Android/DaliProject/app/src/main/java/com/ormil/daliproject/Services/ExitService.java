package com.ormil.daliproject.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ormil.daliproject.Helpers.UserMonitorHelper;

public class ExitService extends Service {
    public static int TaskCompletedGlobal=0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        System.out.println("onTaskRemoved called");
        super.onTaskRemoved(rootIntent);

        String url="http://project-dali.com:5000/system/updateTask?"; //taskCompleted= & taskUndertaken=
        //do something you want before app closes.
        int taskUndertaken=0;
        int taskCompleted=0;
        if(UserMonitorHelper.screens.size()<2){
            //USER IS IN THE PROFILE PAGE AND WENT OUT
            taskUndertaken=1;
            sendRequest(url,taskCompleted,taskUndertaken);
            this.stopSelf();
            return;
        }
        for(int i=0;i< UserMonitorHelper.screens.size()-1;i++){
            int currentNumber=UserMonitorHelper.screens.get(i);
            int nextNumber=UserMonitorHelper.screens.get(i+1);
            if(currentNumber==4 && nextNumber==5){
                //User searched for artist and didn't find the right one
                taskUndertaken++;
            }
            if(currentNumber==3){
                taskCompleted++;
            }
        }
        int lastNumber=UserMonitorHelper.screens.get(UserMonitorHelper.screens.size()-1);
        if(lastNumber==2 || lastNumber== 1 || lastNumber ==4) {
            taskUndertaken += 1;
        }else{
            taskCompleted++;
        }
        taskCompleted+=TaskCompletedGlobal;

        sendRequest(url,taskCompleted,taskUndertaken);
        //stop service
        this.stopSelf();
    }

    private void sendRequest(String url, int taskCompleted, int taskUndertaken){
        url=url+"taskCompleted="+taskCompleted+"&taskUndertaken="+taskUndertaken;
        Log.d("URL_RE",url);
        try {
            HttpService.get(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
