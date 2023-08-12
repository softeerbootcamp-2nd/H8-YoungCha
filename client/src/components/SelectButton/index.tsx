import { HTMLAttributes, PropsWithChildren } from 'react';

interface SelectButtonProps extends HTMLAttributes<HTMLButtonElement> {
  size?: 'default' | 'md' | 'lg';
  type: 'default' | 'active' | 'iconActive';
  onClick?: () => void;
}

type SelectSize = 'default' | 'md' | 'lg' | 'full';

const selectSize: Record<SelectSize, string> = {
  default: 'py-6px',
  md: 'w-166px h-60px py-20px',
  lg: 'w-343px h-60px py-20px',
  full: 'w-full h-60px py-20px',
};

const selectColor = {
  default: 'bg-grey-001 text-grey-003',
  active: 'bg-main-blue text-white ',
  iconActive: 'bg-white text-grey-black border-2px border-main-blue',
};

function SelectButton({
  children,
  size = 'default',
  type,
  onClick,
  ...restProps
}: PropsWithChildren<SelectButtonProps>) {
  const style = `flex justify-between items-center font-medium  rounded-6px body2 px-20px ${selectColor[type]} ${selectSize[size]}`;
  return (
    <button className={style} onClick={onClick} {...restProps}>
      {children}
    </button>
  );
}

export default SelectButton;
