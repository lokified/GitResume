package com.loki.gitresume.util

import androidx.annotation.DrawableRes
import com.loki.gitresume.R

data class Project(
    @DrawableRes
    val image: Int,
    val name: String,
    val url: String
)

val projects = listOf(
    Project(
        image = R.drawable.cocktails,
        name = "Drink Tales",
        url = "https://github.com/lokified/DrinkTales"
    ),
    Project(
        image = R.drawable.okoa_loan,
        name = "Okoa Loan",
        url = "https://github.com/lokified/OkoaLoan"
    ),
    Project(
        image = R.drawable.reet,
        name = "Reet",
        url = "https://github.com/lokified/Reet"
    ),
    Project(
        image = R.drawable.booky,
        name = "Booky",
        url = "https://github.com/lokified/Booky"
    ),
    Project(
        image = R.drawable.dms,
        name = "DMS",
        url = "https://github.com/lokified/Dms"
    ),
    Project(
        image = R.drawable.harry_potter,
        name = "Harry Potter",
        url = "https://github.com/lokified/HarryPotterApp"
    )
)