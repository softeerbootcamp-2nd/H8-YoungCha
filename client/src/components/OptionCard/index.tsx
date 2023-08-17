import MoreDetailsButton from './MoreDetailsButton';
import SummarySection from './AdditionalContents/SummarySection';
import FunctionDetailBox from './AdditionalContents/FunctionDetailBox';
import PriceSection from './PriceSection';
import SubOptions from './AdditionalContents/SubOptions';
import SubOptionDescription from './AdditionalContents/SubOptionDescription';
import ImgSection from './ImgSection';
import CheckIcon from './CheckIcon';
import { useOptionCardState } from '@/store/useOptionCardContext';
import {
  PowerTrain,
  PowerTrainDetails,
  powerTrainMock,
} from '@/assets/mock/optionMock';
import Tags from './Tags';

interface OptionCardProps
  extends Pick<PowerTrain, 'tags'>,
    Pick<PowerTrainDetails, 'imgUrl'> {
  imgUrl: string;
  step: number;
  children?: React.ReactNode;
}

function OptionCard({ tags, imgUrl, step, children }: OptionCardProps) {
  const { isActive, setIsActive, isExpanded, isSelfMode } =
    useOptionCardState();
  const stepsWithDetail = new Set([1, 2, 3, 4, 7]);

  const totalDivColor = isActive
    ? isSelfMode
      ? 'bg-white border-main-blue'
      : 'bg-white border-sub-blue'
    : 'bg-[#EDF2FA] border-[#EDF2FA] hover:border-grey-003';
  const rateTextColor = isActive
    ? isSelfMode
      ? 'text-main-blue'
      : 'text-sub-blue'
    : 'text-grey-003';
  const nameTextColor = isActive ? 'text-grey-black' : 'text-grey-003';

  function hasDetail(step: number) {
    return stepsWithDetail.has(step);
  }

  function handleIsActive() {
    setIsActive((prevState) => !prevState);
  }

  return (
    <div
      className={`m-20px relative border-2 rounded-6px w-375px p-20px cursor-pointer 
      ${totalDivColor} ${
        isExpanded ? 'max-h-750px' : 'max-h-150px'
      } transition-all ease-in-out duration-500`}
      onClick={handleIsActive}
      role="none"
    >
      <div className="flex">
        <CheckIcon />
        {!isSelfMode && <Tags tags={tags} />}
      </div>
      <div
        className={`${rateTextColor}
         body2 mt-10px mb-4px`}
      >
        {isSelfMode
          ? `구매자의 ${powerTrainMock.rate}%가 선택했어요!`
          : `나와 비슷한 ${powerTrainMock.rate}%가 선택한`}
      </div>
      <div className={`${nameTextColor} font-medium text-20px mb-10px`}>
        {powerTrainMock.name}
      </div>
      <ImgSection imgUrl={imgUrl} step={step} />
      <div
        className={`${
          isExpanded
            ? 'max-h-400px opacity-100'
            : 'max-h-0px overflow-hidden opacity-0'
        } transition-all ease-in-out duration-500`}
      >
        <div className="bg-grey-001 w-334 h-1px"></div>
        <div className="my-12px">{children}</div>
      </div>
      <div className="flex justify-between">
        <PriceSection isActive={isActive} />
        {hasDetail(step) ? <MoreDetailsButton /> : ''}
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
