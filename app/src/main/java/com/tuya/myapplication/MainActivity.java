package com.tuya.myapplication;

import android.Manifest;
import android.os.Bundle;

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
import java.util.Arrays;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private final int PERMISSION_CODE = 123;

    //根据实际项目需要配置
    private final static String pid = "";
    private final static String uid = "";
    private final static String key = "";

    private String[] requiredPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH};

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

        //avs初始化
        ret = AvsJniApi.INSTANCE.tuya_avs_init(this, "/sdcard/", new AvsListener() {
            @Override
            public void onUIStatusChange(DataEnum.TUYA_AVS_STAT_TYPE_E status) {

            }
        });
        LogJniApi.INSTANCE.d(TAG, "AvsJniApi.INSTANCE.tuya_avs_init error is " + ret);
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