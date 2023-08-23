package com.youngcha.ohmycarset.data.model

data class TrimMainData(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val model: String,
        val trims: List<Trim>
    ) {
        data class Trim(
            val backgroundImgUrl: String,
            val best: Boolean,
            val description: String,
            val exteriorColors: List<ExteriorColor>,
            val hashTag: String,
            val id: Int,
            val imgUrl: String,
            val interiorColors: List<InteriorColor>,
            val mainOptions: List<MainOption>,
            val name: String,
            val price: Int
        ) {
            data class ExteriorColor(
                val imgUrl: String,
                val name: String
            )

            data class InteriorColor(
                val imgUrl: String,
                val name: String
            )

            data class MainOption(
                val imgUrl: String,
                val name: String
            )
        }
    }
}