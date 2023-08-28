import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import MoreDetailsButton from './MoreDetailsButton';
import SummarySection from './AdditionalContents/SummarySection';
import FunctionDetailBox from './AdditionalContents/FunctionDetailBox';
import PriceSection from './PriceSection';
import SubOptions from './AdditionalContents/SubOptions';
import ImgSection from './ImgSection';
import Tags from './Tags';
import Rate from '@/components/OptionCard/Rate.tsx';
import Name from '@/components/OptionCard/Name.tsx';
import CheckIcon from './CheckIcon';
import { AllOptionType } from '@/types/option';
import { PathParamsType } from '@/types/router.ts';
import FeedbackCard from '@/components/FeedbackCard';

interface OptionCardProps {
  item: AllOptionType;
  isActive?: boolean;
  onClick?: () => void;
  multiSelect?: boolean;
  next?: boolean;
}

function OptionCard({
  isActive = false,
  item,
  onClick,
  multiSelect,
  next,
}: OptionCardProps) {
  const [isExpanded, setIsExpanded] = useState(false);
  const optionCardRef = useRef<HTMLButtonElement>(null);

  const { mode, step } = useParams() as PathParamsType;
  const isSelfMode = mode === 'self';

  const totalDivColor = isActive
    ? isSelfMode
      ? 'bg-white border-main-blue'
      : `bg-white ${item.checked ? 'guide border-grey-002' : 'border-sub-blue'}`
    : 'bg-[#EDF2FA] border-[#EDF2FA] hover:border-grey-003';

  const hasDetail = !!item.details[0]?.description;

  function handleIsActive() {
    onClick?.();
    if (isActive && !multiSelect) setIsExpanded((prevState) => !prevState);
  }

  useEffect(() => {
    if (isActive)
      optionCardRef.current?.scrollIntoView({
        behavior: 'smooth',
        block: 'center',
        inline: 'nearest',
      });
  }, [isActive]);

  useEffect(() => {
    isActive && setIsExpanded(false);
  }, [next]);

  return (
    <button
      ref={optionCardRef}
      className={`text-left relative border-2 rounded-6px min-w-320px w-full p-20px cursor-pointer 
      ${totalDivColor} ${
        isExpanded ? 'max-h-750px' : 'max-h-fit'
      } transition-all ease-in duration-300`}
      onClick={handleIsActive}
    >
      <div className="flex gap-8px">
        <CheckIcon {...{ isActive, isSelfMode }} />
        <Tags tags={item?.tags} />
      </div>
      <Rate {...{ rate: item.rate, isSelfMode, isActive }} />
      <Name isActive={isActive}>{item.name}</Name>
      <ImgSection
        isActive={isActive}
        images={item.images}
        step={Number(step)}
      />
      <div
        className={`${
          isExpanded ? 'max-h-400px opacity-100' : 'max-h-0 opacity-0'
        } transition-all ease-in-out duration-300 origin-top overflow-hidden`}
      >
        {item.details[0]?.description && (
          <div className="flex flex-col border-t-2 border-grey-001 py-12px gap-y-12px">
            {multiSelect ? (
              <>
                <SubOptions options={item?.details} isActive={isActive} />
              </>
            ) : (
              <>
                <SummarySection details={item.details} isActive={isActive} />
                <FunctionDetailBox details={item.details} isActive={isActive} />
              </>
            )}
          </div>
        )}
      </div>
      <div className="flex justify-between">
        <PriceSection price={item.price} isActive={isActive} />
        {hasDetail && <MoreDetailsButton {...{ isExpanded, setIsExpanded }} />}
      </div>
      {mode === 'self' && next && isActive && (
        <FeedbackCard
          feedbackTitle={item.feedbackTitle}
          feedbackDescription={item.feedbackDescription}
        />
      )}
    </button>
  );
}

export default OptionCard;
