import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import { createContext, useState } from 'react';
import { DictionaryContextType, ModeType } from './types';

export const ModeContext = createContext<ModeType>('none');
export const DictionaryContext = createContext<DictionaryContextType>({
  dictionaryOn: false,
  setDictionaryOn: () => {},
});

function App() {
  const [mode, setMode] = useState<ModeType>('none');
  const [dictionaryOn, setDictionaryOn] = useState<boolean>(false);

  return (
    <DictionaryContext.Provider value={{ dictionaryOn, setDictionaryOn }}>
      <ModeContext.Provider value={mode}>
        <Header mode={mode} setMode={setMode} />
        <Outlet />
      </ModeContext.Provider>
    </DictionaryContext.Provider>
  );
}

export default App;
