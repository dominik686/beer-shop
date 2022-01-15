package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BasketModel
import com.example.beershop.models.BeerBreweryModel
import com.example.beershop.models.BeerCategoryModel
import com.example.beershop.models.BeerModel
import com.example.beershop.singletons.CurrentSeller
import com.example.beershop.singletons.CurrentUser
import java.util.*

class CustomerBasketViewModel(context : Context) : ViewModel()
{
   private var mCurrentSeller = CurrentSeller.getInstance()

    private   var mBeerDBHelper = BeerDataBaseHelper(context)
    private  var mUserDBHelper = UserDataBaseHelper(context)


    fun removeFromInventory(beers : List<BeerModel>)
    {
        mUserDBHelper.removeFromInventory(beers, mCurrentSeller.resellerModel)
    }

    fun getCategory(beerModel: BeerModel) : BeerCategoryModel
    {
        return mBeerDBHelper.getCategory(beerModel.getBeerCategoryID())
    }
    fun getBrewery(beerModel: BeerModel) : BeerBreweryModel
    {
       return mBeerDBHelper.getBrewery(beerModel.beerBreweryID)
    }
    fun isBasketEmpty() : Boolean
    {
        return mCurrentSeller.basketModel.beers.isEmpty();
    }
    fun getBasket(): BasketModel
    {
       return mCurrentSeller.basketModel
    }
    fun clearBasket()
    {
        mCurrentSeller.clearBasket();
    }

    private fun getInventory() : String
    {
        return mUserDBHelper.getInventory(mCurrentSeller.resellerModel)
    }

    fun updateInventory()
    {
        mCurrentSeller.updateInventory(getInventory())
    }

}