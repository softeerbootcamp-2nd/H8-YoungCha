package com.youngcha.ohmycarset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.youngcha.ohmycarset.R
import com.youngcha.ohmycarset.model.TrimSelfMode
import com.youngcha.ohmycarset.model.TrimSelfModeExteriorColor
import com.youngcha.ohmycarset.model.TrimSelfModeInteriorColor
import com.youngcha.ohmycarset.model.TrimSelfModeMainOption
import com.youngcha.ohmycarset.model.TrimSelfModeOption

class TrimSelfModeViewModel : ViewModel() {

    // LiveData & MutableLiveData 정의
    private val _trimSelfModeData = MutableLiveData<TrimSelfMode>()
    val trimSelfModeData: LiveData<TrimSelfMode> = _trimSelfModeData
    val displayItemCount: MutableLiveData<Int> = MutableLiveData(5)

    val filteredOptions: MutableLiveData<List<TrimSelfModeOption>> = MutableLiveData()

    // ViewModel 초기화
    init {
        loadTrimSelfMode()
    }

    fun showAllItems() {
        displayItemCount.value = trimSelfModeData.value?.options?.size
    }

    fun filterOptionsByTabName(tabName: String) {
        val allOptions = trimSelfModeData.value?.options ?: emptyList()

        val matchedOptions = if (tabName == "전체") {
            showAllItems()
            allOptions
        } else {
            allOptions.filter { option ->
                option.type == tabName
            }
        }

        filteredOptions.value = matchedOptions
    }

    private fun loadTrimSelfMode() {
        // 임시 데이터 생성
        val tempTrimSelfMode = TrimSelfMode(
            id = 1L,
            name = "펠리세이드",
            imgUrl = R.drawable.img_leblanc,
            hashtag = "#Example1",
            description = "Le Blanc 르블랑",
            isBest = true,
            minPrice = "38,960,000원 부터",
            mainOptions = listOf(
                TrimSelfModeMainOption(
                    imgUrl = R.drawable.img_core_option_explain,
                    description = "Main option 1"
                )
            ),
            exteriorColor = listOf(
                TrimSelfModeExteriorColor(code = "#FAFAFA", name = "크리미 화이트 펄"),
                TrimSelfModeExteriorColor(code = "#111111", name = "어비스 블랙펄"),
                TrimSelfModeExteriorColor(code = "#9B9D9C", name = "쉬머링 실버 메탈릭"),
                TrimSelfModeExteriorColor(code = "#2C2925", name = "그라파이트 그레이 메탈"),
                TrimSelfModeExteriorColor(code = "#1C2234", name = "문라이트 블루 펄"),
                TrimSelfModeExteriorColor(code = "#333635", name = "가이아 브라운 펄")
            ),
            interiorColor = listOf(
                TrimSelfModeInteriorColor(
                    url = R.drawable.img_quilting_natural,
                    name = "퀄팅 천연(블랙)"
                ),
                TrimSelfModeInteriorColor(url = R.drawable.img_cool_grey, name = "쿨 그레이")
            ),
            options = listOf(
                TrimSelfModeOption(R.drawable.img_test_1, "ISG 시스템", "기본 포함"),
                TrimSelfModeOption(R.drawable.img_test_2, "통합 주행 모드", "기본 포함"),
                TrimSelfModeOption(R.drawable.img_test_3, "전자식 변속 버튼", "기본 포함"),
                TrimSelfModeOption(R.drawable.img_test_4, "랙 구동형 전동식 파워 스티어링 (R-MDPS)", "기본 포함"),
                TrimSelfModeOption(R.drawable.img_test_5, "8단 자동변 속기", "기본 포함"),
                TrimSelfModeOption(R.drawable.img_test_1, "성능1", "성능"),
                TrimSelfModeOption(R.drawable.img_test_2, "지능형 안전기술1", "지능형 안전기술"),
                TrimSelfModeOption(R.drawable.img_test_3, "안전1", "안전"),
                TrimSelfModeOption(R.drawable.img_test_4, "외관1", "외관"),
                TrimSelfModeOption(R.drawable.img_test_5, "내장1", "내장"),
                TrimSelfModeOption(R.drawable.img_test_1, "시트1", "시트"),
                TrimSelfModeOption(R.drawable.img_test_2, "편의1", "편의"),
                TrimSelfModeOption(R.drawable.img_test_3, "멀티미디어1", "멀티미디어"),
                TrimSelfModeOption(R.drawable.img_test_4, "멀티미디어2", "멀티미디어"),
                TrimSelfModeOption(R.drawable.img_test_5, "편의2", "편의"),
                TrimSelfModeOption(R.drawable.img_test_1, "시트2", "시트"),
                TrimSelfModeOption(R.drawable.img_test_2, "내장2", "내장"),
                TrimSelfModeOption(R.drawable.img_test_3, "외관2", "외관"),
                TrimSelfModeOption(R.drawable.img_test_4, "안전2", "안전"),
                TrimSelfModeOption(R.drawable.img_test_5, "지능형 안전기술2", "지능형 안전기술"),
                TrimSelfModeOption(R.drawable.img_test_1, "성능2", "성능")
            )
            /*
            백엔드 - 페이징 check - jetpack
                    val tabNames = listOf("전체", "성능", "지능형 안전기술", "안전", "외관", "내장", "시트", "편의", "멀티미디어")
            ISG 시스템 - 기본 포함
    통합주행모드 - 기본 포함
    전자식 변속버튼 - 기본 포함
    랙 구동형 전동식 파워 스티어링 (R-MDPS)
    8단 자동변속기
             */
        )

        _trimSelfModeData.value = tempTrimSelfMode
    }
}