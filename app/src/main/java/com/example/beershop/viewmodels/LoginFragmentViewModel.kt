package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.CustomerModel
import com.example.beershop.models.ResellerModel
import com.example.beershop.singletons.CurrentUser

class LoginFragmentViewModel(context : Context) : ViewModel()
{
    //constructor(context : Context)
    private val mUserDBHelper: UserDataBaseHelper = UserDataBaseHelper(context)
    private val mBeerDBHelper: BeerDataBaseHelper = BeerDataBaseHelper(context)


    private  var mCustomerModel : CustomerModel? = null;
    private  var mResellerModel : ResellerModel? = null

   fun addDefaultVales() : Boolean
   {
        return mBeerDBHelper.addDefaultValues()
   }
    fun costumerCredentialsCheck(username : String, password : String) : Boolean
    {
        mCustomerModel = CustomerModel(-1, username, password)
        return mUserDBHelper.customerCredentialsCheck(mCustomerModel)
    }
    fun resellerCredentialsCheck(username : String, password : String) : Boolean
    {
        mResellerModel = ResellerModel(-1, username, password)
        return mUserDBHelper.resellerCredentialsCheck(mResellerModel)
    }

    fun updateCurrentUser()
    {
        if(mCustomerModel != null)
        {
            CurrentUser.getInstance(null, mCustomerModel)
        }
        else if(mResellerModel != null)
        {
            CurrentUser.getInstance(mResellerModel, null)
        }
    }
}