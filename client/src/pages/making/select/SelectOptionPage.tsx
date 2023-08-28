import { useContext, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import {
  SelectOptionMessage,
  SelectOptionListContainer,
  SelectOptionFooter,
} from './';
import { PathParamsType } from '@/types/router';
import { AllOptionType } from '@/types/option';
import { SelectOptionPageProps } from '@/pages/making/select/type.ts';
import { optionTypeName } from '@/constant.ts';
import { UserSelectedOptionDataContext } from '@/pages/making';
import { OptionGroupType } from '../type';
import { useModal } from '@/hooks/useModal';
import OptionCard from '@/components/OptionCard';
import RotateCarImage from '@/components/RotateCarImage';
import getRotateImages from '@/utils/getRotateImages.ts';
import Spinner from '@/components/Spinner';
import Skeleton from '@/components/OptionCard/Skeleton.tsx';
import PowerTrainChangePopUp from './PowerTrainChangePopUp';

const EXTERIOR_COLOR_STEP = 5;
const FEEDBACK_DELAY_TIME = 2000;

function SelectOptionPage({ data, isLoading }: SelectOptionPageProps) {
  const navigate = useNavigate();
  const { step, mode, id } = useParams() as PathParamsType;
  const [next, setNext] = useState(false);
  const { isOpen, openPopUp, closePopUp } = useModal();
  const { userSelectedOptionData, saveOptionData } = useContext(
    UserSelectedOptionDataContext
  );
  // 선택 아이템 인덱스
  const [selectedItem, setSelectedItem] = useState(-1);
  const [isImageLoading, setIsImageLoading] = useState(true);
  function onNext() {
    if (mode === 'guide') {
      return navigate(`/model/${id}/making/${mode}/${Number(step) + 1}`);
    }

    setTimeout(() => {
      navigate(`/model/${id}/making/${mode}/${Number(step) + 1}`);
      setNext(false);
    }, FEEDBACK_DELAY_TIME);
    setNext(true);
  }

  function getSelectedItemIndex() {
    let itemIndex = 0;
    Object.values(userSelectedOptionData).forEach(
      ({ options }: { options: OptionGroupType }) => {
        Object.values(options).forEach(({ id, categoryId }) => {
          if (categoryId === data[0].categoryId) {
            data.forEach((item, index) => {
              if (id === item.id) itemIndex = index;
            });
          }
        });
      }
    );
    return itemIndex;
  }

  useEffect(() => {
    if (!Array.isArray(data)) return;
    const itemIndex = getSelectedItemIndex();
    setSelectedItem(itemIndex);
  }, [data]);

  useEffect(() => {
    if (!Array.isArray(data) || selectedItem === -1) return;
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
    <>
      {selectedItem !== -1 && (
        <main
          className={`relative flex-grow ${next ? 'pointer-events-none' : ''}`}
        >
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
                    <Skeleton mode={mode} />
                    <Skeleton mode={mode} />
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
                          if (
                            step === '1' &&
                            selectedItem === 0 &&
                            index === 1
                          ) {
                            openPopUp();
                          }
                          setSelectedItem(index);
                        }}
                        item={item}
                        next={next}
                      />
                    ))
                )}
              </SelectOptionListContainer>
              <SelectOptionFooter
                {...{ mode, id, step, onNext: () => onNext() }}
              />
            </div>
          </div>
        </main>
      )}
      {isOpen && (
        <PowerTrainChangePopUp
          closePopUp={closePopUp}
          setSelectedItem={setSelectedItem}
        />
      )}
    </>
  );
}

export default SelectOptionPage;
