import { DictionaryContext } from '@/App';
import useBasicOption from '@/hooks/useBasicOption';
import { useContext, useEffect } from 'react';
import { Highlighter } from 'react-dictionary';

interface BasicOptionListsProps {
  id: number;
  currentSize: number;
  categoryId: number;
  setIsLastPage: React.Dispatch<React.SetStateAction<number>>;
}

function BasicOptionLists({
  currentSize,
  id,
  categoryId,
  setIsLastPage,
}: BasicOptionListsProps) {
  const { basicOptions } = useBasicOption({ id, categoryId, currentSize });
  const { dictionary, dictionaryOn } = useContext(DictionaryContext);

  useEffect(() => {
    setIsLastPage((prev) => (basicOptions.last ? prev + 1 : prev));
  }, [basicOptions]);

  return (
    <>
      {Object.keys(basicOptions).length !== 0 &&
        basicOptions.contents.map(({ name, imgUrl }, index) => (
          <li
            className="flex items-center gap-15px"
            key={`basic-option-content-${index}`}
          >
            <img
              src={imgUrl}
              alt={name}
              className="w-80px h-60px rounded-6px"
            />
            <div className="font-medium body2">
              <Highlighter dictionary={dictionary} isActivate={dictionaryOn}>
                {name}
              </Highlighter>
            </div>
          </li>
        ))}
    </>
  );
}

export default BasicOptionLists;
