interface GetType {
  url: string;
  params?: Record<string, string>;
}
async function get<T>({ url, params }: GetType) {
  try {
    const requestURL =
      url + (params ? new URLSearchParams(params).toString() : '');
    const res = await fetch(requestURL);
    const { data }: { data: T } = await res.json();
    return data;
  } catch (error) {
    console.error(error);
  }
}

export { get };
