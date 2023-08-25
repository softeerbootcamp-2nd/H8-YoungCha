export interface DictionaryType {
  word: string;
  description: string;
  imgUrl?: string | null;
}

export interface DictionaryContextType {
  dictionaryOn: boolean;
  setDictionaryOn: React.Dispatch<React.SetStateAction<boolean>>;
  dictionary: DictionaryType[];
}
