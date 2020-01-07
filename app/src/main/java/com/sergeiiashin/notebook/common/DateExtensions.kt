package com.sergeiiashin.notebook.common

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(format: String): String ="Last change: " + SimpleDateFormat(format, Locale.getDefault()).format(this)