interface SelectButtonProps {
  children: React.ReactNode;
  size?: 'default' | 'md' | 'lg';
  type: 'default' | 'active' | 'IconActive';
  onClick?: () => void;
}

const selectSize = {
  default: 'py-6px',
  md: 'w-166px h-60px py-20px',
  lg: 'w-343px h-60px py-20px',
  full: 'w-full h-60px py-20px',
};

const selectColor = {
  default: 'bg-grey-001 text-grey-003',
  active: 'bg-main-blue text-white ',
  IconActive: 'bg-white text-grey-black border-2px border-main-blue',
};

function SelectButton({
  children,
  size = 'default',
  type,
  onClick,
}: SelectButtonProps) {
  const style = `flex items-center justify-between font-medium  rounded-6px body2 px-20px ${selectColor[type]} ${selectSize[size]}`;
  return (
    <button className={style} onClick={onClick}>
      {children}
    </button>
  );
}

export default SelectButton;
