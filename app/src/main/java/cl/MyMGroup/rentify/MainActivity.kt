package cl.MyMGroup.rentify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cl.MyMGroup.rentify.data.dataBase.RentifyDataBase
import cl.MyMGroup.rentify.navigation.AppNavigation
import cl.MyMGroup.rentify.ui.theme.RentifyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataBase = RentifyDataBase.getDataBase(this)

        setContent {
            RentifyTheme {
                AppNavigation(dataBase = dataBase)
            }
        }
    }
}

