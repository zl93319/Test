package com.hengzhi.test.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hengzhi.test.R;
import com.hengzhi.test.base.SuperApplication;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;



/**
 * 作者: zhangl
 * 时间: 2017/11/2 0002
 * 描述: UI工具类
 **/

public class UiUtil {
    private static final String TAG = "OtherActivity";
    /**
     * 全局的上下文
     */
    private static Context mBaseContext;
    private static Handler mHandler;
    protected static Toast mToast;
    private static boolean mKeyBoardIsActive;
    private static ProgressDialog mProgressDialog;

    public static Context getContext() {
        return mBaseContext;
    }

    public static void init(SuperApplication gpApplication) {
        mBaseContext = gpApplication;
        mHandler = new Handler();
    }

    /**
     * 提交一个任务
     */
    public static void post(Runnable task) {
        mHandler.post(task);
    }

    /**
     * 延时提交一个任务
     */
    public static void postDelay(Runnable task, long delay) {
        mHandler.postDelayed(task, delay);
    }

    /**
     * 取消一个任务
     */
    public static void cacel(Runnable task) {
        mHandler.removeCallbacks(task);
    }

    public static String[] getStringArray(int resId) {
        return mBaseContext.getResources().getStringArray(resId);
    }


    // 获取资源文件夹
    public static Resources getResources() {
        return mBaseContext.getResources();
    }

    // 获取资源文件夹的字符串
    public static String getString(int id, Object... formatArgs) {
        return getResources().getString(id, formatArgs);
    }


    public static String getDateHHmm(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }

    public static String getTime(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }

    public static int getDateDay(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("dd");
        Date d = new Date(time);
        return Integer.parseInt(sf.format(d));
    }


    @NonNull

    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager()
                .getDefaultDisplay().getHeight();
    }

    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager()
                .getDefaultDisplay().getWidth();
    }


    public static void getPwdStyle(CharSequence s, int start, int before, EditText editText) {
        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == '-') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != '-') {
                    sb.insert(sb.length() - 1, '-');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == '-') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            editText.setText(sb.toString());
            editText.setSelection(index);
        }
    }

    /**
     * dp --> px
     *
     * @param dp
     * @return
     */
    public static float dp2px(float dp) {
        float dimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                dp,
                getResources().getDisplayMetrics());
        return dimension;
    }

    // 将px转换为dp
    public static float px2dp(float px) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                px,
                getResources().getDisplayMetrics());
    }


    public static void setImageDrawable(ImageView imageView, int resId) {
        imageView.setImageDrawable(UiUtil.getResources().getDrawable(resId));
    }

    private static Drawable isYBreak() {
   /* Drawable drawable = getResources().getDrawable(R.mipmap.pulse_icon_united);
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    return drawable;*/
        return null;
    }

    public static int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    private static Drawable isNBreak() {
 /*   Drawable drawable = getResources().getDrawable(R.mipmap.pulse_icon_ununited);
    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
    return drawable;*/
        return null;
    }


    /**
     * 压缩图片（质量压缩）
     *
     * @param bitmap
     */
    public static File compressImage(Bitmap bitmap, Context context) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {
            baos.reset();
            options -= 10;
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(context.getFilesDir(), filename + ".png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        recycleBitmap(bitmap);
        return file;
    }

    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps == null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }

    public static void deleteFiles(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            for (File item : file.listFiles()) {
                item.delete();
            }
        }
    }


    //将bitmap调整到指定大小
    public static Bitmap sizeBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (!origin.isRecycled()) {//这时候origin还有吗？
            origin.recycle();
        }
        return newBM;
    }


    private static void getPhoneStyle(CharSequence s, int start, int before, EditText et_01) {
        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8 && s.charAt(i) == '-') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != '-') {
                    sb.insert(sb.length() - 1, '-');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == '-') {
                if (before == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (before == 1) {
                    index--;
                }
            }
            et_01.setText(sb.toString());
            et_01.setSelection(index);
        }
    }

    private static PowerManager.WakeLock wakeLock;

    public static void keepScreenOn(Context context, boolean on) {
        if (on) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, TAG);
            wakeLock.acquire();
        } else {
            if (wakeLock != null) {
                wakeLock.release();
                wakeLock = null;
            }
        }
    }

    // 获取随机颜色
    public static int getRandomColor() {
        return 0xff000000 | new Random().nextInt(0x00ffffff);
    }

    public static float getDiscount() {
        return Math.round(Math.random() * 99 + 1);
    }


    public static boolean checkDeviceHasNavigationBar(Activity activity) {
        boolean hasNavigationBar = false;
        Resources rs = activity.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;
    }

    public static boolean checkDeviceHasStatusBar(Activity activity) {
        if ((activity.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return (int) dipValue;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str) throws PatternSyntaxException {
        return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     */
    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    public static boolean isHKPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^(5|6|8|9)\\d{7}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * @param text      显示的文本
     * @param isGravity 是否居中显示
     */
    public static void showToast(String text, boolean isGravity) {
        Toast toast = mToast;
        if (toast == null) {
            toast = initToast();
        }
        View rootView = LayoutInflater.from(mBaseContext).inflate(R.layout.view_toast, null, false);
        TextView textView = rootView.findViewById(R.id.title_tv);
        textView.setText(text);
        toast.setView(rootView);
        initToastGravity(toast, isGravity);
        toast.show();
    }

    private static void initToastGravity(Toast toast, boolean isGravity) {
        toast.setGravity(Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_y_offset));
        if (isGravity) {
            toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            toast.setGravity(Gravity.BOTTOM, 0, getResources().getDimensionPixelSize(R.dimen.toast_y_offset));
        }
    }

    private static Toast initToast() {
        Toast toast;
        toast = new Toast(mBaseContext);
        toast.setDuration(Toast.LENGTH_SHORT);
        mToast = toast;
        return toast;
    }





    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    public static void hideSoftKeyboard(Context context, List<View> viewList) {
        if (viewList == null) return;

        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);

        for (View v : viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    /**
     * 放大
     *
     * @param view
     */

    public static void zoomOut(final View view, float scale) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);
        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * 缩小
     *
     * @param view
     */
    public static void zoomIn(final View view, float scale, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    public static List<String> get_times(String beginDate, String endDate) {
        List<String> times = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(beginDate));
            for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plaus_1(cal)) {
                times.add(sdf.format(d));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return times;
    }


    public static long get_D_Plaus_1(Calendar c) {
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);
        return c.getTimeInMillis();
    }


    public static List<Integer> getIntegerColors() {
        List<Integer> integers = new ArrayList<>();
        integers.add(0xb200fc);
        integers.add(0x00a510);
        integers.add(0xff0000);
        integers.add(0x0893ff);
        integers.add(0xffa508);
        return integers;
    }




    private static String getSortkey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        } else
            return "#";
    }





    /**
     * 加密
     *
     * @param datasource byte[]
     * @param password   String
     * @return byte[]
     */
    public static byte[] encrypt(byte[] datasource, String password) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            return cipher.doFinal(datasource);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密
     *
     * @param src      byte[]
     * @param password String
     * @return byte[]
     * @throws Exception
     */
    public static byte[] decrypt(byte[] src, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        return cipher.doFinal(src);
    }




    public static void showProgressDialog(String message, Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }


    /**
     * 限制只能输入小数点后两位
     *
     * @param editText
     * @param s
     */
    public static void InputTwo(EditText editText, CharSequence s) {
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + 3);
                editText.setText(s);
                editText.setSelection(s.length());
            }
        }
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }

    /**
     * 计算折扣
     *
     * @param unitPrice 原始价
     * @param discount  折扣
     * @return 折扣价
     */
    public static String afterDiscount(String unitPrice, String discount) {
        DecimalFormat df = new DecimalFormat("#0.00");
        float price = (float) (Float.parseFloat(unitPrice) * Float.parseFloat(discount) / 10.0);
        return df.format(price);
    }

    /**
     * @param
     * @return
     * @throws Throwable
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static String getWeekOfDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
        int offset1 = 1 - dayOfWeek;
        int offset2 = 7 - dayOfWeek;
        calendar1.add(Calendar.DATE, offset1 - 7);
        calendar2.add(Calendar.DATE, offset2 - 6);
        String lastEndDate = sdf.format(calendar2.getTime());
        return lastEndDate;
    }

    public static String[] getWeeks() {
        return new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "周六", "周日"};
    }


    public static String getDecimalFormat(double num) {
        DecimalFormat df = new DecimalFormat("###,###,###,##0.0");
        return df.format(num);
    }

    public static String getMonthOfDate() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        return sf.format(gcLast.getTime());
    }


    public static String getMon() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        String mon = format.format(m);
        return mon;
    }

    public static String getYMD() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String mon = format.format(date);
        return mon;
    }


    public static SpannableString getKeyWordSpan(int color, String str, String patterStr) throws Exception {
        SpannableString spannableString = new SpannableString(str);
        Pattern patten = Pattern.compile(patterStr, Pattern.CASE_INSENSITIVE);
        dealPattern(color, spannableString, patten, 0);
        return spannableString;
    }

    private static void dealPattern(int color, SpannableString spannableString, Pattern patten, int start) throws Exception {
        Matcher matcher = patten.matcher(spannableString);
        while (matcher.find()) {
            String key = matcher.group();
            // 返回第一个字符的索引的文本匹配整个正则表达式,ture 则继续递归
            if (matcher.start() < start) {
                continue;
            }
            // 计算该内容的长度，也就是要替换的字符串的长度
            int end = matcher.start() + key.length();
            //设置前景色span
            spannableString.setSpan(new ForegroundColorSpan(color), matcher.start(), end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (end < spannableString.length()) {
                // 如果整个字符串还未验证完，则继续。。
                dealPattern(color, spannableString, patten, end);
            }
            break;
        }
    }



    public static void editAndDel(SwipeMenu swipeRightMenu) {
        SwipeMenuItem editItem = new SwipeMenuItem(UiUtil.getContext())
                .setBackground(R.color.edit)
                .setText(UiUtil.getString(R.string.edit_text))
                .setTextColor(UiUtil.getColor(android.R.color.white))
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setWidth(getResources().getDimensionPixelSize(R.dimen._90dp))
                .setTextSize(getResources().getDimensionPixelSize(R.dimen._9dp));
        SwipeMenuItem deleteItem = new SwipeMenuItem(UiUtil.getContext())
                .setBackground(R.color.del)
                .setText(UiUtil.getString(R.string.del_text))
                .setTextColor(UiUtil.getColor(android.R.color.white))
                .setHeight(ViewGroup.LayoutParams.MATCH_PARENT)
                .setWidth(getResources().getDimensionPixelSize(R.dimen._90dp))
                .setTextSize(getResources().getDimensionPixelSize(R.dimen._9dp));
        swipeRightMenu.addMenuItem(editItem);//0
        swipeRightMenu.addMenuItem(deleteItem);//1
    }
}
