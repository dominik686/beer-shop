package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.ResellerModel
import com.example.beershop.singletons.CurrentSeller

class CustomerSellerListFragmentViewModel(context : Context) : ViewModel()
{
    var mDBHelper: UserDataBaseHelper =  UserDataBaseHelper(context)

    fun getAllResellers() : List<ResellerModel>{
       return mDBHelper.getAllResellers();
    }

    fun updateSeller(rm : ResellerModel)
    {
        CurrentSeller.getInstance(rm)
    }
}