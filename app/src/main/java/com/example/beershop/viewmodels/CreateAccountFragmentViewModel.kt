package com.example.beershop.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.CustomerModel
import com.example.beershop.models.ResellerModel

class CreateAccountFragmentViewModel(context : Context) : ViewModel()
{
    private var mUserDBHelper = UserDataBaseHelper(context)

    private lateinit var  mCustomerModel : CustomerModel
    private lateinit var mResellerModel  : ResellerModel

    fun createNewCustomerAccount(pUsername: String?, pPassword: String?) : Boolean{
        mCustomerModel = CustomerModel(-1, pUsername, pPassword)

        // Try adding the customer to the database
        // If the method fails it will return false
      return mUserDBHelper.addCustomer(mCustomerModel)
    }

    //Try adding new account ot he database
    fun createNewResellerAccount(pUsername: String?, pPassword: String?)  : Boolean {
        mResellerModel = ResellerModel(-1, pUsername, pPassword)

        // Try adding the reseller to the database
        // If the method fails it will return false
      return mUserDBHelper.addReseller(mResellerModel)
    }

}