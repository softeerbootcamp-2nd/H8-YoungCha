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
import { OptionDetailType, AllOptionType } from '@/types/option';
import Rate from '@/components/OptionCard/Rate.tsx';

interface OptionCardProps
  extends Pick<AllOptionType, 'tags'>,
    Pick<OptionDetailType, 'imgUrl'> {
  item: AllOptionType;
  imgUrl: string;
  step: number;
  price: number;
  children?: React.ReactNode;
  isActive?: boolean;
  onClick?: () => void;
}

function OptionCard({
  isActive = false,
  item,
  imgUrl,
  step,
  children,
  onClick,
}: OptionCardProps) {
  const [isExpanded, setIsExpanded] = useState(false);

  const optionCardRef = useRef<HTMLDivElement>(null);

  const { mode } = useParams() as { mode: string };
  const isSelfMode = mode === 'self';

  const totalDivColor = isActive
    ? isSelfMode
      ? 'bg-white border-main-blue'
      : 'bg-white border-sub-blue'
    : 'bg-[#EDF2FA] border-[#EDF2FA] hover:border-grey-003';

  const nameTextColor = isActive ? 'text-grey-black' : 'text-grey-003';

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
    <div
      ref={optionCardRef}
      className={`relative border-2 rounded-6px w-375px p-20px cursor-pointer 
      ${totalDivColor} ${
        isExpanded ? 'max-h-750px' : 'max-h-150px'
      } transition-all ease-in duration-500`}
      onClick={handleIsActive}
      role="none"
    >
      <div className="flex">
        <CheckIcon {...{ isActive, isSelfMode }} />
        <Tags tags={item?.tags} />
      </div>
      <Rate {...{ rate: item.rate, isSelfMode, isActive }} />
      <div className={`${nameTextColor} font-medium text-20px mb-10px`}>
        {item.name}
      </div>
      <ImgSection isActive={isActive} imgUrl={imgUrl} step={step} />
      <div
        className={`${
          isExpanded ? 'max-h-400px opacity-100' : 'max-h-0 opacity-0'
        } transition-all ease-in-out duration-500 origin-top overflow-hidden`}
      >
        <div className="bg-grey-001 w-334 h-1px"></div>
        <div className="my-12px">{children}</div>
      </div>
      <div className="flex justify-between">
        <PriceSection price={item.price} isActive={isActive} />
        {hasDetail && <MoreDetailsButton {...{ isExpanded, setIsExpanded }} />}
      </div>
    </div>
  );
}

export default Object.assign(OptionCard, {
  SummarySection,
  FunctionDetailBox,
  SubOptions,
  SubOptionDescription,
});
