package com.marky.strivefit.ui.components.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.marky.strivefit.ui.components.myiconpack.`Muscle-anatomyFront`
import kotlin.String
import kotlin.collections.List as ____KtList
import kotlin.collections.Map as ____KtMap

public object MyIconPack

private var __AllIcons: ____KtList<ImageVector>? = null

public val MyIconPack.AllIcons: ____KtList<ImageVector>
  get() {
    if (__AllIcons != null) {
      return __AllIcons!!
    }
    __AllIcons= listOf(`Muscle-anatomyFront`)
    return __AllIcons!!
  }

private var __AllIconsNamed: ____KtMap<String, ImageVector>? = null

public val MyIconPack.AllIconsNamed: ____KtMap<String, ImageVector>
  get() {
    if (__AllIconsNamed != null) {
      return __AllIconsNamed!!
    }
    __AllIconsNamed= mapOf("muscle-anatomyfront" to `Muscle-anatomyFront`)
    return __AllIconsNamed!!
  }
