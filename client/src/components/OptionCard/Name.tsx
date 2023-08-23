import { DictionaryContext } from '@/App';
import { PropsWithChildren, useContext } from 'react';
import { Highlighter } from 'react-dictionary';

interface NameProps {
  isActive: boolean;
}

function Name({ children, isActive }: PropsWithChildren<NameProps>) {
  const nameTextColor = isActive ? 'text-grey-black' : 'text-grey-003';
  const { dictionary, dictionaryOn } = useContext(DictionaryContext);

  return (
    <div
      className={`${nameTextColor} font-medium text-20px mb-10px whitespace-wrap break-words`}
    >
      <Highlighter dictionary={dictionary} isActivate={dictionaryOn}>
        {String(children)}
      </Highlighter>
    </div>
  );
}

export default Name;
