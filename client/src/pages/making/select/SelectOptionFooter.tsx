import { PathParamsType } from '@/types/router';
import { OptionType, UserSelectedOptionDataType } from '../type';
import { useContext, useState } from 'react';
import { UserSelectedOptionDataContext } from '..';
import EstimationSummary from '@/components/SummaryModal';
import { DownArrow } from '@/assets/icons';
import { Link } from 'react-router-dom';
import Button from '@/components/Button';
import { AllOptionType } from '@/types/option';
import { optionTypeName } from '@/constant';

interface SelectOptionFooterProps
  extends Pick<PathParamsType, 'mode' | 'id' | 'step'> {
  data: AllOptionType;
}

function SelectOptionFooter({ mode, id, step, data }: SelectOptionFooterProps) {
  const currentStep = Number(step);

  const [isEstimationSummaryOpen, setIsEstimationSummaryOpen] = useState(false);
  const { setUserSelectedOptionData } = useContext(
    UserSelectedOptionDataContext
  );

  function getNewData({
    newData,
    newOption,
  }: {
    newData: UserSelectedOptionDataType;
    newOption: OptionType;
  }) {
    switch (newOption.categoryId) {
      case 1:
        newData.mainOptions.options.powerTrain = newOption;
        break;
      case 2:
        newData.mainOptions.options.drivingSystem = newOption;
        break;
      case 3:
        newData.mainOptions.options.bodyType = newOption;
        break;
      case 6:
        newData.mainOptions.options.wheel = newOption;
        break;
      case 5:
        newData.colors.options.interiorColor = newOption;
        break;
      case 4:
        newData.colors.options.exteriorColor = newOption;
        break;
      case 7:
        newData.selectedOptions.options.push(newOption);
        break;
      default:
        break;
    }
    return newData;
  }
  function handleOnClick() {
    const {
      name,
      price,
      images: [{ imgUrl }],
      categoryId,
    } = data;

    setUserSelectedOptionData((prev) => {
      const newData = { ...prev };
      const newStorageData = getNewData({
        newData,
        newOption: {
          name,
          price,
          imgUrl,
          categoryId,
          type: optionTypeName[categoryId],
        },
      });
      sessionStorage.setItem('optionData', JSON.stringify(newStorageData));
      return newStorageData;
    });
  }

  return (
    <>
      <EstimationSummary
        render={isEstimationSummaryOpen}
        onClose={() => setIsEstimationSummaryOpen(false)}
      />
      <div className="z-10 flex items-end justify-between w-full bg-white pt-24px pb-40px px-32px">
        <div className="flex flex-col gap-5px">
          <button
            className="flex gap-5px"
            onClick={() => setIsEstimationSummaryOpen((value) => !value)}
          >
            <span className="body2 text-grey-003">총 견적금액</span>
            <span className="bg-grey-001 rounded-4px">
              <DownArrow
                className={`fill-grey-003 transition ${
                  isEstimationSummaryOpen ? 'rotate-180' : ''
                }`}
              />
            </span>
          </button>
          <span className="title1 text-grey-black">47,200,000원</span>
        </div>
        <div className="flex items-center gap-21px">
          {currentStep > 1 && (
            <Link
              to={`/model/${id}/making/${mode}/${Number(step) - 1}`}
              className="body2 text-grey-003"
            >
              이전
            </Link>
          )}
          <Link to={`/model/${id}/making/${mode}/${Number(step) + 1}`}>
            <Button size="sm" onClick={handleOnClick}>
              선택 완료
            </Button>
          </Link>
        </div>
      </div>
    </>
  );
}

export default SelectOptionFooter;
