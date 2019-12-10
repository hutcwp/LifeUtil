package com.hutcwp.homepage.assist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hutcwp.homepage.R

class AccountFragment : Fragment() {

    private var copyLayout: CopyableLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.hp_fragment_acount, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        copyLayout = root.findViewById(R.id.copyLayout)
        copyLayout?.setText("这是要被copy的内容")
    }


    override fun onDetach() {
        super.onDetach()
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = AccountFragment()
    }
}
