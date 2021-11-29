package com.example.beershop.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.beershop.database.BeerDataBaseHelper
import com.example.beershop.database.UserDataBaseHelper

class LoginFragmentViewModel(context : Context) : ViewModel()
{
    //constructor(context : Context)
    private val mUserDBHelper: UserDataBaseHelper? = UserDataBaseHelper(context)
    private val mBeerDBHelper: BeerDataBaseHelper? = BeerDataBaseHelper(context)


}