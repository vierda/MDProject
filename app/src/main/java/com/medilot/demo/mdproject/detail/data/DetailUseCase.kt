package com.medilot.demo.mdproject.detail.data

import android.content.Context
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase
import com.medilot.demo.mdproject.detail.presentation.entity.DetailEntity

class DetailUseCase (context:Context) : UseCase(){

    private var repository: DetailRepository = DetailRepository(context)

    fun getDetailEntity(imageId:String,subscriber: DefaultSubscriber<DetailEntity>) {
        execute(repository.getDetailEntity(imageId), subscriber)
    }
}