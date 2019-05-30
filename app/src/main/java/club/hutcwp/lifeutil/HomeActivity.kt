package club.hutcwp.lifeutil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.hutcwp.cartoon.webp.MainActivity


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
