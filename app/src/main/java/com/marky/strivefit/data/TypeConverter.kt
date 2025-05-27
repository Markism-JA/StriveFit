// Converters.kt
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromStringList(value: String?): List<Int>? {
        if (value == null) {
            return null
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromListString(list: List<Int>?): String? {
        if (list == null) {
            return null
        }
        return Gson().toJson(list)
        // Add more converters if needed for other complex types stored as JSON strings
    }

}