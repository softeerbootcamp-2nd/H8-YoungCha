import * as Icon from '@/assets/icons';
import { useOptionCardContext } from '@/store/useOptionCardContext';

function CheckIcon() {
  const { isActive, isSelfMode } = useOptionCardContext();

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
