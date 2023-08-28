import StepCircle from './StepCircle';
import { TOTAL_STEP } from './constant';

interface StepBoxProps {
  currentStep: number;
}

function StepBox({ currentStep }: StepBoxProps) {
  return (
    <div className="flex gap-16px">
      {Array.from({ length: TOTAL_STEP }, (_, i) => i + 1).map((step) => (
        <StepCircle
          key={step}
          isActive={currentStep === step}
          step={step}
          size="md"
        />
      ))}
    </div>
  );
}

export default StepBox;
