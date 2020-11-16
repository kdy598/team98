package com.example.team98;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.custom.FirebaseCustomRemoteModel;
import com.google.firebase.ml.custom.FirebaseModelInputs;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {
        private static final int REQUEST_IMAGE_CAPTURE = 672;
        public String imageFilePath;
        private Uri photoUri;
        private StorageReference mStorageRef;
    public String imageFilestate;
    private Interpreter interpreter;
    private View v;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        //권한 체크
        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();

        //btn 클릭시 action
        findViewById(R.id.btn_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //카메라 띄우기
                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    File photoFile = null;
                    try{
                        photoFile = createImageFile();
                    }catch(IOException e)
                    {

                    }
                    if(photoFile != null)
                    {
                        photoUri = FileProvider.getUriForFile(getApplicationContext(),getPackageName(),photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); //외부로 카메라 띄움
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE); // 다음 intent 로 화면 변환이 일어났다가  돌아올때 다음 엑티비티로부터 값을 가지고 온다.
                    }
                }
            }
        });



    }



    //이미지 파일 이름을 중복되지않게 생성
    private File createImageFile() throws IOException{
        String timeStamp = new SimpleDateFormat("yyyyMMDdd_HHmmss").format(new Date());
        String imageFileName = "TEST " + timeStamp +"_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        imageFilePath = image.getAbsolutePath();

        /*try {
            // Storage 에서 다운받아 저장시킬 임시파일
            final File imageFile = File.createTempFile("image", "jpg");
            imageRef.getFile(imageFile)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Success Case
                    Bitmap bitmapImage = BitmapFactory.decodeFile(imageFile.getPath());
                    ImageView.setImageBitmap(bitmapImage);
                    Toast.makeText(getApplicationContext(), "Success !!", Toast.LENGTH_LONG).show();
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail Case
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Fail !!", Toast.LENGTH_LONG).show();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFilePath);
            ExifInterface exif = null;

            try {
                exif = new ExifInterface(imageFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            int exifOrientation;
            int exifDegree;

            if (exif != null) {
                exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                exifDegree = exifOrientationToDegress(exifOrientation);
            } else {
                exifDegree = 0;
            }

            ((ImageView) findViewById(R.id.iv_result)).setImageBitmap(rotate(bitmap, exifDegree));
        }
    }

    public void clickUpload(View v){
        FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        String filename= sdf.format(new Date())+ ".png";
        StorageReference imgRef= firebaseStorage.getReference("uploads/"+filename);
        imgRef.putFile(photoUri);


        /*ImageView img_test = findViewById(R.id.iv_result3);
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://team-98.appspot.com/uploads");
        StorageReference storageRef = storage.getReference(); //Create a storage reference from our app
        storageRef.child("filename.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시

                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(img_test);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });*/

        /*Uri file = Uri.fromFile(new File(imageFilePath));
        StorageReference riversRef = mStorageRef.child(imageFilePath);*/
    }


    private int exifOrientationToDegress(int exifOrientation)   //이미지 회전 부분(가로로 찍거나 세로로 찍어도 회전변환해줌)
    {
        if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        }else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        }
        else if(exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private Bitmap rotate(Bitmap bitmap, float degree)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(bitmap,0 ,0,bitmap.getWidth(), bitmap.getHeight(), matrix,true);

    }



    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Toast.makeText(getApplicationContext(),"권한이 허용됨",Toast.LENGTH_SHORT);
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(getApplicationContext(),"권한이 거부됨",Toast.LENGTH_SHORT);

        }
    };
    public void clicked(View v) {

            FirebaseCustomRemoteModel remoteModel =
                    new FirebaseCustomRemoteModel.Builder("improved_model").build(); //객체 생성
            FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                    .requireWifi()          //모델을 앱과 번들로 묶은 경우에는 TensorFlow Lite 모델의 파일 이름을 지정하여 객체 생성
                    .build();
            FirebaseModelManager.getInstance().getLatestModelFile(remoteModel)
                    .addOnCompleteListener(new OnCompleteListener<File>() { //TensorFlow Lite Interpreter 과정
                        @Override
                        public void onComplete(@NonNull Task<File> task) {
                            File modelFile = task.getResult();
                            if (modelFile != null) {
                                // Instantiate an org.tensorflow.lite.Interpreter object.
                                interpreter = new Interpreter(modelFile);
                                System.out.println("2");
                            } else {
                                System.out.println("test");
                            }
                        }
                    });


            FirebaseModelManager.getInstance().download(remoteModel, conditions)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        final TextView tv_output = findViewById(R.id.test);
                        @Override
                        public void onSuccess(Void v) {
                            System.out.println("1");
                            // Download complete. Depending on your app, you could enable
                            // the ML feature, or switch from the local model to the remote
                            // model, etc.
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 4;
                            Bitmap src = BitmapFactory.decodeFile(imageFilePath, options);
                            Bitmap bitmap = Bitmap.createScaledBitmap(src, 7, 7, true);
                            ByteBuffer input = ByteBuffer.allocateDirect(7 * 7 * 512 * 4).order(ByteOrder.nativeOrder());
                            for (int y = 0; y < 7; y++) {
                                for (int x = 0; x < 7; x++) {
                                    int px = bitmap.getPixel(x, y);

                                    // Get channel values from the pixel value.
                                    int r = Color.red(px);
                                    int g = Color.green(px);
                                    int b = Color.blue(px);

                                    // Normalize channel values to [-1.0, 1.0]. This requirement depends
                                    // on the model. For example, some models might require values to be
                                    // normalized to the range [0.0, 1.0] instead.
                                    float rf = (r - 127) / 255.0f;
                                    float gf = (g - 127) / 255.0f;
                                    float bf = (b - 127) / 255.0f;

                                    input.putFloat(rf);
                                    input.putFloat(gf);
                                    input.putFloat(bf);
                                }
                            }

                            int bufferSize = 4 * Float.SIZE / Byte.SIZE;
                            ByteBuffer modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder());
                            interpreter.run(input, modelOutput);
                            System.out.println(imageFilePath);

                            modelOutput.rewind();
                            FloatBuffer probabilities = modelOutput.asFloatBuffer();
                            final float[] dst = new float[probabilities.capacity()];
                            probabilities.get(dst); // Copy the contents of the FloatBuffer into dst
                            for (int i = 0; i < dst.length; i++) {
                                System.out.print(dst[i]);
                                if (i == dst.length - 1) {
                                    System.out.println();
                                } else {
                                    System.out.print(", ");
                                }
                            }

                            tv_output.setText(String.valueOf(probabilities));

                            try {
                                System.out.println(modelOutput);
                                System.out.println(input);
                                System.out.println("modelOutput");
                                BufferedReader reader = new BufferedReader(
                                        new InputStreamReader(getAssets().open("custom_labels.txt")));
                                for (int i = 0; i < probabilities.capacity(); i++) {
                                    String label = reader.readLine();
                                    float probability = probabilities.get(i);
                                    Log.i("TAG", String.format("%s: %1.4f", label, probability));
                                }
                            } catch (IOException e) {
                                System.out.println("test_98");
                                // File not found?
                            }

                        }
                    }
            );

    }

}
