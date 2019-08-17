# By default, the flags in this file are appended to flags specified
# in /usr/share/android-studio/data/sdk/tools/proguard/proguard-android.txt

# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

##---------------Begin: proguard configuration common for all Android apps ----------
-optimizationpasses 5
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-useuniqueclassmembernames
-allowaccessmodification
-keepattributes *Annotation*
-renamesourcefileattribute SourceFile
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
-repackageclasses ''

-ignorewarnings
#-keep class * {
#    public private *;
#}

# Explicitly preserve all serialization members. The Serializable interface #

# Preserve all native method names and the names of their classes.

-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
        public <init>(android.content.Context, android.util.AttributeSet);
        public <init>(android.content.Context);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
  public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#Google play services proguard rules:
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-dontwarn com.google.android.gms.**

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
##---------------End: proguard configuration common for all Android apps ----------

## Keep the support library
#-keep class android.support.v4.** { *; }
#-keep interface android.support.v4.** { *; }
#
## The support library contains references to newer platform versions.
## Don't warn about those in case this app is linking against an older
## platform version. We know about them, and they are safe.
#-dontwarn android.support.**
##---------------End: proguard configuration for Gson  ----------

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

-dontwarn javax.annotation.**

-dontnote rx.internal.util.PlatformDependent

## Dagger2
-dontwarn com.google.errorprone.annotations.*

-keepattributes InnerClasses
 -keep class **.R
 -keep class **.R$* {
    <fields>;
}

-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**


