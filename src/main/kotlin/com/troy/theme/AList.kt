package com.troy.theme

import java.awt.Graphics
import java.awt.Rectangle
import javax.swing.*
import javax.swing.plaf.basic.BasicListUI

class AList : BasicListUI() {

    override fun paint(g: Graphics, c: JComponent) {

    }

    override fun paintCell(
        g: Graphics?,
        row: Int,
        rowBounds: Rectangle?,
        cellRenderer: ListCellRenderer<Any>?,
        dataModel: ListModel<Any>?,
        selModel: ListSelectionModel?,
        leadIndex: Int
    ) {
        super.paintCell(g, row, rowBounds, cellRenderer, dataModel, selModel, leadIndex)
    }

    override fun updateLayoutState() {

        val fixedCellHeight = list.fixedCellHeight
        val fixedCellWidth = list.fixedCellWidth

        cellWidth = if (fixedCellWidth != -1) fixedCellWidth else -1

        if (fixedCellHeight != -1) {
            cellHeight = fixedCellHeight
            cellHeights = null
        } else {
            cellHeight = -1
            cellHeights = IntArray(list.model.size)
        }
        if (fixedCellWidth == -1 || fixedCellHeight == -1) {
            val dataModel = list.model
            val dataModelSize = dataModel.size
            val renderer = list.cellRenderer
            if (renderer != null) {
                for (index in 0 until dataModelSize) {
                    val value = dataModel.getElementAt(index)
                    val c = renderer.getListCellRendererComponent(list, value, index, false, false)
                    rendererPane.add(c)
                    val cellSize = c.preferredSize
                    if (fixedCellWidth == -1) {
                        cellWidth = cellSize.width.coerceAtLeast(cellWidth)
                    }
                    if (fixedCellHeight == -1) {
                        cellHeights[index] = cellSize.height
                    }
                }
            } else {
                if (cellWidth == -1) {
                    cellWidth = 0
                }
                if (cellHeights == null) {
                    cellHeights = IntArray(dataModelSize)
                }
                for (index in 0 until dataModelSize) {
                    cellHeights[index] = 0
                }
            }
        }
    }

    companion object {

        @Suppress("UNUSED_PARAMETER")
        @JvmStatic
        fun createUI(component: JComponent) = AList()

    }

}
