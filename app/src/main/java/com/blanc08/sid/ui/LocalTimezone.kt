package com.blanc08.sid.ui

import androidx.compose.runtime.compositionLocalOf
import kotlinx.datetime.TimeZone

val LocalTimeZone = compositionLocalOf { TimeZone.currentSystemDefault() }