package com.example.tutorial.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.tutorial.R;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Fragment_change_log  extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	TextView textView_version_0_1;
	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.fragment_change_log, container, false);
		textView_version_0_1 = (TextView) myFragmentView.findViewById(R.id.textView_version_0_1);

		return myFragmentView;
	}

	public static Bundle createBundle(String title) {
		Bundle bundle = new Bundle();
		bundle.putString(EXTRA_TITLE, title);
		return bundle;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true); // para utilizar onOptionsItemSelected en un fragment
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		setUserVisibleHint(true); // para que no se caiga en algunas ocaciones (cuando hago un intent dentro de un buton en un fragment)
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		textView_version_0_1.setText(Html.fromHtml(getString(R.string.version_0_1)));
	}
}
