interface GetOptionStorageType<T> {
  key: string;
  initalValue?: T;
}
interface SetOptionStorageType<T> {
  key: string;
  value: T;
}
export function getStorage<T>({ key, initalValue }: GetOptionStorageType<T>) {
  return sessionStorage.getItem(key)
    ? JSON.parse(sessionStorage.getItem(key)!)
    : initalValue;
}

export function setStorage<T>({ key, value }: SetOptionStorageType<T>) {
  sessionStorage.setItem(key, JSON.stringify(value));
}
