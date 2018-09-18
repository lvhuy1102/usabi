package com.hcpt.multileagues.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcpt.multileagues.R;
import com.hcpt.multileagues.xml.utils.ImageUtil;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    private ImageView iv_menu;
    private TextView tvTitle;
    private static final int SELECT_PHOTO = 100;
    private static final int CAPTURE_CAMERA = 101;
    private Bitmap avartar;
    private CircleImageView imgAvatar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(R.color.red));
        }
        // Init View
        imgAvatar = findViewById(R.id.imgAvatar);
        iv_menu = findViewById(R.id.iv_menu);
        tvTitle = findViewById(R.id.tvTitle);

        //
        tvTitle.setText("Sign up");
        iv_menu.setVisibility(View.VISIBLE);
        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChooseImage();
            }
        });

    }

    private void showDialogChooseImage() {
        final CharSequence[] items = {
                getResources().getString(R.string.take_photo),
                getResources().getString(R.string.choose_from_library),
                getResources().getString(R.string.cancel_photo)};

        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle(getResources().getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getResources().getString(R.string.take_photo))) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAPTURE_CAMERA);
                } else if (items[item].equals(getResources().getString(R.string.choose_from_library))) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                } else if (items[item].equals(getResources().getString(R.string.cancel_photo))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = imageReturnedIntent.getData();
                        // Avoid bitmap is rotated
                        avartar = ImageUtil.getCorrectlyOrientedImage(SignUpActivity.this, selectedImage);
                        imgAvatar.setImageBitmap(avartar);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAPTURE_CAMERA:
                if (resultCode == RESULT_OK) {
                    avartar = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imgAvatar.setImageBitmap(avartar);
                }
                break;
        }
    }

}
