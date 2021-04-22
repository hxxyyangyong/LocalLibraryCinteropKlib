package com.example.testlocallibrarycinterop.testKlib


import com.yy.fa.AAA
import platform.UIKit.UIDevice

actual class Platform actual constructor() {
    actual val platform: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion+"CallFA::"+AAA().funA()
}