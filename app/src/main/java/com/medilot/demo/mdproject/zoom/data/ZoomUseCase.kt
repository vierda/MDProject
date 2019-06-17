package com.medilot.demo.mdproject.zoom.data

import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase

class ZoomUseCase : UseCase() {

    private var repository: ZoomRepository = ZoomRepository()

    fun getImage(imageId:String,subscriber: DefaultSubscriber<ByteArray>) {
        execute(repository.getImage(imageId), subscriber)
    }
}