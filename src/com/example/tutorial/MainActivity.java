package com.example.tutorial;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.adapter.Adaptador;
import com.example.clases.Datos_Spinner;
import com.example.clases.globales;
import com.example.database.CrearBaseDeDatos;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends SherlockListActivity {
	// ArrayList<Datos_Spinner> listaDatos;
	ArrayList<Datos_Spinner> items;
	CrearBaseDeDatos crearBaseDeDatos;
	SQLiteDatabase db;

	Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		items = new ArrayList<Datos_Spinner>();

		getSupportActionBar().setTitle(getIntent().getExtras().getString("titulo"));
		getSupportActionBar().setSubtitle(getIntent().getExtras().getString("autor_Tutorial"));
		
		// crearBD = new CrearBD(getApplicationContext());
		crearBaseDeDatos = new CrearBaseDeDatos(this, "basededatos_tutorial",null, globales.DB_VERSION);
		db = crearBaseDeDatos.getWritableDatabase();
		try {
			Cursor c;
			c = db.rawQuery(" SELECT DISTINCT ar.id_Articulo, "
					+ "ar.leido_Articulo, "
					+ "ar.fecha_actualizacion_Articulo, "
					+ "ar.titulo_Articulo, " + "ar.subtitulo_Articulo "
					+ "FROM Articulo ar ", null);

			if (c.moveToFirst()) {
				do {
					String texto[] = c.getString(
							c.getColumnIndex("fecha_Actualizacion_Articulo"))
							.split(" ");
					items.add(new Datos_Spinner(c.getInt(c
							.getColumnIndex("id_Articulo")), 
							c.getString(c.getColumnIndex("titulo_Articulo")), "",
							c.getString(c.getColumnIndex("subtitulo_Articulo")), texto[0], 
							c.getInt(c.getColumnIndex("leido_Articulo")), 0, "",
							"", 2));
				} while (c.moveToNext());
			}
			c.close();
			
			items.add(new Datos_Spinner(100, "[Próximamente] ", "","Principios de usabilidad, Ciclo de vida de una aplicación, Menús, Intents, Servicios, Widgets, SharedPreferencias, Notificaciones y alarmas, telefonía, multimedia, Gallery, Base de datos SQlite, tratamiento de XML, Google Maps API, WebServices, Uso de hilos (Thread y AsyncTask).", "", 3, 0, "","", 2));		//leido = 10 significa que no tiene intent (es un próximamente)
			
			final Adaptador especial_adapter = new Adaptador(this,getApplicationContext(), items);

			setListAdapter(especial_adapter);

			getListView().setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub

					if (items.get(arg2).id == 0 || items.get(arg2).id == 100 ) // si es 0 es una letra
					{
						
					}
					else 
					{
						Intent intent;
						intent = new Intent(arg1.getContext(),TabActivity.class);
						Bundle bundle = new Bundle();

						bundle.putInt("id", items.get(arg2).id);

						bundle.putString("title",items.get(arg2).nombre.toString());
						bundle.putString("subtitle",items.get(arg2).sub_titulo.toString());

						intent.putExtras(bundle);
						startActivityForResult(intent, 0);
					}
					// getActivity().startActivityForResult(intent, 0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				this.onCreate(null);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		menu.add(1, 2, 0, "Contactar con autor").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		menu.add(1, 3, 0, "Acerca de").setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		String[] args = new String[] { String.valueOf(getIntent().getExtras().getInt("id_articulo")) };
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		try {
			Cursor c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
					+ " FROM favorito_Tutorial " + " WHERE id_Tutorial_FK=?", args);

			int favorito;
			if (c.moveToFirst()) {
				favorito = c.getInt(c.getColumnIndex("es_favorito_Tutorial"));
				if (favorito == 0)
					menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
				else
					menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_selected).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			} else
				menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		} catch (Exception ex) {
			ex.printStackTrace();
			db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK) values(" + getIntent().getExtras().getInt("id_articulo") + ")");
			menu.add(1, 1, 0, "Favorito").setIcon(R.drawable.star_empty).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} finally {
			this.menu = menu;
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			try {
				String[] args = new String[] { String.valueOf(getIntent().getExtras().getInt("id_articulo")) };

				Cursor c;
				c = db.rawQuery("SELECT DISTINCT es_favorito_Tutorial "
						+ " FROM favorito_Tutorial "
						+ " WHERE id_Tutorial_FK=?", args);

				int favorito;
				if (c.moveToFirst()) {
					favorito = c.getInt(c.getColumnIndex("es_favorito_Tutorial"));
					ContentValues valores = new ContentValues();
					if (favorito == 0) {
						valores.put("es_favorito_Tutorial", 1);
						db.update("favorito_Tutorial", valores,"id_Tutorial_FK=?", args);

						Toast.makeText(getApplicationContext(),"Ahora es favorito", Toast.LENGTH_LONG).show();
						menu.findItem(1).setIcon(R.drawable.star_selected);
					} else {
						valores.put("es_favorito_Tutorial", 0);
						db.update("favorito_Tutorial", valores,"id_Tutorial_FK=?", args);

						Toast.makeText(getApplicationContext(),"Ya no es favorito", Toast.LENGTH_LONG).show();
						menu.findItem(1).setIcon(R.drawable.star_empty);
					}
				} else
				{
					db.execSQL("INSERT INTO favorito_Tutorial(id_Tutorial_FK,es_favorito_Tutorial) values("
							+ getIntent().getExtras().getInt("id_articulo")
							+ ", " + 1 + ")");
					Toast.makeText(getApplicationContext(),"Ahora es favorito", Toast.LENGTH_LONG).show();
					menu.findItem(1).setIcon(R.drawable.star_selected);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(),
						"Ocurrio un error y no se pudo agregar a favorito",
						Toast.LENGTH_LONG).show();
			}
			return true;
		case 2:
			Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			sharingIntent.setType("message/rfc822");
			sharingIntent.putExtra(Intent.EXTRA_EMAIL,new String[] { "hector.lopez.gatica@gmail.com" });
			sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto");
			sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
			startActivity(Intent.createChooser(sharingIntent,"Seleccione su gestor de correo favorito para enviar un correo."));
			return true;
		case 3:
			Intent intent = new Intent(this, TabsAbout.class);
			startActivity(intent);
			return true;
		
		case android.R.id.home:
			Intent mIntent = new Intent();
			setResult(RESULT_OK, mIntent);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
			Intent mIntent = new Intent();
			setResult(RESULT_OK, mIntent);
			finish();
	}
}
