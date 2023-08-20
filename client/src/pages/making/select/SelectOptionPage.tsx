/* eslint-disable jsx-a11y/no-static-element-interactions */
import Button from '@/components/Button';
import { Link, useParams } from 'react-router-dom';
import { OPTION_ORDER } from '../constant';
import EstimationSummary from '@/components/SummaryModal';
import { useState } from 'react';
import { DownArrow } from '@/assets/icons';

interface SelectOptionPageProps {
  path: 'self' | 'guide';
}

interface SelectOptionMessageProps {
  step: number;
}

function SelectOptionPage({ path }: SelectOptionPageProps) {
  const { step } = useParams() as { step: string; id: string };

  return (
    <div
      className={`m-auto max-w-7xl pr-128px flex justify-between items-center w-full  flex-1 gap-60px`}
    >
      <div className="flex w-full">
        <img
          src="https://www.hyundai.com/contents/spec/LX24/gasoline3.8.jpg"
          alt="palisade"
        />
      </div>
      <div className="flex flex-col justify-center w-375px gap-30px">
        <SelectOptionMessage step={Number(step)} />
        <div>
          <div className="flex flex-col items-center gap-12px h-400px">
            <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
              디젤 2.2 box
            </div>
            <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
              디젤 2.2 box
            </div>
          </div>
          <OptionFooter path={path} />
        </div>
      </div>
    </div>
  );
}

function SelectOptionMessage({ step }: SelectOptionMessageProps) {
  return (
    <div className="font-hsans-head text-24px tracking-[-0.96px] mt-70px mb-30px ml-32px">
      <strong className="font-medium">{OPTION_ORDER[step - 1]}</strong>
      <span className="font-normal">을 선택해주세요.</span>
    </div>
  );
}

function OptionFooter({ path }: SelectOptionPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState(false);

  return (
    <>
      <div className="sticky bottom-0 z-10 min-h-120px">
        {/* <div className="relative before:absolute before:bottom-0 before:pointer-events-none before:gradient-from-white-to-transparent before:contents-[''] before:w-full before:h-100px"></div> */}
        <div className="flex justify-between w-full bg-white pt-24px pb-40px px-32px">
          <div className="flex flex-col gap-5px">
            <div
              className="flex gap-5px"
              onClick={() => setIsBottomSheetOpen((value) => !value)}
              onKeyDown={(event) =>
                event.key === 'enter' && setIsBottomSheetOpen(true)
              }
            >
              <span className="body2 text-grey-003">총 견적금액</span>
              <button
                className={`transition bg-grey-001 rounded-6px ${
                  isBottomSheetOpen ? 'rotate-180' : ''
                }`}
              >
                <DownArrow className="fill-grey-003" />
              </button>
            </div>
            <span className="title1 text-grey-black">47,200,000원</span>
          </div>
          <div className="flex items-center gap-21px">
            {currentStep !== 1 && (
              <Link
                to={`/model/${id}/making/${path}/${Number(step) - 1}`}
                className="body2 text-grey-003 "
              >
                이전
              </Link>
            )}
            <Link to={`/model/${id}/making/${path}/${Number(step) + 1}`}>
              <Button size="sm">선택 완료</Button>
            </Link>
          </div>
        </div>
      </div>
      <div className="relative h-0 -z-0">
        <EstimationSummary
          render={isBottomSheetOpen}
          onClose={() => setIsBottomSheetOpen(false)}
        />
      </div>
    </>
  );
}

export default SelectOptionPage;
