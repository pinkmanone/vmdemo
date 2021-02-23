package com.wc.learn.network

class NetException(val code: Int?, private val msg: String) : Exception(msg) {
}