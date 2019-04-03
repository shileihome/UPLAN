# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/cfp/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

################support###############
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**


-keep class com.uplan.zrx.app.widget.** { *; } #自定义控件不参与混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepnames class * implements java.io.Serializable
-keepattributes Signature
-keep class **.R$* {*;}
-ignorewarnings
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keepclasseswithmembernames class * { # 保持native方法不被混淆
    native <methods>;
}

-keepclassmembers enum * {  # 使用enum类型时需要注意避免以下两个方法混淆，因为enum类的特殊性，以下两个方法会被反射调用，
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

################retrofit###############
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

################butterknife###############
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
   @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
 @butterknife.* <methods>;
}


################gson###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
# Application classes that will be serialized/deserialized over Gson
-keep class com.sunloto.shandong.bean.** { *; }

################glide###############
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class com.bumptech.glide.** { *; }
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}

################okhttp###############
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn com.squareup.okhttp.**


################Rxjava and RxAndroid###############
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-keep class io.reactivex.** { *; }
-keep interface io.reactivex.** { *; }

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn io.reactivex.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class io.reactivex.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn io.reactivex.internal.util.unsafe.**

################RxLifeCycle#################
-keep class com.trello.rxlifecycle2.** { *; }
-keep interface com.trello.rxlifecycle2.** { *; }

################RxPermissions#################
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }

################RxErrorHandler#################
-keep class com.uplan.zrx.app.net.ErrorHandleSubscriber{ *; }

################Timber#################
-dontwarn org.jetbrains.annotations.**
-dontwarn javax.annotation.**

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

# 实例类
-keep class com.uplan.zrx.app.**.entity.** { *; }
-keep class com.uplan.zrx.app.net.ResponseData { *; }

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# address_selector
-keep class com.smarttop.library.** {*;}

# 阿里云视频
-ignorewarnings
-dontwarn okio.**
-dontwarn com.google.common.cache.**
-dontwarn java.nio.file.**
-dontwarn sun.misc.**
-keep class android.support.v4.** { *; }
-keep class android.support.v7.** { *; }
-keep class okhttp3.** { *; }
-keep class com.bumptech.glide.integration.okhttp3.** { *; }
-keep class com.liulishuo.filedownloader.** { *; }
-keep class java.nio.file.** { *; }
-keep class sun.misc.** { *; }

## 录制混淆
-keep class com.qu.preview.** { *; }
-keep class com.qu.mp4saver.** { *; }
-keep class com.duanqu.transcode.** { *; }
-keep class com.duanqu.qupai.render.** { *; }
-keep class com.duanqu.qupai.player.** { *; }
-keep class com.duanqu.qupai.audio.** { *; }
-keep class com.aliyun.qupai.encoder.** { *; }
-keep class com.sensetime.stmobile.** { *; }
-keep class com.duanqu.qupai.yunos.** { *; }
-keep class com.aliyun.common.** { *; }
-keep class com.aliyun.jasonparse.** { *; }
-keep class com.aliyun.struct.** { *; }
-keep class com.aliyun.recorder.AliyunRecorderCreator { *; }
-keep class com.aliyun.recorder.supply.** { *; }
-keep class com.aliyun.querrorcode.** { *; }
-keep class com.qu.preview.callback.** { *; }
-keep class com.aliyun.qupaiokhttp.** { *; }
-keep class com.aliyun.crop.AliyunCropCreator { *; }
-keep class com.aliyun.crop.struct.CropParam { *; }
-keep class com.aliyun.crop.supply.** { *; }
-keep class com.aliyun.qupai.editor.pplayer.AnimPlayerView { *; }
-keep class com.aliyun.qupai.editor.impl.AliyunEditorFactory { *; }
-keep interface com.aliyun.qupai.editor.** { *; }
-keep interface com.aliyun.qupai.import_core.AliyunIImport { *; }
-keep class com.aliyun.qupai.import_core.AliyunImportCreator { *; }
-keep class com.aliyun.qupai.encoder.** { *; }
-keep class com.aliyun.leaktracer.** { *;}
-keep class com.duanqu.qupai.adaptive.** { *; }
-keep class com.aliyun.thumbnail.** { *;}
-keep class com.aliyun.demo.importer.media.MediaCache { *;}
-keep class com.aliyun.demo.importer.media.MediaDir { *;}
-keep class com.aliyun.demo.importer.media.MediaInfo { *;}
-keep class com.alivc.component.encoder.**{ *;}
-keep class com.aliyun.log.core.AliyunLogCommon { *;}
-keep class com.aliyun.log.core.AliyunLogger { *;}
-keep class com.aliyun.log.core.AliyunLogParam { *;}
-keep class com.aliyun.log.core.LogService { *;}
-keep class com.aliyun.log.struct.** { *;}
-keep class com.aliyun.demo.publish.SecurityTokenInfo { *; }

-keep class com.aliyun.vod.common.** { *; }
-keep class com.aliyun.vod.jasonparse.** { *; }
-keep class com.aliyun.vod.qupaiokhttp.** { *; }
-keep class com.aliyun.vod.log.core.AliyunLogCommon { *;}
-keep class com.aliyun.vod.log.core.AliyunLogger { *;}
-keep class com.aliyun.vod.log.core.AliyunLogParam { *;}
-keep class com.aliyun.vod.log.core.LogService { *;}
-keep class com.aliyun.vod.log.struct.** { *;}
-keep class com.aliyun.auth.core.**{*;}
-keep class com.aliyun.auth.common.AliyunVodHttpCommon{*;}
-keep class com.alibaba.sdk.android.vod.upload.exception.**{*;}
-keep class com.alibaba.sdk.android.vod.upload.auth.**{*;}
-keep class com.aliyun.auth.model.**{*;}
-keep class component.alivc.com.facearengine.** {*;}
-keep class com.aliyun.svideo.sdk.external.struct.**{*;}
-keep class com.aliyun.svideo.sdk.internal.common.project.* {*;}

-keep class **.R$* { *; }

## Event Bus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}

## 播放混淆
-keep class com.alivc.player.**{*;}
-keep class com.aliyun.clientinforeport.**{*;}
-dontwarn com.alivc.player.**


## umeng
-keep class com.umeng.** {*;}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class com.uplan.zrx.app.R$*{
    public static final int *;
}

-keepattributes SourceFile,LineNumberTable

# feedback
-keep class com.yitoudai.feedback.** {*;}
-keep class javax.mail.**{*;}
-keep class javax.mail.internet.**{*;}
-keep class com.sun.activation.registries.**{*;}
-keep class javax.activation.**{*;}
-keep class myjava.awt.datatransfer.**{*;}
-keep class org.apache.harmony.**{*;}
-keep class com.sun.mail.**{*;}