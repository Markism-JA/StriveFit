import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.User

@Entity(
    tableName = "step_record",
    foreignKeys = [
        ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StepRecord (
    @PrimaryKey val date: String,
    @ColumnInfo(name = "user_id") val userId: Int,
    val steps: Int = 0,
    @ColumnInfo(name = "distance_km") val distanceKm: Double = 0.0,
    @ColumnInfo(name = "calories_burned")val caloriesBurned: Double = 0.0,
)

