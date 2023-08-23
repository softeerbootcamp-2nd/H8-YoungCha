import { PathParamsType } from '@/types/router';
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
  const { saveOptionData } = useContext(UserSelectedOptionDataContext);

  function handleOnClick() {
    const newOption = {
      name: data.name,
      price: data.price,
      imgUrl: data.images[0].imgUrl,
      categoryId: data.categoryId,
      type: optionTypeName[data.categoryId],
    };

    saveOptionData({ newOption });
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
