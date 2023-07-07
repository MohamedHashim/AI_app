package com.mohamed.ai_app.helpers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

/**
 * Created by Mohamed Hashim on 07/07/2023.
 */

object Utils {
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

    fun hasPermissions(context: Context) =
        PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(
                context,
                it,
            ) == PackageManager.PERMISSION_GRANTED
        }
}
