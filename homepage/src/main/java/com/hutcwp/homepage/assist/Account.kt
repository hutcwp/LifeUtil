package com.hutcwp.homepage.assist

import me.hutcwp.util.CommonPref

/**
 *
 * Created by hutcwp on 2019-12-10 21:31
 *
 *
 *
 **/
object Account {
    private const val ACCOUNT = "account"
    private const val ACCOUNT_SUFFIX = "_account"
    private const val PASSWORD_SUFFIX = "_password"

    fun getAccountFlag() = ACCOUNT + ACCOUNT_SUFFIX
    fun getPasswordFlag() = ACCOUNT + PASSWORD_SUFFIX

    fun saveAccount(account: String?, password: String?) {
        CommonPref.instance().putString(getAccountFlag(), account ?: "")
        CommonPref.instance().putString(getPasswordFlag(), password ?: "")
    }

    fun getAccountName(): String? {
        return CommonPref.instance().getString(getAccountFlag(), "")
    }

    fun getAccountPassword(): String? {
        return CommonPref.instance().getString(getPasswordFlag(), "")
    }
}