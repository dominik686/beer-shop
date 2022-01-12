package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BeerBreweryModel
import com.example.beershop.models.BeerCategoryModel
import com.example.beershop.models.BeerModel
import com.example.beershop.singletons.CurrentSeller
import com.example.beershop.singletons.CurrentUser
import java.util.ArrayList

class ResellerAddBeerFragmentViewModel(context: Context) : ViewModel()
{
    private var  mUserDBHelper = UserDataBaseHelper(context)
    private var  mBeerDBHelper = BeerDataBaseHelper(context)
    private var  mCurrentUser = CurrentUser.getInstance(null, null)

    fun addBeerToInventory(beerModel : BeerModel, quantity : Int) : Boolean
    {
        return  mUserDBHelper.addBeerToInventory(mCurrentUser.resellerModel, mBeerDBHelper.getBeerId(beerModel), quantity)
    }
    fun addBeerToInventory(beerId : Int, beerQuantity : Int) : Boolean
    {
       return mUserDBHelper.addBeerToInventory(mCurrentUser.resellerModel, beerId, beerQuantity)
    }
    fun checkIfBeerExists(beerModel: BeerModel) : Boolean
    {
        return mBeerDBHelper.checkIfBeerExists(beerModel)
    }
    fun addBeerToDatabase(beerModel: BeerModel) : Boolean
    {
       return mBeerDBHelper.addBeer(beerModel);
    }

    fun isBeerInInventory(beerModel: BeerModel) : Boolean
    {
        return mUserDBHelper.getBeer(beerModel, mCurrentUser.resellerModel) == null
    }

    fun getBeerFromInventory(beerModel: BeerModel) : Boolean
    {
        return mUserDBHelper.getBeer(beerModel, mCurrentUser.resellerModel) != null
    }

    fun updateInventory(beerModel: BeerModel) : Boolean
    {
       return mUserDBHelper.updateQuantity(beerModel, mCurrentUser.resellerModel)
    }

    fun getCategoryList() : List<BeerCategoryModel>
    {
        return mBeerDBHelper.allCategories
    }
    fun getBreweryList() : List<BeerBreweryModel>
    {
        return mBeerDBHelper.allBreweries
    }

    //Get names from the list of category models
    fun getCategoryNames(list: List<BeerCategoryModel>): List<String>? {
        val returnList = ArrayList<String>()
        for (model in list) {
            returnList.add(model.beerCategoryName)
        }
        return returnList
    }

    //Get names from the list of brewery models
    fun getBreweryNames(list: List<BeerBreweryModel>): List<String>? {
        val returnList = ArrayList<String>()
        for (model in list) {
            returnList.add(model.beerBreweryName)
        }
        return returnList
    }

}