package com.hutcwp.homepage.assist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.hutcwp.homepage.R

class AccountFragment : Fragment() {

    private var clAccount: CopyableLayout? = null
    private var clPassword: CopyableLayout? = null
    private var btnAdd: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.hp_fragment_acount, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        clAccount = root.findViewById(R.id.clAccount)
        clPassword = root.findViewById(R.id.clPassword)
        btnAdd = root.findViewById(R.id.btnAdd)

        clAccount?.setText(Account.getAccountName().toString())
        clPassword?.setText(Account.getAccountPassword().toString())
        btnAdd?.setOnClickListener {
            val addFragment = AddFragment.newInstance()
            addFragment.show(childFragmentManager, AddFragment.TAG)
        }
    }


    companion object {
        private const val TAG = "AccountFragment"
    }
}
