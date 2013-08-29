package com.example.adapter;

import com.example.tutorial.fragments.fragment_bienvenida1;
import com.example.tutorial.fragments.fragment_bienvenida2;
import com.example.tutorial.fragments.fragment_bienvenida3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter_bienvenida extends FragmentPagerAdapter {

	private String[] titles = { "Bienvenido", "Contenido", "Opciones"};

	public FragmentAdapter_bienvenida(FragmentManager fm) {
		super(fm);
	}

	public CharSequence getPageTitle(int position) {

		return titles[position];
	}

	@Override
	public Fragment getItem(int position) {
		if(position == 0)
			return fragment_bienvenida1.newInstance("This is fragment " + position);
		else if(position == 1)
			return fragment_bienvenida2.newInstance("This is fragment " + position);
		else
			return fragment_bienvenida3.newInstance("This is fragment " + position);
	}

	@Override
	public int getCount() {

		return titles.length;
	}

}