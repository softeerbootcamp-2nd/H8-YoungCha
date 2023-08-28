package team.youngcha.domain.trim.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import team.youngcha.common.exception.CustomException;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.car.dto.TrimBackgroundImgUrl;
import team.youngcha.domain.category.enums.RequiredCategory;
import team.youngcha.domain.option.dto.DefaultOptionSummary;
import team.youngcha.domain.option.dto.OptionSummary;
import team.youngcha.domain.option.enums.OptionType;
import team.youngcha.domain.option.repository.OptionRepository;
import team.youngcha.domain.trim.dto.FindTrimDefaultOptionsResponse;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.repository.TrimRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TrimService {

    private final TrimRepository trimRepository;
    private final OptionRepository optionRepository;

    public List<TrimDetail> extractTrimDetails(List<CarDetails> carDetails) {
        HashMap<Long, TrimDetail> trimDetailMap = new HashMap<>();

        for (CarDetails dto : carDetails) {
            if (dto.getTrimName().equals("Guide Mode")) {
                continue;
            }
            TrimDetail trimDetail = trimDetailMap.computeIfAbsent(dto.getTrimId(), id -> createTrimDetail(dto));
            addTrimOptionToTrimDetail(trimDetail, dto);
        }

        return new ArrayList<>(trimDetailMap.values());
    }

    private TrimDetail createTrimDetail(CarDetails dto) {
        boolean best = isBestTrim(dto);

        return TrimDetail.builder()
                .id(dto.getTrimId())
                .name(dto.getTrimName())
                .backgroundImgUrl(new TrimBackgroundImgUrl(dto.getTrimBackgroundImgUrlWeb(), dto.getTrimBackgroundImgUrlAndroid()))
                .imgUrl(dto.getTrimImgUrl())
                .hashTag(dto.getTrimHashTag())
                .best(best)
                .price(dto.getTrimPrice())
                .description(dto.getTrimDescription())
                .build();
    }

    private boolean isBestTrim(CarDetails dto) {
        return dto.getTrimName().equals("Le Blanc (르블랑)");
    }

    private void addTrimOptionToTrimDetail(TrimDetail trimDetail, CarDetails dto) {
        OptionSummary option = new OptionSummary(dto.getOptionName(), dto.getOptionImgUrl());

        if (dto.getTrimOptionType().equals(OptionType.CORE.getType())) {
            trimDetail.getMainOptions().add(option);
            return;
        }
        if (dto.getOptionCategoryName().equals(RequiredCategory.EXTERIOR_COLOR.getName())) {
            trimDetail.getExteriorColors().add(option);
            return;
        }
        if (dto.getOptionCategoryName().equals(RequiredCategory.INTERIOR_COLOR.getName())) {
            trimDetail.getInteriorColors().add(option);
        }
    }

    public FindTrimDefaultOptionsResponse findPaginatedDefaultOptions(Long trimId, Long categoryId, int page, int size) {
        if (size < 1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "페이지 크기 오류", "페이지의 크기가 1보다 작습니다.");
        }

        if (page < 1) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "페이지 번호 오류", "페이지 번호가 1보다 작습니다.");
        }

        if (trimRepository.findById(trimId).isEmpty()) {
            throw new CustomException(HttpStatus.NOT_FOUND, "트림 조회 오류", "존재하지 않는 트림 id 입니다.");
        }

        int totalElements = optionRepository.countDefaultOptionsByTrimIdAndCategoryId(trimId, categoryId);
        if (totalElements == 0) {
            throw new CustomException(HttpStatus.NOT_FOUND, "트림 기본 품목 조회 실패", "트림의 기본 품목이 존재하지 않습니다.");
        }

        int totalPages = (totalElements - 1 + size) / size;
        if (page > totalPages) {
            throw new CustomException(HttpStatus.NOT_FOUND, "페이지 번호 초과 오류", "해당 페이지는 존재하지 않습니다.");
        }

        boolean isFirstPage = page == 1;
        boolean isLastPage = (page == totalPages);

        List<DefaultOptionSummary> paginatedDefaultOptions
                = optionRepository.findPaginatedDefaultOptionsByTrimIdAndCategoryId(trimId, categoryId, page, size);

        if (paginatedDefaultOptions.size() == 0) {
            throw new CustomException(HttpStatus.NOT_FOUND, "페이지 조회 실패", "페이지 조회에 실패했습니다.");
        }

        return FindTrimDefaultOptionsResponse.builder()
                .trimId(trimId)
                .first(isFirstPage)
                .last(isLastPage)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .contents(paginatedDefaultOptions)
                .build();
    }
}
