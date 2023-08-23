import {
  ButtonHTMLAttributes,
  Dispatch,
  MouseEvent,
  SetStateAction,
  useCallback,
} from 'react';
import * as Icon from '@/assets/icons';
interface MoreDetailsButtonProps extends ButtonHTMLAttributes<HTMLDivElement> {
  isExpanded: boolean;
  setIsExpanded: Dispatch<SetStateAction<boolean>>;
}
function MoreDetailsButton({
  isExpanded,
  setIsExpanded,
}: MoreDetailsButtonProps) {
  const toggleIsExpanded = useCallback((event: MouseEvent<HTMLDivElement>) => {
    event.stopPropagation();
    setIsExpanded((prevState) => !prevState);
  }, []);

  return (
    <div
      className="relative flex justify-center text-center gap-2px"
      onClick={toggleIsExpanded}
      role="none"
    >
      <span className="font-medium text-grey-003 body3">
        {isExpanded ? '접기' : '자세히 보기'}
      </span>
      <Icon.MoreDetailsArrow
        className={`transition duration-300 transform-gpu ${
          isExpanded ? 'rotate-180' : 'rotate-0'
        }`}
      />
    </div>
  );
}

export default MoreDetailsButton;
