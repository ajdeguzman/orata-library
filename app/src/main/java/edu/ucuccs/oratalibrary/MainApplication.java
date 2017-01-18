/**
 * Created by ajdeguzman on 1/18/2017.
 * Email: aljohndeguzman@gmail.com
 */

package edu.ucuccs.oratalibrary;

import android.app.Application;

import edu.ucuccs.oratalibrary.R;
import edu.ucuccs.oratalibrary.TextField;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .addCustomStyle(TextField.class, R.attr.textFieldStyle)
                .build()
        );
    }
}
