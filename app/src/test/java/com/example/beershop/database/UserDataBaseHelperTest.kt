package com.example.beershop.database

import android.os.Build.VERSION_CODES.LOLLIPOP
import com.example.beershop.BuildConfig
import org.robolectric.RobolectricTestRunner
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.CustomerModel
import com.example.beershop.models.ResellerModel
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class UserDataBaseHelperTest : TestCase(){
    // https://medium.com/mobile-app-development-publication/android-sqlite-database-unit-testing-is-easy-a09994701162#.j2bceyqq6
  //   https://medium.com/@boonkeat/android-unit-testing-with-junit5-d1b8f9c620b6
    private var dbHelper: UserDataBaseHelper? = null
    @Before
    fun setup() {
        dbHelper = UserDataBaseHelper(RuntimeEnvironment.getApplication())
        dbHelper!!.clearDbAndRecreate()
    }

    @After
    public override fun tearDown() {
        dbHelper!!.clearDb()
    }

    @Test
    fun testAddCustomer_validData() {
        // Given
        val customerModel = CustomerModel(1, "customer", "password")

        // When
        dbHelper?.addCustomer(customerModel)

        // Then
        assertEquals(dbHelper?.customerToString(), customerModel.toString())
    }


    @Test
    fun testAddReseller_validData()
    {
        // Given
        val resellerModel = ResellerModel(1, "reseller" ,"password")
        // When
        dbHelper!!.addReseller(resellerModel)
        // Then
        assertEquals(dbHelper!!.resellerToString(), resellerModel.toString())
    }
    //Add test cases for invalid customer models as well?
    @Test
    fun `When Given `()
    {

    }
}