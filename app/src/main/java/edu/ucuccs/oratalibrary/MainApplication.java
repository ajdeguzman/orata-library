/**
 * Created by ajdeguzman on 1/18/2017.
 * Email: aljohndeguzman@gmail.com
 */

package edu.ucuccs.oratalibrary;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Proxima-Nova.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                .build()
        );
    }
}
