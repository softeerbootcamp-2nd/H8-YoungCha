package team.youngcha.domain.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.trim.dto.TrimDetail;

import java.util.ArrayList;
import java.util.List;

@ExtendWith({SoftAssertionsExtension.class, MockitoExtension.class})
class TrimServiceTest {

    @InjectSoftAssertions
    SoftAssertions softAssertions;

    @InjectMocks
    TrimService trimService;

    @Test
    @DisplayName("자동차 상세정보로부터 트림별 상세정보 목록을 추출할 수 있어야 한다")
    void extractTrimDetailsFromCarDetailsDtos() {
        //given
        ArrayList<CarDetails> carDetails = new ArrayList<>();
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "베젤리스 인사이드 미러", "내관", 1, "insidemirror_s.jpg"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "내비게이션 기반 스마트 크루즈 컨트롤 (안전구간, 곡선로)", "지능형 안전기술", 1, "ncss_s.jpg"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "12.3인치 내비게이션(블루링크, 폰 프로젝션, 현대 카페이)", "멀티미디어", 1, "12.3navi_s.jpg"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "쿨그레이 ", "내장 색상", 1, "colorchip-interior.png"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "크리미 화이트 펄", "외장 색상", 1, "colorchip-exterior.png"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "그라파이트 그레이 메탈릭", "외장 색상", 1, "colorchip-exterior.png"));
        carDetails.add(new CarDetails(1L, "팰리세이드", 5L, "Guide Mode", "guideModeImg.png", "guideModeBgImg.png", null, 38960000, null, null, null, null, null, null));

        //when
        List<TrimDetail> trimDetails = trimService.extractTrimDetailsFromCarDetailsDtos(carDetails);

        //then
        softAssertions.assertThat(trimDetails.size()).isEqualTo(2);

        TrimDetail leblancTrimDetail;
        TrimDetail guideModeTrimDetail;

        if (trimDetails.get(0).getName().equals("Le Blanc (르블랑)")) {
            leblancTrimDetail = trimDetails.get(0);
            guideModeTrimDetail = trimDetails.get(1);
        } else {
            leblancTrimDetail = trimDetails.get(1);
            guideModeTrimDetail = trimDetails.get(0);
        }

        softAssertions.assertThat(leblancTrimDetail.getId()).isEqualTo(2L);
        softAssertions.assertThat(leblancTrimDetail.getName()).isEqualTo("Le Blanc (르블랑)");
        softAssertions.assertThat(leblancTrimDetail.getImgUrl()).isEqualTo("leblancImg.png");
        softAssertions.assertThat(leblancTrimDetail.getBackgroundImgUrl()).isEqualTo("leblancBgImg.png");
        softAssertions.assertThat(leblancTrimDetail.getHashTag()).isEqualTo("베스트셀러");
        softAssertions.assertThat(leblancTrimDetail.getPrice()).isEqualTo(40440000);
        softAssertions.assertThat(leblancTrimDetail.getDescription()).isEqualTo("모두가 선택한 베스트셀러");
        softAssertions.assertThat(leblancTrimDetail.getMainOptions().size()).isEqualTo(3);
        softAssertions.assertThat(leblancTrimDetail.getInteriorColors().size()).isEqualTo(1);
        softAssertions.assertThat(leblancTrimDetail.getExteriorColors().size()).isEqualTo(2);


        softAssertions.assertThat(guideModeTrimDetail.getId()).isEqualTo(5L);
        softAssertions.assertThat(guideModeTrimDetail.getName()).isEqualTo("Guide Mode");
        softAssertions.assertThat(guideModeTrimDetail.getImgUrl()).isEqualTo("guideModeImg.png");
        softAssertions.assertThat(guideModeTrimDetail.getBackgroundImgUrl()).isEqualTo("guideModeBgImg.png");
        softAssertions.assertThat(guideModeTrimDetail.getHashTag()).isEqualTo(null);
        softAssertions.assertThat(guideModeTrimDetail.getPrice()).isEqualTo(38960000);
        softAssertions.assertThat(guideModeTrimDetail.getDescription()).isEqualTo(null);
        softAssertions.assertThat(guideModeTrimDetail.getMainOptions().size()).isEqualTo(0);
        softAssertions.assertThat(guideModeTrimDetail.getInteriorColors().size()).isEqualTo(0);
        softAssertions.assertThat(guideModeTrimDetail.getExteriorColors().size()).isEqualTo(0);
    }


}