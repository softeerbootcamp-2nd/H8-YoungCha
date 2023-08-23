import { useState } from 'react';
import { useParams } from 'react-router-dom';
import OptionCard from '@/components/OptionCard';
import { INTERIOR_COLOR_STEP, PROGRESS_LIST } from './constant';
import useFetch from '@/hooks/useFetch.ts';
import { PathParamsType } from '@/types/router';
import SelectOptionMessage from './SelectOptionMessage';
import SelectOptionListContainer from './SelectOptionListContainer';
import SelectOptionFooter from './SelectOptionFooter';
import { AllOptionType } from '@/types/option';

function SelectOptionPage() {
  const { step, mode, id } = useParams() as PathParamsType;
  const url = `/car-make/2/${mode}/${PROGRESS_LIST[Number(step) - 1].path}`;
  const { data, loading } = useFetch<AllOptionType[]>({
    url,
    params:
      Number(step) === INTERIOR_COLOR_STEP
        ? {
            exteriorColorId: '13',
          }
        : undefined,
  });
  // 선택 아이템 인덳스
  const [selectedItem, setSelectedItem] = useState(0);
  return (
    !loading && (
      <main className="relative flex-grow">
        <div className="absolute top-0 bottom-0 grid w-full grid-cols-2 lg:grid-cols-12">
          {/* 이미지 영역 */}
          <div className="lg:col-span-7">
            <img
              src={data?.[selectedItem].images[0].imgUrl ?? ''}
              className="object-cover w-full h-full"
              alt="palisade"
            />
          </div>
          {/* 옵션 선택 영역 */}
          <div className="flex flex-col max-w-lg lg:col-span-5">
            <SelectOptionMessage step={Number(step)} />
            <SelectOptionListContainer>
              {data?.map((item: AllOptionType, index) => (
                <OptionCard
                  key={item.name}
                  isActive={selectedItem === index}
                  onClick={() => {
                    setSelectedItem(index);
                  }}
                  item={item}
                />
              ))}
            </SelectOptionListContainer>
            <SelectOptionFooter
              {...{ mode, id, step, data: data[selectedItem] }}
            />
          </div>
        </div>
      </main>
    )
  );
}

export default SelectOptionPage;
