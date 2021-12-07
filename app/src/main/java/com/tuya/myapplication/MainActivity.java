package com.tuya.myapplication;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.tuya.api.AvsJniApi;
import com.tuya.api.BtJniApi;
import com.tuya.api.GwJniApi;
import com.tuya.api.IotJniApi;
import com.tuya.api.LogJniApi;
import com.tuya.listener.AvsListener;
import com.tuya.listener.GwListener;
import com.tuya.listener.IotListener;
import com.tuya.model.DPEvent;
import com.tuya.model.DataEnum;
import com.tuya.model.GwProdInfoBean;
import com.tuya.model.UserModel;
import com.tuya.model.WfGwProdInfoBean;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private final int PERMISSION_CODE = 123;

    //根据实际项目需要配置
    private final static String pid = "lrwd3dzceskyn8wc";
    private final static String uid = "tuya7cf922ea3026bb44";
    private final static String key = "o8u9Tba38zhB5YZYAaUOjTQFswTlU9if";

    private final static boolean isSmartScreen = true;

    private String[] requiredPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.RECORD_AUDIO};

    private String path = "/sdcard/xsj/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!EasyPermissions.hasPermissions(this, requiredPermissions)) {
            EasyPermissions.requestPermissions(this, "需要授予权限以使用设备", PERMISSION_CODE, requiredPermissions);
        } else {
            initSDK();
        }
    }

    private void initSDK() {
        LogJniApi.INSTANCE.setLogListener(new LogJniApi.LogListener() {
            @Override
            public void logCallBack(String msg) {

            }
        });

        LogJniApi.INSTANCE.init(this, "/sdcard/", 3);

        int ret;
        if (BtJniApi.INSTANCE.get_ty_bt_mod() == 1) {
            ret = BtJniApi.INSTANCE.adapt_reg_bt_intf();
            LogJniApi.INSTANCE.d(TAG, "adapt_reg_bt_intf ++1111+++++++++++" + ret);
        }

        ret = IotJniApi.INSTANCE.intf_init();
        LogJniApi.INSTANCE.d(TAG, "intf_init ret ++++++++++++++" + ret);

        LogJniApi.INSTANCE.d(TAG, "iot ret ++++++++++++++ 1111");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        ret = IotJniApi.INSTANCE.iot_init(path);
        LogJniApi.INSTANCE.d(TAG, "iot ret ++++++++++++++" + ret);

        if (ret != 0) {
            LogJniApi.INSTANCE.d(TAG, "IotJniApi.INSTANCE.iot_init error is " + ret);
            return;
        }

        //设置日志等级
        LogJniApi.INSTANCE.setLogLevel(4);

        if (IotJniApi.INSTANCE.get_wifi_gw() == 1) {
            //无限配网
            ret = IotJniApi.INSTANCE.iot_set_wf_gw_prod_info(new WfGwProdInfoBean(uid, key, "", ""));
        } else {
            //有线配网
            ret = IotJniApi.INSTANCE.iot_set_gw_prod_info(new GwProdInfoBean(uid, key));
        }
        if (ret != 0) {
            LogJniApi.INSTANCE.d(TAG, "IotJniApi.INSTANCE.iot_set_gw_prod_info error is " + ret);
            return;
        }

        IotJniApi.INSTANCE.iot_app_cbs_init(() -> "localPath");

        IotJniApi.INSTANCE.iot_enable_blemesh_compatible();

        ret = GwJniApi.INSTANCE.iot_sdk_pre_init();
        if (ret != 0) {
            LogJniApi.INSTANCE.d(TAG, "IotJniApi.INSTANCE.iot_sdk_pre_init error is " + ret);
            return;
        }

        ret = GwJniApi.INSTANCE.user_svc_init(new GwListener.GwInfraListener() {
            @Override
            public boolean isOffline() {
                return false;
            }

            @Override
            public String onGetIP() {
                //返回设备当前的ip
                return "192.168.31.114";
            }

            @Override
            public void upgradeFileDownloadFinished(boolean success) {

            }

            @Override
            public void onUpgradeDownloadUpdate(int progress) {

            }

            @Override
            public void onUpgradeDownloadStart() {

            }

            @Override
            public void onUpgradeInfo(String version) {
                LogJniApi.INSTANCE.d(TAG, "onUpgradeInfo ++++++ type is " + version);
            }

            @Override
            public void gwActiveStatusCallBack(DataEnum.GW_STATUS_E status) {
                LogJniApi.INSTANCE.d(TAG, "gwActiveStatusCallBack ++++++ type is " + status);
                if (DataEnum.GW_STATUS_E.GW_NORMAL == status) {
                    AvsJniApi.INSTANCE.avs_report_init_state();
                }
            }

            @Override
            public void gwResetCallBack(DataEnum.GW_RESET_TYPE_E type) {
                LogJniApi.INSTANCE.d(TAG, "gwResetCallBack ++++++ type is " + type.getValue());
                System.exit(0);
            }

            @Override
            public void gwRebootCallBack() {
                LogJniApi.INSTANCE.d(TAG, "gwRebootCallBack ++++++");
            }

            @Override
            public void gwActiveUrlCallBack(String url) {
                LogJniApi.INSTANCE.d(TAG, "gwActiveUrlCallBack ++++++ url is " + url);
            }
        });
        if (ret != 0) {
            LogJniApi.INSTANCE.d(TAG, "IotJniApi.INSTANCE.user_svc_init error is " + ret);
            return;
        }

        UserModel[] userModels = {new UserModel(1, "1")};
        GwJniApi.INSTANCE.gen_gw_attr(Arrays.asList(userModels));

        if (IotJniApi.INSTANCE.get_wifi_gw() == 1) {
            if (GwJniApi.INSTANCE.gw_supprot_wired_wifi() == 1) {
                ret = GwJniApi.INSTANCE.iot_wired_wf_sdk_init(DataEnum.IOT_GW_NET_TYPE_T.IOT_GW_NET_WIRED_WIFI, DataEnum.GW_WF_CFG_MTHD_SEL.GWCM_OLD, DataEnum.GW_WF_START_MODE.WF_START_AP_ONLY, pid, "1.0.0");
                if (ret != 0) {
                    LogJniApi.INSTANCE.d(TAG, "GwJniApi.INSTANCE.iot_wired_wf_sdk_init error is " + ret);
                    return;
                }
            } else {
                ret = GwJniApi.INSTANCE.iot_wf_sdk_init(DataEnum.GW_WF_CFG_MTHD_SEL.GWCM_SPCL_MODE, DataEnum.GW_WF_START_MODE.WF_START_AP_ONLY, pid, "1.0.0");
                if (ret != 0) {
                    LogJniApi.INSTANCE.d(TAG, "GwJniApi.INSTANCE.iot_wf_sdk_init error is " + ret);
                    return;
                }
            }
        } else {
            ret = GwJniApi.INSTANCE.iot_sdk_init(pid, "1.0.0");
            if (ret != 0) {
                LogJniApi.INSTANCE.d(TAG, "GwJniApi.INSTANCE.iot_sdk_init error is " + ret);
                return;
            }
        }

        GwJniApi.INSTANCE.iot_sdk_reg_net_stat_cb(new GwListener.GwNwkStatusLsitener() {
            @Override
            public void getNwkStatCallBack(int status) {
                LogJniApi.INSTANCE.d(TAG, "getNwkStatCallBack status is " + status);
            }

            @Override
            public void getWifiNwkStatCallBack(int status) {
                LogJniApi.INSTANCE.d(TAG, "getWifiNwkStatCallBack status is " + status);
            }
        });

        GwJniApi.INSTANCE.user_svc_start();

        ret = IotJniApi.INSTANCE.iot_reg_dp_cb(DataEnum.DP_DEV_TYPE_T.DP_GW, DataEnum.GW_PERMIT_DEV_TP_T.GP_DEV_ZB, new IotListener.IotDpListener() {
            @Override
            public void gwDpCallBack(DPEvent[] events) {
                LogJniApi.INSTANCE.d(TAG, "gwDpCallBack +++++");
            }
        });
        if (ret != 0) {
            LogJniApi.INSTANCE.d(TAG, "IotJniApi.INSTANCE.iot_reg_dp_cb error is " + ret);
            return;
        }


        if (isSmartScreen) {
            WebView webView = findViewById(R.id.webview);
            webView.setVisibility(View.VISIBLE);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setAllowFileAccess(true);
            webView.loadDataWithBaseURL("file:///android_asset/",
                    loadHtml(), "text/html", "UTF-8", null);
        }

        //avs初始化
        ret = AvsJniApi.INSTANCE.tuya_avs_init(this, "/sdcard/", new AvsListener() {
            @Override
            public void onUIStatusChange(DataEnum.TUYA_AVS_STAT_TYPE_E status) {
                LogJniApi.INSTANCE.d(TAG, "onUIStatusChange status is " + status);
                if (status == DataEnum.TUYA_AVS_STAT_TYPE_E.TUYA_AVS_STAT_TYPE_BINDED) {

                }
            }
        });
        LogJniApi.INSTANCE.d(TAG, "AvsJniApi.INSTANCE.tuya_avs_init error is " + ret);

        findViewById(R.id.btn_mic_on).setOnClickListener(this);
        findViewById(R.id.btn_mic_close).setOnClickListener(this);
        findViewById(R.id.btn_mic_status).setOnClickListener(this);
        findViewById(R.id.btn_play_start).setOnClickListener(this);
        findViewById(R.id.btn_play_stop).setOnClickListener(this);
        findViewById(R.id.btn_play_status).setOnClickListener(this);
        findViewById(R.id.btn_blue_open).setOnClickListener(this);
        findViewById(R.id.btn_blue_close).setOnClickListener(this);
        findViewById(R.id.btn_blue_status).setOnClickListener(this);
        findViewById(R.id.btn_vloume_set_value).setOnClickListener(this);
        findViewById(R.id.btn_vloume_get_value).setOnClickListener(this);
        findViewById(R.id.btn_wakeup).setOnClickListener(this);
        findViewById(R.id.btn_play_pre).setOnClickListener(this);
        findViewById(R.id.btn_play_next).setOnClickListener(this);
//        findViewById(R.id.btn_dnd_start).setOnClickListener(this);
//        findViewById(R.id.btn_dnd_stop).setOnClickListener(this);
        //开启音频数据
        AudioCapture audioCapture = new AudioCapture();
        audioCapture.startCapture();

        AvsJniApi.INSTANCE.handleAvsServer(this);
    }

    private String loadHtml() {
        InputStream input = null;
        int size = 0;
        try {
            input = getAssets().open("index.html");
            size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mic_close:
                int micStop = AvsJniApi.INSTANCE.native_setMicrophoneMute(false);
                Log.d(TAG, "native_setMicrophoneMute micStop is " + micStop);
                break;
            case R.id.btn_mic_on:
                int micStart = AvsJniApi.INSTANCE.native_setMicrophoneMute(true);
                Log.d(TAG, "native_setMicrophoneMute micStart is " + micStart);
                break;
            case R.id.btn_mic_status:
                boolean ret = AvsJniApi.INSTANCE.native_isMicrophoneMute();
                if (ret) {
                    Toast.makeText(MainActivity.this, "mic处于打开状态", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "mic处于关闭状态", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_play_start:
                AvsJniApi.INSTANCE.native_setSpeakerphoneOn(true);
                break;
            case R.id.btn_play_stop:
                AvsJniApi.INSTANCE.native_setSpeakerphoneOn(false);
                break;
            case R.id.btn_play_status:
                ret = AvsJniApi.INSTANCE.native_isSpeakerphoneOn();
                Toast.makeText(MainActivity.this, "语音播放状态处于" + ret, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_blue_open:
                AvsJniApi.INSTANCE.native_setBluetoothOn(true);
                break;
            case R.id.btn_blue_close:
                AvsJniApi.INSTANCE.native_setBluetoothOn(false);
                break;
            case R.id.btn_blue_status:
                ret = AvsJniApi.INSTANCE.native_isBluetoothOn();
                Toast.makeText(MainActivity.this, "蓝牙播放状态处于打" + ret, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_vloume_set_value:
                AvsJniApi.INSTANCE.native_setVolume(50);
                break;
            case R.id.btn_vloume_get_value:
                int volume = AvsJniApi.INSTANCE.native_getVolume();
                Toast.makeText(MainActivity.this, "当前音量大小为" + volume, Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_wakeup:
                int rett = AvsJniApi.INSTANCE.native_wakeupToggle();
                LogJniApi.INSTANCE.d(TAG, "native_wakeupToggle ret is " + rett);
                break;
            case R.id.btn_play_pre:
                AvsJniApi.INSTANCE.native_playPrev();
                break;
            case R.id.btn_play_next:
                AvsJniApi.INSTANCE.native_playNext();
                break;
            case R.id.btn_dnd_start:
                AvsJniApi.INSTANCE.avs_dnd_ctrl(true);
                break;
            case R.id.btn_dnd_stop:
                AvsJniApi.INSTANCE.avs_dnd_ctrl(false);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        if (requestCode == PERMISSION_CODE) {
            initSDK();
        }
    }
}