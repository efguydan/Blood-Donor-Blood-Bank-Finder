package com.efedaniel.bloodfinder.bloodfinder.notifications

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class NotificationHandlerService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Timber.d("The notification token is $token")
//        sendRegistrationToServer(token)
    }

}