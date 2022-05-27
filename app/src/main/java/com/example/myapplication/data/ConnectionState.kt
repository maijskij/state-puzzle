package com.example.myapplication.data

enum class ConnectionState(val value: Int) {
    CONNECTION_ERROR(0),
    CONNECTING(1),
    CONNECTION_ESTABLISHED(2);

    companion object {
        fun from(num: Int) =
            when (num) {
                CONNECTION_ERROR.value -> CONNECTION_ERROR
                CONNECTING.value -> CONNECTING
                CONNECTION_ESTABLISHED.value -> CONNECTION_ESTABLISHED
                else -> throw  IllegalArgumentException(num.toString())
            }
    }
}