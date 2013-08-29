package com.example.tutorial;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.example.adapter.TabsAdapterPager;
import com.example.tutorial.fragments.Fragment_TabsAbout;
import com.example.tutorial.fragments.Fragment_TabsAutor;
import com.example.tutorial.fragments.Fragment_change_log;

public class TabsAbout extends SherlockFragmentActivity {
	ViewPager mViewPager;
	TabsAdapterPager mTabsAdapter;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		mViewPager = new ViewPager(this);
		mViewPager.setId(0x7F04FFF0);

		setContentView(mViewPager);
		ActionBar bar = getSupportActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);

		getSupportActionBar().setTitle("Acerca de la aplicación");
		//getSupportActionBar().setSubtitle(datos.getString("subtitle"));
		setSupportProgressBarIndeterminateVisibility(false);

		mTabsAdapter = new TabsAdapterPager(this, mViewPager);

		mTabsAdapter.addTab(bar.newTab().setText("Acerca de"),Fragment_TabsAbout.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Versiones"),Fragment_change_log.class, null);
		mTabsAdapter.addTab(bar.newTab().setText("Sobre el autor"),Fragment_TabsAutor.class, null);

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
