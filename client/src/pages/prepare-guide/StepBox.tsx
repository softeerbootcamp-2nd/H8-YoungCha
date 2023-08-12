interface StepBoxProps {
  currentStep: number;
}
interface StepRoundProps {
  isActive: boolean;
  step: number;
}

const TOTAL_STEP = 3;

function StepBox({ currentStep }: StepBoxProps) {
  return (
    <div className="flex justify-between gap-16px">
      {Array.from({ length: TOTAL_STEP }, (_, i) => i + 1).map((step) => (
        <StepRound key={step} isActive={currentStep === step} step={step} />
      ))}
    </div>
  );
}

function StepRound({ isActive, step }: StepRoundProps) {
  const bgColor = isActive ? 'bg-main-blue' : 'bg-[#DFDFDF]';
  return (
    <div
      className={`w-33px h-33px ${bgColor} rounded-full flex justify-center items-center`}
    >
      <span className="font-normal text-white font-hsans-head text-20px leading-28px tracking-[-0.8px]">
        {step}
      </span>
    </div>
  );
}
export default StepBox;
