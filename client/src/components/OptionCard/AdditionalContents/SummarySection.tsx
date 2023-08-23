import { AllOptionType } from '@/types/option';

interface SummarySectionProps extends Pick<AllOptionType, 'details'> {
  isActive: boolean;
}

function SummarySection({ details, isActive }: SummarySectionProps) {
  return (
    <p
      className={`${
        isActive ? 'text-grey-black' : 'text-grey-003'
      } body3 whitespace-pre-wrap`}
    >
      {details[0]?.description}
    </p>
  );
}

export default SummarySection;
