package com.joaog.task.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
		FragmentStateAdapter(fragmentActivity) {
	
	private val fragmentList: MutableList<Fragment> = ArrayList()
	private val titleList: MutableList<Int> = ArrayList()

	override fun createFragment(position: Int) = fragmentList[position]

	override fun getItemCount() = fragmentList.size

	fun getTitle(position: Int) = titleList[position]

	fun addFragment(fragment: Fragment, title: Int) {
		fragmentList.add(fragment)
		titleList.add(title)
	}
}