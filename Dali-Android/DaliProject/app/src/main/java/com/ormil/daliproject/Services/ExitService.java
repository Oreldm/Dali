package com.ormil.daliproject.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ormil.daliproject.Helpers.UserMonitorHelper;
import com.ormil.daliproject.Models.GenreMonitorModel;

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

        //do something you want before app closes.



        //Sending data about screens
        int taskUndertaken=0;
        int taskCompleted=0;
        if(UserMonitorHelper.screens.size()<2){
            //USER IS IN THE PROFILE PAGE AND WENT OUT
            taskUndertaken=1;
            sendRequest(taskCompleted,taskUndertaken);
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

        sendRequest(taskCompleted,taskUndertaken);


        //Sending data about generes
        if(UserMonitorHelper.genreMonitorModels!=null)
            for(GenreMonitorModel model : UserMonitorHelper.genreMonitorModels){
                //calculate score
                long score = UserMonitorHelper.calculateScore(model.getTime());
                //post to server
                try {
                    HttpService.postScore(model.getGenreId(),HttpService.userID,score);
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("Exception", "Unable to send score");
                }
            }



        //stop service
        this.stopSelf();
    }

    private void sendRequest(int taskCompleted, int taskUndertaken){
        try {
            HttpService.updateTask(taskCompleted, taskUndertaken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
