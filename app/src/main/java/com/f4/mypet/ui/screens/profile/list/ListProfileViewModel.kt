package com.f4.mypet.ui.screens.profile.list

import androidx.lifecycle.ViewModel
import com.f4.mypet.data.db.DBRepository
import javax.inject.Inject

class ListProfileViewModel @Inject constructor(
    private val dbRepo: DBRepository
) : ViewModel() {
    fun getPetsProfiles() {
        dbRepo.getPets()
    }
}