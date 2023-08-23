import {
  ButtonHTMLAttributes,
  Dispatch,
  MouseEvent,
  SetStateAction,
} from 'react';
import * as Icon from '@/assets/icons';
interface MoreDetailsButtonProps
  extends ButtonHTMLAttributes<HTMLButtonElement> {
  isExpanded: boolean;
  setIsExpanded: Dispatch<SetStateAction<boolean>>;
}
function MoreDetailsButton({
  isExpanded,
  setIsExpanded,
}: MoreDetailsButtonProps) {
  function toggleIsExpanded(event: MouseEvent<HTMLButtonElement>) {
    event.stopPropagation();
    setIsExpanded((prevState) => !prevState);
  }

  return (
    <button
      className="relative flex justify-center text-center gap-2px"
      onClick={toggleIsExpanded}
    >
      <span className="font-medium text-grey-003 body3">
        {isExpanded ? '접기' : '자세히 보기'}
      </span>
      <Icon.MoreDetailsArrow
        className={`transition duration-300 transform-gpu ${
          isExpanded ? 'rotate-180' : 'rotate-0'
        }`}
      />
    </button>
  );
}

export default MoreDetailsButton;
