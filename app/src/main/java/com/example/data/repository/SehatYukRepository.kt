package com.example.data.repository

import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SehatYukRepository {

    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _trackingState = MutableStateFlow(DailyTrackingState())
    val trackingState: StateFlow<DailyTrackingState> = _trackingState.asStateFlow()

    private val _healthGoals = MutableStateFlow(
        listOf(
            HealthGoalItem(
                id = "goal_water",
                title = "Hidrasi Rutin",
                description = "8 gelas / 2 Liter air putih per hari",
                categoryBadge = "5 Hari",
                streakDays = 5,
                statusText = "Pengingat tiap 2 jam • Tercapai 65%",
                isSelected = true,
                iconType = "water"
            ),
            HealthGoalItem(
                id = "goal_steps",
                title = "Aktivitas Langkah",
                description = "Minimal 6.000 langkah keliling kampus",
                categoryBadge = "Kampus Sehat",
                statusText = "Sensor HP Otomatis • 4.120 / 6.000",
                isSelected = true,
                iconType = "walk"
            ),
            HealthGoalItem(
                id = "goal_sleep",
                title = "Waktu Tidur Cukup",
                description = "7–8 jam per malam, batasi begadang tugas",
                categoryBadge = "3 Hari",
                streakDays = 3,
                statusText = "Target tidur 23:00 WIB • Rata-rata 7.2 Jam",
                isSelected = true,
                iconType = "sleep"
            ),
            HealthGoalItem(
                id = "goal_diet",
                title = "Pola Makan Sehat",
                description = "Kurangi gula berlebih & junk food",
                categoryBadge = "Nutrisi",
                statusText = "Piring bergizi seimbang • Belum aktif",
                isSelected = false,
                iconType = "diet"
            ),
            HealthGoalItem(
                id = "goal_mental",
                title = "Kesehatan Mental & Mood",
                description = "Jurnal harian & latihan pernapasan 5 menit",
                categoryBadge = "7 Hari",
                streakDays = 7,
                statusText = "Evaluasi stres mingguan • Aktif setiap malam",
                isSelected = true,
                iconType = "mental"
            ),
            HealthGoalItem(
                id = "goal_screen",
                title = "Istirahat Layar & Mata",
                description = "Aturan 20–20–20 saat ngoding & nugas",
                categoryBadge = "Ergonomi",
                statusText = "Istirahat 20 detik • Belum aktif",
                isSelected = false,
                iconType = "eye"
            )
        )
    )
    val healthGoals: StateFlow<List<HealthGoalItem>> = _healthGoals.asStateFlow()

    private val _dailyTasks = MutableStateFlow(
        listOf(
            DailyTaskItem("task_1", "Minum air setelah bangun pagi", 10, true),
            DailyTaskItem("task_2", "Jalan santai keliling kampus", 20, true),
            DailyTaskItem("task_3", "Istirahat mata 20 detik setelah nugas", 15, false),
            DailyTaskItem("task_4", "Mood check-in malam", 10, false)
        )
    )
    val dailyTasks: StateFlow<List<DailyTaskItem>> = _dailyTasks.asStateFlow()

    private val _challenges = MutableStateFlow(
        listOf(
            ChallengeItem(
                id = "ch_water",
                title = "Tantangan Hidrasi 2 Liter",
                description = "Target optimal konsentrasi kuliah",
                currentProgress = 1500,
                targetProgress = 2000,
                progressLabel = "1.500 / 2.000 ml (75%)",
                xpReward = 50,
                isCompleted = false,
                actionText = "Minum Air",
                iconType = "water"
            ),
            ChallengeItem(
                id = "ch_steps",
                title = "Jalan Kaki Kampus 6.000",
                description = "Keliling gedung fakultas aktif",
                currentProgress = 5240,
                targetProgress = 6000,
                progressLabel = "5.240 / 6.000 langkah (87%)",
                xpReward = 75,
                isCompleted = false,
                actionText = "Cek Sensor",
                iconType = "walk"
            ),
            ChallengeItem(
                id = "ch_eye",
                title = "Istirahat Mata 20–20–20",
                description = "Kurangi radiasi layar laptop tugas",
                currentProgress = 2,
                targetProgress = 3,
                progressLabel = "2 dari 3 sesi",
                xpReward = 30,
                isCompleted = false,
                actionText = "Mulai Timer",
                iconType = "eye"
            ),
            ChallengeItem(
                id = "ch_journal",
                title = "Jurnal Refleksi Malam",
                description = "Tulis 3 hal yang disyukuri hari ini",
                currentProgress = 0,
                targetProgress = 1,
                progressLabel = "Belum dimulai",
                xpReward = 40,
                isCompleted = false,
                actionText = "Tulis Jurnal",
                iconType = "journal"
            )
        )
    )
    val challenges: StateFlow<List<ChallengeItem>> = _challenges.asStateFlow()

    private val _achievements = MutableStateFlow(
        listOf(
            AchievementBadge("ach_first", "Hari Pertama Sehat", "Mulai langkah awal hidup bugar.", true, null, null, "trophy"),
            AchievementBadge("ach_water", "Pahlawan Hidrasi", "Minum 2.000 ml selama 7 hari beruntun.", true, null, null, "water"),
            AchievementBadge("ach_walk", "Pejalan Tangguh", "Capai 6.000 langkah 5 hari seminggu.", true, null, null, "walk"),
            AchievementBadge("ach_mind", "Master Well-being", "Catat mood & napas 7 hari berturut-turut.", false, "4/7 Hari", 4f / 7f, "zen"),
            AchievementBadge("ach_sleep", "Penjaga Ritme Tidur", "Tidur sebelum jam 23:00 konsisten.", false, "2/5 Hari", 2f / 5f, "moon"),
            AchievementBadge("ach_sdg", "Duta Kampus SDG 3", "Selesaikan semua misi bulanan hidup sehat.", false, "Terkunci", 0f, "badge")
        )
    )
    val achievements: StateFlow<List<AchievementBadge>> = _achievements.asStateFlow()

    private val _reminders = MutableStateFlow(
        listOf(
            ReminderItem("rem_1", "Minum Air Tiap 2 Jam", "08:00 – 20:00 (Setiap 120 menit)", "Jaga konsentrasi kuliah & hidrasi", true, "water"),
            ReminderItem("rem_2", "Peregangan & Jalan Santai", "12:30 & 16:30 WIB", "Jeda kelas dan praktikum", true, "walk"),
            ReminderItem("rem_3", "Aturan Layar 20–20–20", "Saat sesi fokus/nugas malam", "Lihat objek 6 meter selama 20 detik", true, "eye"),
            ReminderItem("rem_4", "Pengingat Waktu Tidur 10.45", "22:45 WIB", "Mulai kurangi layar & bersiap istirahat", false, "moon")
        )
    )
    val reminders: StateFlow<List<ReminderItem>> = _reminders.asStateFlow()

    private val _articles = MutableStateFlow(
        listOf(
            HealthArticle(
                id = "art_editor",
                title = "Manajemen Stres & Beban Kuliah: 5 Teknik Berbasis Bukti Ilmiah",
                summary = "Tekanan deadline tugas dan ujian sering memicu kortisol tinggi. Berikut langkah de-eskalasi praktis yang teruji.",
                content = """
                    Sebagai mahasiswa, beban akademik, tugas presentasi, dan ekspektasi sering kali datang bersamaan. Penelitian dari World Health Organization (WHO) dalam kerangka SDG 3 Target 3.4 menekankan pentingnya intervensi awal terhadap stres psikologis generasi muda.

                    Berikut 5 teknik praktis berbasis bukti ilmiah:
                    
                    1. Teknik Pernapasan Box Breathing (4-4-4-4): Tarik napas 4 detik, tahan 4 detik, hembuskan 4 detik, tahan 4 detik. Ini mengaktifkan saraf parasimpatis dan menurunkan detak jantung secara instan.
                    
                    2. Batasi 'Continuous Partial Attention': Saat mengerjakan tugas, matikan notifikasi HP selama 25 menit (Metode Pomodoro). Multitasking meningkatkan kelelahan mental hingga 40%.
                    
                    3. Paparan Sinar Matahari Pagi (15 Menit): Membantu regulasi hormon melatonin dan serotonin untuk siklus sirkadian yang stabil.
                    
                    4. Hidrasi Cukup Sebelum Masuk Ujian: Dehidrasi ringan (1-2%) terbukti menurunkan daya ingat jangka pendek dan konsentrasi analisis numerik.
                    
                    5. Manfaatkan Support System Kampus: Jangan ragu berkonsultasi dengan layanan konseling mahasiswa kampus. Mengakui kelelahan adalah tanda keberanian, bukan kelemahan.
                """.trimIndent(),
                category = "Kesehatan Mental Mahasiswa",
                readTimeMinutes = 4,
                author = "Dr. Annisa, Sp.KJ",
                rating = 4.9,
                isEditorChoice = true,
                sdgTag = "SDG 3.4 Pilihan Editor"
            ),
            HealthArticle(
                id = "art_water",
                title = "Mengapa 8 Gelas Air Sehari Mengubah Daya Ingat Saat Ujian",
                summary = "Kurang minum air membuat otak cepat lelah. Simak riset hidrasi untuk performa kognitif optimal.",
                content = """
                    Otak manusia terdiri dari sekitar 75% air. Ketika kamu mengalami dehidrasi ringan saja (sekitar 1-2%), jaringan otak kehilangan kadar cairan optimal yang berdampak langsung pada kecepatan neurotransmiter.
                    
                    Manfaat hidrasi konsisten bagi mahasiswa:
                    - Meningkatkan kecepatan kalkulasi logika matematika dan coding
                    - Mencegah sakit kepala berdenyut saat membaca literatur panjang
                    - Menjaga kelembapan mata saat menatap layar komputer berjam-jam
                    
                    Tips: Siapkan tumbler 600ml di meja belajarmu dan isi ulang 3 kali sehari.
                """.trimIndent(),
                category = "Hidrasi",
                readTimeMinutes = 3,
                author = "Tim Nutrisi SehatYuk",
                rating = 4.9,
                sdgTag = "SDG 3.5"
            ),
            HealthArticle(
                id = "art_eye",
                title = "Aturan 20–20–20: Mencegah Mata Kering Saat Skripsian",
                summary = "Setiap 20 menit menatap layar, pandanglah objek sejauh 20 kaki (6 meter) selama 20 detik.",
                content = """
                    Computer Vision Syndrome (CVS) dialami oleh lebih dari 68% mahasiswa yang menghabiskan lebih dari 6 jam per hari di depan layar laptop atau tablet.
                    
                    Gejala CVS meliputi: mata merah, buram sesaat, leher kaku, dan nyeri di belakang bola mata.
                    
                    Solusi 20-20-20:
                    1. Pasang timer setiap 20 menit saat nugas.
                    2. Alihkan pandangan ke jendela luar atau objek berjarak minimal 6 meter.
                    3. Berkedip perlahan 10 kali untuk meratakan lapisan air mata alami.
                    
                    Langkah kecil ini menjaga ketajaman penglihatanmu hingga lulus nanti!
                """.trimIndent(),
                category = "Ergonomi",
                readTimeMinutes = 2,
                author = "dr. Farhan, Sp.M",
                rating = 4.7,
                sdgTag = "SDG 3.8"
            ),
            HealthArticle(
                id = "art_food",
                title = "Menu Makanan Kost Ramah Kantong tapi Kaya Nutrisi",
                summary = "Pilihan makanan sehat anak kost dengan budget di bawah Rp15.000 per porsi.",
                content = """
                    Makan sehat tidak harus mahal atau berlangganan catering premium. Kunci pemenuhan gizi seimbang mahasiswa ada pada variasi sumber protein nabati dan serat:
                    
                    - Telur rebus / dadar daun bawang (Protein tinggi, kolin untuk otak)
                    - Tahu & Tempe bacem/panggang (Isoflavon & prebiotik alami murah meriah)
                    - Sayur bening bayam jagung (Zat besi tinggi untuk mencegah anemia)
                    - Pisang ambon lokal (Kalium untuk mencegah kram otot setelah jalan kaki)
                    
                    Hindari mengandalkan mie instan lebih dari 2 kali seminggu demi kesehatan ginjal dan tekanan darah jangka panjang.
                """.trimIndent(),
                category = "Nutrisi",
                readTimeMinutes = 5,
                author = "Siti Rahma, S.Gz",
                rating = 4.8,
                sdgTag = "SDG 3.4"
            )
        )
    )
    val articles: StateFlow<List<HealthArticle>> = _articles.asStateFlow()

    private val _currentMood = MutableStateFlow(MoodType.GOOD)
    val currentMood: StateFlow<MoodType> = _currentMood.asStateFlow()

    private val _moodFeedback = MutableStateFlow(MoodType.GOOD.defaultFeedback)
    val moodFeedback: StateFlow<String> = _moodFeedback.asStateFlow()

    private val _journalNotes = MutableStateFlow("")
    val journalNotes: StateFlow<String> = _journalNotes.asStateFlow()

    private val _selectedTriggers = MutableStateFlow(setOf("Kuliah / Tugas"))
    val selectedTriggers: StateFlow<Set<String>> = _selectedTriggers.asStateFlow()

    private val _lastReflectionSavedMessage = MutableStateFlow<String?>(null)
    val lastReflectionSavedMessage: StateFlow<String?> = _lastReflectionSavedMessage.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    fun addWater(amountMl: Int) {
        _trackingState.update { current ->
            val newAmount = (current.waterCurrentMl + amountMl).coerceAtLeast(0)
            val newGlasses = ((newAmount.toFloat() / current.waterTargetMl) * 8).toInt().coerceIn(0, 8)
            current.copy(
                waterCurrentMl = newAmount,
                glassesDrank = newGlasses
            )
        }
        // Update user XP slightly for healthy habit
        if (amountMl > 0) {
            _userProfile.update { it.copy(healthPoints = it.healthPoints + 5) }
        }
    }

    fun toggleGlass(index: Int) {
        _trackingState.update { current ->
            val newGlasses = if (index + 1 == current.glassesDrank) index else (index + 1)
            val newAmount = (newGlasses * (current.waterTargetMl / 8))
            current.copy(
                glassesDrank = newGlasses,
                waterCurrentMl = newAmount
            )
        }
    }

    fun addSteps(delta: Int) {
        _trackingState.update { current ->
            val newSteps = (current.stepsCurrent + delta).coerceAtLeast(0)
            val newDist = String.format("%.1f", newSteps * 0.0007).toDoubleOrNull() ?: 3.5
            val newCalories = (newSteps * 0.04).toInt()
            current.copy(
                stepsCurrent = newSteps,
                distanceKm = newDist,
                caloriesKcal = newCalories
            )
        }
    }

    fun toggleDailyTask(taskId: String) {
        _dailyTasks.update { list ->
            list.map { task ->
                if (task.id == taskId) {
                    val completed = !task.isCompleted
                    if (completed) {
                        _userProfile.update { it.copy(healthPoints = it.healthPoints + task.pointsReward) }
                    }
                    task.copy(isCompleted = completed)
                } else task
            }
        }
    }

    fun completeChallenge(challengeId: String) {
        _challenges.update { list ->
            list.map { ch ->
                if (ch.id == challengeId && !ch.isCompleted) {
                    _userProfile.update { user ->
                        val newXp = user.currentXp + ch.xpReward
                        user.copy(
                            currentXp = newXp,
                            healthPoints = user.healthPoints + ch.xpReward
                        )
                    }
                    ch.copy(isCompleted = true, currentProgress = ch.targetProgress)
                } else ch
            }
        }
    }

    fun setMood(mood: MoodType) {
        _currentMood.value = mood
        _moodFeedback.value = mood.defaultFeedback
    }

    fun toggleTrigger(trigger: String) {
        _selectedTriggers.update { current ->
            if (current.contains(trigger)) current - trigger else current + trigger
        }
    }

    fun saveReflection(notes: String) {
        _journalNotes.value = notes
        _lastReflectionSavedMessage.value = "Refleksi berhasil disimpan! +15 Poin Sehat 🎉"
        _userProfile.update { it.copy(healthPoints = it.healthPoints + 15) }
    }

    fun clearSavedMessage() {
        _lastReflectionSavedMessage.value = null
    }

    fun toggleReminder(reminderId: String) {
        _reminders.update { list ->
            list.map { if (it.id == reminderId) it.copy(isEnabled = !it.isEnabled) else it }
        }
    }

    fun addReminder(title: String, time: String, freq: String) {
        val newRem = ReminderItem(
            id = "rem_${System.currentTimeMillis()}",
            title = title,
            scheduleText = "$time WIB ($freq)",
            note = "Pengingat kustom ritme sehat",
            isEnabled = true,
            iconType = "bell"
        )
        _reminders.update { it + newRem }
    }

    fun toggleGoalSelection(goalId: String) {
        _healthGoals.update { list ->
            list.map { if (it.id == goalId) it.copy(isSelected = !it.isSelected) else it }
        }
        val count = _healthGoals.value.count { it.isSelected }
        _userProfile.update { it.copy(activeFocusCount = count) }
    }

    fun toggleBookmark(articleId: String) {
        _articles.update { list ->
            list.map { if (it.id == articleId) it.copy(isBookmarked = !it.isBookmarked) else it }
        }
    }

    fun toggleNotifications() {
        _notificationsEnabled.update { !it }
    }

    fun resetDemoData() {
        _userProfile.value = UserProfile()
        _trackingState.value = DailyTrackingState()
        _dailyTasks.value = listOf(
            DailyTaskItem("task_1", "Minum air setelah bangun pagi", 10, true),
            DailyTaskItem("task_2", "Jalan santai keliling kampus", 20, true),
            DailyTaskItem("task_3", "Istirahat mata 20 detik setelah nugas", 15, false),
            DailyTaskItem("task_4", "Mood check-in malam", 10, false)
        )
    }
}
