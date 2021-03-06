# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\AndroidStudioSDK/tools/proguard/proguard-android.txt
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
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-ignorewarnings
-dontwarn
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.support.v7.app.ActionBarActivity
-keep public class com.android.vending.licensing.ILicensingService
-keepclasseswithmembernames class * { native <methods>;}
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.support.v7.app.ActionBarActivity {
 public protected <methods>;
}
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

-keep class com.loopj.android.http.** {*;}
-keep class com.wooyun.android.utils.** {*;}
-keep class com.wooyun.security.bean.** {*;}

-keep class com.google.json.** {*;}
-keep class com.nostra13.universalimageloader.** {*;}
-keep class com.readystatesoftware.systembartint.** {*;}

-keep class android.support.v4.** {*;}
-keep class android.support.v7.appcompat.** {*;}
-keep class android.support.v7.gridlayout** {*;}
-keep class android.support.v7.palette.** {*;}
-keep class android.support.v7.recyclerview.** {*;}

-dontwarn com.google.**
-keep class com.google.gson.** {*;}
