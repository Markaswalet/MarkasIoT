package com.example.markasiot.data

import com.example.markasiot.presentation.sign_in.UserData

val sampleData: List<IotData> = listOf(
    IotData(1, 30.9, 46.7, 5.7),
    IotData(2, 25.3, 58.9, 2.3),
    IotData(3, 35.8, 81.8, 10.2)
)

val dataIoT: List<IotData> = listOf(
    IotData(1, 0.0, 0.0, 0.0),
    IotData(2, 0.0, 0.0, 0.0),
    IotData(3, 0.0, 0.0, 0.0)
)

val sampleSwitchData: List<SwitchData> = listOf(
    SwitchData(false),
    SwitchData(false),
    SwitchData(false),
    SwitchData(false),
)

val sampleUserData: UserData = UserData("126273673", "Arya Adikusuma", "https://example.com/image.jpg", "088467373737", "dd")