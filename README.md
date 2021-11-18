## 融合 SDK说明
融合sdk集成了iot，网关，蓝牙等功能

### BtJniApi 说明
```java
/**
 * 获取是否打开蓝牙能力
 * @return 1 打开
 *         0 关闭
 */
    external fun get_ty_bt_mod(): Int
```

```java
/**
 *蓝牙初始准备
 * @return  0 成功
 *         非0 失败
 */
    external fun adapt_reg_bt_intf(): Int
```

```java
    /**
 * 蓝牙业务初始化
 * @param parStr 初始参数
 * @return   0 成功
 *          非0 失败
 */
    fun bt_svc_init(btModel: BtModel): Int {
            return bt_svc_init(Gson().toJson(btModel))
            }
```

```java
    /**
 * 蓝牙业务启动
 * @param context 上下文
 * @param btModel 初始参数
 * @return   0 成功
 *          非0 失败
 */
    fun bt_svc_start(context: Context, btModel: BtModel): Int
```

```java
     /**
 * 结束蓝牙业务
 */
    fun stop()
```
### GwJniApi 说明
```java
	/**
     * 网关初始化
     * @param gwListener gw注册回调
     * @return 0 成功
     *         非0 失败
     */
    fun user_svc_init(gwListener: GwListener.GwInfraLsitener): Int
```
```java
	/**
     * 设置 oem 模式
     */
    external fun oem_set()
```

```java
	/**
     * 设置网关attr
     */
    fun gen_gw_attr(userModelList: List<UserModel>)
```

```java
	/**
     * 网关预初始化
     * @return 0 成功
     *         非0 失败
     */
    external fun iot_sdk_pre_init(): Int
```

```java
	/**
     * 有线+无线 iot sdk 初始化
     * @param net_mode 联网方式
     * @param cfg wifi设置模式
     * @param start_mode wifi启动模式
     * @param pid 产品id
     * @param version 版本
     * @return 0 成功
     *         非0 不成功
     */
    fun iot_wired_wf_sdk_init(
        net_mode: DataEnum.IOT_GW_NET_TYPE_T,
        cfg: DataEnum.GW_WF_CFG_MTHD_SEL,
        start_mode: DataEnum.GW_WF_START_MODE,
        pid: String,
        version: String
    ): Int
```

```java
	/**
     * 无线配网初始化
     * @param cfg 配置无线模式
     * @param start_mode 无线开启方式
     * @param pid 产品id
     * @param version 版本
     * @return 0 成功
     *         非0 失败
     */
    fun iot_wf_sdk_init(
        cfg: DataEnum.GW_WF_CFG_MTHD_SEL, start_mode: DataEnum.GW_WF_START_MODE,
        pid: String, version: String
    ): Int
```
```java
	/**
     * 设置网络状态监听
     * @param gwNwkStatusLsitener
     * @return 0 成功
     *         非0 失败
     */
    fun iot_sdk_reg_net_stat_cb(gwNwkStatusLsitener: GwListener.GwNwkStatusLsitener): Int
```

```java
	/**
     * 启动网关业务
     * @return 0 成功
     *         非0 不成功
     */
    external fun user_svc_start(): Int
```

```java
	/**
     * 获取sdk是否支持有线+无线配网模式
     * @return  1 支持
     *          0 不支持
     */
    external fun gw_supprot_wired_wifi(): Int
```

```java
	/**
     * iot sdk 初始化
     * @param pid 产品id
     * @param version 版本
     * @return 0 成功
     *         非0 失败
     */
    external fun iot_sdk_init(pid: String, version: String): Int
```

```java
	/**
     * 上传dp信息
     * @param devId 设备id
     * @param dataPoint dp点的集合
     * @param timeout 超时时间
     * @return 0 成功
     *         非0 不成功
     */
    external fun gw_dev_report_dp_stat_sync_extend(
        devId: String,
        dataPoint: Array<DataPoint>,
        timeout: Int
    ): Int
````

```java
	/**
     * 获取deviceId
     */
    external fun iot_get_gw_id(): String
```

```java
	/**
     * 开始升级包
     * @param 升级包下载路径
     */
    external fun native_upgradeDownload(path: String)
```

```java
	/**
     * 设置token绑定
     * @param token
     */
    external fun gw_user_token_bind(token: String)
```

### IotJniApi 说明

```java
	/**
     * 设置工作网卡名
     * @param netCardName 网卡名
     */
    external fun set_net_config(netCardName: String)
```

```java
	/**
     * 初始化系统的适配接口
     * @return 0 成功
     *         非0 不成功
     */
    external fun intf_init(): Int
```

```java
	/**
     * iot 初始化
     * @param path
     * @return 0 成功
     *         非0 不成功
     */
    external fun iot_init(path: String): Int
```

```java
	/**
     * 设置无线网关信息
     * @param bean 配网信息
     * @return 0 成功
     *         非0 失败
     */
    fun iot_set_wf_gw_prod_info(bean: WfGwProdInfoBean): Int
```

```java
	/**
     * 设置网关信息
     * @param bean 配网信息
     * @return 0 成功
     *         非0 失败
     */
    fun iot_set_gw_prod_info(bean: GwProdInfoBean): Int
```

```java
	/**
     * iot初始化回调设置
     * @param iotAppLogListener iot日志设置回调设置
     */
    fun iot_app_cbs_init(iotAppLogListener: IotListener.IotAppLogListener)
```

```java
	/**
     * dp下发回调注册
     * @param dp_dev_type dp设置类型
     * @param gw_permit_dev_tp_t
     * @param iotDpListener dp回调
     * @return 0 成功
     *         非0 失败
     */
    fun iot_reg_dp_cb(
        dp_dev_type: DataEnum.DP_DEV_TYPE_T,
        gw_permit_dev_tp_t: DataEnum.GW_PERMIT_DEV_TP_T,
        iotDpListener: IotListener.IotDpListener
    ): Int
```

```java
	/**
     * 判断当前是否是无线模式
     * @return 1 是
     *         0 否
     */
    external fun get_wifi_gw(): Int
```

```java
	/**
     * 适配安卓版本的蓝牙模块
     */
    external fun iot_enable_blemesh_compatible()
```

```java
/**
     * 发送ATOP命令
     *
     * @param apiName  参考API_NAME_xxx
     * @param apiVer   API版本
     * @param uuid 设备uid
     * @param devid 设备id
     * @param postData json格式的数据
     * @param pHeadOther
     * @return 成功返回json结果字符串，否则返回null
     */
    fun iotHttpcCommonPost(
        apiName: String,
        apiVer: String,
        uuid: String,
        devid: String,
        postData: String,
        pHeadOther: String
    ): String
```


### LogJniApi 说明
```java
/**
     * 初始化日志配置
     * @param context 上下文
     * @param logPath 日志存储路径
     * @param cacheDays 缓存天数
     */
    fun init(context: Context, logPath: String?, cacheDays: Int)
```

```java
/**
     * 设置日志等级
     * @param level 一般设置为4
     */
    external fun setLogLevel(level: Int)
```

### VoiceApi 说明
```java
/**
     * 语音上传初始化
     * @param voiceListener 声音设置回调
     * @return 0 成功
     *         非0 失败
     */
    fun voice_speaker_upload_init(voiceListener: VoiceListener): Int 
```

```java
/**
     *语音开始上传
     * @return 0 成功
     *         非0 失败
     */
    external fun voice_speaker_upload_start(): Int
```

```java
/**
     *语音上传
     * @param data 上传的数据
     * @return 0 成功
     *         非0 失败
     */
    external fun voice_speaker_upload_send(data: ByteArray): Int
```

```java
/**
     *语音停止上传
     * @return 0 成功
     *         非0 失败
     */
    external fun voice_speaker_upload_stop(): Int
```

```java
/**
     * 设置昵称
     * @param mode 删除/设置 模式
     * @param nickName 昵称
     * @param pinyin 昵称拼音
     */
    fun voice_speaker_mqtt_report_nick_name(
        mode: DataEnum.TY_NICK_NAME_MODE_T,
        nickName: String,
        pinyin: String
    ): Int
```

```java
/**
     * 上传语音状态
     * @param tyVoiceDevStatusE
     *        TY_VOICE_DEV_STATUS_NORMAL(101),
     *        TY_VOICE_DEV_STATUS_CALL(102),
     *        TY_VOICE_DEV_STATUS_BT_PLAYING_MEDIA(103),
     * @return 0 成功
     *         非0 失败
     */
    fun speaker_mqtt_report_dev_status(tyVoiceDevStatusE: DataEnum.TY_VOICE_DEV_STATUS_E): Int
```

### ZigbeeJniApi 说明
```java
/**
     * 设置设备状态回调
     * @param dep_tp
     * @param devMgrLsitener
     * @return 0 成功
     *         非0 失败
     */
    fun iot_reg_gw_mgr_cb(dep_tp: Int, devMgrLsitener: ZigbeeListener.DevMgrListener): Int
```

```java
/**
     * zigbee业务初始化
     * @param zigbeeModel
     * @return 0 成功
     *         非0 失败
     */
    fun zigbee_svc_init(zigbeeModel: ZigbeeModel): Int
```

```java
/**
     * zigbee业务启动
     * @param zigbeeModel
     * @return 0 成功
     *         非0 失败
     */
    fun zigbee_svc_start(zigbeeModel: ZigbeeModel): Int
```

```java
/**
     * zigbee发送数据
     * @param zigbeeZ3ApsFrameMode
     * @return 0 成功
     *         非0 失败
     */
    fun ziggbe_send_data(zigbeeZ3ApsFrameMode: ZigbeeZ3ApsFrameMode): Int
```

```java
/**
     * zigbee下删除子设备
     *@param dip 子设备id
     * @return 0 成功
     *         非0 失败
     */
    external fun ziggbe_del_dev(dip: String): Int
```

```java
/**
     * 设置施工模式
     */
    external fun set_engineer_mode()
```

```java
 /**
     * 获取施工模式是否打开
     * @return 1 打开
     *         0 关闭
     */
    external fun get_engineer_mode(): Int
```

```java
/**
     * 设置施工模式使能
     * @return 0 成功
     *         非0 失败
     */
    external fun engineer_set_tuya_zigbee_enable(): Int
```

```java
/**
     * 工程模式启动
     * @param pKey product_key
     * @param engEventListener 工程模式回调
     */
    fun engineer_srv_start(pKey: String, engEventListener: ZigbeeListener.EngEventListener):Int
```

```java
/**
     * 设置zigbee的tx_radio值
     * @param txt_power (values are between -70 and 20）
     * @return 0 成功
     *         非0 失败
     */
    external fun zigbee_set_tx_radio_power(tx_power: Int): Int
```

### 回调说明
```java
网关相关回调
interface GwInfraListener {
        /**
         * 网关重置回调
         * @param type
         */
        fun gwResetCallBack(type: DataEnum.GW_RESET_TYPE_E)

        /**
         * 网关重启回调
         */
        fun gwRebootCallBack()

        /**
         * sdk 接收到后端的升级推送的时候，会触发此接口 附带升级信息
         * @param version
         */
        fun onUpgradeInfo(version: String)


        /**
         * 升级文件开始下载
         */
        fun onUpgradeDownloadStart()

        /**
         * 升级文件下载进度
         */
        fun onUpgradeDownloadUpdate(progress: Int)

        /**
         * sdk 下载升级文件下载完成触发此接口
         */
        fun upgradeFileDownloadFinished(success: Boolean)

        /**
         * 网关配网回调
         * @param url 二维码地址
         */
        fun gwActiveUrlCallBack(url: String)

        /**
         * 网关激活状态回调
         * @param status 0 重置
         *               1 已经激活
         *               2 初次启动
         *               3 激活并启动
         *               4 网关ble激活
         */
        fun gwActiveStatusCallBack(status: Int)
    }

```

```java
interface GwNwkStatusLsitener {
        /**
         * 有线网络状态回调
         * @param status 0 离线
         *               1 在线
         */
        fun getNwkStatCallBack(status: Int)

        /**
         * 无线网络状态回调
         * @param status 0 离线
         *               1 在线
         */
        fun getWifiNwkStatCallBack(status: Int)
    }
```

```java
interface IotAppLogListener {
        /**
         * iot 日志回调
         * @param path 日志路径
         */
        fun gwAppLogPathCallBack(path: String)
    }
```

```java
interface IotDpListener {
        /**
         * dp接收回调
         * @param events  dp集合
         */
        fun gwDpCallBack(events: Array<DPEvent>)
    }
```

```java
interface IotMqProtListener {
        /**
         * 协议2监听回调
         * @param parString 监听信息
         */
        fun iotMqPortCallbackWith2(parString: String)

        /**
         * 协议401监听回调
         * @param parString 监听信息
         */
        fun iotMqPortCallbackWith401(parString: String)

        /**
         * 协议903监听回调
         * @param parString 监听信息
         */
        fun iotMqPortCallbackWith903(parString: String)
    }
```

```java
interface VoiceListener {
    /** 任务提醒回调
     * 云端下发的任务提醒通知。
     * @since 3.1.0
     * @param type 任务类型，值为 TASK_TYPE_XXX 之一：
     * [.TASK_TYPE_NORMAL]
     * [.TASK_TYPE_CLOCK]
     * [.TASK_TYPE_ALERT]
     * [.TASK_TYPE_RING_TONE]
     * [.TASK_TYPE_CALL]
     * [.TASK_TYPE_CALL_TTS]
     * [.TASK_TYPE_INVALD]
     * @param msg 下发的提醒事件，JSON格式的字符串:
     */
    fun onTaskAlert(type: Int, msg: String)

    /**
     * @param mediaAttributes
     */
    fun onCloudMedia(mediaAttributes: Array<MediaAttribute>)

    /** 闹钟回调
     * 设置的闹钟事件触发通知。
     * @since 3.1.0
     * @param alarm 闹钟触发的事件，JSON格式的字符串:
     * <pre>
     * {
     * "text":"收看电视",
     * "type":"tts",
     * "target":"alert"
     * }
    </pre> *
     */
    fun onAlarmClock(alarm: String)

    /** 媒体控制回调
     * 网关下发的媒体控制指令，app 需要更具下发的指令执行对于的操作。
     * @since 3.1.0
     * @param ctl 媒体控制指令，值为 MEDIA_CTL_XXX 之一：
     * [.MEDIA_CTL_MIN]，
     * [.MEDIA_MIC_OPEN]，
     * [.MEDIA_MIC_CLOSE]，
     * [.MEDIA_PLAY]，
     * [.MEDIA_PAUSE]，
     * [.MEDIA_BT_PLAY_OPEN]，
     * [.MEDIA_BT_PLAY_CLOSE]，
     * [.MEDIA_PLAY_NEXT]，
     * [.MEDIA_PLAY_PREV]
     */
    fun onMediaControl(ctl: Int)

    /** 音量设置回调
     * app 需要根据下发的值去设置系统的音量
     * @since 3.1.0
     * @param vol 音量值，取值 0 ~ 100
     */
    fun onMediaVolume(vol: Int)

    /** Mqtt protocol 501 扩展消息接口
     *
     * @param type    消息类型
     * @param msg     Json格式消息
     */
    fun onCloudExtMsg(type: String, msg: String)

    /** 昵称设置回调
     * app 需根据状态调用前端语音接口设置或删除昵称
     * @since 3.1.0
     * @param mode 昵称设状态，值为 NICKNAME_XXX 之一：
     * [.NICKNAME_SET]，
     * [.NICNAME_DEL]，
     * [.NICKNAME_INVALD]
     * @param nickname 昵称名
     * @param pinyin 昵称拼音
     */
    fun onNickName(mode: Int, nickname: String?, pinyin: String?)
}
```

```java
interface DevMgrListener {
        /**
         * 子设备删除回调
         * @param devId
         * @param type  /** mqtt */    GWDEV_DELTYPE_MQTT = 0,
         *              /** sync */    GWDEV_DELTYPE_SYNC = 1,
         */
        fun devDelCallBack(devId: String, type: Int)

        /**
         * 子设备绑定回调
         * @param devId
         * @param opRet
         */
        fun devBindCallBack(devId: String, opRet: Int)

        /**
         * 删除hb回调
         * @param devId
         */
        fun devHbCallBack(devId: String)

        /**
         * 子设备升级回调
         * @param devId
         * @param fw
         */
        fun devUpgardeCallBack(devId: String, fw: FwModel)

        /**
         * 子设备重置回调
         * @param devId
         * @param type
         */
        fun devResetCallBack(devId: String, type: Int)
    }

    interface TyZ3DevListener {
        /**
         * 设备加入 ZigBee 网络回调
         * @param zigbeeZ3DESCMode 设备模型
         */
        fun z3Join(zigbeeZ3DESCMode: ZigbeeZ3DESCMode)

        /**
         * 设备本地离开 ZigBee 网络回调
         * @param dip
         */
        fun z3_leave(dip: String)

        /**
         * 设备 ZCL 数据上报回调
         * @param zigbeeZ3ApsFrameMode
         */
        fun z3_report(zigbeeZ3ApsFrameMode: ZigbeeZ3ApsFrameMode)

        /**
         * ZigBee 模组启动完成通知回调
         */
        fun z3_notify()

        /**
         * 设备升级完成通知回调
         * @param dip
         * @param rc
         * @param version
         */
        fun z3_upgrade_end(dip: String, rc: Int, version: Int)
    }

    interface TyZ3RftestResultListener {
        fun z3RftTestResultCallBack(pack: Int)
    }

    interface EngEventListener {
        /**
         * 工程模式下日志回调
         */
        fun engrGetLogCallBack(path: String)

        /**
         * 工程模式下同步配置回调
         */
        fun engrSyncConfigCallBack()

        /**
         * 工程模式下重置回调
         */
        fun engrResetCallBack()

        /**
         * 工程模式施工完成回调
         */
        fun engrFinishCallBack()
    }
```

### 枚举说明
```java
enum class IOT_GW_NET_TYPE_T(val value: Int) {
        // only support wried
        IOT_GW_NET_WIRED(0),

        // only support wifi
        IOT_GW_NET_WIFI(1),

        // support wired and wifi
        IOT_GW_NET_WIRED_WIFI(2)
    }

    enum class GW_WF_START_MODE(val value: Int) {
        // only have ap-cfg mode
        WF_START_AP_ONLY(0),

        // only have smart-cfg mode
        WF_START_SMART_ONLY(1),

        // have both ap-cfg and smart-cfg. default is ap-cfg mode
        WF_START_AP_FIRST(2),

        // have both ap-cfg and smart-cfg. default is smart-cfg mode
        WF_START_SMART_FIRST(3),

        // ap-cfg and smart-cfg is concurrent
        WF_START_SMART_AP_CONCURRENT(4),

        //  no wifi start mode
        WF_START_MODE_NULL(15)
    }

    // wifi config method select
    enum class GW_WF_CFG_MTHD_SEL(val value: Int) {
        // do not have low power mode
        GWCM_OLD(0),

        // with low power mode
        GWCM_LOW_POWER(1),

        // special with low power mode
        GWCM_SPCL_MODE(2),

        // GWCM_OLD mode with product
        GWCM_OLD_PROD(3),

        // with low power mode && auto cfg
        GWCM_LOW_POWER_AUTOCFG(4),

        // special with low power mode && auto cfg
        GWCM_SPCL_AUTOCFG(5)
    }

    enum class TY_NICK_NAME_MODE_T(val value: Int) {
        TY_NICK_NAME_SET(0),
        TY_NICK_NAME_DEL(1),
    }

    enum class GW_PERMIT_DEV_TP_T(val value: Int) {
        // zigbee
        GP_DEV_ZB(3),

        // ble
        GP_DEV_BLE(1),

        // MCU
        GP_DEV_MCU(9),

        // attach 1
        DEV_ATTACH_MOD_1(10),

        // attach 2
        DEV_ATTACH_MOD_2(11),

        // attach 3
        DEV_ATTACH_MOD_3(12),

        // attach 4
        DEV_ATTACH_MOD_4(13),

        // attach 5
        DEV_ATTACH_MOD_5(14),

        // attach 6
        DEV_ATTACH_MOD_6(15),

        // attach 7
        DEV_ATTACH_MOD_7(16),

        // attach 8
        DEV_ATTACH_MOD_8(17),

        // attach 9
        DEV_ATTACH_MOD_9(18),

        // attach 10
        DEV_ATTACH_MOD_10(19),

        //default
        GP_DEV_DEF(255)
    }

    /**
     * @brief subscirbe DP type
     *
     */
    enum class DP_DEV_TYPE_T(val value: Int) {
        // only subscribe DP of subdevices
        DP_DEV(0),

        // only subscirbe DP of gw
        DP_GW(1),

        // subscirbe DP of gw and subdevices
        DP_GW_DEV(2)
    }

    enum class TY_VOICE_DEV_STATUS_E(val value: Int) {
        TY_VOICE_DEV_STATUS_NORMAL(101),
        TY_VOICE_DEV_STATUS_CALL(102),
        TY_VOICE_DEV_STATUS_BT_PLAYING_MEDIA(103),
    }

    /* tuya sdk gateway reset type */
    enum class GW_RESET_TYPE_E(val value: Int) {
        /**
         * 本地恢复工厂设置
         */
        GW_LOCAL_RESET_FACTORY(0),

        /**
         *远端网关重置，即为通过APP取消与账号绑定
         */
        GW_REMOTE_UNACTIVE(1),

        /**
         * 本地网关重置
         */
        GW_LOCAL_UNACTIVE(2),

        /**
         * 远端恢复工厂设置
         */
        GW_REMOTE_RESET_FACTORY(3),

        /**
         * 恢复数据工厂设置
         */
        GW_RESET_DATA_FACTORY(4),
    };
    
    
     /**
     * avs状态回调
     */
    enum class TUYA_AVS_STAT_TYPE_E(val value: Int) {
        TUYA_AVS_STAT_TYPE_IDLE(0),                         /*< avs idle status */
        TUYA_AVS_STAT_TYPE_UNBIND(1),                           /*< avs unbind status */
        TUYA_AVS_STAT_TYPE_BINDED(2),                           /*< avs binded status */
        TUYA_AVS_STAT_TYPE_BINDING(3),                          /*< avs unbind status */
        TUYA_AVS_STAT_TYPE_LISTENING(4),                     /*< avs listen start status */
        TUYA_AVS_STAT_TYPE_LISTEN_END(5),                       /*< avs listen end status */
        TUYA_AVS_STAT_TYPE_THINKING(6),                         /*< avs think start status */
        TUYA_AVS_STAT_TYPE_SPEAKING(7),                      /*< avs speak start status */
        TUYA_AVS_STAT_TYPE_SPEAK_END(8),                        /*< avs speak end status */
        TUYA_AVS_STAT_TYPE_MIC_ON_TO_OFF(9),                    /*< avs mic off status */
        TUYA_AVS_STAT_TYPE_MIC_OFF_TO_ON(10),                    /*< avs mic on status */
        TUYA_AVS_STAT_TYPE_NOTIFY(11),                           /*< avs notification status */
        TUYA_AVS_STAT_TYPE_NOTIFY_MIC_OFF(12),                   /*< avs notification status when mic if off */
        TUYA_AVS_STAT_TYPE_ALERT(13),                            /*< avs alert status */
        TUYA_AVS_STAT_TYPE_NOTIFY_ALERT(14),                     /*< avs notify & alert status */
        TUYA_AVS_STAT_TYPE_ALERT_MIC_OFF(15),                    /*< avs alert status when mic is off */
        TUYA_AVS_STAT_TYPE_ALERT_NOTIFY_MIC_OFF(16),             /*< avs alert & notify status when mic is off*/
        TUYA_AVS_STAT_TYPE_BT_SCAN(17),                          /*< avs bt scan status */
        TUYA_AVS_STAT_TYPE_BT_SCAN_EXIT(18),                     /*< avs bt scan exit status */
        TUYA_AVS_STAT_TYPE_BT_CONNECTED(19),                     /*< avs bt connected status */
        TUYA_AVS_STAT_TYPE_BT_DISCONNECT(20),                    /*< avs bt disconnect status */
        TUYA_AVS_STAT_TYPE_ALERT_NOTIFY(21),                     /*< avs alert & notify status */
        TUYA_AVS_STAT_TYPE_MUSIC_START(22),                      /*< avs music start status */
        TUYA_AVS_STAT_TYPE_MUSIC_STOP(23),                       /*< avs music stop status */
        TUYA_AVS_STAT_TYPE_VOLUME_CHG(24),                       /*< avs volume change status */
        TUYA_AVS_STAT_TYPE_DISTRUB(25),                          /*< avs distrub status */
        TUYA_AVS_STAT_TYPE_BOOTUP(26),                           /*< avs bootup status */
        TUYA_AVS_STAT_TYPE_SYSTEM_ERROR(27),                     /*< avs system error status */
        TUYA_AVS_STAT_TYPE_SERVICE_ERROR(28),                    /*< avs service error status */
        TUYA_AVS_STAT_TYPE_NETWORK_ERROR(29),                    /*< avs network error status */
    }
```

### avs 说明
```java
/**
     * avs 初始化
     * @param context 上下文
     * @param storePath 路径
     * @param avsListener avs状态回调
     * @return 0 成功
     *         非0 失败
     */
    fun tuya_avs_init(context: Context, storePath: String, avsListener: AvsListener): Int {
        this.avsListener = avsListener
        unzipFromAssets(context, "alexa-res.zip", storePath)
        return tuya_avs_init(storePath, storePath)
    }

    /**
     * avs 停止
     * @return 0 成功
     *         非0 失败
     */
    external fun avs_stop(): Int

    /**
     * mic设置
     * @param on
     * @return 0 成功
     *         非0 失败
     */
    external fun native_setMicrophoneMute(on: Boolean): Int

    /**
     * mic获取mic状态
     */
    external fun native_isMicrophoneMute(): Boolean

    /**
     *设置播放状态
     * @return 0 成功
     *         非0 失败
     */
    external fun native_setSpeakerphoneOn(on: Boolean): Int

    /**
     * 获取播放状态
     */
    external fun native_isSpeakerphoneOn(): Boolean

    /**
     * 设置蓝牙播放状态开关
     * @return 0 成功
     *         非0 失败
     */
    external fun native_setBluetoothOn(on: Boolean): Int

    /**
     * 获取设置蓝牙播放状态开关
     */
    external fun native_isBluetoothOn(): Boolean

    /**
     * 设置音量大小
     * @param index 音量
     * @return 0 成功
     *         非0 失败
     */
    external fun native_setVolume(index: Int): Int

    /**
     * 获取音量大小
     */
    external fun native_getVolume(): Int

    /**
     * avs wakeup切换
     * @return 0 成功
     *         非0 失败
     */
    external fun native_wakeupToggle(): Int

    /**
     * 向前播放
     * @return 0 成功
     *         非0 失败
     */
    external fun native_playPrev(): Int

    /**
     * 向后播放
     * @return 0 成功
     *         非0 失败
     */
    external fun native_playNext(): Int

    /**
     * 上传音频数据
     * @return 0 成功
     *         非0 失败
     */
    external fun native_feedPcm(asr: ByteArray): Int
```

### 混淆说明
```java
添加以下配置：
        -keep class com.tuya.api.**{*;}
        -keep class com.tuya.ble.**{*;}
        -keep class com.tuya.listener.**{*;}
        -keep class com.tuya.model.**{*;}

        ################gson###############
        -keepattributes Signature
        -keepattributes *Annotation*
        -keep class sun.misc.Unsafe { *; }
        -keep class com.google.gson.stream.** { *; }
        # Application classes that will be serialized/deserialized over Gson
        -keep class com.sunloto.shandong.bean.** { *; }

        -keep class com.tencent.mars.xlog.**{*;}
```