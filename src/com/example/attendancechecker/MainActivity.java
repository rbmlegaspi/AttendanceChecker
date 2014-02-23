package com.example.attendancechecker;

import android.app.Activity;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.content.*;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*;


public class MainActivity extends Activity {
  private EditText text;
  private Camera mCamera;
  private int cameraId;
  private Adapter adapter;
  private ImageAdapter imgAdapter;
  private CameraPreview mPreview;
  private LayoutInflater inflater;
  private ImageView imageV;
  public final static String DEBUG_TAG = "MainActivity";
  private GridView gridview = null;
  static final String[] classList = new String[] 
	  {
	  "ALMASCO", "ALMONTE", "ARAN", "BURCE", "CALIWAGAN", "CLABITA", "COS","CUERDO", "DE MESA","DOGELIO", "FLORES", "GEGUIERA", "HILIS", "MARQUEZ", "MERCADO", "MIRANDA", "SUALLO", "TAN", "YANTO", 
	  "ABUNDO", "AGUILA", "ALIGORA", "AMONCIO", "BRAGAIS", "BUNDALIAN","CALEON", "DALEON", "DITAN", "DOMINGO","EMIA", "ESTAYAN", "GAMBOL","HERNANDEZ","MARTE","MERCED","PANOL","RAMOS","SALE","TUYO","VALENCIA","VALDEZ","VINOYA","YAP",
	  "ALTEA","BERGADO","BORDEOS","CARACUEL","CONDA","DINO","EMALADA","FERNANDEZ","GARCIA","INTERNO","JUEVESANO","LEAL","LOMIBAO","MENDOZA","PACHECA","RAMILO","RAMIREZ","RINOS","RONGCALES","ROXAS","RUBENECIA","RUEL","VELUZ",
	  "AMADO","ANACAY","ARCILLA","ASIS","BANARES","BELTRAN","DIMACUHA","ENCISA","ESCORIAL","HERNANDEZ","LAGGUI","LEDESMA","LEGASPI","MORADA","OLAZO","ONDOY","ORNALES","PALOMENO","ROBERTO","RUSTAN","SEECKTS","VEGA","FERRER",
	  "BAYLOSIS","BELARO","BORROMEO","CABANGON","DELA CRUZ","ESCAMOS","ESQUINAS","ESTOCAPIO, JR","FANDIALAN","FURISCAL","MANAS","MANGARING","MARANAN","NARDO","PANULAYA","POSADAS","RICO","ROLDAN","SAN BUENAVENTURA","SEJALBO"  	 
  	   };  
  private Bitmap[] images = new Bitmap[classList.length];
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    //text = (EditText) findViewById(R.id.editText1);
    inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    int n = Camera.getNumberOfCameras();
	for(int i=0;i<n;i++){
		CameraInfo info = new CameraInfo();
		 Camera.getCameraInfo(i, info);
		 if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
		        cameraId = i;
		        break;
		}
	}
	

	mCamera = Camera.open(cameraId);
	mCamera.setDisplayOrientation(90);
    mPreview = new CameraPreview(this, mCamera);
    FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
  	ViewGroup.LayoutParams params = mPreview.getLayoutParams();
  	//RelativeLayout my  = (RelativeLayout) findViewById(R.id.relay);
  	//params.width = (int) (my.getWidth()/2);
  	//mPreview.setLayoutParams(params);
    preview.addView(mPreview);
  	
    gridview = (GridView) findViewById(R.id.gridview);
   
    gridview.setAdapter(new ImageAdapter(this,classList,images,imageV));
    
    
    gridview.setOnItemClickListener(new OnItemClickListener() {
    	
    	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
    		File pictureFileDir = getDir();

    	    if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

    	      //Log.d(MainActivity.DEBUG_TAG, "Can't create directory to save image.");
    	      Toast.makeText(MainActivity.this, "Can't create directory to save image.",Toast.LENGTH_LONG).show();
    	      //return;

    	    }
    		
    	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmm");
    	    String date = dateFormat.format(new Date());
    	    String photoFile = classList[position] + date + ".jpg";
    	    
    	    //View g = new View(MainActivity.this);
    	    String filename = pictureFileDir.getPath() + File.separator + photoFile;
    	//    ImageView img = new ImageView(MainActivity.this);
    	    //ImageView img = (ImageView) v;
    	    //g = inflater.inflate(R.layout.mobile, null);
    	    File pictureFile = new File(filename);
    	//    ImageView img = (ImageView) v;
    	    ImageView img = (ImageView) v.findViewById(R.id.grid_item_image);
            TextView txt = (TextView) v.findViewById(R.id.grid_item_label);
    	    mCamera.takePicture(null, null, new PhotoHandler(getApplicationContext(),filename,pictureFile,img,txt,images,position,mCamera));
            
    	}
    });
    
   }

  private void helloworld(){
	  Toast.makeText(MainActivity.this, "Hello world", Toast.LENGTH_LONG).show();
  }
  
  private File getDir() {
	    File sdDir = Environment
	      .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
	    return new File(sdDir, "CameraAPIDemo");	
	  }
  
  public void onClick(View v){
	 
	//File x = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"C");
	//mCamera.takePicture(null, null, new PhotoHandler(getApplicationContext()));
	mCamera.startPreview();
	//Toast.makeText(this,"" + this.mCamera, Toast.LENGTH_LONG).show();
	//mPreview = new CameraPreview(this, mCamera);
    //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
  	//ViewGroup.LayoutParams params = mPreview.getLayoutParams();
  	//preview.addView(mPreview);
	
  }

   
// This method is called at button click because we assigned the name to the
  // "OnClick property" of the button
  /*
  public void onClick(View view) {
	  
	String x = "";
	LinearLayout l = new LinearLayout(this);
	
	for(int i=0;i<=10;i++){
		x = x+i;
		TextView t = (TextView) findViewById(R.id.sampleString);	
		t.setText(String.valueOf(x));
		
	}
	
    switch (view.getId()) {
    case R.id.button1:
      RadioButton celsiusButton = (RadioButton) findViewById(R.id.radio0);
      RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radio1);
      if (text.getText().length() == 0) {
        Toast.makeText(this, "Please enter a valid number",
            Toast.LENGTH_LONG).show();
        return;
      }

      float inputValue = Float.parseFloat(text.getText().toString());
      if (celsiusButton.isChecked()) {
        text.setText(String
            .valueOf(convertFahrenheitToCelsius(inputValue)));
        celsiusButton.setChecked(false);
        fahrenheitButton.setChecked(true);
      } else {
        text.setText(String
            .valueOf(convertCelsiusToFahrenheit(inputValue)));
        fahrenheitButton.setChecked(false);
        celsiusButton.setChecked(true);
      }
      break;
    }
  }

  // Converts to celsius
  private float convertFahrenheitToCelsius(float fahrenheit) {
    return ((fahrenheit - 32) * 5 / 9);
  }

  // Converts to fahrenheit
  private float convertCelsiusToFahrenheit(float celsius) {
    return ((celsius * 9) / 5) + 32;
  }
*/  
  private void releaseCamera(){
      if (mCamera != null){
          mCamera.release();        // release the camera for other applications
          mCamera = null;
      }
  }
  
  /*
  protected void onPause() {
	    if (mCamera != null) {
	      mCamera.release();
	      mCamera = null;
	    }
	    super.onPause();
}*/
  
} 