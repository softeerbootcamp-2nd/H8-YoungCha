import { AllOptionType } from '@/types/option';
import { Highlighter } from 'react-dictionary';
import { useContext } from 'react';
import { DictionaryContext } from '@/App';
interface SummarySectionProps extends Pick<AllOptionType, 'details'> {
  isActive: boolean;
}

function SummarySection({ details, isActive }: SummarySectionProps) {
  const { dictionary, dictionaryOn } = useContext(DictionaryContext);

  return (
    <div
      className={`${
        isActive ? 'text-grey-black' : 'text-grey-003'
      } body3 whitespace-pre-wrap`}
    >
      <Highlighter dictionary={dictionary} isActivate={dictionaryOn}>
        {details[0]?.description}
      </Highlighter>
    </div>
  );
}

export default SummarySection;
