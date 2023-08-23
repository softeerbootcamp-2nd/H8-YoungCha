import useBasicOption from '@/hooks/useBasicOption';
import { useEffect } from 'react';

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
            <p className="font-medium body2">{name}</p>
          </li>
        ))}
    </>
  );
}

export default BasicOptionLists;
