import { get } from '@/service';
import { useState, useEffect } from 'react';

interface FetchType {
  url: string;
  params?: Record<string, string>;
}

function useFetch<T>({ url, params }: FetchType) {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<T>({} as T);

  let requestURL = url;
  if (params) {
    requestURL += new URLSearchParams(params).toString();
  }
  useEffect(() => {
    (async () => {
      const { data } = await get(requestURL);
      setData(data);
      setLoading(false);
    })();
  }, [url, params]);

  return { loading, data };
}

export default useFetch;
