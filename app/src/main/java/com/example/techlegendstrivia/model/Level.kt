package com.example.techlegendstrivia.model

import androidx.annotation.StringRes
import com.example.techlegendstrivia.R

enum class Level(@StringRes val displayNameRes: Int, val skips: Int, val helps: Int) {
    EASY(displayNameRes = R.string.easy_level, skips = 5, helps = 3),
    MEDIUM(displayNameRes = R.string.medium_level, skips = 3, helps = 2),
    ADVANCED(displayNameRes = R.string.advanced_level, skips = 1, helps = 1)
}



