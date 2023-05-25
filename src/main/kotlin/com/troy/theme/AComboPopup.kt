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
            override fun uninstallDefaults() {}
            override fun installDefaults() {
                if (popupMenu.layout == null || popupMenu.layout is UIResource) {
                    popupMenu.layout = DefaultMenuLayout(popupMenu, BoxLayout.Y_AXIS)
                }
                popupMenu.isOpaque = true
                LookAndFeel.installColorsAndFont(
                    popupMenu,
                    "PopupMenu.background",
                    "PopupMenu.foreground",
                    "PopupMenu.font"
                )
            }

            override fun getPopup(popup: JPopupMenu, x: Int, y: Int): Popup {
                val factory = PopupFactory.getSharedInstance()
                var oldType = -1
                val isRoundBorder = WindowRoundedCornersManager.isAvailable()
                if (isRoundBorder) {
                    oldType = PopupUtil.getPopupType(factory)
                    if (oldType == 2) {
                        oldType = -1
                    } else {
                        PopupUtil.setPopupType(factory, 2)
                    }
                }
                val p = super.getPopup(popup, x, y)
                if (oldType >= 0) {
                    PopupUtil.setPopupType(factory, oldType)
                }
                if (isRoundBorder) {
                    val window = ComponentUtil.getWindow(popup)
                    if (window != null) {
                        if (SystemInfoRt.isMac && UIUtil.isUnderDarcula() || SystemInfoRt.isWindows) {
                            WindowRoundedCornersManager.setRoundedCorners(
                                window,
                                JBUI.CurrentTheme.Popup.borderColor(true)
                            )
                            popup.border = null
                        } else {
                            WindowRoundedCornersManager.setRoundedCorners(window)
                        }
                    }
                }
                return p
            }
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
