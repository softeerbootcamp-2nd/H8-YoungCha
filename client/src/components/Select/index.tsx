interface SelectProps {
  children: React.ReactNode;
  size?: 'default' | 'md' | 'lg';
  type: 'default' | 'active' | 'IconActive';
  onClick?: () => void;
}

const selectSize = {
  default: 'py-6px',
  md: 'w-166px h-60px py-20px',
  lg: 'w-343px h-60px py-20px',
};

const selectColor = {
  default: 'bg-grey-001 text-grey-003',
  active: 'bg-main-blue text-white ',
  IconActive: 'bg-white text-grey-black border-2px border-main-blue',
};

function Select({ children, size = 'default', type, onClick }: SelectProps) {
  return (
    <button
      className={`flex items-center justify-between font-medium  rounded-6px body2 px-20px ${selectColor[type]} ${selectSize[size]}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

export default Select;
