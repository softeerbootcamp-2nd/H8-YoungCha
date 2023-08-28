package com.youngcha.ohmycarset.data.dto

data class TrimMainData(
    val `data`: Data,
    val message: String
) {
    data class Data(
        val guide: Guide,
        val modelName: ModelName,
        val trims: List<Trim>
    ) {
        data class Guide(
            val backgroundImgUrl: BackgroundImgUrl,
            val hashTag: String,
            val price: Int
        ) {
            data class BackgroundImgUrl(
                val android: String,
                val web: String
            )
        }

        data class ModelName(
            val en: String,
            val ko: String
        )

        data class Trim(
            val backgroundImgUrl: BackgroundImgUrl,
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
            data class BackgroundImgUrl(
                val android: String,
                val web: String
            )

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