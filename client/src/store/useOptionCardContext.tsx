import { useState, createContext, useContext } from 'react';

interface OptionCardState {
  isActive: boolean;
  setIsActive: React.Dispatch<React.SetStateAction<boolean>>;
  isExpanded: boolean;
  setIsExpanded: React.Dispatch<React.SetStateAction<boolean>>;
  isSelfMode: boolean;
  setIsSelfMode: React.Dispatch<React.SetStateAction<boolean>>;
}

const initialOptionCardState: OptionCardState = {
  isActive: false,
  setIsActive: () => {},
  isExpanded: false,
  setIsExpanded: () => {},
  isSelfMode: false,
  setIsSelfMode: () => {},
};

const OptionCardContext = createContext<OptionCardState>(
  initialOptionCardState
);

export function OptionCardProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [isActive, setIsActive] = useState(false);
  const [isExpanded, setIsExpanded] = useState(false);
  const [isSelfMode, setIsSelfMode] = useState(false);

  const contextValue: OptionCardState = {
    isActive,
    setIsActive,
    isExpanded,
    setIsExpanded,
    isSelfMode,
    setIsSelfMode,
  };

  return (
    <OptionCardContext.Provider value={contextValue}>
      {children}
    </OptionCardContext.Provider>
  );
}

export function useOptionCardState() {
  return useContext(OptionCardContext);
}
