package com.example.beershop.fragments;

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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.beershop.R;
import com.example.beershop.database.BeerDataBaseHelper;
import com.example.beershop.database.UserDataBaseHelper;
import com.example.beershop.models.BeerModel;
import com.example.beershop.singletons.CurrentSeller;
import com.example.beershop.utils.AnimationHelper;
import com.example.beershop.viewmodels.CustomerScanBarcodeFragmentViewModel;
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

public class CustomerScanBarcodeFragment extends Fragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    String mCurrentPhotoPath;
    LottieAnimationView mScanAnimation;

    CustomerScanBarcodeFragmentViewModel mViewModel;
    public static CustomerScanBarcodeFragment newInstance() {

        Bundle args = new Bundle();

        CustomerScanBarcodeFragment fragment = new CustomerScanBarcodeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_customer_scan_barcode, null);
        mScanAnimation = v.findViewById(R.id.lottie_scan_barcode);

        mViewModel = new CustomerScanBarcodeFragmentViewModel( getContext());


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
        // dispatchTakePictureIntent();

        //mScanAnimation.cancelAnimation();
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
                        assert s != null;
                        BeerModel beerFromDB = mViewModel.getBeer(s);

                        // If bm == null then the beer doesnt exist in the DB
                        if (beerFromDB == null) {
                            // FailureDialog?
                            Toast.makeText(getContext(), "There was a mistake! try adding the beer manually", Toast.LENGTH_LONG).show();
                        } else {
                            BeerModel beerFromInventory = mViewModel.getBeerFromInventory(beerFromDB);
                            if (beerFromInventory.getQuantity() > 0) {
                                runSuccessDialog(beerFromInventory);
                            }
                            // Play a success animation, and display a dialog confirming the beer and quantity?
                            // Look for beer model with the barcode
                        }
                    }
                    getActivity().finish();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void runSuccessDialog(BeerModel bm) {
        SuccessDialog cdd = new SuccessDialog(bm);
        cdd.create();
        cdd.show();
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
                    // If the inputQuantity is less than the max quantity, add the beer to the basket
                    // and destroy the activity
                    if ((mBeerModel.getQuantity() >= inputQuantity) && (mQuantity.getText().toString() != "")) {
                        AnimationHelper.bounce(mAddButton);
                        BeerModel newBeer = mBeerModel.copy();
                        newBeer.setQuantity(Integer.parseInt((mQuantity.getText().toString())));
                        mViewModel.addToBasket(newBeer);
                        Toast.makeText(getContext(), "Beer has been added!", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                        dismiss();
                    } else {
                        Toast.makeText(getContext(), "Quantity needs to be less or equal to: " + mBeerModel.getQuantity(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //
            mBeerName.setText(mBeerModel.getBeerName());
            mBeerBrewery.setText(mViewModel.getBrewery(mBeerModel).getBeerBreweryName());
            mBeerCategory.setText(mViewModel.getCategory(mBeerModel).getBeerCategoryName());

        }
    }
}
