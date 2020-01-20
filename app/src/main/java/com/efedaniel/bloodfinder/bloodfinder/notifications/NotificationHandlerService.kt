package com.efedaniel.bloodfinder.bloodfinder.notifications

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavDeepLinkBuilder
import com.efedaniel.bloodfinder.App
import com.efedaniel.bloodfinder.MainActivity
import com.efedaniel.bloodfinder.R
import com.efedaniel.bloodfinder.bloodfinder.models.request.BloodPostingRequest
import com.efedaniel.bloodfinder.bloodfinder.models.request.UserDetails
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingRequest.BloodPostingRequestFragment.Companion.BLOOD_POSTING_REQUEST_KEY
import com.efedaniel.bloodfinder.bloodfinder.notifications.bloodPostingResponse.BloodPostingResponseFragment.Companion.BLOOD_POSTING_RESPONSE_KEY
import com.efedaniel.bloodfinder.bloodfinder.repositories.DatabaseRepository
import com.efedaniel.bloodfinder.networkutils.Result
import com.efedaniel.bloodfinder.utils.ApiKeys
import com.efedaniel.bloodfinder.utils.Misc
import com.efedaniel.bloodfinder.utils.PrefKeys
import com.efedaniel.bloodfinder.utils.PrefsUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
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

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val data = remoteMessage.data
        when (data["notificationType"]) {
            ApiKeys.REQUEST_NOTIFICATION_TYPE -> sendRequestNotification(data)
            ApiKeys.ANSWER_NOTIFICATION_TYPE -> sendAnswerNotification(data)
        }
    }

    private fun sendRequestNotification(data: Map<String, String>) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel(notificationManager, Misc.REQUEST_NOTIFICATION_CHANNEL_ID,
                R.string.blood_request_channel, R.string.blood_request_channel_description)
        }
        notificationManager.notify(Random().nextInt(50000), getRequestNotification(data))
    }

    private fun getRequestNotification(data: Map<String, String>): Notification {
        val args = Bundle()
        args.putParcelable(BLOOD_POSTING_REQUEST_KEY, BloodPostingRequest.getBloodPostingFromMap(data))
        val pendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.bloodPostingRequestFragment)
            .setArguments(args)
            .createPendingIntent()

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBody = String.format(getString(R.string.blood_request_message),
            data["bloodProviderFullName"], data["bloodSeekerFullName"])

        return NotificationCompat.Builder(this, Misc.REQUEST_NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_transfusion)
            color = ContextCompat.getColor(this@NotificationHandlerService, R.color.colorAccent)
            setContentTitle(getString(R.string.donation_request))
            setContentText(notificationBody)
            setStyle(NotificationCompat.BigTextStyle().bigText(notificationBody))
            setAutoCancel(true)
            setSound(soundUri)
            setDefaults(Notification.DEFAULT_VIBRATE)
            setWhen(System.currentTimeMillis())
            setContentIntent(pendingIntent)
        }.build()
    }

    private fun sendAnswerNotification(data: Map<String, String>) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannel(notificationManager, Misc.ANSWER_NOTIFICATION_CHANNEL_ID,
                R.string.blood_answer_channel, R.string.blood_answer_channel_description)
        }
        notificationManager.notify(Random().nextInt(50000), getAnswerNotification(data))
    }

    private fun getAnswerNotification(data: Map<String, String>): Notification {
        val args = Bundle()
        args.putParcelable(BLOOD_POSTING_RESPONSE_KEY, BloodPostingRequest.getBloodPostingFromMap(data))
        val pendingIntent = NavDeepLinkBuilder(this)
            .setComponentName(MainActivity::class.java)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.bloodPostingResponseFragment)
            .setArguments(args)
            .createPendingIntent()

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBody = String.format(getString(R.string.blood_response_message),
            data["bloodSeekerFullName"], data["status"], data["bloodProviderFullName"])

        return NotificationCompat.Builder(this, Misc.REQUEST_NOTIFICATION_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_transfusion)
            color = ContextCompat.getColor(this@NotificationHandlerService, R.color.colorAccent)
            setContentTitle(getString(R.string.donation_response))
            setContentText(notificationBody)
            setStyle(NotificationCompat.BigTextStyle().bigText(notificationBody))
            setAutoCancel(true)
            setSound(soundUri)
            setDefaults(Notification.DEFAULT_VIBRATE)
            setWhen(System.currentTimeMillis())
            setContentIntent(pendingIntent)
        }.build()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannel(
        notificationManager: NotificationManager,
        channelID: String,
        @StringRes channelNameID: Int,
        @StringRes channelDescriptionID: Int
    ) {
        notificationManager.createNotificationChannel(NotificationChannel(channelID, getString(channelNameID),
            NotificationManager.IMPORTANCE_HIGH).apply {
            description = getString(channelDescriptionID)
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
        })
    }
}
