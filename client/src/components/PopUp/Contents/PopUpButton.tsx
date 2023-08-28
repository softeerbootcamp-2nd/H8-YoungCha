import Button from '@/components/Button';
import { HTMLAttributes } from 'react';

interface PopUpButtonProps extends HTMLAttributes<HTMLButtonElement> {
  greyButtonContent: string;
  blueButtonContent: string;
  onClose?: () => void;
}

function PopUpButton({
  greyButtonContent,
  blueButtonContent,
  onClick,
  onClose,
}: PopUpButtonProps) {
  return (
    <div className="flex justify-center gap-8px mt-24px">
      <Button size="lg" color="grey" onClick={onClose}>
        {greyButtonContent}
      </Button>
      <Button size="lg" color="main-blue" onClick={onClick}>
        {blueButtonContent}
      </Button>
    </div>
  );
}

export default PopUpButton;
