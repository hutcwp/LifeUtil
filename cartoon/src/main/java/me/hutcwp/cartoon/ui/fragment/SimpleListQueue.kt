package me.hutcwp.cartoon.ui.fragment

import me.hutcwp.cartoon.bean.SimpleQueue

/**
 *
 * Created by hutcwp on 2019-06-02 18:25
 *
 *
 *
 **/
class SimpleListQueue<Element>(
        private val actual: MutableList<Element> = mutableListOf()
) : SimpleQueue<Element>, List<Element> by actual {

    private var curIdx = 5

    fun setCurPage(page: Int) {
        if (page in 1..size){
            curIdx = page-1
        }
    }

    fun addFirst(data: List<Element>) {
        actual.addAll(0, data)
        curIdx += data.size
    }

    fun addLast(data: List<Element>) {
        actual.addAll(data)
    }

    override fun next(): Element? {
        return actual.getOrNull(curIdx + 1)
    }

    override fun current(): Element? {
        return actual.getOrNull(curIdx)
    }

    override fun moveToNext() {
        curIdx++
    }

    override fun prev(): Element? {
        return actual.getOrNull(curIdx - 1)
    }

    override fun moveToPrev() {
        curIdx--
    }

    fun moveTo(idx: Int) {
        if (idx in 0 until actual.size) {
            curIdx = idx
        } else {
            throw IndexOutOfBoundsException("index must between 0 and ${actual.size} " +
                    "but now is $idx")
        }
    }
}