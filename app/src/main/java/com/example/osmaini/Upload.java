package com.example.osmaini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.osmaini.Api.ApiRequest;
import com.example.osmaini.Api.RetroClient;
import com.example.osmaini.Model.ResponseReadLahModel;
import com.example.osmaini.Utils.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Upload extends AppCompatActivity {
    TextView id_topup, total;
    SessionManager sm;
    Button metode;
    EditText title;
    private static final int PICK_IMAGE = 100;
    private static final int PERMISSION_STORAGE = 2;

    private static final String TAG = Upload.class.getSimpleName();
    private Bitmap bitmap;

    ImageView mImageThumb;

    @BindView(R.id.btn_upload)
    protected AppCompatButton mBtnUploads;

    Button mAddImage;
    Button mBtnUpload;
    private Unbinder mUnbinder;
    private String selectImagePath;
    private Snackbar mSnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        sm = new SessionManager(Upload.this);
        sm.checkLogin();
        mAddImage = (Button)findViewById(R.id.btn_tmbh);
        mBtnUpload = (Button) findViewById(R.id.btn_upload);
        mImageThumb = (ImageView) findViewById(R.id.img_thumb);
        id_topup = (TextView) findViewById(R.id.texttopup);
        total = (TextView) findViewById(R.id.textuang);
        title = (EditText) findViewById(R.id.haa);
        title.setVisibility(View.INVISIBLE);
        String stotal = total.getText().toString();
        final String sid_topup = id_topup.getText().toString();
        metode = (Button) findViewById(R.id.button6);
        mUnbinder = ButterKnife.bind(this);
        HashMap<String, String> map = sm.getDetailLogin();
//        title.setText(map.get(sm.KEY_ID));

        String sid_pembayaran = title.getText().toString();
        sm.checkLogin();

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle == null) {
                sid_pembayaran = null;
                title.setText(sid_pembayaran);
            }
            else {
                sid_pembayaran = bundle.getString("id_pembayaran");
                title.setText(sid_pembayaran);
            }
        }
        else {
            sid_pembayaran = (String) savedInstanceState.getSerializable("id_pembayaran");
            title.setText(sid_pembayaran);
        }
        id_topup.setText("ID PEMBAYARAN : "+sid_pembayaran);
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    uploadImage();
                }
                catch (Exception e) {
//                    Toast.makeText(UploadBukti.this, "Anda Belum memilih", Toast.LENGTH_SHORT).show();
                    Log.e("MYAPP", "exception", e);
                }

            }
        });
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Upload.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(Upload.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(Upload.this,
                            new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSION_STORAGE);

                } else {
                    openImage();
                }
            }
        });
    }
    public void openImage() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,PICK_IMAGE);
    }
    public void uploadImage() {
        String Image = iamgeToString();
//        String Title = title.getText().toString();
        String id_user = title.getText().toString();

        ApiRequest request = RetroClient.getRequestService();
        Call<ResponseReadLahModel> responseCall = request.sendTopup(id_user);
        responseCall.enqueue(new Callback<ResponseReadLahModel>() {
            @Override
            public void onResponse(Call<ResponseReadLahModel> call, Response<ResponseReadLahModel> response) {
                ResponseReadLahModel imageClass = response.body();
                Toast.makeText(Upload.this, "Upload berhasil", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseReadLahModel> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectImageUri = data.getData();
            selectImagePath = getRealPathFromURI(selectImageUri);
            decodeImage(selectImagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openImage();
                }

                return;
            }
        }
    }
    private String getRealPathFromURI(Uri selectImageUri) {
        String[] imageprojection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectImageUri, imageprojection, null, null, null);
        if (cursor == null) {
            return selectImageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(imageprojection[0]);
            return cursor.getString(idx);
        }
    }

    private void decodeImage(String selectImagePath) {
        int targetW = mImageThumb.getWidth();
        int targetH = mImageThumb.getHeight();

        final BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectImagePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bitmap = BitmapFactory.decodeFile(selectImagePath, bmOptions);
        if (bitmap != null) {
            mImageThumb.setImageBitmap(bitmap);
        }
    }
    private String iamgeToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
    public void onBackPressed(){
        Intent intent = new Intent(Upload.this, Home.class);
        startActivity(intent);
    }

}
