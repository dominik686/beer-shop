package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BeerBreweryModel
import com.example.beershop.models.BeerCategoryModel
import com.example.beershop.models.BeerModel
import com.example.beershop.singletons.CurrentUser

class ResellerMainPageFragmentViewModel(context : Context) : ViewModel()
{

    private  var mBeerDBHelper: BeerDataBaseHelper = BeerDataBaseHelper(context)
    private var mUserDBHelper: UserDataBaseHelper = UserDataBaseHelper(context)

    private var mCurrentUser: CurrentUser = CurrentUser.getInstance(null, null)

    fun getListFromInventory(inventory : String) : List<BeerModel>
    {
        return  mBeerDBHelper.getBeerListFromInventory(inventory)
    }

    fun getCategory(id  : Int) : BeerCategoryModel
    {
        return mBeerDBHelper.getCategory(id)
    }

    fun getBrewery(id : Int) : BeerBreweryModel
    {
        return mBeerDBHelper.getBrewery(id)
    }
    fun getInventory() : String
    {
        return mUserDBHelper.getInventory(mCurrentUser.resellerModel)
    }
}