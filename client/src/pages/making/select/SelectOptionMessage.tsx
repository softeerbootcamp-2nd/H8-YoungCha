import { DictionaryContext } from '@/App';
import { OPTION_ORDER } from '../constant';
import { useContext } from 'react';
import { Highlighter } from 'react-dictionary';

interface SelectOptionMessageProps {
  step: number;
}

function SelectOptionMessage({ step }: SelectOptionMessageProps) {
  const { dictionary, dictionaryOn } = useContext(DictionaryContext);

  return (
    <div className="font-hsans-head text-24px tracking-[-0.96px] pt-64px pb-32px px-32px">
      <span className="flex font-medium ">
        <Highlighter dictionary={dictionary} isActivate={dictionaryOn}>
          {`${OPTION_ORDER[step - 1]}`}
        </Highlighter>
        <span className="font-normal">을 선택해주세요.</span>
      </span>
    </div>
  );
}

export default SelectOptionMessage;
