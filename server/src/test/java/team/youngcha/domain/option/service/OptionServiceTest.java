package team.youngcha.domain.option.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import team.youngcha.common.enums.AgeRange;
import team.youngcha.common.enums.Gender;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.category.enums.SelectiveCategory;
import team.youngcha.domain.estimate.repository.EstimateRepository;
import team.youngcha.domain.keyword.dto.KeywordRate;
import team.youngcha.domain.keyword.entity.Keyword;
import team.youngcha.domain.keyword.repository.KeywordRepository;
import team.youngcha.domain.option.dto.*;
import team.youngcha.domain.option.entity.*;
import team.youngcha.domain.option.enums.OptionType;
import team.youngcha.domain.option.repository.OptionDetailRepository;
import team.youngcha.domain.option.repository.OptionImageRepository;
import team.youngcha.domain.option.repository.OptionRepository;
import team.youngcha.domain.sell.repository.SellRepository;
import team.youngcha.domain.trim.entity.Trim;
import team.youngcha.domain.trim.repository.TrimRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {

    @InjectMocks
    OptionService optionService;

    @Mock
    TrimRepository trimRepository;

    @Mock
    SellRepository sellRepository;

    @Mock
    OptionRepository optionRepository;

    @Mock
    KeywordRepository keywordRepository;

    @Mock
    EstimateRepository estimateRepository;

    @Mock
    OptionImageRepository optionImageRepository;

    @Mock
    OptionDetailRepository optionDetailRepository;


    final Long trimId = 1L;

    List<Option> createOptions(int count, Long initId, Long categoryId) {
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Option option = new Option(initId, "name" + initId, Math.toIntExact(initId), "feedback" + initId, categoryId);
            options.add(option);
            initId++;
        }
        return options;
    }

    List<OptionImage> createOptionImages(int count, Long initId, Long optionId) {
        List<OptionImage> optionImages = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            OptionImage optionImage = new OptionImage(initId, "imgUrl" + initId, 0, optionId);
            optionImages.add(optionImage);
            initId++;
        }
        return optionImages;
    }

    List<OptionDetail> createOptionDetails(int count, Long initId, Long optionId) {
        List<OptionDetail> optionDetails = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Spec spec = new Spec(1L, "name", "description", initId);
            OptionDetail optionDetail = new OptionDetail(initId, "name" + initId, "description" + initId, "imgUrl" + initId, optionId, List.of(spec));
            optionDetails.add(optionDetail);
            initId++;
        }
        return optionDetails;
    }

    @Nested
    class FindSelfOptions {
        @Test
        @DisplayName("power train을 self mode로 판매량 내림차순 조회한다.")
        void findPowerTrainSelf() {
            //given
            List<Option> options = createOptions(3, 1L, 1L);
            List<OptionImage> optionImages = createOptionImages(2, 1L, options.get(0).getId());
            optionImages.addAll(createOptionImages(1, 3L, options.get(1).getId()));
            List<OptionDetail> optionDetails = createOptionDetails(1, 1L, options.get(0).getId());
            optionDetails.addAll(createOptionDetails(1, 2L, options.get(1).getId()));
            optionDetails.addAll(createOptionDetails(1, 3L, options.get(2).getId()));

            Map<Long, Long> powerTrainCounts = new HashMap<>();
            powerTrainCounts.put(options.get(1).getId(), 1L);
            powerTrainCounts.put(options.get(2).getId(), 2L);

            given(trimRepository.findById(trimId))
                    .willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.
                    findOptionsByTrimIdAndType(trimId, OptionType.OPTIONAL, SelectiveCategory.POWER_TRAIN))
                    .willReturn(options);
            given(sellRepository.
                    countOptionsByTrimIdAndContainOptionsIds(eq(trimId), anyList(), eq(SelectiveCategory.POWER_TRAIN)))
                    .willReturn(powerTrainCounts);
            given(optionImageRepository.findByContainOptionIds(anyList()))
                    .willReturn(optionImages);
            given(optionDetailRepository.findWithSpecsByContainOptionIds(anyList()))
                    .willReturn(optionDetails);

            //when
            List<FindSelfOptionResponse> powerTrainResponses = optionService.
                    findSelfOptions(trimId, SelectiveCategory.POWER_TRAIN);

            //then
            FindSelfOptionResponse expected1 = new FindSelfOptionResponse(options.get(0), 0,
                    optionImages.subList(0, 2), optionDetails.subList(0, 1));
            FindSelfOptionResponse expected2 = new FindSelfOptionResponse(options.get(1), 33,
                    optionImages.subList(2, 3), optionDetails.subList(1, 2));
            FindSelfOptionResponse expected3 = new FindSelfOptionResponse(options.get(2), 67,
                    List.of(), optionDetails.subList(2, 3));
            assertThat(powerTrainResponses).hasSize(3);
            assertThat(powerTrainResponses).usingRecursiveComparison()
                    .isEqualTo(List.of(expected3, expected2, expected1));
        }

        @Test
        @DisplayName("trimId로 trim을 찾을 수 없으면 예외가 발생한다.")
        void trimNotFound() {
            //given
            given(trimRepository.findById(trimId)).willReturn(Optional.empty());

            //when
            Throwable throwable = catchThrowable(() ->
                    optionService.findSelfOptions(trimId, SelectiveCategory.POWER_TRAIN));

            //then
            assertThat(throwable).isInstanceOf(CustomException.class);
            CustomException customException = (CustomException) throwable;
            assertThat(customException.getMessage()).isEqualTo("존재하지 않는 트림입니다.");
            assertThat(customException.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    class FindSelfInteriorColors {
        @Test
        @DisplayName("내장 색상을 self mode로 판매량 내림차순 조회한다.")
        void findPowerTrainSelf() {
            //given
            Long exteriorColorId = 2L;
            List<Option> options = createOptions(3, 1L, 1L);
            List<OptionImage> optionImages = createOptionImages(2, 1L, options.get(0).getId());
            optionImages.addAll(createOptionImages(1, 3L, options.get(1).getId()));
            List<OptionDetail> optionDetails = createOptionDetails(1, 1L, options.get(0).getId());
            optionDetails.addAll(createOptionDetails(1, 2L, options.get(1).getId()));
            optionDetails.addAll(createOptionDetails(1, 3L, options.get(2).getId()));

            Map<Long, Long> powerTrainCounts = new HashMap<>();
            powerTrainCounts.put(options.get(1).getId(), 1L);
            powerTrainCounts.put(options.get(2).getId(), 2L);

            given(trimRepository.findById(trimId))
                    .willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository
                    .findInteriorColorsByTrimIdAndExteriorColorId(trimId, exteriorColorId))
                    .willReturn(options);
            given(sellRepository
                    .countOptionsByTrimIdAndContainOptionsIds(eq(trimId), anyList(), eq(SelectiveCategory.INTERIOR_COLOR)))
                    .willReturn(powerTrainCounts);
            given(optionImageRepository.findByContainOptionIds(anyList()))
                    .willReturn(optionImages);
            given(optionDetailRepository.findWithSpecsByContainOptionIds(anyList()))
                    .willReturn(optionDetails);

            //when
            List<FindSelfOptionResponse> powerTrainResponses = optionService.
                    findSelfInteriorColors(trimId, exteriorColorId);

            //then
            FindSelfOptionResponse expected1 = new FindSelfOptionResponse(options.get(0), 0,
                    optionImages.subList(0, 2), optionDetails.subList(0, 1));
            FindSelfOptionResponse expected2 = new FindSelfOptionResponse(options.get(1), 33,
                    optionImages.subList(2, 3), optionDetails.subList(1, 2));
            FindSelfOptionResponse expected3 = new FindSelfOptionResponse(options.get(2), 67,
                    List.of(), optionDetails.subList(2, 3));
            assertThat(powerTrainResponses).hasSize(3);
            assertThat(powerTrainResponses).usingRecursiveComparison()
                    .isEqualTo(List.of(expected3, expected2, expected1));
        }
    }

    @Nested
    class FindGuideOptions {
        final Option diesel = Option.builder()
                .id(1L).name("디젤")
                .categoryId(1L).price(1000).
                build();
        final Option gasoline = Option.builder()
                .id(2L).name("가솔린")
                .categoryId(1L).price(2000).
                build();
        final List<Long> preferenceKeywords = List.of(1L, 2L, 3L);

        @Test
        @DisplayName("파워 트레인 조회 후 사용자가 선택한 키워드가 하나의 옵션과 겹치면 해당 옵션을 선택한다.")
        void findPowerTrainOverlapOneKeyword() {
            //given
            List<Long> optionIds = List.of(1L, 2L);
            GuideInfo guideInfo = GuideInfo.builder()
                    .ageRange(AgeRange.AGE_20)
                    .gender(Gender.FEMALE)
                    .keywordIds(preferenceKeywords)
                    .build();
            List<OptionImage> optionImages = createOptionImages(2, 1L, 1L);
            optionImages.addAll(createOptionImages(1, 3L, 2L));

            List<OptionDetail> optionDetails = createOptionDetails(1, 1L, 1L);
            optionDetails.addAll(createOptionDetails(2, 2L, 2L));

            Map<Long, Long> powerTrainCounts = new HashMap<>();
            powerTrainCounts.put(1L, 20L);

            Map<Long, List<Keyword>> groupKeyword = new HashMap<>();
            groupKeyword.put(1L, List.of(new Keyword(1L, "효율"), new Keyword(2L, "안전")));
            groupKeyword.put(2L, List.of(new Keyword(3L, "주행력")));

            mockedTrim(optionIds, optionImages, optionDetails, guideInfo, powerTrainCounts, groupKeyword);
            given(estimateRepository.calculateRate(trimId, 1L, 1L))
                    .willReturn(50);
            given(keywordRepository.findById(1L))
                    .willReturn(Optional.of(new Keyword(1L, "효율")));

            //when
            List<FindGuideOptionResponse> guideOptionResponses = optionService.findGuideOptions(trimId, guideInfo);

            //then
            FindGuideOptionResponse expected1 = FindGuideOptionResponse.builder()
                    .id(1L).checked(true)
                    .rate(100).name("디젤").price(1000)
                    .tags(List.of(new KeywordRate(50, "효율")))
                    .images(List.of(new FindOptionImageResponse(optionImages.get(0)),
                            new FindOptionImageResponse(optionImages.get(1))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(0))))
                    .build();
            FindGuideOptionResponse expected2 = FindGuideOptionResponse.builder()
                    .id(2L).checked(false)
                    .rate(0).name("가솔린").price(2000)
                    .tags(List.of())
                    .images(List.of(new FindOptionImageResponse(optionImages.get(2))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(1)),
                            new FindOptionDetailResponse(optionDetails.get(2))))
                    .build();
            assertThat(guideOptionResponses).usingRecursiveComparison()
                    .isEqualTo(List.of(expected1, expected2));
        }

        @Test
        @DisplayName("첫번째 키워드가 충돌하고 두번째 키워드로 옵션을 선택한다.")
        void findPowerTrainConflictFirstKeyword() {
            //given
            List<Long> optionIds = List.of(1L, 2L);
            GuideInfo guideInfo = GuideInfo.builder()
                    .ageRange(AgeRange.AGE_20)
                    .gender(Gender.FEMALE)
                    .keywordIds(preferenceKeywords)
                    .build();
            List<OptionImage> optionImages = createOptionImages(2, 1L, 1L);
            optionImages.addAll(createOptionImages(1, 3L, 2L));

            List<OptionDetail> optionDetails = createOptionDetails(1, 1L, 1L);
            optionDetails.addAll(createOptionDetails(2, 2L, 2L));

            Map<Long, Long> powerTrainCounts = new HashMap<>();
            powerTrainCounts.put(2L, 20L);

            Map<Long, List<Keyword>> groupKeyword = new HashMap<>();
            groupKeyword.put(1L, List.of(new Keyword(1L, "효율"), new Keyword(3L, "주행력")));
            groupKeyword.put(2L, List.of(new Keyword(2L, "안전"), new Keyword(1L, "효율")));

            int rate = 50;

            mockedTrim(optionIds, optionImages, optionDetails, guideInfo, powerTrainCounts, groupKeyword);
            given(estimateRepository.calculateRate(trimId, 2L, 2L))
                    .willReturn(rate);
            given(keywordRepository.findById(2L))
                    .willReturn(Optional.of(new Keyword(2L, "안전")));

            //when
            List<FindGuideOptionResponse> guideOptionResponses = optionService.findGuideOptions(trimId, guideInfo);

            //then
            FindGuideOptionResponse expected1 = FindGuideOptionResponse.builder()
                    .id(1L).checked(false)
                    .rate(0).name("디젤").price(1000)
                    .tags(List.of())
                    .images(List.of(new FindOptionImageResponse(optionImages.get(0)),
                            new FindOptionImageResponse(optionImages.get(1))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(0))))
                    .build();
            FindGuideOptionResponse expected2 = FindGuideOptionResponse.builder()
                    .id(2L).checked(true)
                    .rate(100).name("가솔린").price(2000)
                    .tags(List.of(new KeywordRate(rate, "안전")))
                    .images(List.of(new FindOptionImageResponse(optionImages.get(2))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(1)),
                            new FindOptionDetailResponse(optionDetails.get(2))))
                    .build();
            assertThat(guideOptionResponses).usingRecursiveComparison()
                    .isEqualTo(List.of(expected2, expected1));
        }

        @Test
        @DisplayName("겹치는 키워드가 없으면 유사 유저가 많이 선택한 옵션을 선택한다.")
        void findPowerTrainChooseBySimilarityUser() {
            //given
            List<Long> optionIds = List.of(1L, 2L);
            GuideInfo guideInfo = GuideInfo.builder()
                    .ageRange(AgeRange.AGE_20)
                    .gender(Gender.FEMALE)
                    .keywordIds(preferenceKeywords)
                    .build();
            List<OptionImage> optionImages = createOptionImages(2, 1L, 1L);
            optionImages.addAll(createOptionImages(1, 3L, 2L));

            List<OptionDetail> optionDetails = createOptionDetails(1, 1L, 1L);
            optionDetails.addAll(createOptionDetails(2, 2L, 2L));

            Map<Long, Long> powerTrainCounts = new HashMap<>();
            powerTrainCounts.put(2L, 20L);

            Map<Long, List<Keyword>> groupKeyword = new HashMap<>();
            groupKeyword.put(1L, List.of(new Keyword(4L, "효율"), new Keyword(5L, "주행력")));
            groupKeyword.put(2L, List.of(new Keyword(6L, "안전"), new Keyword(7L, "효율")));

            mockedTrim(optionIds, optionImages, optionDetails, guideInfo, powerTrainCounts, groupKeyword);

            //when
            List<FindGuideOptionResponse> guideOptionResponses = optionService.findGuideOptions(trimId, guideInfo);

            //then
            FindGuideOptionResponse expected1 = FindGuideOptionResponse.builder()
                    .id(1L).checked(false)
                    .rate(0).name("디젤").price(1000)
                    .tags(List.of())
                    .images(List.of(new FindOptionImageResponse(optionImages.get(0)),
                            new FindOptionImageResponse(optionImages.get(1))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(0))))
                    .build();
            FindGuideOptionResponse expected2 = FindGuideOptionResponse.builder()
                    .id(2L).checked(true)
                    .rate(100).name("가솔린").price(2000)
                    .tags(List.of())
                    .images(List.of(new FindOptionImageResponse(optionImages.get(2))))
                    .details(List.of(new FindOptionDetailResponse(optionDetails.get(1)),
                            new FindOptionDetailResponse(optionDetails.get(2))))
                    .build();
            assertThat(guideOptionResponses).usingRecursiveComparison()
                    .isEqualTo(List.of(expected2, expected1));
        }

        private void mockedTrim(List<Long> optionIds, List<OptionImage> optionImages,
                                List<OptionDetail> optionDetails, GuideInfo guideInfo,
                                Map<Long, Long> powerTrainCounts, Map<Long, List<Keyword>> groupKeyword) {
            given(trimRepository.findById(trimId)).willReturn(Optional.of(mock(Trim.class)));
            given(optionRepository.
                    findOptionsByTrimIdAndType(trimId, OptionType.OPTIONAL, SelectiveCategory.POWER_TRAIN))
                    .willReturn(List.of(diesel, gasoline));
            given(optionImageRepository.findByContainOptionIds(optionIds))
                    .willReturn(optionImages);
            given(optionDetailRepository.findWithSpecsByContainOptionIds(optionIds))
                    .willReturn(optionDetails);
            given(estimateRepository.countPowerTrainsSimilarityUsers(trimId, optionIds, guideInfo))
                    .willReturn(powerTrainCounts);
            given(keywordRepository.findByContainOptionIdsAndGroupKeywords(optionIds))
                    .willReturn(groupKeyword);
        }
    }
}