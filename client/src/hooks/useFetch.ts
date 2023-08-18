import { get } from '@/service';
import { useState, useEffect } from 'react';

interface FetchType {
  url: string;
  params?: Record<string, string>;
}

function useFetch<T>({ url, params }: FetchType) {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<T>({} as T);

  useEffect(() => {
    (async () => {
      const data = await get<T>({ url, params });
      setData(data as T);
      setLoading(false);
    })();
  }, [url, params]);

  return { loading, data };
}

export default useFetch;
