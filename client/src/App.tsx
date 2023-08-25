import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import { createContext, useEffect, useState } from 'react';
import { ModeType } from './types';
import { DictionaryContextType, DictionaryType } from './types/dictionary';
import { get } from './service';

export const ModeContext = createContext<ModeType>('none');
export const DictionaryContext = createContext<DictionaryContextType>({
  dictionaryOn: false,
  setDictionaryOn: () => {},
  dictionary: [],
});

function App() {
  const [mode, setMode] = useState<ModeType>('none');
  const [dictionaryOn, setDictionaryOn] = useState<boolean>(false);
  const [dictionary, setDictionary] = useState<DictionaryType[]>([]);
  const url = '/car-make/dictionary';

  useEffect(() => {
    (async () => {
      const data = (await get<DictionaryType[]>({ url })) as DictionaryType[];
      setDictionary(data);
    })();
  }, []);

  return (
    <DictionaryContext.Provider
      value={{ dictionaryOn, setDictionaryOn, dictionary }}
    >
      <ModeContext.Provider value={mode}>
        <Header mode={mode} setMode={setMode} />
        <Outlet />
      </ModeContext.Provider>
    </DictionaryContext.Provider>
  );
}

export default App;
