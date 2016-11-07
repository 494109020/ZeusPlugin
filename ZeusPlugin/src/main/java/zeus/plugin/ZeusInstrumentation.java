package zeus.plugin;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by huangjian on 2016/7/28.
 */
public class ZeusInstrumentation extends Instrumentation {

    @Override
    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        /**
         * 这里是在启动Activity的时候，如果使用瞒过系统校验的方式启动的，
         * 则真正要启动的Activity名称是放在了Bundle里了。
         * 表面上要启动的都是"com.zeus.ZeusActivityForStandard"这个Activity。
         */
        if (className.equals("com.zeus.ZeusActivityForStandard") && intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String realActivity = bundle.getString(PluginConfig.PLUGIN_REAL_ACTIVITY);
                if (!TextUtils.isEmpty(realActivity)) {
                    return super.newActivity(cl, realActivity, intent);
                }
            }
        }
        return super.newActivity(cl, className, intent);
    }
}
