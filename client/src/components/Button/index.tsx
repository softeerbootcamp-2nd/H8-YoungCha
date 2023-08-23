import { ButtonHTMLAttributes } from 'react';

type ColorType = 'main-blue' | 'white' | 'grey';
interface ButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl' | 'full';
  color?: ColorType;
}

const buttonSize = {
  xs: 'w-80px h-32px',
  sm: 'w-113px h-46px',
  md: 'w-140px h-50px',
  lg: 'w-150px h-46px',
  xl: 'w-244px h-57px',
  full: 'w-full h-full',
};

const buttonColor = {
  'main-blue': 'bg-main-blue text-white',
  'white': 'bg-white text-black border border-black',
  'grey': 'bg-grey-001 text-main-blue',
};

function Button({
  children,
  size = 'md',
  color = 'main-blue',
  onClick,
  ...restProps
}: ButtonProps) {
  const className = `flex justify-center items-center rounded-[6px] font-medium body2 cursor-pointer ${buttonSize[size]} ${buttonColor[color]}`;

  return (
    <button className={className} onClick={onClick} {...restProps}>
      {children}
    </button>
  );
}

export default Button;
