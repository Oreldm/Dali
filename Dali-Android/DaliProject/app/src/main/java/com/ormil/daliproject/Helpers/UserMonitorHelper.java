package com.ormil.daliproject.Helpers;

import com.ormil.daliproject.Models.GenreMonitorModel;

import java.util.ArrayList;
import java.util.List;

public class UserMonitorHelper {
    public static List<Integer> screens=new ArrayList<Integer>();
    public static List<GenreMonitorModel> genreMonitorModels = new ArrayList<>();

    public static long calculateScore(float time){
        int seconds=Math.round(time);
        long score=0;
        while(seconds>0){
            score=score+seconds;
            seconds--;
        }
        return score;
    }
}
