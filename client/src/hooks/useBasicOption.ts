import { get } from '@/service';
import { BasicOptionType, ContentsType } from '@/types';
import { useEffect, useState } from 'react';

interface UseBaicOptionProps {
  id: number;
  categoryId: number;
  currentSize: number;
}

function useBasicOption({ id, categoryId, currentSize }: UseBaicOptionProps) {
  const OPTION_SIZE = 5;
  const url = `/trims/${id}/default-components`;
  const currentPage = Math.floor((currentSize - 1) / OPTION_SIZE) + 1;

  const [basicOptions, setBasicOptions] = useState<BasicOptionType>(
    {} as BasicOptionType
  );

  const params = {
    categoryId: String(categoryId),
    page: String(currentPage),
    size: String(OPTION_SIZE),
  };

  useEffect(() => {
    if (basicOptions.last) return;
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
        setBasicOptions(() => ({
          ...data,
          contents: newContents,
        }));
      } else {
        setBasicOptions(data);
      }
    })();
  }, [currentSize]);

  useEffect(() => {
    (async () => {
      const data = (await get<BasicOptionType>({
        url,
        params,
      })) as BasicOptionType;

      setBasicOptions(data);
    })();
  }, [categoryId]);

  return { basicOptions };
}

export default useBasicOption;
