/**
 * Created by wogaljohn on 1/18/2017.
 * Email: aljohndeguzman@gmail.com
 */

package edu.ucuccs.oratalibrary.app;

import android.content.Context;
import android.widget.Toast;

public class Utils {

    private static Context mContext;

    public Utils(Context mContext) {
        this.mContext = mContext;
    }
    static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
    public static String capsFirst(String str) {
        String[] words = str.split(" ");
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < words.length; i++) {
            ret.append(Character.toUpperCase(words[i].charAt(0)));
            ret.append(words[i].substring(1));
            if(i < words.length - 1) {
                ret.append(' ');
            }
        }
        return ret.toString();
    }
    //Local
    public static final String BASE_URL = "http://192.168.4.141/opac-test/v1/";

    //Production
    //public static final String BASE_URL = "http://192.168.4.141/opac/index.php";

}
