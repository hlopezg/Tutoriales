package com.example.database;

import com.example.clases.globales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class CrearBaseDeDatos  extends SQLiteOpenHelper {	
    String sqlCreate = "CREATE TABLE IF NOT EXISTS  Articulo (id_Articulo INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		"titulo_Articulo nvarchar(40), " +
    		"subtitulo_Articulo nvarchar(60),leido_Articulo integer, " +
    		"fecha_Actualizacion_Articulo TIMESTAMP DEFAULT (datetime('now','localtime')), " +
    		"posicion_lectura_Articulo FLOAT, " +
    		"favorito_Articulo integer DEFAULT 0)";
    
    String sqlCreate2 = "CREATE TABLE IF NOT EXISTS Tutorial (id_Tutorial INTEGER PRIMARY KEY, " +
    		"titulo_Turorial nvarchar(40), " +
    		"autor_Tutorial nvarchar(60)," +
    		"subtitulo_Tutorial nvarchar(100) DEFAULT ''," +
    		"disponiblidad_Tutorial integer, " +
    		"fecha_Actualizacion_Tutorial nvarchar(10), " +
    		"idioma_Tutorial nvarchar(15), " +
    		"url_Tutorial nvarchar(200))";
    
    String sqlCreate3 = "CREATE TABLE IF NOT EXISTS favorito_Tutorial (id_favorito_Tutorial INTEGER PRIMARY KEY AUTOINCREMENT, " +
    		" id_Tutorial_FK INTEGER, " +
    		" es_favorito_Tutorial INTEGER DEFAULT 0)";
    
    String idioma_espanol = "Español";
    String idioma_ingles = "English";
 
    public CrearBaseDeDatos(Context contexto, String nombre,CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
    	db.execSQL(" CREATE TABLE IF NOT EXISTS android_metadata(locale TEXT DEFAULT 'es_ES');");
    	
        db.execSQL(sqlCreate);

        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        cargarDatosPrueba(db);
    }
    
	public void cargarDatosPrueba(SQLiteDatabase db){

		db.execSQL("DELETE FROM Articulo");	
		
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Introducción','Vamos a ver',0,0,'14/08/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Versiones de Android (API)','Para saber en que versión programar.',0,0,'14/08/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Componentes de una aplicación en Android','Definición de: Activity, Services, Intent, etc.',0,0,'14/08/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Creación de un proyecto en Android','Como crear un proyecto y su estructura.',0,0,'14/08/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Empezando con nuestro proyecto','Un ejemplo sencillo.',0,0,'14/08/2013')");
        db.execSQL("INSERT INTO Articulo(titulo_Articulo,subtitulo_Articulo,leido_Articulo,posicion_lectura_Articulo,fecha_Actualizacion_Articulo)" +
        		" values('Layouts','Tipos de layout, ancho, alto, alinamiento.',0,0,'14/08/2013')");

    	db.execSQL("DELETE FROM Tutorial");	
    	//Español, English
    	
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(1,'Android','Héctor López Gatica',1,'14/08/2013','Español','')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(2,'Windows Phone 7','Rodrigo Díaz Concha',2,'14/08/2013','Español','http://blogs.msdn.com/b/warnov/archive/2011/06/22/tutoriales-de-desarrollo-para-windows-phone-totalmente-en-espa-241-ol-y-gratuitos.aspx')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(3,'Android','Salvador Gómez',2,'14/08/2013','Español','http://www.sgoliver.net/blog/?page_id=3011')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(4,'Perl','ZenTut.com',2,'14/08/2013','English','http://www.zentut.com/perl-tutorial/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(5,'Ruby on Rails','xenodesystems.blogspot.com',2,'14/08/2013','Español','http://xenodesystems.blogspot.com/2012/06/curso-gratuito-de-ruby-on-rails.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(6,'PHP','php.net',2,'14/08/2013','Español','http://php.net/manual/es/index.php')");
                    
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(7,'iOS 6','manzanamagica.com',2,'14/08/2013','Español','http://www.manzanamagica.com/ios/desarrollo/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(8,'ASP.NET','maestrosdelweb.com',2,'14/08/2013','Español','http://www.maestrosdelweb.com/editorial/tutoria-desarrolloweb-asp-net/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(9,'Django','django-book.blogspot.com',2,'14/08/2013','Español','http://django-book.blogspot.com/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(10,'SQL','Claudio Caseres',3,'14/08/2013','Español','http://www.unalmed.edu.co/~mstabare/Sql.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(11,'SQL','desarrolloweb.com',2,'14/08/2013','Español','http://www.desarrolloweb.com/manuales/9/')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(12,'C','elrincondelc.com',2,'14/08/2013','Español','http://www.elrincondelc.com/cursoc/cursoc.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(13,'C','cprogramming.com',2,'14/08/2013','English',' http://www.cprogramming.com/tutorial.html')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(14,'C','programatium.com',2,'14/08/2013','English','http://www.programatium.com/c.htm')");
       
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(15,'Unity 4','Brackeys',5,'14/08/2013','English','PLPV2KyIb3jR7F_B4p8X3YwHPaExh0R9Kk')");	//youtubeplaylist
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(16,'JQuery Mobile','lawebera.es',2,'14/08/2013','Español','http://www.lawebera.es/jquery-mobile')");	
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(17,'Unity 3.3','Matt Stuttard Parker',2,'14/08/2013','English',' http://active.tutsplus.com/tutorials/unity/getting-started-with-unity/')");	
       
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(18,'JAVA','Fundamentos de Programación en JAVA','Jorge Martínez Ladrón de Guevara',3,'14/08/2013','Español','http://pendientedemigracion.ucm.es/info/tecnomovil/documentos/fjava.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(19,'C++','Manual básico de Programación en C++','Servicios Informáticos Universidad Complutense de Madrid',3,'14/08/2013','Español','http://www.sisoft.ucm.es/Manuales/C++.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(20,'HTML5','El presente de la web. HTML5, css3 y javascript','John Freddy Vega, Christian Van Der Henst',3,'14/08/2013','Español','https://mejorando.la/documentos/mejorandolaweb-guia-html5.pdf')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(21,'Java','','Studios CAT y Otakus TV',5,'14/08/2013','Español','PL3CF8EB568255B619')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(22,'DFD','Diagramas de Flujo, Algoritmos y Programación','Juan Manuel',5,'14/08/2013','Español','PLA608712FF24643A3')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(23,'Android','Tutoriales Android - Programar para Android desde cero','Jorge Villalobos',5,'14/08/2013','Español','PL0A103A65064BC6E2')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(24,'PHP-MYSQL','','VideoTutoriales.com',5,'14/08/2013','Español','PLF2E7FC0407FF3398')");
        
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(25,'Microsoft XNA','Videojuegos y ejemplos programados con Microsoft XNA','Juan Antonio Calles',5,'14/08/2013','Español','PLF021C545571C69D2')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(26,'C#','Aprende C# (Guía rápida)','Félix Manuel Brito Amarante',5,'14/08/2013','Español','PL1CFD6C344CEE058D')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(27,'iOS','Tutorial de iPhone SDK','Félix Manuel Brito Amarante',5,'14/08/2013','Español','PL9AE1A840A5B7BD43')");
        
        db.execSQL("INSERT INTO Tutorial(id_Tutorial,titulo_Turorial,subtitulo_Tutorial,autor_Tutorial,disponiblidad_Tutorial,fecha_Actualizacion_Tutorial,idioma_Tutorial,url_Tutorial)" +
        		" values(28,'Django','Tutorial Django 1.4 + Ubuntu','Alex Dzul',5,'14/08/2013','Español','PL2670DAAFCFECA138')");
        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Articulo");
        db.execSQL("DROP TABLE IF EXISTS Tutorial");	
 
        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);

        db.execSQL(sqlCreate2);
        db.execSQL(sqlCreate3);
        cargarDatosPrueba(db);
    }
    public boolean comprobarBaseDatos() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = globales.DB_PATH + globales.DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.NO_LOCALIZED_COLLATORS);
		} catch (SQLiteException e) {
			// No existe
		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}
}