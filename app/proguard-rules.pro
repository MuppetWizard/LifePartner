
# 不混淆第三方引用的库
-dontskipnonpubliclibraryclasses

-dontoptimize
# 表示不进行校验,这个校验作用 在java平台上的
-dontpreverify
-optimizationpasses 5

-dontskipnonpubliclibraryclassmembers
-keep class androidx.annotation.**{*;}
-keep class androidx.**{*;}

# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-dontshrink
#保护泛型
-keepattributes Signature

-keepattributes *Annotation*

-keepattributes Exceptions


# 保持基本组件不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# 保持 Google 原生服务需要的类不被混淆
-keep public class com.google.android.material.** { *; }
-keep public class com.alibaba.fastjson.** { *; }
-keep public class com.bigkoo.** { *; }
-keep public class com.youth.banner.** { *; }
-keep public class com.bumptech.glide.** { *; }
-keep public class com.hp.hpl.sparta.** { *; }
-keep public class com.muppet.lifepartner.util.** { *;}

# Support包规则
-dontwarn android.support.**
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留自定义控件(继承自View)不被混淆
-keep public class * extends android.view.View


# 保留在Activity中的方法参数是view的方法(避免布局文件里面onClick被影响)
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

# 保持 BaseAdapter 类不被混淆
-keep public class * extends android.widget.BaseAdapter { *; }
# 保持 CusorAdapter 类不被混淆


# WebView处理，项目中没有使用到webView忽略即可
# 保持Android与JavaScript进行交互的类不被混淆

-keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
     public void *(android.webkit.WebView,java.lang.String);
}


-keep class com.muppet.lifepartner.mode.** { *; }

-keep public class **.*mode*.** {*;}

-keep class **.R$* {*;}
-keepclassmembers enum * { *;}

##kotlin
#-keep class kotlin.** { *; }
#-keep class kotlin.Metadata { *; }
#-dontwarn kotlin.**
#-keepclassmembers class **$WhenMappings {
#    <fields>;
#}
#-keepclassmembers class kotlin.Metadata {
#    public <methods>;
#}
#-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
#    static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
#}
#
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#-keepclassmembers class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#-keep class **.R$* {*;}
#-keepclassmembers enum * { *;}

# Gson
-keepattributes EnclosingMethod
-keep class com.google.gson.** {*;}
-keep class com.google.**{*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
#-libraryjars jars/libs/gson-2.8.5.jar

 # Prevent R8 from leaving Data object members always null
 -keepclassmembers,allowobfuscation class * {
   @com.google.gson.annotations.SerializedName <fields>;
 }

#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
-dontwarn com.bumptech.glide.**

# 穿山甲
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.** {*;}
-keep class com.bytedance.embed_dr.** {*;}
-keep class com.bytedance.embedapplog.** {*;}
#广点通
-keep class com.qq.e.** { *;}
-dontwarn sun.misc.**
#游易
-keep class com.youyi.yesdk.**{*;}
#快手联盟
-keep class org.chromium.** {*;}
-keep class org.chromium.** { *; }
-keep class aegon.chrome.** { *; }
-keep class com.kwai.**{ *; }
-dontwarn com.kwai.**
-dontwarn com.kwad.**
-dontwarn com.ksad.**
-dontwarn aegon.chrome.**