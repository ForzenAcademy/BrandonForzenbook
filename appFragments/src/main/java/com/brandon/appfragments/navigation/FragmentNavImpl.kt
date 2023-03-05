package com.brandon.appfragments.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.brandon.appfragments.R
import com.brandon.appfragments.view.LandingPageFragment
import com.brandon.createaccountfragment.view.CreateAccountFragment
import com.brandon.loginfragments.view.LoginFragment
import com.brandon.navigation.FragmentNav

class FragmentNavImpl: FragmentNav {
    override fun navigateToCreateAccount(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.fragment_container, CreateAccountFragment::class.java, null)
        }
    }

    override fun navigateToLogin(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.fragment_container, LoginFragment::class.java, null)
        }
    }

    override fun navigateToLandingPage(fragmentManager: FragmentManager) {
        fragmentManager.commit {
            addToBackStack(null)
            setReorderingAllowed(true)
            replace(R.id.fragment_container, LandingPageFragment::class.java, null)
        }
    }
}