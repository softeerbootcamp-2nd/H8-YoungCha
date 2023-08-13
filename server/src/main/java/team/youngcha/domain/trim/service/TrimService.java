package team.youngcha.domain.trim.service;

import org.springframework.stereotype.Service;
import team.youngcha.domain.car.dto.CarDetailsDto;
import team.youngcha.domain.category.enums.CategoryName;
import team.youngcha.domain.option.dto.ColorOption;
import team.youngcha.domain.option.dto.MainOption;
import team.youngcha.domain.trim.dto.TrimDetail;
import team.youngcha.domain.trim.enums.TrimOptionType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TrimService {

    public List<TrimDetail> extractTrimDetailsFromCarDetailsDtos(List<CarDetailsDto> carDetailsDtos) {
        HashMap<Long, TrimDetail> trimDetailMap = new HashMap<>();

        for (CarDetailsDto dto : carDetailsDtos) {
            TrimDetail trimDetail = trimDetailMap.get(dto.getTrimId());
            if (trimDetail == null) {
                TrimDetail newTrimDetail = createTrimDetail(dto);
                trimDetailMap.put(newTrimDetail.getId(), newTrimDetail);
                trimDetail = newTrimDetail;
            }

            if (dto.getTrimName().equals("Guide Mode")) {
                continue;
            }

            addTrimOptionToTrimDetail(trimDetail, dto);
        }

        return new ArrayList<>(trimDetailMap.values());
    }

    private TrimDetail createTrimDetail(CarDetailsDto dto) {
        return TrimDetail.builder()
                .id(dto.getTrimId())
                .name(dto.getTrimName())
                .backgroundImgUrl(dto.getTrimBackgroundImgUrl())
                .imgUrl(dto.getTrimImgUrl())
                .hashTag(dto.getTrimHashTag())
                .price(dto.getTrimPrice())
                .description(dto.getTrimDescription())
                .build();
    }

    private void addTrimOptionToTrimDetail(TrimDetail trimDetail, CarDetailsDto dto) {
        if (dto.getTrimOptionType().equals(TrimOptionType.MAIN.getValue())) {
            MainOption mainOption = new MainOption(dto.getOptionName(), dto.getOptionImgUrl());
            trimDetail.getMainOptions().add(mainOption);
            return;
        }
        if (dto.getOptionCategoryName().equals(CategoryName.EXTERIOR_COLOR.getValue())) {
            ColorOption exteriorColorOption = new ColorOption(dto.getOptionName(), dto.getOptionImgUrl());
            trimDetail.getExteriorColors().add(exteriorColorOption);
            return;
        }
        if (dto.getOptionCategoryName().equals(CategoryName.INTERIOR_COLOR.getValue())) {
            ColorOption interiorColorOption = new ColorOption(dto.getOptionName(), dto.getOptionImgUrl());
            trimDetail.getInteriorColors().add(interiorColorOption);
        }
    }

}
