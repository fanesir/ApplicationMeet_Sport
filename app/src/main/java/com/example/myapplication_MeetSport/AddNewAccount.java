package com.example.myapplication_MeetSport;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jzxiang.pickerview.TimePickerDialog;
import com.jzxiang.pickerview.data.Type;

import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

import static android.util.Log.i;


public class AddNewAccount extends AppCompatActivity {
    AboutAccountUsetDataset aboutAccountUsetDataset;
    ImageView EditimageView;
    Uri uri = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account_edit_data);

        StorageReference referenceStorage = FirebaseStorage.getInstance().getReference().child("UserImageIcon").child(LoginActivity.USER_EMAIL);


        EditText EditTextUserWantName = findViewById(R.id.editTextUserWantName);
        EditText EditTextUsertEmail = findViewById(R.id.editTextTextUserEmail);
        EditText EditTextUsertPerson = findViewById(R.id.editTextUserPerson);
        EditText EditTextUsertJob = findViewById(R.id.editTextUserJOB);
        EditText EditTextUserContent = findViewById(R.id.editTextUserContent);
        EditText EditTextUserBirthday = findViewById(R.id.editTextUserBirthday);
        Button okButton = findViewById(R.id.button2);
        EditimageView = findViewById(R.id.userInfoEditImageView);
        ProgressBar mProgressBar = findViewById(R.id.serInfoEditImageViewProgressBar);
        mProgressBar.getIndeterminateDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

        EditTextUsertEmail.setText(LoginActivity.USER_EMAIL);
        EditTextUsertEmail.setEnabled(false);
        EditTextUsertPerson.setInputType(InputType.TYPE_NULL);
        EditTextUserBirthday.setInputType(InputType.TYPE_NULL);

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

        okButton.setOnClickListener(view -> {

            if (EditTextUserWantName.getText().toString().matches("") || EditTextUsertEmail.getText().toString().matches("")
                    || EditTextUsertPerson.getText().toString().matches("") || EditTextUsertJob.getText().toString().matches("")
                    || EditTextUserContent.getText().toString().matches("") || EditTextUserBirthday.getText().toString().matches("")) {
                Toast.makeText(AddNewAccount.this, "有欄位空白", Toast.LENGTH_LONG).show();
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
                                aboutAccountUsetDataset.setUserAccountUid(mbase2.push().getKey());
                                PutData(mbase2, aboutAccountUsetDataset, SportTypeRecyclerviewActivity.class);

                            });

                        }).addOnFailureListener(e -> i("FireBaseErrowMessage", e.getMessage()))
                        .addOnProgressListener(taskSnapshot -> {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //100.0*3932160/7842896=50.14 很像在算準確度  錯誤張數/總張數＝總準確度%
                            mProgressBar.setProgress((int) progress);
                        });


            }


        });
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

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);

    }

    public void PutData(DatabaseReference databaseReference, AboutAccountUsetDataset aboutAccountUsetDataset, Class intentClass) {

        databaseReference.setValue(aboutAccountUsetDataset);
        startActivity(new Intent(AddNewAccount.this, intentClass));
        finish();
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void onBackPressed() {
        finish();

    }
}
