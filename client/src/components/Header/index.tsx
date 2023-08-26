import { useCallback, useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useModal } from '@/hooks/useModal';
import { ModeType } from '@/types';
import DictionaryToggleButton from './DictionaryToggleButton';
import ModelChangeButton from './ModelChangeButton';
import ModeChangeButton from './ModeChangeButton';
import ExitPopUp from './HeaderPopUp/ExitPopUp';
import * as Icon from '../../assets/icons';

interface HeaderProps {
  mode?: ModeType;
  setMode?: React.Dispatch<React.SetStateAction<ModeType>>;
}

const TEXT_MAKING_MY_CAR = '내 차 만들기';

function Header({ mode = 'none', setMode = () => {} }: HeaderProps) {
  const [backgroundColor, setBackgroundColor] = useState('');
  const { isOpen, openPopUp, closePopUp } = useModal();

  const handleScroll = useCallback(() => {
    if (window.scrollY === 0) setBackgroundColor('');
    else if (window.scrollY > 0) setBackgroundColor('bg-grey-001');
  }, [setBackgroundColor]);

  const { pathname } = useLocation();

  useEffect(() => {
    if (pathname.indexOf('guide') > 0) {
      setMode('guide');
    } else if (pathname.indexOf('self') > 0) {
      setMode('self');
    } else {
      setMode('none');
    }
  }, [pathname]);

  useEffect(() => {
    document.addEventListener('scroll', handleScroll);
    return () => {
      document.removeEventListener('scroll', handleScroll);
    };
  }, []);

  return (
    <header
      className={`${backgroundColor} z-20 transition-colors duration-300 ease-out fixed top-0 w-full h-80px select-none min-w-768px px-32px`}
    >
      <div className="flex items-center justify-between h-full max-w-5xl mx-auto xl:max-w-none xl:px-96px">
        <div className="flex h-24px gap-20px">
          <Icon.HDLogo
            className="w-45px lg:w-166px h-23px"
            onClick={() => {
              if (mode !== 'none') {
                openPopUp();
              }
            }}
          />
          {isOpen && mode !== 'none' && <ExitPopUp closePopUp={closePopUp} />}
          <span>|</span>

          <div className={`flex font-medium title3 gap-8px `}>
            <span className={`${mode === 'guide' ? 'text-sub-blue' : ''}`}>
              {TEXT_MAKING_MY_CAR}
            </span>
            {mode !== 'none' && (
              <>
                <span className={`${mode === 'guide' ? 'text-sub-blue' : ''}`}>
                  -
                </span>
                <ModeChangeButton />
              </>
            )}
          </div>
        </div>
        <div className="flex gap-20px">
          <DictionaryToggleButton />

          <span>|</span>
          <ModelChangeButton />
        </div>
      </div>
    </header>
  );
}

export default Header;
