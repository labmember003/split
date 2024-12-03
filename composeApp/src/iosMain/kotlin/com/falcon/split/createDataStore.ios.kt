@file:OptIn(ExperimentalForeignApi::class)

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.falcon.split.utils.DATA_STORE_FILE_NAME
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

fun createDataStore():DataStore<Preferences>{
    return com.falcon.split.utils.createDataStore {
        val directory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        requireNotNull(directory).path + "/$DATA_STORE_FILE_NAME"
    }
}