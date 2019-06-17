package com.medilot.demo.mdproject.common.util

class Constant {

    companion object {

        const val IMAGE_ID = "ImageID"
        const val BYTE_IMAGE = "ByteImage"

        const val ADMIN = "Admin"
        const val USER = "User"

        var CURRENT_ROLE = USER
        var CURRENT_USER_ID = 0

        const val PENDING = "Pending"
        const val REJECTED = "Rejected"
        const val APPROVED = "Approved"

        const val SUCCESS_SUBMIT_REVIEW = 100
        const val ERROR_ENOUGH_REVIEWER = 101
        const val ERROR_SUBMIT_REVIEW = 102
        const val ERROR_SAME_REVIEWER = 103
        const val ERROR_REJECT_WITHOUT_COMMENT = 104

    }
}