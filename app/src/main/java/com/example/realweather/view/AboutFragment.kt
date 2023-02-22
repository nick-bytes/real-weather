package com.example.realweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.realweather.BuildConfig
import com.example.realweather.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val versionElement = Element()
        versionElement.title = "Version ${BuildConfig.VERSION_NAME}"
        return AboutPage(context)
            .isRTL(false)
            .setDescription(getString(R.string.app_description))
            .addGroup(getString(R.string.contact_group))
            .addEmail("fssoftwareapps@gmail.com", "Email")
            .addItem(versionElement)
            .addGitHub("nicke2668")
            .create()
    }
}
