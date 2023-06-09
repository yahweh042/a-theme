package com.troy.theme

import com.intellij.openapi.ui.popup.util.PopupUtil
import com.intellij.openapi.util.SystemInfoRt
import com.intellij.ui.ComponentUtil
import com.intellij.ui.WindowRoundedCornersManager
import com.intellij.util.ui.JBUI
import com.intellij.util.ui.UIUtil
import javax.swing.*
import javax.swing.plaf.UIResource
import javax.swing.plaf.basic.BasicComboPopup
import javax.swing.plaf.basic.BasicPopupMenuUI
import javax.swing.plaf.basic.DefaultMenuLayout

class AComboPopup(comboBox: JComboBox<Any>) : BasicComboPopup(comboBox) {

    override fun configurePopup() {
        super.configurePopup()
        border = JBUI.Borders.empty()
    }

    override fun updateUI() {
        setUI(object : BasicPopupMenuUI() {

        })
    }

    override fun getPopupHeightForRowCount(maxRowCount: Int): Int {
        var result = super.getPopupHeightForRowCount(maxRowCount)
        val rowCount = maxRowCount.coerceAtMost(comboBox.itemCount)
        val renderer = list.cellRenderer
        for (i in 0 until rowCount) {
            val value = list.model.getElementAt(i)
            val c = renderer.getListCellRendererComponent(list, value, i, false, false)
            val preferredSize = c.preferredSize
            result -= preferredSize.height
            result += UIUtil.updateListRowHeight(preferredSize).height
        }
        return result
    }

    override fun configureList() {

    }

}
