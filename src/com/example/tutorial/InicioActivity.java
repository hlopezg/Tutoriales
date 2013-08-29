package com.example.tutorial;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.danielme.android.webviewdemo.WebViewDemoActivity;
import com.example.adapter.Adaptador_GPlus;
import com.example.clases.Datos_Spinner;
import com.example.clases.globales;
import com.example.controlador.Crear_datos_tablas;
import com.example.database.CrearBaseDeDatos;
import com.google.android.youtube.player.YouTubeIntents;

public class InicioActivity extends SherlockListActivity {
	ArrayList<Datos_Spinner> listaDatos;
	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;
	ArrayList<Datos_Spinner> items;
	char ultima_primeraletra;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private ListView mDrawerList;
	private String[] mPlanetTitles;

	private SpeedScrollListener listener;

	Adaptador_GPlus especial_adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try // lo hago en un try por que cuando refresco la pagina se cae al leer esto
		{
			requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
			requestWindowFeature(Window.FEATURE_PROGRESS);
		} catch (Exception e)
		{

		}
		try {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_main);

			setSupportProgressBarIndeterminateVisibility(true);

			// getSupportActionBar().setDisplayShowCustomEnabled(true);

			new cargarDatos().execute();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		// FrameLayout layout_filtro = (FrameLayout) findViewById(R.id.layout_filtro);
		mTitle = mDrawerTitle = getTitle();
		mPlanetTitles = getResources().getStringArray(R.array.filtros);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		// creo los datos de la tabla para el filtro
		Crear_datos_tablas crear_datos_tablas = new Crear_datos_tablas(InicioActivity.this);

		mDrawerList.setAdapter(crear_datos_tablas.tabla_filtro());

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		// iconos por del ejemplo
		mDrawerToggle = new ActionBarDrawerToggle(
				this, /* host Activity */
				mDrawerLayout, /* DrawerLayout object */
				R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
				R.string.drawer_open, /* "open drawer" description for accessibility */
				R.string.drawer_close /* "close drawer" description for accessibility */
				) {
					/** Called when a drawer has settled in a completely closed state. */
					public void onDrawerClosed(View view) {
						getSupportActionBar().setTitle(mTitle);
						// invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
					}

					/** Called when a drawer has settled in a completely open state. */
					public void onDrawerOpened(View drawerView) {
						getSupportActionBar().setTitle(mDrawerTitle);
						// invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
					}
				};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	class cargarDatos extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			try
			{
				items = new ArrayList<Datos_Spinner>();

				crearBaseDeDatos = new CrearBaseDeDatos(InicioActivity.this, "basededatos_tutorial", null, globales.DB_VERSION);
				db = crearBaseDeDatos.getWritableDatabase();

				Cursor c;
				c = db.rawQuery(
						" SELECT DISTINCT t.id_Tutorial, "
								+ "t.titulo_Turorial, "
								+ "t.subtitulo_Tutorial, "
								+ "t.autor_Tutorial, "
								+ "t.disponiblidad_Tutorial, "
								+ "t.fecha_Actualizacion_Tutorial, "
								+ "t.idioma_Tutorial, "
								+ "ft.es_favorito_Tutorial, "
								+ "t.url_Tutorial "
								+ "FROM Tutorial t"
								+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial "
								+ " ORDER BY upper(titulo_Turorial),id_Tutorial "
						, null);

				// if (listaDatos.get(position).disponiblidad == 1) { //dispositivo
				// } else if (listaDatos.get(position).disponiblidad == 2) {//web
				// } else if (listaDatos.get(position).disponiblidad == 3) {//pdf
				// } else if (listaDatos.get(position).disponiblidad == 4) {//pdf descargado
				// else if (listaDatos.get(position).disponiblidad == 5) {//youtube
				listener = new SpeedScrollListener();
				getListView().setOnScrollListener(listener);

				do_while_cursor(c);

			} catch (Exception ex)
			{
				ex.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String result) {
			// getListView().setFastScrollEnabled(true);
			// getListView().setScrollingCacheEnabled(true);

			setListAdapter(especial_adapter);
			setSupportProgressBarIndeterminateVisibility(false);
			// getListView().setTextFilterEnabled(true);

			getListView().setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Intent intent = null;
					Bundle bundle = new Bundle();

					bundle.putInt("id_articulo", items.get(arg2).id);
					bundle.putString("url_Tutorial", items.get(arg2).url);

					bundle.putString("titulo", items.get(arg2).nombre);
					bundle.putString("sub_titulo", items.get(arg2).sub_titulo);
					bundle.putString("autor_Tutorial", items.get(arg2).autor);

					if (items.get(arg2).url.equals("") && items.get(arg2).id != 0)
					{
						intent = new Intent(arg1.getContext(), MainActivity.class);
					}

					else
					{
						if (items.get(arg2).disponiblidad == 5)
						{
							intent = YouTubeIntents.createOpenPlaylistIntent(arg1.getContext(), items.get(arg2).url);
							// intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=zlDoZCEp9Po&list=SP6156F2C5F487917C"));
						}
						else if (items.get(arg2).disponiblidad == 3 || items.get(arg2).disponiblidad == 4)
						{
							intent = new Intent(arg1.getContext(), pdfActivity.class);
						}
						else
							intent = new Intent(arg1.getContext(), WebViewDemoActivity.class);
					}

					try
					{
						if (items.get(arg2).id != 0)
						{
							intent.putExtras(bundle);
							startActivityForResult(intent, 0);
						}

					} catch (Exception ex2)
					{
						if (items.get(arg2).disponiblidad == 5) // en algunos dispositivos tiene problemas el YouTubeIntents, en este caso lanzamos un intent implisito
						{
							intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=PLPV2KyIb3jR7F_B4p8X3YwHPaExh0R9Kk&list=playlist-id&feature=plpp_play_all"));
							startActivityForResult(intent, 0);
						}
					}
				}
			});
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				// this.onCreate(null);
				new cargarDatos().execute();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(1, 1, 0, "Contactar con autor").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 2, 0, "Acerca de").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 3, 0, "Filtro").setIcon(R.drawable.filter).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		// menu.add(1, 3, 0, "Buscar").setActionView(R.layout.layout_search_edittext).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		getSupportMenuInflater().inflate(R.menu.search, menu);

		MenuItem menuSearch = menu.findItem(R.id.menu_search);

		EditText searchText = (EditText) menuSearch.getActionView().findViewById(R.id.search_edittext);

		searchText.addTextChangedListener(new TextWatcher() {

			public void afterTextChanged(Editable s) {
			}

			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			public void onTextChanged(CharSequence s, int start, int before, int count) {
				filtrar_edittext(s.toString());
			}
		});

		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
					return filtrar_edittext(arg0.getText().toString());
				}
				return false;
			}
		});
		return super.onCreateOptionsMenu(menu);
		// return true;
	}

	boolean filtrar_edittext(String arg0) {
		items = new ArrayList<Datos_Spinner>();
		Cursor c;
		c = db.rawQuery(
				" SELECT DISTINCT  t.id_Tutorial, "
						+ "t.titulo_Turorial, "
						+ "t.subtitulo_Tutorial, "
						+ "t.autor_Tutorial, "
						+ "t.disponiblidad_Tutorial, "
						+ "t.fecha_Actualizacion_Tutorial, "
						+ "t.idioma_Tutorial, "
						+ "ft.es_favorito_Tutorial, "
						+ "t.url_Tutorial "
						+ "FROM Tutorial t"
						+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial "
						+ " WHERE titulo_Turorial LIKE '%" + arg0
						+ "%'" + " OR autor_Tutorial LIKE '%"
						+ arg0 + "%'"
						+ " ORDER BY titulo_Turorial,autor_Tutorial"
				, null);

		do_while_cursor(c);
		setListAdapter(especial_adapter);
		setSupportProgressBarIndeterminateVisibility(false);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mDrawerToggle.onDrawerOpened(mDrawerLayout);
		switch (item.getItemId()) {
			case 1:
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("message/rfc822");
				sharingIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "hector.lopez.gatica@gmail.com" });
				sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
				sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
				startActivity(Intent.createChooser(sharingIntent, "Seleccione su gestor de correo favorito para enviar un correo."));
				return true;
			case 2:
				Intent intent = new Intent(this, TabsAbout.class);
				startActivity(intent);
				return true;
			case 3:
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
			case android.R.id.home:
				if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
					mDrawerLayout.closeDrawer(mDrawerList);
				} else {
					mDrawerLayout.openDrawer(mDrawerList);
				}
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public boolean onQueryTextChange(String newText) {
		// mStatusView.setText("Query = " + newText);
		return false;
	}

	public boolean onQueryTextSubmit(String query) {
		// mStatusView.setText("Query = " + query + " : submitted");
		return false;
	}

	public boolean onClose() {
		// mStatusView.setText("Closed!");
		return false;
	}

	protected boolean isAlwaysExpanded() {
		return false;
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position != 0)
			{

				mDrawerList.setItemChecked(position, true);
				setTitle(mPlanetTitles[position - 1]); // -1 por que el primer item es un texto
				mDrawerLayout.closeDrawer(mDrawerList);

				try
				{
					setSupportProgressBarIndeterminateVisibility(true);

					items = new ArrayList<Datos_Spinner>();

					String query = " SELECT DISTINCT t.id_Tutorial, "
							+ "t.titulo_Turorial, "
							+ "t.subtitulo_Tutorial, "
							+ "t.autor_Tutorial, "
							+ "t.disponiblidad_Tutorial, "
							+ "t.fecha_Actualizacion_Tutorial, "
							+ "t.idioma_Tutorial, "
							+ "ft.es_favorito_Tutorial, "
							+ "t.url_Tutorial "
							+ "FROM Tutorial t"
							+ " LEFT JOIN favorito_Tutorial ft on ft.id_Tutorial_FK = t.id_Tutorial ";

					if (position < 6) // si NO seleccione TODOS
						query = query + " WHERE t.disponiblidad_Tutorial = " + position;
					else if (position == 7)
						query = query + " WHERE ft.es_favorito_Tutorial = " + 1;

					Cursor c;
					c = db.rawQuery(
							query + " ORDER BY upper(titulo_Turorial),id_Tutorial "
							, null);

					do_while_cursor(c);
					setListAdapter(especial_adapter);
					setSupportProgressBarIndeterminateVisibility(false);
				} catch (Exception ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	// When using the ActionBarDrawerToggle, you must call it during onPostCreate() and onConfigurationChanged()...

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void do_while_cursor(Cursor c)
	{
		if (c.moveToFirst())
		{
			do
			{
				char primera_letra = c.getString(c.getColumnIndex("titulo_Turorial")).toUpperCase().charAt(0);
				if (primera_letra != ultima_primeraletra)
				{
					ultima_primeraletra = c.getString(c.getColumnIndex("titulo_Turorial")).toUpperCase().charAt(0);
					items.add(new Datos_Spinner(0, ultima_primeraletra + "", "", "", "", 0, 0, "", "", 2));
				}

				items.add(new Datos_Spinner(c.getInt(c.getColumnIndex("id_Tutorial")),
						c.getString(c.getColumnIndex("titulo_Turorial")),
						c.getString(c.getColumnIndex("subtitulo_Tutorial")),
						c.getString(c.getColumnIndex("autor_Tutorial")),
						c.getString(c.getColumnIndex("fecha_Actualizacion_Tutorial")),
						4,
						c.getInt(c.getColumnIndex("disponiblidad_Tutorial")),
						c.getString(c.getColumnIndex("idioma_Tutorial")),
						c.getString(c.getColumnIndex("url_Tutorial")),
						c.getInt(c.getColumnIndex("es_favorito_Tutorial"))));
			} while (c.moveToNext());
			ultima_primeraletra = 'z';
		}
		c.close();
		// db.close();
		especial_adapter = new Adaptador_GPlus(InicioActivity.this, getApplicationContext(), items, listener);

	}
}
