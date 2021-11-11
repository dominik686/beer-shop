package com.example.beershop;

import android.animation.Animator;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerModel;
import com.example.beershop.singletons.CurrentUser;
import com.example.beershop.utils.AnimationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.Barcode;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.common.InputImage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ResellerScanBarcodeFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    LottieAnimationView mScanAnimation;
    String mCurrentPhotoPath;

    BeerDataBaseHelper mBeerDBHelper;
    UserDataBaseHelper mUserDBHelper;
    CurrentUser mCurrentUser;
    SuccessDialog mSuccessDialog;

    public static ResellerScanBarcodeFragment newInstance() {

        Bundle args = new Bundle();

        ResellerScanBarcodeFragment fragment = new ResellerScanBarcodeFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reseller_scan_barcode, container, false);
        mScanAnimation = v.findViewById(R.id.lottie_scan_barcode1);

        mCurrentUser = CurrentUser.getInstance();
        mUserDBHelper = new UserDataBaseHelper(getContext());
        mBeerDBHelper = new BeerDataBaseHelper(getContext());


        mScanAnimation.playAnimation();
        mScanAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchTakePictureIntent();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return v;
    }

    // create a collision-resistant file name
    private File createImageFile() throws IOException {
        // Create image file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timestamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }


    // Save the photo as a file
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                //Display error state to the user
            }

            //Continue only  if the file was successfuly created
            if (photoFile != null)
                try {
                    Uri photoUri = FileProvider.getUriForFile(getContext(),
                            "com.example.beershop.fileprovider", photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    //Display error state to the user
                }
        }
    }


    // If the activity returns a photo, check if the photo contains a barcode
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            bitmap = Bitmap.createScaledBitmap(bitmap, 1920, 1080, true);

            InputImage image = InputImage.fromBitmap(bitmap, 180);

            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            Task<List<Barcode>> result = scanner.process(image).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onSuccess(List<Barcode> barcodes) {
                    //Task completed successfuly
                    if (barcodes.size() > 0) {

                        String s = barcodes.get(0).getRawValue();

                        //Check if the beer is the resellers inventory, and check the max quantity that the user can add
                        BeerModel beerFromDB = mBeerDBHelper.getBeer(s);

                        // If bm == null then the beer doesnt exist in the DB
                        if (beerFromDB == null) {
                            // FailureDialog?
                            Toast.makeText(getContext(), "There was a mistake! try adding the beer manually", Toast.LENGTH_LONG).show();
                        }
                        // If the beer exists in the DB but not the inventory
                        else if (mUserDBHelper.getBeer(beerFromDB, mCurrentUser.getResellerModel()) == null) {
                            // Add the a single beer to the inventory - if there is need to add more,
                            // try adding again
                            mUserDBHelper.addBeerToInventory(mCurrentUser.getResellerModel(), beerFromDB.getBeerID(), 1);
                            finishActivity();
                        } else {
                            BeerModel beerFromInventory = mUserDBHelper.getBeer(beerFromDB, mCurrentUser.getResellerModel());
                            if (beerFromInventory.getQuantity() > 0) {
                                runSuccessDialog(beerFromInventory);

                            }

                            // Play a success animation, and display a dialog confirming the beer and quantity?
                            // Look for beer model with the barcode
                        }

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Task failed with an exception

                }
            }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    String s = "fail";

                }
            });
        }
    }

    public void finishActivity() {
        getActivity().finish();
        Intent intent = new Intent(getContext(), ResellerMainPageActivity.class);
        Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getContext(),
                android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
        startActivity(intent, bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void runSuccessDialog(BeerModel bm) {
        mSuccessDialog = new SuccessDialog(bm);
        mSuccessDialog.create();
        mSuccessDialog.show();
    }

    public class SuccessDialog extends Dialog implements View.OnClickListener {

        EditText mQuantity;
        LottieAnimationView mSuccessAnimation;
        BeerModel mBeerModel;

        TextView mBeerName;
        TextView mBeerBrewery;
        TextView mBeerCategory;
        Button mAddButton;

        public SuccessDialog(BeerModel bm) {
            super(getActivity());

            mBeerModel = bm;
        }

        @Override
        public void onClick(View v) {
            Log.d("ScanBarcode", "ClickedDialog");
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.dialog_customer_scan_barcode);
            mQuantity = findViewById(R.id.et_quantity1);
            mSuccessAnimation = findViewById(R.id.lottie_success_animation);
            mSuccessAnimation.playAnimation();
            mAddButton = findViewById(R.id.bt_add1);
            mBeerName = findViewById(R.id.tv_beer_name5);
            mBeerBrewery = findViewById(R.id.tv_beer_brewery5);
            mBeerCategory = findViewById(R.id.tv_beer_category5);


            mAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Check if the entered quantity is less than the max quantity
                    //If it is
                    //Add to basket
                    if (TextUtils.isEmpty(mQuantity.getText().toString())) {
                        mQuantity.setError("Please put in the quantity.");
                        AnimationHelper.shake(mQuantity);
                        return;
                    }

                    int inputQuantity = Integer.parseInt((mQuantity.getText().toString()));
                    // Add the beer to the inventory and
                    AnimationHelper.bounce(mAddButton);
                    BeerModel newBeer = mBeerModel.copy();
                    newBeer.setQuantity(mBeerModel.getQuantity() + inputQuantity);
                    //  newBeer.addQuantity(inputQuantity);

                    // If the beer doesnt exist in the inventory
                    if (mUserDBHelper.getBeer(newBeer, mCurrentUser.getResellerModel()) == null) {

                        mUserDBHelper.addBeerToInventory(mCurrentUser.getResellerModel(), newBeer.getBeerID(), inputQuantity);
                    } else// If it does exist, just add the quantity
                    {
                        mUserDBHelper.updateQuantity(newBeer, mCurrentUser.getResellerModel());
                    }

                    dismiss();
                    finishActivity();

                    // If the beer already exists
                    // Simply add quantity instead of adding a whole new beer

                }
            });

            //
            mBeerName.setText(mBeerModel.getBeerName());
            mBeerBrewery.setText(mBeerDBHelper.getBrewery(mBeerModel.getBeerBreweryID()).getBeerBreweryName());
            mBeerCategory.setText(mBeerDBHelper.getCategory(mBeerModel.getBeerCategoryID()).getBeerCategoryName());

        }
    }
}
