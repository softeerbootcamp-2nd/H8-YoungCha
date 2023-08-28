import { get } from '@/service';
import { useState, useEffect } from 'react';

interface FetchType {
  url: string;
  params?: Record<string, string>;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  deps?: any[];
}

function useFetch<T>({ url, params, deps = [] }: FetchType) {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<T>({} as T);

  useEffect(() => {
    (async () => {
      setLoading(true);
      const data = await get<T>({ url, params });
      setData(data as T);
      setLoading(false);
    })();
  }, [url, ...deps]);

  return { loading, data };
}

export default useFetch;
