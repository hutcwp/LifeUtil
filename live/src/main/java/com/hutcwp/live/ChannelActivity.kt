package com.hutcwp.live

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.drakeet.multitype.MultiTypeAdapter
import kotlinx.android.synthetic.main.live_activity_channel.*
import me.hutcwp.constants.RoutePath


@Route(path = RoutePath.LIVE_CHANNEL, name = "直播间")
class ChannelActivity : AppCompatActivity() {

    private val adapter = MultiTypeAdapter()
    private val items = ArrayList<Any>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_activity_channel)

        initRV()
    }

    private fun initRV() {

        adapter.register(FooViewDelegate())

        adapter.register(Data::class).to(
                DataType1ViewBinder(),
                DataType2ViewBinder()
        ).withKotlinClassLinker { _, data ->
            when (data.type) {
                Data.TYPE_2 -> DataType2ViewBinder::class
                else -> DataType1ViewBinder::class
            }
        }

        rv_public_screen?.adapter = adapter

        val textItem = Foo("world")

        for (i in 0..19) {
            items.add(textItem)
        }

        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy1", Data.TYPE_1))
        items.add(Data("typy2", Data.TYPE_2))
        items.add(Data("typy2", Data.TYPE_2))

        adapter.items = items



        adapter.notifyDataSetChanged()
    }
}