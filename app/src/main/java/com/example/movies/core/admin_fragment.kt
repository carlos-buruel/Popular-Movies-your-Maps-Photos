package com.example.movies.core

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun addFragment(
    fragmentManager: FragmentManager,
    fragment: Fragment,
    idContainer: Int,
    nameFragment: String = "") {
    if (fragmentManager.backStackEntryCount > 0) {
        fragmentManager.popBackStackImmediate()
    }
    fragmentManager.beginTransaction()
        .replace(idContainer, fragment, nameFragment)
        .commitAllowingStateLoss()
    fragmentManager.executePendingTransactions()
}