import { useState, useEffect } from 'react';
import { LOADING_DURATION } from '../making/loading/constant';
import Loading from '../making/loading';
import CompleteGuide from './CompleteGuide';
import { keywordId } from './constant';
import { AllGuideOptionType } from '@/types/option';
import { get } from '@/service';
import useSelectOption from '@/hooks/useSelectOption';
import { optionTypeName } from '@/constant';
import { useLocation } from 'react-router-dom';
import { AgeType, GenderType } from './type';

function CompleteGuideWithLoading() {
  const [isLoading, setIsLoading] = useState(true);
  const {
    state: { selectedAge, selectedGender, selectedKeyword },
  }: {
    state: {
      selectedAge: AgeType | null;
      selectedGender: GenderType | null;
      selectedKeyword: string[];
    };
  } = useLocation();

  const trimId = 2;
  const { saveOptionData } = useSelectOption();

  function filterContextOptions(options: AllGuideOptionType[]) {
    const filteredOptions = options.filter((option) => option.checked);

    const newOptions = filteredOptions.map((option) => {
      return {
        name: option.name,
        price: option.price,
        imgUrl: option.images[0].imgUrl,
        categoryId: option.categoryId,
        type: optionTypeName[option.categoryId],
      };
    })[0];

    return newOptions;
  }

  async function getUserGuideOptions() {
    const [age, gender, keyword1Id, keyword2Id, keyword3Id] = [
      keywordId[selectedAge!],
      keywordId[selectedGender!],
      keywordId[selectedKeyword[0] as keyof typeof keywordId],
      keywordId[selectedKeyword[1] as keyof typeof keywordId],
      keywordId[selectedKeyword[2] as keyof typeof keywordId],
    ];
    const exterirColorData = await get<AllGuideOptionType[]>({
      url: `/car-make/${trimId}/guide/exterior-color`,
      params: { age, gender, keyword1Id, keyword2Id, keyword3Id },
    });
    const exteriorColorId = String(
      exterirColorData?.filter((data) => data.checked)[0].id
    );
    const params = {
      age,
      gender,
      keyword1Id,
      keyword2Id,
      keyword3Id,
      exteriorColorId,
    };
    const urls = [
      {
        url: `/car-make/${trimId}/guide/wheel`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/power-train`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/options`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/interior-color`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/driving-system`,
        params,
      },
      {
        url: `/car-make/${trimId}/guide/body-type`,
        params,
      },
    ];

    const options = await Promise.all(
      urls.map(({ url, params }) => get<AllGuideOptionType>({ url, params }))
    );
    return [...options, exterirColorData];
  }

  useEffect(() => {
    const loadingTime = setTimeout(() => {
      setIsLoading(false);
    }, LOADING_DURATION * 10);

    (async () => {
      const userGuideOptions =
        (await getUserGuideOptions()) as AllGuideOptionType[][];

      userGuideOptions.forEach((option) => {
        const filteredOptions = filterContextOptions(option);
        console.log(filteredOptions);
        saveOptionData({ newOption: filteredOptions });
      });

      clearTimeout(loadingTime);
      setIsLoading(false);
    })();
    return () => clearTimeout(loadingTime);
  }, []);

  return (
    <div className="flex-grow h-full">
      {isLoading ? <Loading /> : <CompleteGuide />}
    </div>
  );
}

export default CompleteGuideWithLoading;
