package com.falcon.split

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.falcon.split.data.network.ApiClient
import com.falcon.split.data.network.createHttpClient
import com.falcon.split.payment.AndroidPaymentHandler
import com.falcon.split.payment.ui.UPIPaymentScreen
import com.falcon.split.payment.viewmodel.UPIPaymentViewModel
import io.ktor.client.engine.okhttp.OkHttp



class MainActivity : ComponentActivity() {
    private val paymentHandler by lazy { AndroidPaymentHandler(applicationContext, this) }
    private val viewModel by lazy { UPIPaymentViewModel(paymentHandler) }
    companion object {
        const val NEWS_PATH_SEGMENT = "Split"
    }

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            // Perform Some Code During Splash Screen
        }
        setContent {
            val paymentState by viewModel.paymentState.collectAsState()
//            UPIPaymentScreen(
//                paymentState = paymentState,
//                onInitiatePayment = { payment ->
//                    viewModel.initiatePayment(payment)
//                }
//            )
            App(
                client = remember {
                    ApiClient(createHttpClient(OkHttp.create()))
                },
                prefs = remember {
                    createDataStore(context = this)
                },
            )
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AndroidPaymentHandler.UPI_PAYMENT_REQUEST) {
            viewModel.handlePaymentResult(resultCode, data)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.let {
//             TODO: Handle new intent (if app is already running)
            val deepLinkNewsId = handleDeepLink(it)
            // Update your newsId state
        }
    }

    // Handle the deep link intent and extract the newsId
    private fun handleDeepLink(intent: Intent?): String {
        intent?.data?.let { uri ->
            if (uri.pathSegments.isNotEmpty() && uri.pathSegments[0] == "news") {
                return uri.lastPathSegment ?: ""
            }
        }
        return ""
    }
}