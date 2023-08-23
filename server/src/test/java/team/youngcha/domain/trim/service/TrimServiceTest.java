package team.youngcha.domain.trim.service;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.InjectSoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.option.repository.OptionRepository;
import team.youngcha.domain.trim.dto.FindTrimDefaultOptionsResponse;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.entity.Trim;
import team.youngcha.domain.trim.repository.TrimRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith({SoftAssertionsExtension.class, MockitoExtension.class})
class TrimServiceTest {

    @InjectSoftAssertions
    SoftAssertions softAssertions;

    @Mock
    TrimRepository trimRepository;

    @Mock
    OptionRepository optionRepository;

    @InjectMocks
    TrimService trimService;

    @Test
    @DisplayName("자동차 상세정보로부터 트림별 상세정보 목록을 추출할 수 있어야 한다")
    void extractTrimDetails() {
        //given
        ArrayList<CarDetails> carDetails = new ArrayList<>();
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "베젤리스 인사이드 미러", "내관", 1, "insidemirror_s.jpg"));
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "내비게이션 기반 스마트 크루즈 컨트롤 (안전구간, 곡선로)", "지능형 안전기술", 1, "ncss_s.jpg"));
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 2, "12.3인치 내비게이션(블루링크, 폰 프로젝션, 현대 카페이)", "멀티미디어", 1, "12.3navi_s.jpg"));
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "쿨그레이 ", "내장 색상", 1, "colorchip-interior.png"));
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "크리미 화이트 펄", "외장 색상", 1, "colorchip-exterior.png"));
        carDetails.add(new CarDetails(2L, "Le Blanc (르블랑)", "leblancImg.png", "leblancBgImg.png", "베스트셀러", 40440000, "모두가 선택한 베스트셀러", 1, "그라파이트 그레이 메탈릭", "외장 색상", 1, "colorchip-exterior.png"));
        carDetails.add(new CarDetails(5L, "Guide Mode", "guideModeImg.png", "guideModeBgImg.png", null, 38960000, null, null, null, null, null, null));

        //when
        List<TrimDetail> trimDetails = trimService.extractTrimDetails(carDetails);

        //then
        softAssertions.assertThat(trimDetails.size()).isEqualTo(1);

        TrimDetail leblancTrimDetail = trimDetails.get(0);

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
    }

    @Nested
    @DisplayName("트림 기본 품목 조회")
    class FindDefaultOptions {

        Long trimId = 2L;
        Long categoryId = 1L;
        int pageSize = 10;
        int totalElements = 60;
        int totalPages = 6;

        ArrayList<DefaultOptionSummary> defaultOptionSummaries = new ArrayList<>(Arrays.asList(
                new DefaultOptionSummary("옵션1", trimId, "이미지주소1"),
                new DefaultOptionSummary("옵션2", trimId, "이미지주소2"),
                new DefaultOptionSummary("옵션3", trimId, "이미지주소3")));

        @Test
        @DisplayName("트림 기본 품목 조회 시, 페이지의 크기가 1 미만이면 BAD REQUEST 예외가 발생한다")
        void pageSizeSmallerThanOne() {
            //given
            int pageSizeZero = 0;
            int pageSizeNegative = -10;

            //when
            CustomException customExceptionZero = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, 1, pageSizeZero));

            CustomException customExceptionNegative = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, 1, pageSizeNegative));

            //then
            softAssertions.assertThat(customExceptionZero.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            softAssertions.assertThat(customExceptionZero.getMessage()).isEqualTo("페이지 크기 오류");
            softAssertions.assertThat(customExceptionNegative.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            softAssertions.assertThat(customExceptionNegative.getMessage()).isEqualTo("페이지 크기 오류");
        }

        @Test
        @DisplayName("트림 기본 품목 조회 시, 페이지 번호가 1 미만이면 BAD REQUEST 예외가 발생한다")
        void pageNumberSmallerThanOne() {
            //given
            int pageNumberZero = 0;
            int pageNumberNegative = -10;

            //when
            CustomException customExceptionZero = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, pageNumberZero, 5));

            CustomException customExceptionNegative = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, pageNumberNegative, 5));

            //then
            softAssertions.assertThat(customExceptionZero.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            softAssertions.assertThat(customExceptionZero.getMessage()).isEqualTo("페이지 번호 오류");
            softAssertions.assertThat(customExceptionNegative.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
            softAssertions.assertThat(customExceptionNegative.getMessage()).isEqualTo("페이지 번호 오류");
        }

        @Test
        @DisplayName("트림 기본 품목 조회 시, 트림 아이디가 존재하지 않으면 NOT FOUND 예외가 발생한다")
        void nonExistentTrimId() {
            //given
            Long notFoundTrimId = -37L;

            given(trimRepository.findById(anyLong())).willReturn(Optional.empty());

            //when
            CustomException customException = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(notFoundTrimId, categoryId, 1, 5));

            //then
            softAssertions.assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(customException.getMessage()).isEqualTo("트림 조회 오류");
        }

        @Test
        @DisplayName("트림의 기본 품목이 존재하지 않으면, NOT FOUND 예외가 발생한다")
        void noDefaultOptions() {
            //given
            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(0);

            //when
            CustomException customException = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, 1, 5));

            //then
            softAssertions.assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(customException.getMessage()).isEqualTo("트림 기본 품목 조회 실패");
        }

        @Test
        @DisplayName("페이지 번호가 마지막 페이지 번호보다 크면, NOT FOUND 예외가 발생한다")
        void outRangedPageNumber() {
            //given
            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(totalElements);

            //when
            CustomException customException = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, totalPages + 1, pageSize));

            //then
            softAssertions.assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(customException.getMessage()).isEqualTo("페이지 번호 초과 오류");
        }

        @Test
        @DisplayName("페이지 조회 실패 시, NOT FOUND 예외가 발생한다")
        void failedRetrievingPage() {
            //given
            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(totalElements);
            given(optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong(), anyInt(), anyInt()))
                    .willReturn(new ArrayList<>());

            //when
            CustomException customException = assertThrows(CustomException.class, () ->
                    trimService.findPaginatedDefaultOptions(trimId, categoryId, totalPages, pageSize));

            //then
            softAssertions.assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(customException.getMessage()).isEqualTo("페이지 조회 실패");
        }

        @Test
        @DisplayName("첫 번째 페이지의 조회를 검증한다")
        void findFirstPage() {
            //given
            int targetPage = 1;

            FindTrimDefaultOptionsResponse expectedResponse =
                    new FindTrimDefaultOptionsResponse(trimId, true, false, totalElements, totalPages, defaultOptionSummaries);

            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(totalElements);
            given(optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong(), anyInt(), anyInt()))
                    .willReturn(defaultOptionSummaries);

            //when
            FindTrimDefaultOptionsResponse actualResponse = trimService.findPaginatedDefaultOptions(trimId, categoryId, targetPage, pageSize);

            //then
            softAssertions.assertThat(actualResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }

        @Test
        @DisplayName("중간 페이지의 조회를 검증한다")
        void findMiddlePage() {
            //given
            int targetPage = totalPages - 1;

            FindTrimDefaultOptionsResponse expectedResponse =
                    new FindTrimDefaultOptionsResponse(trimId, false, false, totalElements, totalPages, defaultOptionSummaries);

            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(totalElements);
            given(optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong(), anyInt(), anyInt()))
                    .willReturn(defaultOptionSummaries);

            //when
            FindTrimDefaultOptionsResponse actualResponse = trimService.findPaginatedDefaultOptions(trimId, categoryId, targetPage, pageSize);

            //then
            softAssertions.assertThat(actualResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }

        @Test
        @DisplayName("마지막 페이지의 조회를 검증한다")
        void findLastPage() {
            //given
            int targetPage = totalPages;

            FindTrimDefaultOptionsResponse expectedResponse =
                    new FindTrimDefaultOptionsResponse(trimId, false, true, totalElements, totalPages, defaultOptionSummaries);

            given(trimRepository.findById(anyLong())).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.countDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong())).willReturn(totalElements);
            given(optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(anyLong(), anyLong(), anyInt(), anyInt()))
                    .willReturn(defaultOptionSummaries);

            //when
            FindTrimDefaultOptionsResponse actualResponse = trimService.findPaginatedDefaultOptions(trimId, categoryId, targetPage, pageSize);

            //then
            softAssertions.assertThat(actualResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedResponse);
        }
    }
}