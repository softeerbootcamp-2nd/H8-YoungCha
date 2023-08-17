import * as Icon from '@/assets/icons';
import { useOptionCardState } from '@/store/useOptionCardContext';

function CheckIcon() {
  const { isActive, isSelfMode } = useOptionCardState();

  return (
    <>
      {isActive && isSelfMode ? (
        <Icon.BlueCheckIcon />
      ) : isActive && !isSelfMode ? (
        <Icon.SubBlueCheckIcon />
      ) : (
        <Icon.GreyCheckIcon />
      )}
    </>
  );
}

export default CheckIcon;
