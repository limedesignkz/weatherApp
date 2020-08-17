package kz.limedesign.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.SettingsClient
import kz.limedesign.weatherapp.App

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return App.instance.getString(stringRes, *formatArgs)
    }

    fun appendCelciusDegree(s: String): String {
        return StringBuilder(s).append(" \\u2103 C").toString()
    }
}

object Logger {

    private enum class LogStatus(val isLogging: Boolean) {
        VERBOSE(true),
        INFO(true),
        DEBUG(true),
        WARN(true),
        ERROR(true)
    }

    @JvmOverloads
    @JvmStatic
    fun v(vararg messages: String?) {
        val result: StringBuilder = StringBuilder()
        for (msg in messages) result.append(msg).append(", ")
        if(LogStatus.VERBOSE.isLogging) Log.v(
            getCallerClassName(), result.toString() ?: " ")
    }

    @JvmOverloads
    @JvmStatic
    fun i(vararg messages: String?) {
        val result: StringBuilder = StringBuilder()
        for (msg in messages) result.append(msg).append(", ")
        if(LogStatus.INFO.isLogging) Log.i(
            getCallerClassName(), result.toString() ?: " ")
    }

    @JvmOverloads
    @JvmStatic
    fun w(vararg messages: String?) {
        val result: StringBuilder = StringBuilder()
        for (msg in messages) result.append(msg).append(", ")
        if(LogStatus.WARN.isLogging) Log.w(
            getCallerClassName(), result.toString() ?: " ")
    }

    @JvmOverloads
    @JvmStatic
    fun d(vararg messages: String?) {
        if (LogStatus.DEBUG.isLogging) {
            var result = messages.joinToString()
            result = if (result.isEmpty()) " " else result
            Log.d(getCallerClassName(), result)
        }
    }


    @JvmOverloads
    @JvmStatic
    fun e(vararg messages: String?) {
        if(LogStatus.ERROR.isLogging) Log.e(
            getCallerClassName(), messages.joinToString() ?: " ")
    }

    private fun getCallerClassName(): String? {
        val stElements = Thread.currentThread().stackTrace
        for (i in 1 until stElements.size) {
            val ste = stElements[i]
            if (ste.className != Logger::class.java.getName() && ste.className
                    .indexOf("java.lang.Thread") != 0
            ) {
                return String.format("%s %s() (%d)", ste.className, ste.methodName, ste.lineNumber)
            }
        }
        return null
    }
}

class Gps(private val context: Context) {

    private val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
    private val locationSettingsRequest: LocationSettingsRequest?
    private val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    init {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationLiveData.locationRequest)
        locationSettingsRequest = builder.build()
        builder.setAlwaysShow(true)
    }

    fun turnGPSOn(OnGpsListener: OnGpsListener?) {

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGpsListener?.gpsStatus(true)
        } else {
            settingsClient
                .checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(context as Activity) {
                    //  GPS is already enable, callback GPS status through listener
                    OnGpsListener?.gpsStatus(true)
                }
                .addOnFailureListener(context) { e ->
                    when ((e as ApiException).statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->

                            try {
                                // Show the dialog by calling startResolutionForResult(), and check the
                                // result in onActivityResult().
                                val rae = e as ResolvableApiException
                                rae.startResolutionForResult(context,
                                    GPS_REQUEST
                                )
                            } catch (sie: IntentSender.SendIntentException) {
                                Logger.i("PendingIntent unable to execute request.")
                            }

                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                            val errorMessage =
                                "Location settings are inadequate, and cannot be fixed here. Fix in Settings."
                            Logger.e(errorMessage)

                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }

    interface OnGpsListener {
        fun gpsStatus(isGPSEnable: Boolean)
    }
}

const val LOCATION_REQUEST = 100
const val GPS_REQUEST = 101