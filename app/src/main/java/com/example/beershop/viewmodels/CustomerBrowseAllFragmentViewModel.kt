package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.*
import com.example.beershop.singletons.CurrentSeller
import com.example.beershop.singletons.CurrentUser

class CustomerBrowseAllFragmentViewModel(context : Context) : ViewModel()
{
    private val mUserDBHelper: UserDataBaseHelper = UserDataBaseHelper(context)
    private val mBeerDBHelper: BeerDataBaseHelper = BeerDataBaseHelper(context)


    private var mCurrentSeller: CurrentSeller = CurrentSeller.getInstance()

    fun getCurrentSellerInventory() : String
    {
      return  mUserDBHelper.getInventory(mCurrentSeller.resellerModel)
    }
  fun getBeerListFromInventory(inventory : String): MutableList<BeerModel>? {
      return mBeerDBHelper.getBeerListFromInventory(inventory)
  }



    fun addToBasket(tempBeer : BeerModel)
    {
        mCurrentSeller.addToBasket(tempBeer)
    }
    fun getCategory(bm : BeerModel) : BeerCategoryModel
    {
       return mBeerDBHelper.getCategory(bm.beerCategoryID)
    }

    fun getCategoryName(bm : BeerModel) : String
    {
        var category =  mBeerDBHelper.getCategory(bm.beerCategoryID)

        return category.beerCategoryName;
    }
    fun getBreweryName(bm : BeerModel) : String
    {
        var brewery =  mBeerDBHelper.getBrewery(bm.beerBreweryID)

        return brewery.beerBreweryName;
    }
    fun getBrewery(bm : BeerModel) : BeerBreweryModel
    {
        return mBeerDBHelper.getBrewery(bm.beerBreweryID)
    }
}