package com.example.tutorial.fragments;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.example.clases.globales;
import com.example.tutorial.R;

public class Fragment_TabsAbout  extends SherlockFragment {
	public static final String EXTRA_TITLE = "title";
	public static WebView mWebView;
	 private TextView textView_titulo, textView_version, textView_fecha,textView_autor;

	 Button button_about_compartir, buttonabout_califica,button_about_enviar_correo;
	 
	int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View myFragmentView;

		myFragmentView = inflater.inflate(R.layout.fragment_about, container, false);
		//textView_titulo = (TextView) myFragmentView.findViewById(R.id.textView_titulo);
		textView_version = (TextView) myFragmentView.findViewById(R.id.textView_version);
		textView_fecha = (TextView) myFragmentView.findViewById(R.id.textView_fecha);
		textView_autor = (TextView) myFragmentView.findViewById(R.id.textView_autor);
		button_about_compartir = (Button) myFragmentView.findViewById(R.id.button_about_compartir);
        buttonabout_califica = (Button) myFragmentView.findViewById(R.id.buttonabout_califica);
        button_about_enviar_correo = (Button) myFragmentView.findViewById(R.id.button_about_enviar_correo);

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
		
		try {
			textView_version.setText("Versión: " +  getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0).versionName.toString());
			textView_fecha.setText("Fecha última versión: " + globales.fecha_actualizacion(getActivity()));
			
			textView_autor.setText(Html.fromHtml(getString(R.string.about_autor)));
			
			button_about_compartir.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
 
				    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);  
				      
				    shareIntent.setType("text/plain");  
				    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,   "Insert Subject Here");  
  
				    String shareMessage = "Insert message body here.";  
				        
				    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareMessage);  
				        
				    startActivity(Intent.createChooser(shareIntent, "¡Comparte esta aolicación!"));  
				}
			});
			
			button_about_enviar_correo.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
					sharingIntent.setType("message/rfc822");
					sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hector.lopez.gatica@gmail.com" });
					sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
					sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
					startActivity(Intent.createChooser(sharingIntent, "Seleccione su gestor de correo favorito para enviar un correo."));		
				}
			});
			
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
