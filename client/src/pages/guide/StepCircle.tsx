interface StepCircleProps {
  isActive: boolean;
  step: number;
  size: 'sm' | 'md';
}

function StepCircle({ isActive, step, size }: StepCircleProps) {
  const bgColor = isActive ? 'bg-main-blue' : 'bg-[#DFDFDF]';
  const circleSize = size === 'sm' ? 'w-24px h-24px' : 'w-33px h-33px';
  const fontSize =
    size === 'sm'
      ? 'text-14px tracking-[-0.42px]'
      : 'text-20px tracking-[-0.8px]';
  return (
    <div
      className={`${circleSize} ${bgColor} rounded-full flex justify-center items-center`}
    >
      <span
        className={`font-normal text-white font-hsans-head text-20px leading-28px ${fontSize}`}
      >
        {step}
      </span>
    </div>
  );
}

export default StepCircle;
