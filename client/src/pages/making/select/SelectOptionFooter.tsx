import { PathParamsType } from '@/types/router';
import { useContext, useState } from 'react';

import { DownArrow } from '@/assets/icons';
import { Link } from 'react-router-dom';
import Button from '@/components/Button';
import SummaryModal from '@/components/SummaryModal';
import { UserSelectedOptionDataContext } from '..';
import getOptionGroupsTotalPrice from '@/utils/getTotalPrice';
import { formatPrice } from '@/utils';

interface SelectOptionFooterProps
  extends Pick<PathParamsType, 'mode' | 'id' | 'step'> {
  onNext: () => void;
}

function SelectOptionFooter({
  mode,
  id,
  step,
  onNext,
}: SelectOptionFooterProps) {
  const currentStep = Number(step);

  const [isSummaryModalOpen, setIsSummaryModalOpen] = useState(false);

  const { userSelectedOptionData } = useContext(UserSelectedOptionDataContext);
  const totalPrice = Object.values(userSelectedOptionData).reduce(
    (sum, { options }) => getOptionGroupsTotalPrice(options) + sum,
    0
  );
  return (
    <>
      <SummaryModal
        render={isSummaryModalOpen}
        onClose={() => setIsSummaryModalOpen(false)}
      />
      <div className="z-10 flex items-end justify-between w-full bg-white pt-24px pb-40px px-32px">
        <div className="flex flex-col gap-5px">
          <button
            className="flex gap-5px"
            onClick={() => setIsSummaryModalOpen((value) => !value)}
          >
            <span className="body2 text-grey-003">총 견적금액</span>
            <span className="bg-grey-001 rounded-4px">
              <DownArrow
                className={`fill-grey-003 transition ${
                  isSummaryModalOpen ? 'rotate-180' : ''
                }`}
              />
            </span>
          </button>
          <span className="title1 text-grey-black">
            {formatPrice(totalPrice)}
          </span>
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
          <Button size="sm" onClick={onNext}>
            선택 완료
          </Button>
        </div>
      </div>
    </>
  );
}

export default SelectOptionFooter;