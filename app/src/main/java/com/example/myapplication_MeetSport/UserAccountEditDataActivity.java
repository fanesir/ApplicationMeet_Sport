package com.example.myapplication_MeetSport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static android.util.Log.i;

public class UserAccountEditDataActivity extends AppCompatActivity {
    Date date;
    AboutAccountUsetDataset aboutAccountUsetDataset;
    String userid, userImage;
    Uri uri = null;
    ImageView EditimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit_data);
        StorageReference referenceStorage = FirebaseStorage.getInstance().getReference().child(LoginActivity.USER_EMAIL);


        EditText EditTextUserWantName = findViewById(R.id.editTextUserWantName);
        EditText EditTextUsertEmail = findViewById(R.id.editTextTextUserEmail);
        EditText EditTextUsertPerson = findViewById(R.id.editTextUserPerson);
        EditText EditTextUsertJob = findViewById(R.id.editTextUserJOB);
        EditText EditTextUserContent = findViewById(R.id.editTextUserContent);
        EditText EditTextUserBirthday = findViewById(R.id.editTextUserBirthday);
        Button okButton = findViewById(R.id.button2);
        EditimageView = findViewById(R.id.userInfoEditImageView);
        ProgressBar mProgressBar = findViewById(R.id.progressBar);


        EditTextUsertEmail.setText(LoginActivity.USER_EMAIL);
        EditTextUsertEmail.setEnabled(false);
        EditTextUsertPerson.setInputType(InputType.TYPE_NULL);
        EditTextUserBirthday.setInputType(InputType.TYPE_NULL);

        EditimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.OFF).setAspectRatio(1, 1).setMinCropResultSize(129, 129)
                        .setRequestedSize(600, 600)//最後幹上去mageview的圖片畫素
                        .setCropShape(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P ? CropImageView.CropShape.RECTANGLE : CropImageView.CropShape.OVAL)
                        .start(UserAccountEditDataActivity.this);
            }
        });

        if (ALLDataBasedirector.USER_WANT_NEW_EDIT == 99) {
            EditTextUsertPerson.setEnabled(false);
            EditTextUserBirthday.setEnabled(false);
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                    .child(LoginActivity.USER_EMAIL.replace(".", ""));
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    aboutAccountUsetDataset = snapshot.getValue(AboutAccountUsetDataset.class);
                    Picasso.get().load(aboutAccountUsetDataset.getUserImage()).into(EditimageView);

                    EditTextUserWantName.setText(aboutAccountUsetDataset.getUserIDName());
                    EditTextUsertPerson.setText(aboutAccountUsetDataset.getUserPerson());
                    EditTextUsertJob.setText(aboutAccountUsetDataset.getUserJob());
                    EditTextUserContent.setText(aboutAccountUsetDataset.getUserContent());
                    EditTextUserBirthday.setText(aboutAccountUsetDataset.getUserBirthday());
                    userid = aboutAccountUsetDataset.getUserAccountUid();
                    userImage = aboutAccountUsetDataset.getUserImage();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


        EditTextUsertPerson.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(EditTextUsertPerson.getContext(), view);
            popup.getMenuInflater().inflate(R.menu.boyorgirllayout, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item1:
                        EditTextUsertPerson.setText(R.string.USER_boy);
                        return true;

                    case R.id.item2:
                        EditTextUsertPerson.setText(R.string.USER_girl);
                        return true;

                }


                return false;
            });
            popup.show();
        });

        EditTextUserBirthday.setOnClickListener(view -> timePickerDialog(EditTextUserBirthday, "選擇生日").show(getSupportFragmentManager(), "year_month_day"));

//完成
        okButton.setOnClickListener(view -> {

            if (EditTextUserWantName.getText().toString().matches("") || EditTextUsertEmail.getText().toString().matches("")
                    || EditTextUsertPerson.getText().toString().matches("") || EditTextUsertJob.getText().toString().matches("")
                    || EditTextUserContent.getText().toString().matches("") || EditTextUserBirthday.getText().toString().matches("")) {
                Toast.makeText(UserAccountEditDataActivity.this, "有欄位空白", Toast.LENGTH_LONG).show();
                return;
            }

            aboutAccountUsetDataset = new AboutAccountUsetDataset();

            aboutAccountUsetDataset.setUserIDName(EditTextUserWantName.getText().toString());
            aboutAccountUsetDataset.setUserEmail(LoginActivity.USER_EMAIL);
            aboutAccountUsetDataset.setUserPerson(EditTextUsertPerson.getText().toString());
            aboutAccountUsetDataset.setUserJob(EditTextUsertJob.getText().toString());
            aboutAccountUsetDataset.setUserContent(EditTextUserContent.getText().toString());
            aboutAccountUsetDataset.setUserBirthday(EditTextUserBirthday.getText().toString());


            DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference("user_Account_Data")
                    .child(LoginActivity.USER_EMAIL.replace(".", ""));

            if (uri != null) {
                StorageReference storageReference = referenceStorage.child(System.currentTimeMillis() + "." + getFileExtension(uri));//幹上去
                storageReference.putFile(uri)
                        .addOnSuccessListener(taskSnapshot -> {
                            Handler handler = new Handler();
                            handler.postDelayed(() -> mProgressBar.setProgress(0), 600);
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();//圖片剛上傳的資料在這

                            result.addOnSuccessListener(uri -> {
                                String imageUrll = uri.toString();
                                aboutAccountUsetDataset.setUserImage(imageUrll);

                                if (ALLDataBasedirector.USER_WANT_NEW_EDIT == 99) {//編輯用
                                    PutData(mbase2, aboutAccountUsetDataset, UserAccountInfo.class);

                                } else if (ALLDataBasedirector.USER_WANT_NEW_EDIT == 21) {//新增用
                                    aboutAccountUsetDataset.setUserAccountUid(userid);
                                    aboutAccountUsetDataset.setUserAccountUid(mbase2.push().getKey());

                                    PutData(mbase2, aboutAccountUsetDataset, SportTypeRecyclerviewActivity.class);

                                }

                            });

                        }).addOnFailureListener(e -> i("FireBaseErrowMessage", e.getMessage()))
                        .addOnProgressListener(taskSnapshot -> {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //100.0*3932160/7842896=50.14 很像在算準確度  錯誤張數/總張數＝總準確度%
                            mProgressBar.setProgress((int) progress);
                        });


            } else if (uri == null & ALLDataBasedirector.USER_WANT_NEW_EDIT == 99) {

                aboutAccountUsetDataset.setUserImage(userImage);
                mbase2.setValue(aboutAccountUsetDataset);
                startActivity(new Intent(UserAccountEditDataActivity.this, UserAccountInfo.class));
            }


        });

    }

    public void PutData(DatabaseReference databaseReference, AboutAccountUsetDataset aboutAccountUsetDataset, Class intentClass) {

        databaseReference.setValue(aboutAccountUsetDataset);
        ALLDataBasedirector.USER_WANT_NEW_EDIT = 0;
        startActivity(new Intent(UserAccountEditDataActivity.this, intentClass));
        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);

        if (resultCode == RESULT_OK) {
            uri = result.getUri();
            Picasso.get().load(uri).into(EditimageView);
        }
    }


    TimePickerDialog timePickerDialog(EditText editText, String Message) {
        TimePickerDialog timePickerDialog = new TimePickerDialog.Builder()
                .setType(Type.YEAR_MONTH_DAY)
                .setMinMillseconds(new Long(31536000 / 1000))
                .setTitleStringId(Message)
                .setThemeColor(getResources().getColor(android.R.color.holo_blue_dark))
                .setCallBack((timePickerView, millseconds) -> {
                    getLongTime(millseconds);
                    editText.setText(getDateToString(millseconds));
                })
                .build();
        return timePickerDialog;
    }


    Long getLongTime(long millseconds) {
        return millseconds;
    }

    public String getDateToString(long time) {//顯示的時間
        Log.i("a", time + "");
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);

    }

    Date setDataWanttheTime(String setTime) {
        try {
            date = new SimpleDateFormat("yyyy-MM-dd ").parse(setTime);//setTime 可以設置2021-09-23 00:00:00

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void onBackPressed() {
        startActivity(new Intent(UserAccountEditDataActivity.this, UserAccountInfo.class));
        finish();
    }

}