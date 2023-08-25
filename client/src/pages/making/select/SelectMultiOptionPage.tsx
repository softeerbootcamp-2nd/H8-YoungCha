import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import OptionCard from '@/components/OptionCard';
import {
  SelectOptionMessage,
  SelectOptionListContainer,
  SelectOptionFooter,
} from './';
import { PathParamsType } from '@/types/router';
import { AllOptionType } from '@/types/option';
import { SelectOptionPageProps } from '@/pages/making/select/type.ts';
import Spinner from '@/components/Spinner';
import Skeleton from '@/components/OptionCard/Skeleton.tsx';

const CATEGORY = ['시스템', '온도관리', '외부장치', '내부장치'];

function SelectMultiOptionPage({ data, isLoading }: SelectOptionPageProps) {
  const { step, mode, id } = useParams() as PathParamsType;

  const [selectedItem, setSelectedItem] = useState<number>(0);
  const [selectedItems, setSelectedItems] = useState<number[]>([]);

  const [category, setCategory] = useState('시스템');
  const [categorizedData, setCategorizedData] = useState(
    {} as { [key: string]: AllOptionType[] }
  );

  function onNext() {
    // data: AllOptionType[]
    // const newOption = data?.filter((item) => selectedItems.includes(item.id));
    //   TODO: 배열 저장
  }

  useEffect(() => {
    if (!Array.isArray(data)) return;
    const newCategorizedData = data?.reduce(
      (acc, cur) => {
        const { categoryId } = cur;
        const categoryName = CATEGORY[categoryId - 7];
        acc[categoryName] = acc[categoryName] ?? [];
        acc[categoryName].push(cur);
        return acc;
      },
      {} as { [key: string]: AllOptionType[] }
    );
    setCategorizedData(newCategorizedData);
  }, [data]);

  return (
    <main className="relative flex-grow">
      <div className="absolute top-0 bottom-0 grid w-full grid-cols-2 lg:grid-cols-12">
        {/* 이미지 영역 */}
        <div className="lg:col-span-7 relative flex flex-col justify-center items-center bg-grey-001">
          {isLoading ? (
            <Spinner />
          ) : (
            <>
              <img
                src={
                  data?.filter((item) => item.id === selectedItem)[0]?.images[0]
                    .imgUrl ?? data?.[0].images[0].imgUrl
                }
                className="object-cover w-full h-full"
                alt="palisade"
              />
              <div className="absolute bottom-0 left-0 right-0 flex justify-center items-center h-80px drop-shadow-lg">
                <span className="body1 text-white">
                  {data?.filter((item) => item.id === selectedItem)[0]?.name ??
                    data?.[0].name}
                </span>
              </div>
            </>
          )}
        </div>
        {/* 옵션 선택 영역 */}
        <div className="flex flex-col max-w-lg lg:col-span-5">
          <SelectOptionMessage step={Number(step)} />
          <div className="flex justify-between px-32px pb-16px gap-12px">
            {isLoading ? (
              <>
                <div className="flex-1 px-8px py-6px rounded-6px body3 bg-grey-002 text-grey-002">
                  {'Loading'}
                </div>
                <div className="flex-1 px-8px py-6px rounded-6px body3 bg-grey-002 text-grey-002">
                  {'Loading'}
                </div>
                <div className="flex-1 px-8px py-6px rounded-6px body3 bg-grey-002 text-grey-002">
                  {'Loading'}
                </div>
                <div className="flex-1 px-8px py-6px rounded-6px body3 bg-grey-002 text-grey-002">
                  {'Loading'}
                </div>
              </>
            ) : (
              Object.keys(categorizedData)?.map((key) => (
                <button
                  className={`flex-1 px-8px py-6px rounded-6px body3 ${
                    key === category
                      ? 'bg-main-blue text-white'
                      : 'bg-grey-002 text-grey-003'
                  }`}
                  key={key}
                  onClick={() => setCategory(key)}
                >
                  {key}
                </button>
              ))
            )}
          </div>
          <SelectOptionListContainer>
            {isLoading ? (
              <>
                <Skeleton />
                <Skeleton />
                <Skeleton />
              </>
            ) : (
              categorizedData[category]?.map((item: AllOptionType) => (
                <OptionCard
                  key={item.name}
                  isActive={selectedItems.indexOf(item.id) >= 0}
                  onClick={() => {
                    setSelectedItem(item.id);
                    setSelectedItems((prevState) => {
                      if (prevState.indexOf(item.id) >= 0) {
                        return prevState.filter((id) => id !== item.id);
                      } else {
                        return [...prevState, item.id];
                      }
                    });
                  }}
                  item={item}
                  multiSelect
                />
              ))
            )}
          </SelectOptionListContainer>
          <SelectOptionFooter {...{ mode, id, step, onNext: () => onNext() }} />
        </div>
      </div>
    </main>
  );
}

export default SelectMultiOptionPage;
