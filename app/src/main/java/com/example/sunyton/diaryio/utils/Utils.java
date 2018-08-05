package com.example.sunyton.diaryio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.example.sunyton.diaryio.Const;
import com.example.sunyton.diaryio.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Utils {

    private static Context mContext;

    public Utils getContext(Context context) {
        mContext = context;
        return this;
    }
// SP相关的存储，读取操作
    public static void StoreKey(String userKey) {
        SharedPreferences.Editor editor = getSharePref().edit();
        editor.putString(Const.USER_KEY, userKey);
        editor.apply();

    }

    private static SharedPreferences getSharePref() {

        return mContext.getSharedPreferences(Const.API_KEY, Context.MODE_PRIVATE);
    }

    public static String getKey(Context context) {

        String key = getSharePref().getString(Const.USER_KEY, null);

        return key;

    }
//单位转换
    public static int dp2px(int dp) {
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm));

    }

    public static String formatDate(String unDate) {


        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(unDate);
            SimpleDateFormat out = new SimpleDateFormat("MMM d");
            return out.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

//    获取当前月份，天
    public static String getToday() {

        Date dateToday = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        String date = format.format(dateToday);
        return date;
    }












}
