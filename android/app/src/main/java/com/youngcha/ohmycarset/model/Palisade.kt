package com.youngcha.ohmycarset.model
data class Palisade(
    val powerTrain: PowerTrain,
    val drivingSystem: DrivingSystem,
    val bodyType: BodyType,
    val exteriorColor: ExteriorColor,
    val interiorColor: InteriorColor,
    val wheel: Wheel,
    val option:List<Option>
) {
}