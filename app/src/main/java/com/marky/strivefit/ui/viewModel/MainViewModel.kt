package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.marky.strivefit.data.local.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
   private val userRepository: UserRepository,
    private val auth: FirebaseAuth
): ViewModel() {
   val isUserSetupComplete: StateFlow<Boolean> = flow {

       val userId = auth.currentUser?.uid
       if (userId == null){
           emit(false)
           return@flow
       }

       userRepository.getUser(userId).collect { userEntity ->
           emit(userEntity?.setupFinished ?: false)
       }
   }.stateIn(
       scope = viewModelScope,
       started = SharingStarted.WhileSubscribed(5000),
       initialValue = false
   )
}