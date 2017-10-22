package com.example.apichaya.addrealmsudent.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apichaya.addrealmsudent.Model.RgbColor;
import com.example.apichaya.addrealmsudent.R;
import com.example.apichaya.addrealmsudent.adepter.MyAdapter;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainActivity extends AppCompatActivity{


    Button btnAssay;
    Button btnSendData;
    Button btn2;
    Button btnAssayPercent;
    Bitmap bitmap;
    ImageView imageview;
    TextView tvRedvalue;
    TextView tvGreenvalue;
    TextView tvBluevalue;

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button btnAdd;
    private Button btnDelete;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    Realm realm;
    TextView total;
    int sum = 0;
    ArrayList<RgbColor> rgbColorArrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstance();

    }

    private void initInstance() {
        realm = Realm.getDefaultInstance();
        btn2 = (Button) findViewById(R.id.btn2);
        btnAssay = (Button) findViewById(R.id.btnAssay);
        btnAssayPercent = (Button) findViewById(R.id.btnAssayPerCent);
        imageview = (ImageView) findViewById(R.id.quick_start_cropped_image);
        btnSendData = (Button) findViewById(R.id.sentDataButton);
        btnAdd = (Button) findViewById(R.id.addButton);
        btnDelete = (Button) findViewById(R.id.delButton);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);


        tvRedvalue = (TextView) findViewById(R.id.tvRedvalue) ;
        tvGreenvalue = (TextView) findViewById(R.id.tvGreenvalue) ;
        tvBluevalue = (TextView) findViewById(R.id.tvBluevalue) ;




        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyAdapter(getApplicationContext(), rgbColorArrayList);
        mRecyclerView.setAdapter(mAdapter);
        showData();



        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv1.length() == 0 && tv2.length() == 0 && tv3.length() == 0) {
                    return;
                }
                mAdapter.addRgbColor(new RgbColor(Integer.parseInt(tv1.getText().toString().trim()),
                        Integer.parseInt(tv2.getText().toString().trim()), Integer.parseInt(tv3.getText().toString().trim())));
                try {
                    writeToDB(Integer.parseInt(tv1.getText().toString().trim()), Integer.parseInt(tv2.getText().toString().trim()), Integer.parseInt(tv3.getText().toString().trim()));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Input Data!", Toast.LENGTH_SHORT).show();
                }
                //showData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
                showData();
            }
        });

        btnAssay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAverageColorRGB(bitmap);
            }
        });
        btnAssayPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAverageColorRGBpercent(bitmap);

            }
        });



    }

    private void deleteData() {
        final RealmResults<RgbColor> results = realm.where(RgbColor.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();

            }
        });
    }

    private void showData() {
        RealmResults<RgbColor> result = realm.where(RgbColor.class).findAll();
        rgbColorArrayList = new ArrayList<>();
        rgbColorArrayList.addAll(result);
        mAdapter.setRgbColorArrayList(rgbColorArrayList);
    }

    private void writeToDB(final int redValue, final int greenValue, final int blueValue) {

        realm.beginTransaction();
        RgbColor rgbColor = realm.createObject(RgbColor.class);
        rgbColor.setRedValue(redValue);
        rgbColor.setGreenValue(greenValue);
        rgbColor.setBlueValue(blueValue);

        realm.commitTransaction();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void onSelectImageClick(View view) {
        CropImage.activity(null)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelinesColor(Color.BLACK)
                .setBorderCornerColor(Color.BLACK)
                .setBorderLineColor(Color.BLACK)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .start(this);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageview.setImageBitmap(bitmap);
//                imageview.setImageURI(result.getUri());
                Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public int[] getAverageColorRGB(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        int size = width * height;
        int pixelColor;
        int r, g, b;
        r = g = b = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = bitmap.getPixel(x, y);
                if (pixelColor == 0) {
                    size--;
                    continue;
                }
                r += Color.red(pixelColor);
                g += Color.green(pixelColor);
                b += Color.blue(pixelColor);


            }
        }
        r /= size;
        g /= size;
        b /= size;

        tv1.setText(String.valueOf(r));
        tv2.setText(String.valueOf(g));
        tv3.setText(String.valueOf(b));


        Log.e("Color red = ", String.valueOf(r));
        Log.e("Color G = ", String.valueOf(g));
        Log.e("Color B = ", String.valueOf(b));

        return new int[]{
                r, g, b
        };
    }

    public int[] getAverageColorRGBpercent(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        int size = width * height;
        int pixelColor;
        int r, g, b;
        r = g = b = 0;
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                pixelColor = bitmap.getPixel(x, y);
                if (pixelColor == 0) {
                    size--;
                    continue;
                }
                r += Color.red(pixelColor);
                g += Color.green(pixelColor);
                b += Color.blue(pixelColor);


            }
        }
        r /= size;
        g /= size;
        b /= size;

        Double total = Double.parseDouble(String.valueOf(r + g + b));
        Double percentRed = (r * 100) / total;
        Double percentGreen = (g * 100) / total;
        Double percentBlue = (b * 100) / total;

        tv1.setText(String.format("Red: %.2f ", percentRed) + " %");
        tv2.setText(String.format("Green: %.2f ", percentGreen) + " %");
        tv3.setText(String.format("Blue: %.2f ", percentBlue) + " %");

        Log.e("Color red = ", String.valueOf(r));
        Log.e("Color G = ", String.valueOf(g));
        Log.e("Color B = ", String.valueOf(b));

        return new int[]{
                r, g, b
        };

    }





}