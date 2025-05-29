package com.android.firebasesongs.domain

import com.android.firebasesongs.data.Repository

class CanAccessToApp {
    private val repository = Repository()

    suspend operator fun invoke(): Boolean {
        val currentVersion = repository.getCurrentVersion() // 1.1
        val minAllowedVersion = repository.getMinAllowedVersion() // 1.0

        for((currentPart, minVersionPart) in currentVersion.zip(minAllowedVersion)){
            if(currentPart!=minVersionPart){
                return currentPart > minVersionPart
            }
        }
        return true
    }
}