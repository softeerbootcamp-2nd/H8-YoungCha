import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import MoreDetailsButton from './MoreDetailsButton';
import SummarySection from './AdditionalContents/SummarySection';
import FunctionDetailBox from './AdditionalContents/FunctionDetailBox';
import PriceSection from './PriceSection';
import SubOptions from './AdditionalContents/SubOptions';
import SubOptionDescription from './AdditionalContents/SubOptionDescription';
import ImgSection from './ImgSection';
import CheckIcon from './CheckIcon';
import Tags from './Tags';
import { AllOptionType } from '@/types/option';
import Rate from '@/components/OptionCard/Rate.tsx';
import Name from '@/components/OptionCard/Name.tsx';

interface OptionCardProps {
  item: AllOptionType;
  isActive?: boolean;
  onClick?: () => void;
}

function OptionCard({ isActive = false, item, onClick }: OptionCardProps) {
  const [isExpanded, setIsExpanded] = useState(false);

  const optionCardRef = useRef<HTMLButtonElement>(null);

  const { mode, step } = useParams() as { mode: string; step: string };
  const isSelfMode = mode === 'self';

  const totalDivColor = isActive
    ? isSelfMode
      ? 'bg-white border-main-blue'
      : 'bg-white border-sub-blue'
    : 'bg-[#EDF2FA] border-[#EDF2FA] hover:border-grey-003';

  const hasDetail = !!item.details[0]?.description;

  function handleIsActive() {
    // setIsActive((prevState) => !prevState);
    onClick?.();
    if (isActive) setIsExpanded((prevState) => !prevState);
  }

  useEffect(() => {
    if (isActive)
      optionCardRef.current?.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'nearest',
      });
  }, [isActive]);

  return (
    <button
      ref={optionCardRef}
      className={`text-left relative border-2 rounded-6px w-375px p-20px cursor-pointer 
      ${totalDivColor} ${
        isExpanded ? 'max-h-750px' : 'max-h-150px'
      } transition-all ease-in duration-500`}
      onClick={handleIsActive}
    >
      <div className="flex">
        <CheckIcon {...{ isActive, isSelfMode }} />
        <Tags tags={item?.tags} />
      </div>
      <Rate {...{ rate: item.rate, isSelfMode, isActive }} />
      <Name isActive={isActive}>{item.name}</Name>
      <ImgSection
        isActive={isActive}
        imgUrl={item.images[0].imgUrl}
        step={Number(step)}
      />
      <div
        className={`${
          isExpanded ? 'max-h-400px opacity-100' : 'max-h-0 opacity-0'
        } transition-all ease-in-out duration-300 origin-top overflow-hidden`}
      >
        <div className="border-t-2 border-grey-001 py-12px flex flex-col gap-y-12px">
          <SummarySection details={item.details} isActive={isActive} />
          <FunctionDetailBox details={item.details} isActive={isActive} />
        </div>
      </div>
      <div className="flex justify-between">
        <PriceSection price={item.price} isActive={isActive} />
        {hasDetail && <MoreDetailsButton {...{ isExpanded, setIsExpanded }} />}
      </div>
    </button>
  );
}

export default Object.assign(OptionCard, {
  SubOptions,
  SubOptionDescription,
});
