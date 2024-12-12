package com.example.foodcatalogue

import android.os.Build
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.TransitionSet
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodcatalogue.data.Food
import com.example.foodcatalogue.databinding.ActivityFoodDetailBinding

class FoodDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodDetailBinding

    companion object {
        const val EXTRA_FOOD = "extra_food"
        const val EXTRA_TRANSITION_NAME = "extra_transition_name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val transitionName = intent.getStringExtra(EXTRA_TRANSITION_NAME)
        val food: Food? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_FOOD, Food::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_FOOD)
        }

        Toast.makeText(this, "Kamu memilih $transitionName", Toast.LENGTH_SHORT).show()

        if (food != null) {
            binding.ivDetailPhoto.setImageResource(food.photoUrl)
            binding.tvDetailName.text = food.name
            binding.tvDetailDescription.text = food.description

            binding.ivDetailPhoto.transitionName = transitionName

            // Combine ChangeBounds and ChangeImageTransform
            val transitionSet = TransitionSet().apply {
                addTransition(ChangeBounds().apply {
                    duration = 500
                })
//                addTransition(ChangeImageTransform().apply {
//                    duration = 500
//                })
            }

            // Apply the transition set
            window.sharedElementEnterTransition = ChangeBounds().apply {
                duration = 300
            }

            // Optionally, apply a shared exit transition
            window.sharedElementExitTransition = ChangeBounds().apply {
                duration = 300
            }
        } else {
            finish()
        }
    }
}