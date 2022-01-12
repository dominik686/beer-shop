package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BeerModel
import com.example.beershop.singletons.CurrentUser

class ResellerScanBarcodeFragmentViewModel(context : Context)  : ViewModel()
{
    private  var mBeerDBHelper: BeerDataBaseHelper = BeerDataBaseHelper(context)
    private var mUserDBHelper: UserDataBaseHelper = UserDataBaseHelper(context)

    private var mCurrentUser: CurrentUser = CurrentUser.getInstance(null, null)


    fun getBreweryNameFromID(id : Int) : String
    {
       return mBeerDBHelper.getBrewery(id).beerBreweryName
    }
    fun getCategoryNameFromID(id : Int) : String
    {
        return mBeerDBHelper.getCategory(id).beerCategoryName
    }
    fun updateQuantity(newBeer :BeerModel)
    {
        mUserDBHelper.updateQuantity(newBeer, mCurrentUser.resellerModel)
    }

    fun addBeerToInventory(beerId : Int, quantity : Int) : Boolean
    {

        // Add the a single beer to the inventory - if there is need to add more,
        // try adding again
       return mUserDBHelper.addBeerToInventory(mCurrentUser.resellerModel, beerId, quantity)
    }
    fun getBeer(barcode : String) : BeerModel
    {
        return mBeerDBHelper.getBeer(barcode)
    }

    fun doesBeerExistInDB(beerModel: BeerModel) : Boolean
    {
        return mBeerDBHelper.checkIfBeerExists(beerModel)
    }
    fun doesBeerExistInInventory(beerModel : BeerModel) : Boolean
    {
        return mUserDBHelper.getBeer(beerModel, mCurrentUser.resellerModel) != null
    }
}