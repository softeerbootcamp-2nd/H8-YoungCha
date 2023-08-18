import { get } from '@/service';
import { BasicOptionType, ContentsType } from '@/types';
import { useEffect, useState } from 'react';

interface BasicOptionListsProps {
  id: number;
  currentSize: number;
  setIsLastPage: React.Dispatch<React.SetStateAction<number>>;
}

const OPTION_SIZE = 5;

function BasicOptionLists({
  currentSize,
  id,
  setIsLastPage,
}: BasicOptionListsProps) {
  const [basicOptions, setBasicOptions] = useState<BasicOptionType>(
    {} as BasicOptionType
  );
  const url = `${import.meta.env.VITE_API_URL}/trims/${id}/default-components?`;
  const currentPage = Math.floor((currentSize - 1) / OPTION_SIZE) + 1;
  const params = {
    categoryId: String(1),
    page: String(currentPage),
    size: String(OPTION_SIZE),
  };
  useEffect(() => {
    setIsLastPage((prev) => (basicOptions.last ? prev + 1 : prev));
  }, [basicOptions]);

  useEffect(() => {
    (async () => {
      const data = (await get<BasicOptionType>({
        url,
        params,
      })) as BasicOptionType;

      if (currentSize > OPTION_SIZE) {
        const newContents: ContentsType[] = [
          ...basicOptions.contents,
          ...data.contents,
        ];
        setBasicOptions((prev) => ({
          ...prev,
          contents: newContents,
        }));
      } else {
        setBasicOptions(data);
      }
    })();
  }, [currentSize]);

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
