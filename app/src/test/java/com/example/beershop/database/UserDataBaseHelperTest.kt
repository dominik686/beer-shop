package com.example.beershop.database;

import com.example.beershop.BuildConfig;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 30)
public class UserDataBaseHelperTest extends TestCase {
   // https://medium.com/mobile-app-development-publication/android-sqlite-database-unit-testing-is-easy-a09994701162#.j2bceyqq6
    private UserDataBaseHelper dbHelper;

    @Before
    public void setup()
    {
        dbHelper = new UserDataBaseHelper(RuntimeEnvironment.getApplication());
        //Add a clear method here?
    }

    public void testAddCustomer()
    {
    }

    public void testAddReseller()
    {
    }
}