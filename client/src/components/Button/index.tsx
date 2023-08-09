type ColorType = 'main-blue' | 'white' | 'grey';
interface ButtonProps {
  children: React.ReactNode;
  title: string;
  size: 'xs' | 'sm' | 'md' | 'lg' | 'xl';
  color: ColorType;
  onClick: () => void;
}

function Button({ children, size, color, onClick }: ButtonProps) {
  const buttonColor = getButtonColor(color);
  const buttonSize = getButtonSize(size);
  const className = `flex justify-center items-center rounded-[6px] ${buttonSize} ${buttonColor}`;

  return (
    <button className={className} onClick={onClick}>
      {children}
    </button>
  );
}

function getButtonSize(size: string) {
  switch (size) {
    case 'xs':
      return 'w-[80px] h-[32px]';
    case 'sm':
      return 'w-[113px] h-[46px]';
    case 'md':
      return 'w-[140px] h-[50px]';
    case 'lg':
      return 'w-[150px] h-[46px]';
    case 'xl':
      return 'w-[244px] h-[57px]';
  }
}

function getButtonColor(color: string) {
  switch (color) {
    case 'main-blue':
      return 'bg-main-blue text-white';
    case 'white':
      return 'bg-white text-black border border-black';
    case 'grey':
      return 'bg-grey-001 text-main-blue';
  }
}

export default Button;
