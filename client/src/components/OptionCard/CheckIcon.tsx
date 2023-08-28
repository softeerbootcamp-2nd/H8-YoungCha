import * as Icon from '@/assets/icons';

interface CheckIconProps {
  isActive: boolean;
  isSelfMode: boolean;
}

function CheckIcon({ isActive, isSelfMode }: CheckIconProps) {
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
