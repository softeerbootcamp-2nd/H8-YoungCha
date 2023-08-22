import { HTMLAttributes, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import Button from '@/components/Button';
import OptionCard from '@/components/OptionCard';
import EstimationSummary from '@/components/SummaryModal';
import { DownArrow } from '@/assets/icons';
import { INTERIOR_COLOR_STEP, PROGRESS_LIST } from './constant';
import { OptionType } from '@/types/option';
import { OPTION_ORDER } from '../constant';
import useFetch from '@/hooks/useFetch.ts';

interface SelectOptionPageProps {
  path: 'self' | 'guide';
}

interface SelectOptionMessageProps {
  step: number;
}

function SelectOptionPage({ path }: SelectOptionPageProps) {
  const { step, mode } = useParams() as { step: string; mode: string };
  const url = `/car-make/2/${mode}/${PROGRESS_LIST[Number(step) - 1].path}`;
  const { data, loading } = useFetch<OptionType[]>({
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
          <div className="flex flex-col max-w-md lg:col-span-5">
            <SelectOptionMessage step={Number(step)} />
            <SelectOptionListContainer>
              {data?.map((item: OptionType, index) => (
                <button className="text-left" key={item.name}>
                  <OptionCard
                    isActive={selectedItem === index}
                    onClick={() => {
                      setSelectedItem(index);
                    }}
                    item={item}
                    tags={item.tags ?? []}
                    imgUrl={item.details[0]?.imgUrl ?? ''}
                    price={item.price}
                    step={Number(step)}
                  >
                    <OptionCard.SummarySection
                      details={item.details}
                      isActive={selectedItem === index}
                    />
                    <OptionCard.FunctionDetailBox
                      details={item.details}
                      isActive={selectedItem === index}
                    />
                  </OptionCard>
                </button>
              ))}
            </SelectOptionListContainer>

            <SelectOptionFooter path={path} />
          </div>
        </div>
      </main>
    )
  );
}

function SelectOptionMessage({ step }: SelectOptionMessageProps) {
  return (
    <div className="font-hsans-head text-24px tracking-[-0.96px] pt-64px pb-32px px-32px">
      <span className="font-medium">{OPTION_ORDER[step - 1]}</span>
      <span>을 선택해주세요.</span>
    </div>
  );
}

function SelectOptionListContainer({
  children,
}: HTMLAttributes<HTMLDivElement>) {
  return (
    <div className="relative flex-grow">
      <div className="absolute top-0 bottom-0 w-full overflow-auto">
        <div className="flex flex-col items-center justify-center gap-12px">
          {children}
        </div>
      </div>
    </div>
  );
}

function SelectOptionFooter({ path }: SelectOptionPageProps) {
  const { step, id } = useParams() as { step: string; id: string };
  const currentStep = Number(step);

  const [isEstimationSummaryOpen, setIsEstimationSummaryOpen] = useState(false);

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
              to={`/model/${id}/making/${path}/${Number(step) - 1}`}
              className="body2 text-grey-003"
            >
              이전
            </Link>
          )}
          <Link to={`/model/${id}/making/${path}/${Number(step) + 1}`}>
            <Button size="sm">선택 완료</Button>
          </Link>
        </div>
      </div>
    </>
  );
}

export default SelectOptionPage;
