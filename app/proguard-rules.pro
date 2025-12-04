# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/ahmetyigitdayi/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.

# Keep all data classes used for API requests/responses to prevent obfuscation issues
-keep class com.ch3x.chatlyzer.data.remote.** { *; }
-keep class com.ch3x.chatlyzer.data.remote.dto.** { *; }
