import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import { createContext, useState } from 'react';
import { ModeType } from './types';

export const ModeContext = createContext<ModeType>(null);
function App() {
  const [mode, setMode] = useState<ModeType>(null);
  return (
    <ModeContext.Provider value={mode}>
      <Header setMode={setMode} />
      <Outlet />
    </ModeContext.Provider>
  );
}

export default App;
