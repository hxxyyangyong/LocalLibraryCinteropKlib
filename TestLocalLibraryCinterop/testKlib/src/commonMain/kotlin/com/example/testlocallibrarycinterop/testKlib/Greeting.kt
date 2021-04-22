package com.example.testlocallibrarycinterop.testKlib


class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
