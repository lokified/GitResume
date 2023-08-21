package com.loki.gitresume.util

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.loki.gitresume.data.remote.repository.ResumeRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DownloadManager {

    private const val FILE_NAME = "sheldon-okware-resume.pdf"

    @SuppressLint("Range")
    suspend fun download(
        context: Context,
        uri: Uri,
        onDownloadStatus: (DownloadStatus) -> Unit
    ) {

        onDownloadStatus(DownloadStatus.OnDownloadStarted)

        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            FILE_NAME
        ).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setTitle("Sheldon's Resume")


        val downloadId = downloadManager.enqueue(request)

        withContext(Dispatchers.IO) {
            var isDownloadFinished = false

            while (!isDownloadFinished) {

                val cursor =
                    downloadManager.query(DownloadManager.Query().setFilterById(downloadId))

                if (cursor.moveToFirst()) {

                    when (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))) {

                        DownloadManager.STATUS_SUCCESSFUL -> {
                            isDownloadFinished = true
                            onDownloadStatus(DownloadStatus.OnDownloadSuccess)
                        }

                        DownloadManager.STATUS_PAUSED, DownloadManager.STATUS_PENDING -> {
                        }

                        DownloadManager.STATUS_FAILED -> {
                            onDownloadStatus(DownloadStatus.OnDownloadFailed)
                            isDownloadFinished = true
                        }
                    }
                } else {
                    // Download cancelled by the user.
                    isDownloadFinished = true
                    onDownloadStatus(DownloadStatus.OnDownloadFailed)
                }

                cursor.close()
            }
        }
    }
}

sealed  class DownloadStatus(val message: String) {
    object OnDownloadStarted: DownloadStatus("Downloading")
    object OnDownloadSuccess: DownloadStatus("Download success")
    object OnDownloadFailed: DownloadStatus("Download Failed")
}