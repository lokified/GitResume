package com.loki.gitresume.data.remote.repository

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.loki.gitresume.domain.repository.ResumeRepository
import com.loki.gitresume.util.DownloadManager
import com.loki.gitresume.util.DownloadStatus
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ResumeRepositoryImpl @Inject constructor(
    private val context: Context,
    private val storage: FirebaseStorage
): ResumeRepository {

    override suspend fun downloadResume(downloadStatus: (DownloadStatus) -> Unit) {

        val uri: Uri = storage.reference.child(RESUME_REFERENCE)
            .child(CHILD_NAME)
            .downloadUrl.await()

        DownloadManager.download(
            context,
            uri,
            onDownloadStatus = downloadStatus
        )
    }

    companion object {
        const val RESUME_REFERENCE = "resume"
        const val CHILD_NAME = "sheldon-okware-resume.pdf"
    }
}