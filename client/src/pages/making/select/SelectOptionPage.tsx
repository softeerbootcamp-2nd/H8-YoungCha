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

function SelectOptionPage({ path }: SelectOptionPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  const [isBottomSheetOpen, setIsBottomSheetOpen] = useState(false);

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
        <div className="flex gap-12px">
          <h1 className="font-hsans-head text-24px tracking-[-0.96px]">
            <strong className="font-medium">
              {OPTION_ORDER[currentStep - 1]}
            </strong>
            을 선택해주세요.
          </h1>
        </div>
        <div>
          <div className="flex flex-col items-center gap-12px h-400px">
            <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
              디젤 2.2 box
            </div>
            <div className="flex items-center justify-center border rounded-lg border-main-blue w-375px h-150px">
              디젤 2.2 box
            </div>
          </div>
          <div className="flex flex-col">
            <div className="z-10 flex justify-between bg-whit p-20px">
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
        </div>
      </div>
    </div>
  );
}

export default SelectOptionPage;
