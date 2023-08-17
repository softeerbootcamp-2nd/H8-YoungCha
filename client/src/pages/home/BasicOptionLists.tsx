import { BasicOptionType } from '@/assets/mock/mock';
import useFetch from '@/hooks/useFetch';
import { useEffect, useState } from 'react';

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
  const [params, setParams] = useState({
    url: `${import.meta.env.VITE_API_URL}/trims/2/default-components?`,
    params: {
      categoryId: String(id),
      page: '1',
      size: String(currentSize),
    },
  });
  const { data, loading } = useFetch<BasicOptionType>(params);

  useEffect(() => {
    setIsLastPage((prev) => (data.last ? prev + 1 : prev));
  }, [data]);

  useEffect(() => {
    setParams((prev) => ({
      ...prev,
      params: { ...prev.params, size: String(currentSize) },
    }));
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
