package com.medilot.demo.mdproject.gallery.data

import com.medilot.demo.mdproject.common.data.model.Image
import com.medilot.demo.mdproject.common.util.DefaultSubscriber
import com.medilot.demo.mdproject.common.util.UseCase


class GalleryUseCase : UseCase() {

    private var repository: GalleryRepository = GalleryRepository()

    fun getAllImages(subscriber: DefaultSubscriber<List<Image>>) {
        execute(repository.getAllImages(), subscriber)
    }
}