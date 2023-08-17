import { useState, useEffect } from 'react';

interface FetchType {
  url: string;
  params?: Record<string, string>;
}

function useFetch<T>({ url, params }: FetchType) {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState<T>({} as T);
  const [error, setError] = useState(null);

  let requestURL = url;
  if (params) {
    requestURL += new URLSearchParams(params).toString();
  }
  function reFetch() {
    fetch(requestURL)
      .then((res) => res.json())
      .then((data) => {
        setData(data.data);
        setLoading(false);
      })
      .catch((error) => {
        setError(error);
        setLoading(false);
      });
  }

  useEffect(() => {
    reFetch();
  }, [url]);

  return { loading, data, error, reFetch };
}

export default useFetch;
