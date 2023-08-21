package com.loki.gitresume.data.permissions

sealed class PermissionAction {
    object PermissionGranted: PermissionAction()
    object PermissionDenied: PermissionAction()
    object PermissionAlreadyGranted: PermissionAction()
}