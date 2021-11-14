package com.example.beershop.database

import android.os.Build.VERSION_CODES.LOLLIPOP
import com.example.beershop.BuildConfig
import org.robolectric.RobolectricTestRunner
import com.example.beershop.database.UserDataBaseHelper
import com.example.beershop.models.BeerModel
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
        assertEquals(customerModel.toString(), dbHelper?.customerToString())
    }

    @Test
    fun testCustomerCredentialsCheck_validCredentials_ReturnsTrue()
    {
        // Given
        val customerModel = CustomerModel(1, "customer", "passowrd")
        dbHelper!!.addCustomer(customerModel)

        // When
        val result = dbHelper!!.customerCredentialsCheck(customerModel)

        // Then
        assertEquals(true, result)
    }
    @Test
    fun testCustomerCredentialsCheck_invalidCredentials_ReturnsFalse()
    {
        // Given
        val customerModel = CustomerModel(1, "customer", "passowrd")

        // When
        val result = dbHelper!!.customerCredentialsCheck(customerModel)

        // Then
        assertEquals(false, result)

    }

    @Test
    fun testCustomerUsernameCheck_validUsername_ReturnsTrue()
    {
        // Given
        val customerModel = CustomerModel(1, "customer", "passowrd")
        dbHelper!!.addCustomer(customerModel)

        // When
        val result = dbHelper!!.customerUsernameCheck(customerModel)

        // Then
        assertEquals(true, result)
    }
    @Test
    fun testCustomerUsernameCheck_invalidUsername_ReturnsFalse()
    {
        // Given
        val customerModel = CustomerModel(1, "customer", "passowrd")

        // When
        val result = dbHelper!!.customerUsernameCheck(customerModel)

        // Then
        assertEquals(false, result)
    }


    @Test
    fun testAddReseller_validData()
    {
        // Given
        val resellerModel = ResellerModel(1, "reseller" ,"password")
        // When
        dbHelper!!.addReseller(resellerModel)
        // Then
        assertEquals(resellerModel.toString(), dbHelper!!.resellerToString())
    }
    @Test
    fun testResellerCredentialsCheck_validCredentials_ReturnsTrue()
    {
        // Given
        val resellerModel = ResellerModel(1, "Reseller", "Password", "")
        dbHelper!!.addReseller(resellerModel)

        // When
        val result = dbHelper!!.resellerCredentialsCheck(resellerModel)

        // Then
        assertEquals(true, result)
    }
    @Test
    fun testResellerCredentialsCheck_invalidCredentials_ReturnsFalse()
    {
        // Given
        val resellerModel = ResellerModel(1, "Reseller", "Password", "")

        // When
        val result = dbHelper!!.resellerCredentialsCheck(resellerModel)

        // Then
        assertEquals(false, result)

    }
    @Test
    fun testResellerUsernameCheck_validUsername_ReturnsTrue()
    {
        // Given
        val resellerModel = ResellerModel(1, "Reseller", "Password", "")
        dbHelper!!.addReseller(resellerModel)

        // When
        val result = dbHelper!!.resellerUsernameCheck(resellerModel)

        // Then
        assertEquals(true, result)
    }

    @Test
    fun testResellerUsernameCheck_invalidUsername_ReturnsFalse()
    {
        // Given
        val resellerModel = ResellerModel(1, "Reseller", "Password", "")

        // When
        val result = dbHelper!!.resellerUsernameCheck(resellerModel)

        // Then
        assertEquals(false, result)
    }

    @Test
    fun testAddBeerToInventory_unknownReseller_ReturnsTrue()
    {
        // Given
        val resellerModel = ResellerModel(1, "Reseller", "Password", "")
        dbHelper!!.addReseller(resellerModel)

        // When
        val result = dbHelper!!.addBeerToInventory(resellerModel, 1, 50)



        // Then
        assertEquals(true, result )
    }
}