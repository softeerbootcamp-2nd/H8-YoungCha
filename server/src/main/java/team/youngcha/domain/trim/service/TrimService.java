package team.youngcha.domain.trim.service;

import org.springframework.stereotype.Service;
import team.youngcha.domain.car.dto.CarDetails;
import team.youngcha.domain.category.enums.CategoryName;
import team.youngcha.domain.option.dto.OptionSummary;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.enums.TrimOptionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TrimService {

    public List<TrimDetail> extractTrimDetailsFromCarDetailsDtos(List<CarDetails> carDetails) {
        HashMap<Long, TrimDetail> trimDetailMap = new HashMap<>();

        for (CarDetails dto : carDetails) {
            TrimDetail trimDetail = trimDetailMap.computeIfAbsent(dto.getTrimId(), id -> createTrimDetail(dto));
            if (dto.getTrimName().equals("Guide Mode")) {
                continue;
            }

            addTrimOptionToTrimDetail(trimDetail, dto);
        }

        return new ArrayList<>(trimDetailMap.values());
    }

    private TrimDetail createTrimDetail(CarDetails dto) {
        boolean best = isBestTrim(dto);

        return TrimDetail.builder()
                .id(dto.getTrimId())
                .name(dto.getTrimName())
                .backgroundImgUrl(dto.getTrimBackgroundImgUrl())
                .imgUrl(dto.getTrimImgUrl())
                .hashTag(dto.getTrimHashTag())
                .best(best)
                .price(dto.getTrimPrice())
                .description(dto.getTrimDescription())
                .build();
    }

    private boolean isBestTrim(CarDetails dto) {
        return dto.getCarName().equals("팰리세이드") && dto.getTrimName().equals("Le Blanc (르블랑)");
    }

    private void addTrimOptionToTrimDetail(TrimDetail trimDetail, CarDetails dto) {
        OptionSummary option = new OptionSummary(dto.getOptionName(), dto.getOptionImgUrl());

        if (dto.getTrimOptionType().equals(TrimOptionType.MAIN.getValue())) {
            trimDetail.getMainOptions().add(option);
            return;
        }
        if (dto.getOptionCategoryName().equals(CategoryName.EXTERIOR_COLOR.getValue())) {
            trimDetail.getExteriorColors().add(option);
            return;
        }
        if (dto.getOptionCategoryName().equals(CategoryName.INTERIOR_COLOR.getValue())) {
            trimDetail.getInteriorColors().add(option);
        }
    }

}
