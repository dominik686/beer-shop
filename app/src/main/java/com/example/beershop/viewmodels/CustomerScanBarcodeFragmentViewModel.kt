package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BeerBreweryModel
import com.example.beershop.models.BeerCategoryModel
import com.example.beershop.models.BeerModel
import com.example.beershop.singletons.CurrentSeller
import com.google.mlkit.vision.barcode.Barcode

class CustomerScanBarcodeFragmentViewModel(context : Context) : ViewModel()
{
    private var mBeerDBHelper = BeerDataBaseHelper(context)
    private var mUserDBHelper = UserDataBaseHelper(context)

    private var mCurrentSeller = CurrentSeller.getInstance()


    fun getBeer( barcode : String) : BeerModel
    {
        return mBeerDBHelper.getBeer(barcode)
    }

    fun getBrewery(mBeer : BeerModel) : BeerBreweryModel
    {
        return mBeerDBHelper.getBrewery(mBeer.beerBreweryID)
    }

    fun addToBasket(beer : BeerModel)
    {
        mCurrentSeller.addToBasket(beer)
    }
    fun getCategory(mBeer : BeerModel) : BeerCategoryModel
    {

        return mBeerDBHelper.getCategory(mBeer.beerCategoryID)
    }
    fun getBeerFromInventory(bm : BeerModel) : BeerModel
    {
        return mUserDBHelper.getBeer(bm, mCurrentSeller.resellerModel)
    }
}