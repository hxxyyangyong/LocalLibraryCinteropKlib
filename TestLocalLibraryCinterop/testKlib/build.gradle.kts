import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.PrintStream

plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

val isOpenFB = false
kotlin {
    android()

    val faDef = project.file("src/nativeInterop/cinterop/FA.def")
    if (faDef.exists()) {
        try {
            val ps = PrintStream(FileOutputStream(faDef))
            ps.println("language = Objective-C\n" +
                    "package = com.yy.FA\n" +
                    "headers = ${projectDir.parent}/extframework/FA.framework/Headers/AAA.h\n" +
                    "libraryPaths = ${projectDir.parent}/extframework\n"+
                    "staticLibraries = FA.framework")
        } catch (e: FileNotFoundException) {
            println("重写 def 文件失败")
        }
    }

    if(isOpenFB) {
        val fbDef = project.file("src/nativeInterop/cinterop/FB.def")
        if (fbDef.exists()) {
            try {
                val ps = PrintStream(FileOutputStream(fbDef))
                ps.println("language = Objective-C\n" +
                        "package = com.yy.FB\n" +
                        "headers = ${projectDir.parent}/extframework/FB.framework/Headers/BBB.h\n" +
                        "libraryPaths = ${projectDir.parent}/extframework\n" +
                        "staticLibraries = FB.framework")
            } catch (e: FileNotFoundException) {
                println("重写 def 文件失败")
            }
        }
    }


    iosX64 {
        binaries {
            framework {
                baseName = "testKlib"
                isStatic = true
            }
        }
        compilations["main"].cinterops.create("FA") {
            defFile = project.file("src/nativeInterop/cinterop/FA.def")
            packageName = "com.yy.fa"
        }
        if(isOpenFB) {
            compilations["main"].cinterops.create("FB") {
                defFile = project.file("src/nativeInterop/cinterop/FB.def")
                packageName = "com.yy.fb"
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
//        val iosMain by getting
        val iosX64Main by getting{
            this.kotlin.srcDirs("src/iosMain/kotlin")
        }
//        val iosTest by getting
    }
}

android {
    compileSdkVersion(29)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(24)
        targetSdkVersion(29)
    }
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework = kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}

tasks.getByName("build").dependsOn(packForXcode)