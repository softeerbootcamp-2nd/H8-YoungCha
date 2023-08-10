type ColorType = 'main-blue' | 'white' | 'grey';
interface ButtonProps {
  children: React.ReactNode;
  size?: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
  color?: ColorType;
  onClick?: () => void;
}

const buttonSize = {
  xs: 'w-80px h-32px',
  sm: 'w-113px h-46px',
  md: 'w-140px h-50px',
  lg: 'w-150px h-46px',
  xl: 'w-244px h-57px',
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
}: ButtonProps) {
  const className = `flex justify-center items-center rounded-[6px] ${buttonSize[size]} ${buttonColor[color]}`;

  return (
    <button className={className} onClick={onClick}>
      {children}
    </button>
  );
}

export default Button;