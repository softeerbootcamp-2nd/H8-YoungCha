import Button from '@/components/Button';

interface PopUpButtonProps {
  greyButtonContent: string;
  blueButtonContent: string;
}

function PopUpButton({
  greyButtonContent,
  blueButtonContent,
}: PopUpButtonProps) {
  return (
    <div className="flex gap-8px mt-24px">
      <Button size="lg" color="grey">
        {greyButtonContent}
      </Button>
      <Button size="lg" color="main-blue">
        {blueButtonContent}
      </Button>
    </div>
  );
}

export default PopUpButton;
