package com.efedaniel.bloodfinder.bloodfinder.notifications

import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NotificationHandlerService : FirebaseMessagingService() {

    @Inject
    lateinit var prefsUtils: PrefsUtils

    @Inject
    lateinit var databaseRepository: DatabaseRepository

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).component.inject(this)
    }

    override fun onNewToken(token: String) {
        Timber.d("The notification token is $token")
        prefsUtils.putString(PrefKeys.DEVICE_NOTIFICATION_TOKEN, token)
        prefsUtils.putBoolean(PrefKeys.IS_NOTIFICATION_SUBSCRIBED, false)
        if (prefsUtils.doesContain(PrefKeys.LOGGED_IN_USER_DATA)) {
            subscribeNotification(token)
        }
    }

    private fun subscribeNotification(token: String) {
        val user = prefsUtils.getPrefAsObject(PrefKeys.LOGGED_IN_USER_DATA, UserDetails::class.java)
        serviceScope.launch {
            when (databaseRepository.saveUserNotificationToken(user.localID!!, token)) {
                is Result.Success -> {
                    prefsUtils.putBoolean(PrefKeys.IS_NOTIFICATION_SUBSCRIBED, true)
                }
                is Result.Error -> {
                    prefsUtils.putBoolean(PrefKeys.IS_NOTIFICATION_SUBSCRIBED, false)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    override fun onMessageReceived(p0: RemoteMessage) {

    }

}