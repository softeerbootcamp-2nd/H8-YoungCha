import { BasicOptionType } from '@/assets/mock/mock';
import useFetch from '@/hooks/useFetch';
import { useEffect } from 'react';

interface BasicOptionListsProps {
  id: number;
  currentSize: number;
  setIsLastPage: React.Dispatch<React.SetStateAction<number>>;
}
function BasicOptionLists({
  currentSize,
  id,
  setIsLastPage,
}: BasicOptionListsProps) {
  const { data, loading, reFetch } = useFetch<BasicOptionType>({
    url: `${import.meta.env.VITE_API_URL}/trims/2/default-components?`,
    params: {
      categoryId: String(id),
      page: '1',
      size: String(currentSize),
    },
  });

  useEffect(() => {
    setIsLastPage((prev) => (data.last ? prev + 1 : prev));
  }, [data]);

  useEffect(() => {
    if (!loading && data.contents.length !== currentSize) {
      reFetch();
    }
  }, [currentSize]);
  return (
    <>
      {!loading &&
        data.contents.map(({ name, imgUrl }, index) => (
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
