package com.medilot.demo.mdproject.common.util

import io.reactivex.Scheduler


interface PostExecutionThread {
    val scheduler: Scheduler
}