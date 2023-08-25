import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  SelectOptionMessage,
  SelectOptionListContainer,
  SelectOptionFooter,
} from './';
import { UserSelectedOptionDataContext } from '@/pages/making';
import OptionCard from '@/components/OptionCard';
import RotateCarImage from '@/components/RotateCarImage';
import getRotateImages from '@/utils/getRotateImages.ts';
import { PathParamsType } from '@/types/router';
import { AllOptionType } from '@/types/option';
import { SelectOptionPageProps } from '@/pages/making/select/type.ts';
import { optionTypeName } from '@/constant.ts';
import Spinner from '@/components/Spinner';
import Skeleton from '@/components/OptionCard/Skeleton.tsx';

const EXTERIOR_COLOR_STEP = 5;
const FEEDBACK_DELAY_TIME = 2000;

function SelectOptionPage({ data, isLoading }: SelectOptionPageProps) {
  const { step, mode, id } = useParams() as PathParamsType;
  const navigate = useNavigate();
  const [next, setNext] = useState(false);
  const { saveOptionData } = useContext(UserSelectedOptionDataContext);

  // 선택 아이템 인덳스
  const [selectedItem, setSelectedItem] = useState(0);
  const [isImageLoading, setIsImageLoading] = useState(true);

  function onNext() {
    setTimeout(() => {
      navigate(`/model/${id}/making/${mode}/${Number(step) + 1}`);
    }, FEEDBACK_DELAY_TIME);
    setNext(true);
  }

  useEffect(() => {
    setNext(false);
    setSelectedItem(0);
  }, [data]);

  useEffect(() => {
    if (!Array.isArray(data)) return;

    const newOption = {
      id: data[selectedItem].id,
      name: data[selectedItem].name,
      price: data[selectedItem].price,
      imgUrl: data[selectedItem].images?.[0].imgUrl,
      categoryId: data[selectedItem].categoryId,
      type: optionTypeName[data[selectedItem].categoryId],
    };
    saveOptionData({ newOption });
  }, [selectedItem]);

  return (
    <main className={`relative flex-grow ${next ? 'pointer-events-none' : ''}`}>
      <div className="absolute top-0 bottom-0 grid w-full grid-cols-2 lg:grid-cols-12">
        {/* 이미지 영역 */}
        <div className="flex flex-col items-center justify-center lg:col-span-7 bg-grey-001">
          {isLoading ? (
            <Spinner />
          ) : Number(step) === EXTERIOR_COLOR_STEP ? (
            <RotateCarImage
              images={getRotateImages({
                url: data?.[selectedItem]?.images[0].imgUrl ?? '',
                count: 60,
              })}
              className="basis-1/2"
            />
          ) : (
            <>
              {isImageLoading && <Spinner />}
              <img
                src={data?.[selectedItem]?.images[0].imgUrl ?? ''}
                className={`w-full h-full object-cover ${
                  isImageLoading && 'hidden'
                } `}
                onLoad={() => setIsImageLoading(false)}
                alt="palisade"
              />
            </>
          )}
        </div>
        {/* 옵션 선택 영역 */}
        <div className="flex flex-col max-w-lg lg:col-span-5">
          <SelectOptionMessage step={Number(step)} />
          <SelectOptionListContainer>
            {isLoading ? (
              <>
                <Skeleton />
                <Skeleton />
              </>
            ) : (
              Array.isArray(data) &&
              data
                ?.sort((a, b) => b.rate - a.rate)
                .map((item: AllOptionType, index) => (
                  <OptionCard
                    key={`OptionCard-${item.name}`}
                    isActive={selectedItem === index}
                    onClick={() => {
                      setSelectedItem(index);
                    }}
                    item={item}
                    next={next}
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

export default SelectOptionPage;
