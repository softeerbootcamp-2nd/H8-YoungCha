import * as Icon from '@/assets/icons';
import { useOptionCardState } from '../../store/useOptionCardContext';

function MoreDetailsButton() {
  const { isExpanded, setIsExpanded } = useOptionCardState();

  function handleIsExpanded(event: React.MouseEvent<HTMLInputElement>) {
    event.stopPropagation();
    setIsExpanded((prevState) => !prevState);
  }
  return (
    <div
      className="relative flex justify-center text-center"
      onClick={handleIsExpanded}
      role="none"
    >
      <span className="font-medium text-grey-003 body3 pr-2px">
        {isExpanded ? '접기' : '자세히 보기'}
      </span>
      <div className="relative top-5px">
        {isExpanded ? (
          <Icon.MoreDetailsArrow transform="rotate(180)" />
        ) : (
          <Icon.MoreDetailsArrow />
        )}
      </div>
    </div>
  );
}

export default MoreDetailsButton;
