import HyundaiLogoButton from './HyundaiLogoButton';
import DictionaryToggleButton from './DictionaryToggleButton';
import ModelChangeButton from './ModelChangeButton';
import { useEffect, useState } from 'react';

const TEXT_MAKING_MY_CAR = '내 차 만들기';

function Header() {
  const [backgroundColor, setBackgroundColor] = useState('');
  useEffect(() => {
    document.addEventListener('scroll', () => {
      if (window.scrollY === 0) setBackgroundColor('');
      if (window.scrollY > 0) setBackgroundColor('bg-grey-001');
    });
    return () => {
      document.removeEventListener('scroll', () => {});
    };
  }, []);
  return (
    <div
      className={`flex justify-between  px-128px py-32px fixed z-100 w-full mx-auto left-0 right-0 ${backgroundColor} duration-500 z-10`}
    >
      <div className="flex text-center">
        <HyundaiLogoButton />
        <div>
          <span>|</span>
        </div>
        <div>
          <span className="font-medium pl-20px title3">
            {TEXT_MAKING_MY_CAR}
          </span>
        </div>
      </div>
      <div className="flex gap-20px">
        <DictionaryToggleButton />
        <div>
          <span>|</span>
        </div>
        <ModelChangeButton />
      </div>
    </div>
  );
}

export default Header;
