import { AgeType, GenderType } from '@/pages/guide/type';
import { useState, createContext, useContext } from 'react';

interface TagSelectType {
  selectedAge: AgeType | null;
  setSelectedAge: React.Dispatch<React.SetStateAction<AgeType | null>>;
  selectedGender: GenderType | null;
  setSelectedGender: React.Dispatch<React.SetStateAction<GenderType | null>>;
  selectedKeyword: string[];
  setselectedKeyword: React.Dispatch<React.SetStateAction<string[]>>;
}

const initialTagState: TagSelectType = {
  selectedAge: null,
  setSelectedAge: () => {},
  selectedGender: null,
  setSelectedGender: () => {},
  selectedKeyword: [],
  setselectedKeyword: () => {},
};

const TagSelectContext = createContext<TagSelectType>(initialTagState);

export function TagSelectProvider({ children }: { children: React.ReactNode }) {
  const [selectedAge, setSelectedAge] = useState<AgeType | null>(null);
  const [selectedGender, setSelectedGender] = useState<GenderType | null>(null);
  const [selectedKeyword, setselectedKeyword] = useState<string[]>([]);

  return (
    <TagSelectContext.Provider
      value={{
        selectedAge,
        setSelectedAge,
        selectedGender,
        setSelectedGender,
        selectedKeyword,
        setselectedKeyword,
      }}
    >
      {children}
    </TagSelectContext.Provider>
  );
}

export function useTagSelectContext() {
  return useContext(TagSelectContext);
}
