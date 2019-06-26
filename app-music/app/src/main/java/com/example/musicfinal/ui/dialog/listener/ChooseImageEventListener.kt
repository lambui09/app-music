package com.example.musicfinal.ui.dialog.listener

import com.example.musicfinal.ui.dialog.ChooseImageDialog

interface ChooseImageEventListener {
    fun open(type: ChooseImageDialog.ChosenImageType)
}
