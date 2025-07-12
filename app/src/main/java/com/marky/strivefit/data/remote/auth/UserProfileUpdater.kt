import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.Result

class UserProfileUpdater @Inject constructor() {
    suspend fun updateDisplayName(user: FirebaseUser, name: String): Result<Unit> {
        return runCatching {
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
            user.updateProfile(profileUpdates).await()
        }
    }
}
