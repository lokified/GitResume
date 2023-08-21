package com.loki.gitresume.domain.repository

import com.loki.gitresume.util.DownloadStatus
import com.loki.gitresume.util.Resource
import kotlinx.coroutines.flow.Flow

interface ResumeRepository {

    suspend fun downloadResume(downloadStatus: (DownloadStatus) -> Unit)
}