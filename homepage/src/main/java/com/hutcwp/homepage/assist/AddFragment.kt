package com.hutcwp.homepage.assist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.hutcwp.homepage.R
import kotlinx.android.synthetic.main.hp_fragment_add.*


class AddFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.hp_fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        btnConfirm?.setOnClickListener {
            val account = etAccount?.text?.trim()
            val password = etPassword?.text?.trim()
            Account.saveAccount(account.toString(), password.toString())
        }
    }


    companion object {
        const val TAG = "AddFragment"
        fun newInstance() = AddFragment()
    }
}
