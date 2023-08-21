import { Outlet } from 'react-router-dom';
import Header from './components/Header';
import { createContext, useState } from 'react';
import { ModeType } from './types';

export const ModeContext = createContext<ModeType>('none');

function App() {
  const [mode, setMode] = useState<ModeType>('none');
  return (
    <ModeContext.Provider value={mode}>
      <Header mode={mode} setMode={setMode} />
      <Outlet />
    </ModeContext.Provider>
  );
}

export default App;
