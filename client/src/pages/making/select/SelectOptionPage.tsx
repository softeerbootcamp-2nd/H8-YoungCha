import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import OptionCard from '@/components/OptionCard';
import { INTERIOR_COLOR_STEP, PROGRESS_LIST } from './constant';
import useFetch from '@/hooks/useFetch.ts';
import { PathParamsType } from '@/types/router';
import SelectOptionMessage from './SelectOptionMessage';
import SelectOptionListContainer from './SelectOptionListContainer';
import SelectOptionFooter from './SelectOptionFooter';
import { AllOptionType } from '@/types/option';
import { UserSelectedOptionDataContext } from '@/pages/making';
import { optionTypeName } from '@/constant.ts';
import getRotateImages from '@/utils/getRotateImages.ts';
import RotateCarImage from '@/components/RotateCarImage';

const EXTERIOR_COLOR_STEP = 5;

function SelectOptionPage() {
  const { step, mode, id } = useParams() as PathParamsType;
  const url = `/car-make/2/${mode}/${PROGRESS_LIST[Number(step) - 1].path}`;

  const { userSelectedOptionData, saveOptionData } = useContext(
    UserSelectedOptionDataContext
  );

  const { data, loading } = useFetch<AllOptionType[]>({
    url,
    params:
      // TODO: 가이드 모드에서 키워드 및 성별, 연령대 데이터 추가
      mode === 'guide'
        ? ({
            keyword1Id: '1',
            keyword2Id: '2',
            keyword3Id: '3',
            age: '2',
            gender: '0',
            exteriorColorId:
              Number(step) === INTERIOR_COLOR_STEP
                ? userSelectedOptionData.colors.options.exteriorColor.id.toString()
                : '0',
          } as Record<string, string>)
        : Number(step) === INTERIOR_COLOR_STEP
        ? ({
            exteriorColorId:
              userSelectedOptionData.colors.options.exteriorColor.id.toString(),
          } as Record<string, string>)
        : undefined,
  });

  // 선택 아이템 인덳스
  const [selectedItem, setSelectedItem] = useState(0);

  function onNext(data: AllOptionType) {
    const newOption = {
      id: data.id,
      name: data.name,
      price: data.price,
      imgUrl: data.images?.[0].imgUrl,
      categoryId: data.categoryId,
      type: optionTypeName[data.categoryId],
    };
    saveOptionData({ newOption });
  }

  useEffect(() => {
    setSelectedItem(0);
  }, [data]);

  return (
    !loading && (
      <main className="relative flex-grow">
        <div className="absolute top-0 bottom-0 grid w-full grid-cols-2 lg:grid-cols-12">
          {/* 이미지 영역 */}
          <div className="lg:col-span-7 flex flex-col justify-center">
            {Number(step) === EXTERIOR_COLOR_STEP ? (
              <RotateCarImage
                images={getRotateImages({
                  url: data?.[selectedItem]?.images[0].imgUrl ?? '',
                  count: 60,
                })}
                className="h-fit"
              />
            ) : (
              <img
                src={data?.[selectedItem]?.images[0].imgUrl ?? ''}
                className="w-full h-full object-cover"
                alt="palisade"
              />
            )}
          </div>
          {/* 옵션 선택 영역 */}
          <div className="flex flex-col max-w-lg lg:col-span-5">
            <SelectOptionMessage step={Number(step)} />
            <SelectOptionListContainer>
              {data
                ?.sort((a, b) => b.rate - a.rate)
                .map((item: AllOptionType, index) => (
                  <OptionCard
                    key={`OptionCard-${item.name}`}
                    isActive={selectedItem === index}
                    onClick={() => {
                      setSelectedItem(index);
                    }}
                    item={item}
                  />
                ))}
            </SelectOptionListContainer>
            <SelectOptionFooter
              {...{ mode, id, step, onNext: () => onNext(data[selectedItem]) }}
            />
          </div>
        </div>
      </main>
    )
  );
}

export default SelectOptionPage;
