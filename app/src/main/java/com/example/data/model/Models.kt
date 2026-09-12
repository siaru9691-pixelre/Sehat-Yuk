package com.example.data.model

enum class MoodType(
    val emoji: String,
    val labelIndo: String,
    val labelSub: String,
    val defaultFeedback: String
) {
    GREAT("🤩", "Semangat", "Great", "Luar biasa! Salurkan energimu untuk hal-hal positif hari ini!"),
    GOOD("😊", "Senang", "Rileks", "Bagus sekali! Pertahankan energi positif dan ketenanganmu hari ini."),
    OKAY("😐", "Biasa", "Okay", "Hari yang tenang. Luangkan sejenak waktu untuk bernapas dan bersyukur."),
    TIRED("🥱", "Lelah", "Tired", "Wajar merasa lelah setelah beraktivitas. Jangan lupa rehat dan cukupi hidrasi."),
    STRESSED("😰", "Cemas", "Stres", "Tarik napas dalam-dalam. Kamu tidak sendiri, ambil jeda sejenak untuk rileks.")
}

data class UserProfile(
    val id: String = "user-1",
    val name: String = "Raditya Pratama",
    val email: String = "raditya.p@kampus.ac.id",
    val major: String = "Mahasiswa Aktif • S1 Ilmu Komputer",
    val level: Int = 4,
    val levelTitle: String = "Pejuang Kebugaran",
    val currentXp: Int = 850,
    val maxXp: Int = 1000,
    val healthPoints: Int = 1450,
    val healthScore: Int = 78,
    val previousScore: Int = 74,
    val currentStreak: Int = 7,
    val longestStreak: Int = 14,
    val activeFocusCount: Int = 4
)

data class HealthGoalItem(
    val id: String,
    val title: String,
    val description: String,
    val categoryBadge: String,
    val streakDays: Int? = null,
    val statusText: String? = null,
    val isSelected: Boolean = true,
    val iconType: String
)

data class DailyTrackingState(
    val dateLabel: String = "Hari ini, 24 Mei",
    val waterCurrentMl: Int = 1500,
    val waterTargetMl: Int = 2000,
    val glassesDrank: Int = 6, // out of 8
    val stepsCurrent: Int = 5240,
    val stepsTarget: Int = 6000,
    val distanceKm: Double = 3.4,
    val caloriesKcal: Int = 245,
    val activeMinutes: Int = 42,
    val hourlySteps: List<Float> = listOf(0.2f, 0.4f, 0.9f, 0.3f, 0.85f, 0.1f),
    val sleepDuration: String = "7 Jam 15 Menit",
    val sleepBedtime: String = "23:30",
    val sleepWakeup: String = "06:45",
    val sleepScore: Int = 88,
    val sleepQuality: String = "Nyenyak"
)

data class DailyTaskItem(
    val id: String,
    val title: String,
    val pointsReward: Int,
    val isCompleted: Boolean
)

data class ChallengeItem(
    val id: String,
    val title: String,
    val description: String,
    val currentProgress: Int,
    val targetProgress: Int,
    val progressLabel: String,
    val xpReward: Int,
    val isCompleted: Boolean,
    val actionText: String,
    val iconType: String
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val progressText: String? = null,
    val progressFraction: Float? = null,
    val iconType: String
)

data class ReminderItem(
    val id: String,
    val title: String,
    val scheduleText: String,
    val note: String,
    val isEnabled: Boolean,
    val iconType: String
)

data class HealthArticle(
    val id: String,
    val title: String,
    val summary: String,
    val content: String,
    val category: String,
    val readTimeMinutes: Int,
    val author: String,
    val rating: Double,
    val isEditorChoice: Boolean = false,
    val sdgTag: String = "SDG 3.4",
    val isBookmarked: Boolean = false
)

data class SDGImpactStats(
    val connectedStudents: String = "1.200+",
    val healthyHabitConsistency: String = "85%",
    val activeMinutesLogged: String = "142.500+",
    val waterGlassesLogged: String = "96.000+"
)
