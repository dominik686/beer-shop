package com.example.beershop.database

import org.robolectric.RobolectricTestRunner
import com.example.beershop.database.UserDataBaseHelper
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class UserDataBaseHelperTest : TestCase() {
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
    fun testAddCustomer() {
        // Given


        // When


        // Then
    }

    fun testAddReseller() {}

    @Test
    fun `When Given `()
    {

    }
}